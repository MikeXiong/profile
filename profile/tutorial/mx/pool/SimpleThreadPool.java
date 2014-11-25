package mx.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hxiong on 4/14/14.
 */
public class SimpleThreadPool {

    private static int default_ThreadNum = 5;

    private boolean hasStopped = true;

    private final static SimpleThreadPool ins = new SimpleThreadPool();
    //not special the queue size will not block the execute function as the size been set to Integer.MAX_VALUE
    //if special the queue size the execute function may be blocked
    private BlockingQueue<Runnable> workerQueue = new LinkedBlockingDeque<Runnable>();

    private List<WorkerThread> threads  = new ArrayList<WorkerThread>(default_ThreadNum);

    private SimpleThreadPool(){
        for(int i = 0; i < default_ThreadNum; i++){
            WorkerThread wt = new WorkerThread(i);
            threads.add(wt);
            new Thread(wt).start();
        }
        this.hasStopped = false;
    }


   public static SimpleThreadPool getDefaultInstance(){
       return ins;
   }


    public void execute(Runnable r){
        if(this.hasStopped){
            throw new RuntimeException("The thread pool has stopped!");
        }
        try {
            this.workerQueue.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean hasStopped(){
        return this.hasStopped;
    }

    public void stop(){
       for(WorkerThread t: this.threads){
            t.stop();
       }
       this.workerQueue = new LinkedBlockingDeque<Runnable>();;
       this.threads.clear();
       this.hasStopped = true;
    }


    class WorkerThread implements Runnable {
        private int id = -1;
        private boolean shouldRunning = true;

        public WorkerThread(int id){
            this.id = id;
        }

        @Override
        public void run(){
            System.out.println("Thread[" + this.id + "] begin to Run! " + this.toString());
            while(shouldRunning){
                try {
                    Runnable r = workerQueue.take();
                    System.out.println("Thread[" + this.id + "] TASK:" + r);
                    r.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread[" + this.id + "] end to Run! " + this.toString());
        }


        public void stop(){
            this.shouldRunning = false;
            System.out.println("Thread[" + this.id + "] to STOP! " + this.toString());
            Thread.interrupted();
        }
    }
}



class TestSimpleThreadPool{
    public static void main(String[] args) {
        final SimpleThreadPool pool = SimpleThreadPool.getDefaultInstance();
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3 * 1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                pool.stop();
//            }
//        }).start();

        for(int i = 0; i < 15; i++){
            final int id = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("********* Task [" + id +"] BEGIN !");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("********* Task [" + id +"] END !");
                }

                @Override
                public String toString(){
                    return "Task::" + id;
                }
            });
        }

//        try {
//            Thread.sleep(3 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pool.stop();
        System.out.println("****************main thread finished !****************");
    }
}
