package mx.concurrent;

import java.lang.ref.PhantomReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Created by hxiong on 5/5/14.
 */
public class TestPhaser {

    /**if COUNT  = 4; p = 2; below exception may throw
     * Exception in thread "pool-1-thread-1" java.lang.IllegalStateException: Attempted arrival of unregistered party for java.util.concurrent.Phaser@5efb5ae[phase = 0 parties = 2 arrived = 2]
     at java.util.concurrent.Phaser.arriveAndAwaitAdvance(Phaser.java:686)
     at mx.concurrent.TestPhaser$1.run(TestPhaser.java:31)
     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
     at java.lang.Thread.run(Thread.java:745)
     */
    static void sim_CountDownLatch(){

        final int COUNT = 5;
        final Phaser p = new Phaser(COUNT);
        ExecutorService es = Executors.newFixedThreadPool(COUNT);
        for(int i = 0; i < COUNT; i++){
            final int id = i;

            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + id + "] BEGIN! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] wait! p.getPhase=" + p.getPhase() + ", p=" + p);
                    //this function is not a atomic operator, it only gurantee thread will continue working
                    //when satisfy some condition
                    p.arriveAndAwaitAdvance();
                    System.out.println("Thread[" + id + "] END! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        es.shutdown();
    }

    static void sim_Condition(){
        final int COUNT = 5;
        final Phaser p = new Phaser(1);
        ExecutorService es = Executors.newFixedThreadPool(COUNT);
        for(int i = 0; i < COUNT; i++){
            final int id = i;
            p.register();
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + id + "] BEGIN! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] wait! p.getPhase=" + p.getPhase() + ", p=" + p);
                    //this function is not a atomic operator, it only gurantee thread will continue working
                    //when satisfy some condition
                    p.arriveAndAwaitAdvance();
                    System.out.println("Thread[" + id + "] END! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        es.shutdown();

        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread[MAIN]: unregister phaser! p=" + p);
        //this is un-blocking arrive() is the same
        p.arriveAndDeregister();

        System.out.println("Thread[MAIN]: END! p=" + p);
    }

    static void sim_BarrierAction(){
        final int COUNT = 5;
        final Phaser p = new Phaser(1){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("onAdvanec: phase=" + phase + ", registeredParties=" + registeredParties);
                System.out.println("*********Do some thing like barrier action here**********");
                //Do some thing like barrier action here!
                return phase >= COUNT || registeredParties == 0;
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(COUNT);
        for(int i = 0; i < COUNT; i++){
            final int id = i;
            p.register();
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + id + "] BEGIN! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] wait! p.getPhase=" + p.getPhase() + ", p=" + p);
                    //this function is not a atomic operator, it only gurantee thread will continue working
                    //when satisfy some condition
                    p.arriveAndAwaitAdvance();
                    System.out.println("Thread[" + id + "] END! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        es.shutdown();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread[MAIN]: unregister phaser! p=" + p);
        //this is un-blocking arrive() is the same
        p.arriveAndDeregister();

        System.out.println("Thread[MAIN]: END! p=" + p);
    }

    static void sim_BlockingMainThread(){
        final int COUNT = 5;
        final int SHUT_DOWN_PHASE = 10;
        final Phaser p = new Phaser(COUNT){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                //let the phaser terminate when phase = count;
                System.out.println("[Phaser] onAdvanec: phase=" + phase + ", registeredParties=" + registeredParties);
                return phase >= SHUT_DOWN_PHASE  || registeredParties == 0;
            }
        };
        p.register();
        ExecutorService es = Executors.newFixedThreadPool(COUNT);
        for(int i = 0; i < COUNT; i++){
            final int id = i;
            es.execute(new Runnable() {
                @Override
                public void run() {
                    while(!p.isTerminated()){
                    System.out.println("Thread[" + id + "] BEGIN! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread[" + id + "] wait! p.getPhase=" + p.getPhase() + ", p=" + p);
                    //this function is not a atomic operator, it only gurantee thread will continue working
                    //when satisfy some condition
                    p.arriveAndAwaitAdvance();

                    System.out.println("Thread[" + id + "] END! p.getPhase=" + p.getPhase() + ", p=" + p);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
            });
        }
        es.shutdown();

        System.out.println("Thread[MAIN]: p=" + p);
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!p.isTerminated()){
            System.out.println("Thread[MAIN]: waiting p=" + p);
            p.arriveAndAwaitAdvance();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread[MAIN]: END! p=" + p);
    }


    public static void main(String[] args) {
//        sim_CountDownLatch();
//        sim_Condition();
//        sim_BarrierAction();
        sim_BlockingMainThread();
    }
}
class PhaserTest4 {

    public static void main(String args[]) throws Exception {
        //
        final int count = 5;
        final int phaseToTerminate = 3;
        final Phaser phaser = new Phaser(count) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======");
                return phase == phaseToTerminate || registeredParties == 0;
            }
        };

        //
        for(int i = 0; i < count; i++) {
            System.out.println("starting thread, id: " + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }

        //
        phaser.register();
        while (!phaser.isTerminated()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.println("done, phaser=" + phaser);
    }

    public static class Task implements Runnable {
        //
        private final int id;
        private final Phaser phaser;

        public Task(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            while(!phaser.isTerminated()) {
                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    // NOP
                }
                System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
                phaser.arriveAndAwaitAdvance();
            }
        }
    }
}

