package mx.javacore;

/**
 * Created by hxiong on 6/26/14.
 */
public class TestThread {

    public static void main(String[] args) {
        Thread t1 = new Thread1();
        Thread t2 = new Thread2();
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}

class Thread1 extends Thread{

    @Override
    public void run() {
        System.out.println("T1 started");
        System.out.println("T1 continue");
    }
}

class Thread2 extends Thread{

    @Override
    public void run() {
        System.out.println("T2 started");
        System.out.println("T2 continue");
    }
}

