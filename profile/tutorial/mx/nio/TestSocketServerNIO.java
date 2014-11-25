package mx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hxiong on 5/6/14.
 * http://weixiaolu.iteye.com/blog/1479656
 * http://www.open-open.com/lib/view/1343002247880
 */
public class TestSocketServerNIO {

    public static void main(String[] args) {

            new Thread(new Connector()).start();

    }


    static class Connector implements Runnable{

        private int port = 9999;

        private Selector selector = null;

        private ExecutorService pool;

        public Connector(){

            this.pool = Executors.newFixedThreadPool(5);
        }


        @Override
        public void run() {
            try{

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

                serverSocketChannel.configureBlocking(false);

                Selector selector = Selector.open();

                serverSocketChannel.bind(new InetSocketAddress(this.port));

                serverSocketChannel.register(selector,  SelectionKey.OP_ACCEPT);

                ISelectorHandler handler = new SelectorHandler(-1);

                int channelNum = 0;
                int c = 0;
                while(true){
                    channelNum = selector.select();
                    System.out.println("********NEXT Event*********");
                    if(0 < channelNum){
                        final Iterator<SelectionKey> keyItor =  selector.selectedKeys().iterator();
                        while(keyItor.hasNext()){
                            final int id = c++;
                            final SelectionKey key = keyItor.next();
                            keyItor.remove();

                            handler.handle(key);
//                            new SelectorHandler(id).handle(key);
                            //FIXME::asynchronize is not allowed here, it will cause exception.
//                            this.pool.execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try{
//                                        new SelectorHandler(id).handle(key);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                        System.out.println("Server[" + id + "] error: " + e);
//                                    }
//
//                                }
//                            });
                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class SelectorHandler implements ISelectorHandler{

        private String id = null;

        public SelectorHandler(int id){
            this.id = String.valueOf(id);
        }


        @Override
        public void handle(SelectionKey key) throws IOException {
            if(key.isReadable()){
                read(key);
            }
            if(key.isWritable()){
                write(key);
            }
            if(key.isConnectable()){
                connect(key);
            }
            if(key.isAcceptable()){
                accept(key);
            }
        }

        private void write(SelectionKey key) throws IOException {
            // Never close the channel as client need read it.
//            try(SocketChannel sc = (SocketChannel) key.channel();) {
            try{
                SocketChannel sc = (SocketChannel) key.channel();

                sc.write(ByteBuffer.wrap(("Server[" + this.id + "] write data to client.").getBytes()));

            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        private void connect(SelectionKey key) throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
            System.out.println("Server[" + this.id + "][CONNECT] connected");
            //TODO
        }

        private void read(SelectionKey key) throws IOException {
            try{
                System.out.println("Server[" + this.id + "][READ]");

                //FIXME:: Must read data from channel, ortherwise the read event will be fired again and again
                //so this function can't just print some thing. other wise the console is like:
                /*
                    ********NEXT Event*********
                    Server[-1][READ]
                    Server[-1][READ]
                    ********NEXT Event*********
                    Server[-1][READ]
                    Server[-1][READ]
                    ********NEXT Event*********
                    Server[-1][READ]
                    Server[-1][READ]
                    ********NEXT Event*********
                    Server[-1][READ]
                    Server[-1][READ]
                 */
                SocketChannel sc = (SocketChannel) key.channel();
//                sc.configureBlocking(false);//no need set no-blocking as it has been set in previous step
                ByteBuffer buff = ByteBuffer.allocate(1024);
                String s = "";
                sc.read(buff);
                buff.flip();
                while(buff.hasRemaining()){
                    s += (char)buff.get();
                }
                System.out.println("Server[" + this.id + "][READ] read:" + s);

                this.write(key);

            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        private void accept(SelectionKey key) throws IOException {
            try{
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

                SocketChannel sc = ssc.accept();

                System.out.println("Server[" + this.id + "][ACCEPT] accepted");

                sc.configureBlocking(false);

                sc.write(ByteBuffer.wrap(("Server[" + this.id + "] accepting...").getBytes()));

                sc.register(key.selector(), SelectionKey.OP_READ);


            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
}

interface ISelectorHandler{
    void handle(SelectionKey key) throws IOException;
}
