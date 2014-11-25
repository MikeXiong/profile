package mx.thread;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.util.Vector;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 14-8-4.
 */
public class SimpleThreadPool {

    private int defaultSize = 5;

    private int maxSize = 10;

    private int maxIdle = 5;

    private int size = 0;

    private Vector<WorkThread> workThreads = new Vector<WorkThread>(defaultSize);

    private BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);

    private SimpleThreadPool(){
        this.initailize();
    }

    public static SimpleThreadPool getPool(){
        return new SimpleThreadPool();
    }

    private void initailize(){
        for(int i = 0; i < this.defaultSize; i++){
            WorkThread workThread = new WorkThread(i + "");
            this.workThreads.add(workThread);
            workThread.start();
        }
    }

    public void submit(Runnable task) throws InterruptedException {
        this.taskQueue.put(task);
    }

    public void stop(){
        for(int i = 0; i < this.workThreads.size(); i++){
            WorkThread t =  this.workThreads.get(i);
            t.stoped = true;
            if(t.isWaitting){
                t.interrupt();
            }

        }
    }


    class WorkThread extends Thread{

        private String id = null;

        boolean isWaitting = true;

        boolean stoped = false;

        WorkThread(String id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("WorkThread[" + this.id + "] Begin.");
            while(!stoped){
                System.out.println("WorkThread[" + this.id + "] Getting Task.");

                Runnable task = null;
                try {
                    task = taskQueue.take();
                    isWaitting = false;
                    task.run();
                    isWaitting = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println("WorkThread[" + this.id + "] Finished Task.");
            }
        }

//        public void stop(){
//
//        }
    }
}


class Test{
    public static void main(String[] args) {
        Runnable [] tasks = new Runnable[11];
        for(int i = 0; i < tasks.length; i++){
            final int no = i;
            tasks[i] = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + no + "] Started !");
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + no + "] End !");
                }
            };
        }

        SimpleThreadPool pool = SimpleThreadPool.getPool();
        for(Runnable task : tasks){
            try{
                pool.submit(task);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stop");
        pool.stop();

    }
}
