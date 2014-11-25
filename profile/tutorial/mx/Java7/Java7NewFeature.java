package mx.Java7;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by hxiong on 4/21/14.
 */
public class Java7NewFeature {
    /**
     * Automatic Resource Management
     * no finally needed in code if resource has implemented
     * interface: AutoClosable or Cloaeable
     * Stream, File, Socket and java.sql.Connection has implemented it in API level
     */
    static void try_with_resource(){
        try(FileWriter fw = new FileWriter("temp.tmp")) {
            fw.write("Hello Java 7, I'm using Java 7 new feature: try_with_resource .");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phaser refer to mx.concurrent.TestPhaser
     * http://whitesock.iteye.com/blog/1135457
     */

    /**
     * work-stealing algorithm
     */
    static void folk_join_RecursiveTask(){
        ForkJoinPool pool = new ForkJoinPool();
        for(int i = 1; i < 11; i++){
            System.out.println("Begin to calculate faboraci: " + i);
            pool.invoke(new FaboraciTask(i));
        }
    }

    /**
     * work-stealing algorithm
     * @throws InterruptedException
     */
    static void folk_join_RecursiveAction() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        int [] src = {1, -1, 100, 1010, 2, 5, 0, 11, -100};
        ArrayMergeSortAction sort = new ArrayMergeSortAction(src, 0, src.length);
        pool.submit(sort);
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
        for(int i: src){
            System.out.print(i + ",");
        }
        System.out.println();
    }

    /**
     * before 1.7, it can only throw IOException, Now can throw both of them
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    static void throw_exception() throws FileNotFoundException, IOException {
        try(
                FileWriter fw = new FileWriter("notExist.tmp")
        ) {
            fw.write("Hello Java 7, I'm using Java 7 new feature: try_with_resource .");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * catch multi exception in one catch statement
     */
    static void catch_multi_exception(){
        try(
                FileWriter fw = new FileWriter("notExist.tmp")
        ) {
            fw.write("Hello Java 7, I'm using Java 7 new feature: try_with_resource .");
        } catch (NullPointerException | IOException e
//                | FileNotFoundException : this is not allowed as FileNotFoundException is subclass of IOException
            ) {
            e.printStackTrace();
        }
    }

    /**
     * 1: G1 garbage collection
     * 2: NIO
     */

    /**
     *
     */
    static void miscellaneous(){
        long a  = 1_000_000L;
        int b   = 0b10_10_10_11_1;
        Map<String, List<String>> employeeRecords =  new HashMap<>();
        String c    = "abc";
        switch (c){
            case "a":System.out.println("a");break;
            case "b":System.out.println("b"); break;
            default: System.out.println("default");
        }
    }


    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        try_with_resource();
//        folk_join_RecursiveTask();
        folk_join_RecursiveAction();
    }
}

class FaboraciTask extends RecursiveTask<Integer>{

    final Integer n;

    public FaboraciTask(Integer n){
        this.n = n;
        System.out.print("{new Instance:" + n + "}");
    }


    @Override
    protected Integer compute() {
        if(n < 2){
            System.out.print("Faboraci[" + n + "] =" + n + ",  ");
            return n;
        }
        FaboraciTask fn_1 = new FaboraciTask(n - 1);
        FaboraciTask fn_2 = new FaboraciTask(n - 2);
        fn_1.fork();
        fn_2.fork();
        int rs = fn_1.join() + fn_2.join();
        System.out.print("Faboraci[" + n + "] =" + rs + ",  ");
        return rs;
    }

}

class ArrayMergeSortAction extends RecursiveAction{

    final private int [] src;
    final private int start;
    final private int end;

    private final static int THRESHOLD = 2;

    ArrayMergeSortAction(int[] src, int start, int end) {
        this.src = src;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if(end - start <= THRESHOLD){
            Arrays.sort(src, start, end);
        }else{
            int center = (start + end) / 2;
            ArrayMergeSortAction left = new ArrayMergeSortAction(this.src, this.start, center);
            ArrayMergeSortAction right = new ArrayMergeSortAction(this.src, center, this.end);
            invokeAll(left, right);

            this.merge(this.src, this.start, center, this.end);
        }
    }

    private static void merge(int[] src, int start, int center, int end) {
        int pr  = center;
        int p   = start;
        int [] tmp  = new int [center - start]; // copy left partition
        int pl  = 0;
        System.arraycopy(src, start, tmp, 0, tmp.length);
        while(pl < tmp.length && pr < end){
            if(tmp[pl] > src [pr]){
                src[p] = src [pr++];
            }else{
                src[p] = tmp [pl++];
            }
            p++;
        }
        while(pl < tmp.length){
            src[p++] = tmp[pl++];
        }
    }

    static void test_merge(){
        int [] src = {1, 2, 3, 1, 2, 3};
        merge(src, 0, 3, 6);

    }

    private void swap(int [] array, int left, int right){
        array[left]     += array[right];
        array[right]    = array[left] - array[right];
        array[left]     -= array[right];
    }

    public static void main(String[] args) {
        test_merge();
    }
}


