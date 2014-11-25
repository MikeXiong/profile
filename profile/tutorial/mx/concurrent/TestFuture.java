package mx.concurrent;

import java.util.concurrent.*;

/**
 * Created by hxiong on 4/25/14.
 */
public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable c1 = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5 * 1000);
                System.out.println("[Thread-1] END!");
                return "[Thread-1]Doing long time thing 1";
            }
        };
        Callable c2 = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2 * 1000);
                System.out.println("[Thread-2] END!");
                return "[Thread-2]Doing long time thing 2";
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<String> f1 = es.submit(c1);
        Future<String> f2 = es.submit(c2);

        System.out.println("Main Thread...");
        Thread.sleep(1 * 1000);

        String rs0 = f1.get();
        System.out.println("rs1 = " + rs0);
        String rs2 = f2.get();
        System.out.println("rs2 = " + rs2);
        String rs1 = f1.get();
        System.out.println("rs1 = " + rs1);

        es.shutdown();

    }
}
