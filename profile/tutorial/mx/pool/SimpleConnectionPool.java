package mx.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by hxiong on 4/15/14.
 */
public class SimpleConnectionPool<CONN> {

    private final static int default_size = 5;

    private int size = 0;

    private ConnectionFactory<CONN> factory = null;

    private BlockingQueue<CONN> queue = new ArrayBlockingQueue<CONN>(default_size);

//    private BlockingQueue<CONN> usingQueue = new ArrayBlockingQueue<CONN>(default_size);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    private SimpleConnectionPool(){}

    public static synchronized SimpleConnectionPool getDefaultInstance(ConnectionFactory factory){
        SimpleConnectionPool pool = new SimpleConnectionPool();
        pool.factory = factory;
        return pool;
    }


    public CONN borrowConnection(){
        lock.writeLock().lock();
        try {
            if(queue.isEmpty() && default_size > size){
                queue.offer(factory.newConnection());
                size ++ ;
                System.out.println("*** create an new connection, size=" + size);
            }
        } finally {
            lock.writeLock().unlock();
        }
        try {
            CONN conn = queue.take();
            System.out.println("Borrow an connection: " + conn);
            return conn;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnConnection(CONN conn){
        // simple treat all connection is available
        this.queue.offer(conn);
        System.out.println("Return an connection: " + conn);
    }
}

interface ConnectionFactory<CONN> {
    CONN newConnection();
}

class Connection {
    private int id = -1;
    private String name = null;

    public Connection(int id){
        this.id = id;
        System.out.println("Constructor connection: " + id);
    }

    @Override
    public String toString() {
        return "Connection: " + id;
    }
}

class DefaultConnectionFactory implements ConnectionFactory {

    private AtomicInteger ai = new AtomicInteger(0);

    @Override
    public Connection newConnection() {
        return new Connection(ai.incrementAndGet());
    }
}

class TestSimpleConnectionPool {

    public static void main(String[] args) {
        final SimpleConnectionPool<Connection> pool = SimpleConnectionPool.getDefaultInstance(new DefaultConnectionFactory());

        for(int i = 0; i < 10; i++){
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread [" + id + "] started!");
                    Connection conn = pool.borrowConnection();
                    try {
                        System.out.println("Thread [" + id + "] Begin do along time thing with conn: " + conn);
                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread [" + id + "] End do along time thing with conn: " + conn);
                    } finally {
                        pool.returnConnection(conn);
                    }
                    System.out.println("Thread [" + id + "] end!");
                }
            }).start();
        }


        System.out.println("Main Thread finished !");
    }
}

