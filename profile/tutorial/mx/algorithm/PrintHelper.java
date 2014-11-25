package mx.algorithm;

/**
 * Created by hxiong on 3/5/14.
 */
public class PrintHelper {

    static void print(int [] array){
        for(int ele: array){
            System.out.print(ele + ",");
        }
        System.out.println();
    }

    static void print(Integer [] array){
        for(int ele: array){
            System.out.print(ele + ",");
        }
        System.out.println();
    }
}
