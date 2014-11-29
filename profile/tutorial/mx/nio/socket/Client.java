package mx.nio.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hxiong on 11/27/14.
 */
public class Client{

    final static String TAG = "[CLIENT]";

    int id = -1;
    public Client(int id) {
        this.id = id;
    }

    String getTag(){
        return TAG + "(" + this.id + ")";
    }

    public static void main(String[] args) {
        startClients();
    }

    static void startClients(){
    	final int THREAD_NUM = 2;
        final ExecutorService EXE = Executors.newFixedThreadPool(THREAD_NUM);
        for(int i = 0; i < THREAD_NUM; i++){
            final int ID = i;
            EXE.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    new Client(ID).startClient();
                    return null;
                }
            });
        }
    }

    void startClient(){
        try {
            SocketChannel clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            clientChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), Server.PORT));

            Selector selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_CONNECT);

            int num = 0;
            while(0 < (num = selector.select())){
                Set<SelectionKey> keys      = selector.selectedKeys();
                Iterator<SelectionKey> itor = keys.iterator();
                while(itor.hasNext()){
                    SelectionKey key        = itor.next();
                    itor.remove();
                    SocketChannel channel   =  (SocketChannel) key.channel();
                    if(key.isAcceptable()){
                        Util.println(this.getTag(), "accept.");
                        //never go here

                    }else if(key.isReadable()){
                        Util.println(this.getTag(), "read.");

                        ByteBuffer buff = ByteBuffer.allocate(1024);
                        channel.read(buff);
                        String s    = Util.read(buff);
                        Util.println("s=" + s);
                        
                        Util.write(channel, this.getTag() + "send bak.");
                        
                        //TODO..	
                    }else if(key.isConnectable()){
                        Util.println(this.getTag(), "connect.");

                        if(channel.isConnectionPending()){
                            channel.finishConnect();
                        }

                        Util.write(channel, this.getTag() + "send to server");

                        channel.register(selector, SelectionKey.OP_READ);

                    }else if(key.isWritable()){
                        Util.println(this.getTag(), "write.");


                    }else{
                        Util.println(this.getTag(), "Unsupported key state=" + key);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
