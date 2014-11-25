package mx.pool;

import java.util.concurrent.*;

/**
 * Created by hxiong on 4/14/14.
 */
public class TestJavaExecutor {

    public static void test_newFixedThreadPool(){
        Executor exc = Executors.newFixedThreadPool(5);
//        Executor exc = new ThreadPoolExecutor(5, 5,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(5));
        for(int i = 0; i < 100; i++){
            final int id = i;
            try{
                exc.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Task [" + id + "] Begin !");
                        try {
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Task [" + id + "] Done !");
                    }
                });
            } catch (Exception e){
                System.out.println("Error:" + i);
            }

        }
        System.out.println("*************test_newFixedThreadPool invoke END!*************");

    }

    public static void main(String[] args) {
        test_newFixedThreadPool();
    }
}
