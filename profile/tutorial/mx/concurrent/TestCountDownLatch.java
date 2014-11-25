package mx.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hxiong on 4/24/14.
 */
public class TestCountDownLatch {


    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5);

        for(int i = 0; i < 5; i++){
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Await-Thread[" + id + "] BEGIN!");

                    try {
                        Thread.sleep(id * 1000);

                        System.out.println("Await-Thread[" + id + "] await!");
                        latch.await();
                        System.out.println("Await-Thread[" + id + "] await finished!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Await-Thread[" + id + "] END!");
                }
            }).start();
        }

        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++){
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Notify-Thread[" + id + "] BEGIN!");

                    try {
                        Thread.sleep(id * 1000);

                        System.out.println("Notify-Thread[" + id + "] count down!");
                        latch.countDown();

                        System.out.println("Notify-Thread[" + id + "] count down finished! Now count is:" + latch.getCount());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Notify-Thread[" + id + "] END!");
                }
            }).start();
        }
    }
}
