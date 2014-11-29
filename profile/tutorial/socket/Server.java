package socket;

import org.omg.PortableServer._ServantLocatorStub;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hxiong on 11/27/14.
 */
public class Server {

    final static int PORT   = 8888;

    final static String TAG = "[SERVER]";
    final static String TAG_END = "$$END";


    public static void main(String[] args) {
        startSever();
    }

    static void startSever(){
        try {
            ServerSocketChannel srvChannel = ServerSocketChannel.open();
            srvChannel.configureBlocking(false);

            Refactor refactor   = new Refactor();

            Selector multiplexor    = refactor.getSelector();
            srvChannel.register(multiplexor, SelectionKey.OP_ACCEPT);

            refactor.start();

            srvChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), Server.PORT));

            System.out.println("-----------[MAIN]END--------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void stopServer(){
        //TODO..
    }

}

class Refactor extends Thread{

    private Selector multiplexor = null;

    Refactor() throws IOException {
        this.multiplexor = Selector.open();
    }

    Selector getSelector(){
        return this.multiplexor;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while(0 < (num = this.multiplexor.select())){
                Set<SelectionKey> keys      = this.multiplexor.selectedKeys();
                Iterator<SelectionKey> itor = keys.iterator();
                while(itor.hasNext()){
                    SelectionKey key        = itor.next();
                    itor.remove();
//                    System.out.println("---------------");
                    SocketChannel channel   = null;
                    if(key.isAcceptable()){
                        Util.println(Server.TAG, "accept.");
                        ServerSocketChannel srv = (ServerSocketChannel) key.channel();
                        try {
                            channel     = srv.accept();
                            channel.configureBlocking(false);
                            channel.register(key.selector(), SelectionKey.OP_READ);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else if(key.isReadable()){
                        Util.println(Server.TAG, "read.");
                        ByteBuffer buff = ByteBuffer.allocate(1024);
                        try{
                            channel     = (SocketChannel) key.channel();
                            channel.read(buff);

                        }catch (IOException ex){
                            //if client shut down, this will happened
                            ex.printStackTrace();
                            channel.close();
                            //TODO.. clear Cache...
                            continue;
                        }

//                        String s    = Util.read(buff);
//                        Util.println("s=" + s);
                        HandlerPool.handle(key, buff);

                    }else if(key.isConnectable()){
                        Util.println(Server.TAG, "connect.");

                    }else if(key.isWritable()){
                        Util.println(Server.TAG, "write.");
                    }else{
                        Util.println(Server.TAG, "Unsupported key state=" + key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HandlerPool{
    private final static Executor EXE = Executors.newFixedThreadPool(5);

    private final static Map<SocketChannel, String> cache = new HashMap<>();


    static void handle(final SelectionKey key, ByteBuffer buff){
        ISimpleHandler<String> handler = null;
        if(key.isReadable()){
            handler     = SimpleHandlerFactory.getSimpleReadHanlder();
        }else if(key.isWritable()){
            handler     = SimpleHandlerFactory.getSimpleWriteHanlder();
        }else{
            throw new UnsupportedOperationException("Unsupporte key state:" + key);
        }
        Result<String> result = new Result<>();
        execute(handler, key, buff, result);
    }

    private static void execute(final ISimpleHandler handler, final SelectionKey key,
                                final ByteBuffer buff, final Result result){
        EXE.execute(new Runnable() {
            @Override
            public void run() {
                handler.handle(key, buff, result);
                String s = cache.get(key.channel());
                if(null == s){
                    cache.put((SocketChannel) key.channel(), s = "NULL->");
                }
                Util.println("CACHE: s=" + s + ". channel:" + key.channel());
                cache.put((SocketChannel)key.channel(), s + (String)result.data);
            }
        });
    }

}

interface ISimpleHandler<E>{
    void handle(final SelectionKey key, ByteBuffer buff, Result<E> result);
//    void write(final SelectionKey key, ByteBuffer buff, Result<E> result);
}

class Result<E>{
    E data = null;

    Result(){
    }

    Result(E data){
        this.data = data;
    }
}

final class SimpleHandlerFactory<E>{

    private static AtomicInteger count = new AtomicInteger(1);

    private SimpleHandlerFactory(){
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new UnsupportedOperationException("Singleton, now allowed clone.");
    }

    static ISimpleHandler getSimpleReadHanlder(){
        return new SimpleReadHandler(count.getAndIncrement());
    }

    static ISimpleHandler getSimpleWriteHanlder(){
        return new SimpleWriteHandler(count.getAndIncrement());
    }


   static class SimpleReadHandler<E> implements ISimpleHandler<E>{
       protected int id = -1;

       SimpleReadHandler(int id) {
           this.id = id;
       }

       protected String getTag(){
           return "Handler[" + id + "]";
       }

       @Override
       public void handle(SelectionKey key, ByteBuffer buff, Result result) {
           String s = Util.read(buff);
           Util.println(this.getTag() + ":RCV:" + s);
           result.data = s;
           try {
               SocketChannel channel = (SocketChannel) key.channel();
               Util.write(channel, this.getTag() + "send to client.");
               key.channel().register(key.selector(), SelectionKey.OP_READ);
               if(false){
                   throw new ClosedChannelException();
               }
           } catch (ClosedChannelException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

    static class SimpleWriteHandler<E> extends SimpleReadHandler<E>{

        SimpleWriteHandler(int id) {
            super(id);
        }

        @Override
        public void handle(SelectionKey key, ByteBuffer buff, Result result) {
            Util.println(this.getTag() + ":SND: Do Nothing.");
        }
    }

}


class Util{

    static String println(String tag, String msg){
        return println(tag + "" + msg);
    }

    static String println(String msg){
        System.out.println(msg);
        return msg;
    }

    static void write(SocketChannel channel, String s) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(s.length());
        buff.put(s.getBytes());
        buff.flip();
        channel.write(buff);
    }

    static String read(ByteBuffer buff){
        String s = "";
        buff.flip();
        while(buff.hasRemaining()){
            s += (char)buff.get();
        }
        return s;
    }

    static void sleep(int sec){
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
