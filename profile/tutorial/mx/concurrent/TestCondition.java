package mx.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hxiong on 4/18/14.
 */
public class TestCondition {

    public static void main(String[] args) {
        final ReentrantLock lock    = new ReentrantLock();
        final Condition condition         = lock.newCondition();

        new Thread(new Runnable() {
            int id = 1;
            @Override
            public void run() {
                System.out.println("Thread[" + id + "] Begin.");
                try {
                    lock.lock();
                    System.out.println("Thread[" + id + "] come in Lock. Begin to wait.");
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] come in Lock. End to wait.");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    System.out.println("Thread[" + id + "] release Lock.");
                    lock.unlock();
//                    lock.unlock();
                }
                System.out.println("Thread[" + id + "] End.");
            }
        }).start();

        new Thread(new Runnable() {
            int id = 2;
            @Override
            public void run() {
                System.out.println("Thread[" + id + "] Begin.");
                try{
                    lock.lock();
                    System.out.println("Thread[" + id + "] come in Lock. Begin to Sleep.");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] come in Lock. End to Sleep. Send signal");
                    condition.signalAll();
                } finally {
                    System.out.println("Thread[" + id + "] release Lock.");
                    lock.unlock();
                }
                System.out.println("Thread[" + id + "] End.");
            }
        }).start();
    }
}
