package mx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hxiong on 5/6/14.
 */
public class TestSocketClientNIO {

    private String id = null;

    public TestSocketClientNIO(int id){
        this.id = String.valueOf(id);
    }

    void startClient(){
        try{

            SocketChannel socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            socketChannel.connect(new InetSocketAddress("localhost", 9999));

            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while(true){
                int keys = selector.select();
                if(0 < keys){
                    Iterator<SelectionKey> keyItor = selector.selectedKeys().iterator();
                    while(keyItor.hasNext()){
                        System.out.println("*********Next Event*********");
                        SelectionKey key = keyItor.next();
                        keyItor.remove();

                        SocketChannel sc = (SocketChannel) key.channel();
                        if(key.isReadable()){

                            System.out.println("Client[" + this.id + "][READ] Key Readable.");

                            ByteBuffer buff= ByteBuffer.allocate(1024);
                            String s = "";
                            sc.read(buff);
                            buff.flip();
                            while(buff.hasRemaining()){
                                s += (char)buff.get();
                            }
                            System.out.println("Client[" + this.id + "][READ] read data from server: " + s);

                            sc.write(ByteBuffer.wrap(("Client[" + id + "] write back to server.").getBytes()));

                            System.out.println("Client[" + this.id + "][READ] finished write data to server !" );

                        }else if(key.isWritable()){
                            System.out.println("Client[" + this.id + "][WRITE] Key Writable:");
                            sc.write(ByteBuffer.wrap(("Client[" + id + "] write to server.").getBytes()));
                        }else if(key.isConnectable()){
                            System.out.println("Client[" + this.id + "][CONNECT] Key Connectable.");

                            if(sc.isConnectionPending()){
                                sc.finishConnect();
                            }

                            ByteBuffer buff = ByteBuffer.allocate(1024);
                            String s = "";
                            sc.read(buff);
                            buff.flip();
                            while(buff.hasRemaining()){
                                s += (char)buff.get();
                            }
                            System.out.println("Client[" + this.id + "][CONNECT] connect and read:" + s);

                            sc.configureBlocking(false);

                            sc.write(ByteBuffer.wrap(("Client[" + this.id + "] Connected...").getBytes()));

                            sc.register(key.selector(), SelectionKey.OP_READ);

                        }else if(key.isAcceptable()){
                            System.out.println("Client[" + this.id + "][ACCEPT] Key Acceptable.");
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 2; i++){
            final int id = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    new TestSocketClientNIO(id).startClient();
                }
            });
        }

//        new TestSocketClientNIO(1).startClient();
    }
}
