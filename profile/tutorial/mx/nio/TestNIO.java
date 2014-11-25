package mx.nio;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hxiong on 4/29/14.
 * http://www.open-open.com/lib/view/1343002247880
 */
public class TestNIO {
    private final static Class THIS_CLASS = TestNIO.class;

    static void testChannel(){
        URL url = ClassLoader.getSystemClassLoader().getResource("mx/nio/temp.tmp");
        try(RandomAccessFile ra = new RandomAccessFile(new File(url.toURI()), "rw")){
            FileChannel channel = ra.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(1);
            int byteRead    = channel.read(buff);
            while(-1 != byteRead){
                buff.flip();
                while(buff.hasRemaining()){
                    System.out.print((char)buff.get());
                }
                buff.clear();

                byteRead    = channel.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static void testScatter_Gatter(){
        URL rurl = THIS_CLASS.getResource("test_scatter_read.tmp");
        URL wurl = THIS_CLASS.getResource("test_gather_write.tmp");

        try(RandomAccessFile readF = new RandomAccessFile(new File(rurl.toURI()), "r");
            RandomAccessFile writeF = new RandomAccessFile(new File(wurl.toURI()), "rw");

            ){
            ByteBuffer buff1 = ByteBuffer.allocate(5);
            ByteBuffer buff2 = ByteBuffer.allocate(5);
            ByteBuffer[] buffs = {buff1, buff2};

            FileChannel rchannel = readF.getChannel();
            long bread = rchannel.read(buffs);
            System.out.println("test scatter");
            System.out.println("read byte:" + bread);
            System.out.print("read buffer 1: ");
            buff1.flip();
            while(buff1.hasRemaining()){
                System.out.print((char)buff1.get());
            }

            buff1.clear();
            System.out.print(", read buffer 2: ");
            buff2.flip();
            buff2.mark();
            while(buff2.hasRemaining()){
                System.out.print((char)buff2.get());
            }

            buff2.clear();

            System.out.println();
            System.out.println("test gather");
            FileChannel wchannel = writeF.getChannel();
            wchannel.write(buffs);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static void test_transfer(){
        URL rurl = THIS_CLASS.getResource("test_transfer_from.tmp");
        URL wurl = THIS_CLASS.getResource("test_transfer_to.tmp");
        try(RandomAccessFile readF = new RandomAccessFile(new File(rurl.toURI()), "rw");
            RandomAccessFile writeF = new RandomAccessFile(new File(wurl.toURI()), "rw");
        ){
            FileChannel from = readF.getChannel();
            FileChannel to = writeF.getChannel();
            from.transferFrom(to, 9, 10);
            to.transferTo(0, 9, from);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * FileChannel is not support no-blocking mode, so Selector can not used for it
     * DatagramChannel, ServerSocketChannel,SocketChannel can work fine through
     *
     * @param nioChannel
     */
    static void test_selector(SocketChannel nioChannel){
        try (Selector selector = Selector.open()) {

            //Selector can only used in no blocking model
            nioChannel.configureBlocking(false);
            nioChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE
                | SelectionKey.OP_ACCEPT | SelectionKey.OP_CONNECT);

            while(true){
                int readCounts = selector.select();
                if(1 > readCounts)continue;

                Iterator<SelectionKey> selectionKeys =  selector.selectedKeys().iterator();
                while(selectionKeys.hasNext()){
                    SelectionKey key = selectionKeys.next();
                    if(key.isAcceptable()){
                        // a connection was accepted by a ServerSocketChannel.

                    }else if(key.isConnectable()){
                        // a connection was established with a remote server.

                    }else if(key.isReadable()){
                        // a channel is ready for reading

                    }else if(key.isWritable()){
                        // a channel is ready for writing

                    }
                    selectionKeys.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void test_filechannel(){
        URL rurl = THIS_CLASS.getResource("test_filechannel_read.tmp");
        URL wurl = THIS_CLASS.getResource("test_filechannel_write.tmp");
        try(FileChannel readChannel = new FileInputStream(new File(rurl.toURI())).getChannel();
            FileChannel writeChannel = new FileOutputStream(new File(wurl.toURI())).getChannel();
            ){
            ByteBuffer buff = ByteBuffer.allocate(2);

            int count = readChannel.read(buff);
            while(-1 != count){
                buff.flip();
                while(buff.hasRemaining()){
                    System.out.print((char)buff.get());
                }
                buff.clear();
                count = readChannel.read(buff);
            }

            buff.rewind();
            String s = "I will written with NIO FileChannel";
            byte [] content = s.getBytes();
            int pos = 0;
            int offset = buff.limit();
            while(pos < content.length){
                buff.clear();
                offset = offset > content.length - pos ? content.length - pos: offset;
                buff.put(content, pos, offset);
                buff.flip();
                while(buff.hasRemaining()){
                    writeChannel.write(buff);
                }
                pos += offset;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * http://blog.csdn.net/fcbayernmunchen/article/details/8635427
     */
    static void test_MappedByteBuffer(){
        URL rurl = THIS_CLASS.getResource("test_mmap_read.tmp");
        URL wurl = THIS_CLASS.getResource("test_mmap_write.tmp");
        try(FileChannel readChannel = new FileInputStream(new File(rurl.toURI())).getChannel();
            FileChannel writeChannel = new RandomAccessFile(new File(wurl.toURI()), "rw").getChannel();
        ){
            MappedByteBuffer rbuff = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());
            MappedByteBuffer wbuff = writeChannel.map(FileChannel.MapMode.READ_WRITE, 0, readChannel.size());

            while(rbuff.hasRemaining()){
                wbuff.put(rbuff.get());
            }

            wbuff.force();


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        testChannel();
//        testScatter_Gatter();
//        test_transfer();
//        test_filechannel();
        test_MappedByteBuffer();
    }
}

/**
 * blocking mode
 */
class TestSocketChannelClient{
    private String id;
    public TestSocketChannelClient(int id){
        this.id = String.valueOf(id);
    }

    void startClient(){
       final char EOF = '%';
       try(SocketChannel channel = SocketChannel.open();){
           channel.connect(new InetSocketAddress("localhost", 9999));
//           channel.configureBlocking(true);
           String s = "Hello Sever, I am client: " + this.id;
//           s = "XX";
           s += EOF;
           byte [] content = s.getBytes();
           ByteBuffer  buff = ByteBuffer.allocate(1024);
           int pos = 0;
           int offset = buff.limit();
           while(pos < content.length){
               buff.clear();
               offset = offset > content.length - pos ? content.length - pos : offset;
               buff.put(content, pos, offset);
               pos += offset;
               buff.flip();
               while(buff.hasRemaining()){
                   System.out.println("Write buff:" + buff);
                   channel.write(buff);
               }
           }

           System.out.println("Thread[" + this.id + "]***Begin reading***");
           buff.clear();
           channel.read(buff);

           buff.flip();
           String str  = "";
           while(buff.hasRemaining()){
//               System.out.print((char) buff.get());
               str += (char)buff.get();
           }
           System.out.println(str);
           buff.clear();
       } catch (IOException e) {
           e.printStackTrace();
       }

    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 1; i++){
            final int id = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    new TestSocketChannelClient(id).startClient();
                }
            });
        }
        service.shutdown();
    }
}

/**
 * blocking mode
 */
class TestSocketServerChannel{

    void startServer(){
        try(ServerSocketChannel channel = ServerSocketChannel.open();
            final Selector selector = Selector.open();
        ){
            channel.bind(new InetSocketAddress("localhost", 9999));
//            channel.configureBlocking(false);

            ExecutorService service = Executors.newFixedThreadPool(5);

            while(true){
                final SocketChannel sc = channel.accept();
//                sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                service.execute(new Runnable() {
                    final char EOF = '%';
                    @Override
                    public void run(){
                        try {

                            ByteBuffer buff = ByteBuffer.allocate(1);
                            int readCount = sc.read(buff);
                            String str = "";
                            while (-1 != readCount) {
                                buff.flip();
                                char c = EOF;
                                while (buff.hasRemaining()) {
//                                    System.out.print((char) buff.get());
                                    str += (c = (char)buff.get());
                                }

                                buff.clear();
                                //unlike FileChannel, socket channel will block here even it's the last word this time
                                if(EOF == c)break;
                                readCount = sc.read(buff);
                            }
                            System.out.println(str);

//                            buff.put(("I' m returned from Server Thread :" + Thread.currentThread()).getBytes());
                            buff.flip();
//                            while(buff.hasRemaining()){
                                System.out.println("***Begin Write to Client***");
                                sc.write(ByteBuffer.wrap("I' m returned from Server Thread :".getBytes()));
//                                sc.write(buff);
//                            }
                        } catch (Throwable ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                sc.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new TestSocketServerChannel().startServer();
    }
}