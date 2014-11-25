package mx.JCF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hxiong on 4/25/14.
 */
public class TestConcurrentModificationException {

    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            l.add(i);
        }
        Iterator<Integer> itor1 = l.iterator();
        Iterator<Integer> itor2 = l.iterator();

        while(itor1.hasNext()){
            System.out.println("-->" + itor1.next());
            itor2.next();
            itor2.remove();
        }
        //foreach loop will throw this exception the same as it use iterator internal

    }
}
