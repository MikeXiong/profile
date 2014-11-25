package mx.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by hxiong on 4/24/14.
 */
public class TestSemaphore {

    public static void main(String[] args) {
        final Semaphore sm = new Semaphore(5);
        ExecutorService exec = Executors.newFixedThreadPool(20);
        for(int i = 0; i < 11; i++){
            final int id = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        sm.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] BEGIN!");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] END!");
                    sm.release();
                }
            });
        }
        exec.shutdown();
        System.out.println("Thread[Main] Finished.");
    }
}
