package mx.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by hxiong on 4/24/14.
 */
public class TestCyclicBarrier {



    public static void main(String[] args) {
        final CyclicBarrier barrier = new CyclicBarrier(5);
        for(int i = 0; i < 5; i++){
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + id + "] BEGIN!");

                    try {
                        Thread.sleep(id * 1000);

                        System.out.println("Thread[" + id + "] await!");
                        barrier.await();
                        System.out.println("Thread[" + id + "] await finished!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] END!");
                }
            }).start();
        }
    }

}
