package mx.concurrent;

import java.util.concurrent.*;

/**
 * Created by hxiong on 4/25/14.
 */
public class TestCompleteService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService s = Executors.newFixedThreadPool(5);
        ExecutorCompletionService ecs = new ExecutorCompletionService(s);

        for(int i = 0; i < 5; i++){
            final int id = i;
            Callable task = new Callable() {
                @Override
                public String call() throws Exception {
                    System.out.println("Thread[" + id + "] BEGIN!");
                    Thread.sleep((6 - id) * 1000);
                    System.out.println("Thread[" + id + "] END!");
                    return "Thread[" + id + "] Working Done!";
                }
            };
            ecs.submit(task);
        }
        Thread.sleep(1 * 1000);

        for(int i = 0; i < 6; i++){
            System.out.println("----->");
            Future f = ecs.take();//blocking until it get result
            System.out.println("-----");
            String rs = (String) f.get();
            System.out.println("Get rs:" + rs);
        }

        System.out.println("Thread[Main] Finished.");

        s.shutdown();
    }
}
