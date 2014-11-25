package mx.algorithm.recursive;

import com.sun.org.apache.regexp.internal.recompile;

import java.util.*;

/**
 * Created by hxiong on 8/11/14.
 */
public class Main {

    /**
     * use stack and queue to implement it
     * 1: first set a full path
     * 2: iterate the path from tail
     * 3: each time when rotate happened update the full path.
     * 4: no consider duplicate words in str
     * @param src
     */
    static void doAnagram_with_Stack_Queue(String src){
        Map<Integer, String> indexMap = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < src.length(); i++){
            stack.push(String.valueOf(src.charAt(i)));
            indexMap.put(i, String.valueOf(src.charAt(i)));
        }
        //a set used to validate the result.
        List<String> result = new ArrayList<>();
        int cc = 0;
        while(!stack.isEmpty()){
            if(queue.isEmpty()){
                cc++;

                System.out.println("[" +cc + "]:" + stack.toString());
                result.add(stack.toString());
                String s = stack.pop();
                queue.add(s);
                continue;
            }

            String s = queue.peek();
            if(s.equals(indexMap.get(stack.size() - 1))){
                String s1 = stack.pop();
                //no need update index map here
//                indexMap.remove(stack.size());

                queue.add(s1);
                continue;
            }else{
                //rotate
                //each time when rotate, updating the index map
                String s2 = stack.pop();
                queue.add(s2);
                String s1 = queue.poll();
                //must not update the index map here.
                stack.push(s1);
                while(!queue.isEmpty()){
                    String s3 = queue.poll();
                    //update index map
                    indexMap.put(stack.size(), s3);
                    stack.push(s3);
                }
            }
        }

        validateResult(src, result);
    }

    /***
     * use recursive implement it
     * @param src
     */
    static void doAngram_recursive(String src){
        String [] s = src.split("");
        List<String> result = new ArrayList<>();
        doAngram_recursive(s, s.length - 1, result);
        int size = result.size();
        for(int i = 0; i < size; i++){
            System.out.println("[" + (i + 1) + "]:" + result.get(i));
        }

        validateResult(src, result);
    }

    static void doAngram_recursive(String [] src, int dept, List<String> result){
        if(0 == dept){
//            System.out.println(Arrays.toString(src));
                result.add(Arrays.toString(src));
            return;
        }

        int cc = 0;

        while(dept >= cc++){
            doAngram_recursive(src, dept - 1, result);
            int pos = src.length - 1 - dept;
            String temp = src[pos];
            for(int i = pos; i < src.length - 1; i++){
                src[i] = src[i + 1];
            }
            src[src.length - 1] = temp;
        }
    }

    /**
     * helper function used to validate the algorithm is right or not
     * @param src
     * @param list
     */
    static void validateResult(String src, List list){
        int expectSize = 1;
        for(int i = src.length(); 0 < i; i--){
            expectSize *= i;
        }
        Set set = new HashSet();
        set.addAll(list);
        if(expectSize == list.size() && expectSize == set.size()){
            System.out.println("Right result!");
        }else{
            System.out.println("Wrong result! EXPECTED size=" + expectSize + ", actual SET size=" + set.size()
                    + ", LIST size=" + list.size());
        }
    }

    /**
     * use recursive simulate hanio problem
     * @param storey
     * @param from
     * @param to
     * @param pillars
     * @param ident helper string used for pretty print
     */
    private static int cc = 0;
    static void hanio(int storey, String from, String to,
                      Map<String, Stack<String>> pillars, String ident){
        String c = null;
        for(String p: pillars.keySet()){
            if(p.equals(from) || p.equals(to)){
                continue;
            }
            c = p;
        }
        if(1 == storey){
            String plate = pillars.get(from).pop();
            System.out.println("[STEP]:(" + (++cc) + ")\t" + ident +
                    storey + "[" + from + "->" + to +"], plate is:" + plate);
            pillars.get(to).push(plate);
            return;
        }
        String _ident = ident + "--";
        hanio(storey - 1, from, c, pillars, _ident);
        hanio(1, from, to, pillars, _ident);
        hanio(storey - 1, c, to, pillars, _ident);
//        System.out.println("[STEP]: (" + from + "-->" + to + ") storey:" + storey);
    }

    static void printTower(Map<String, Stack<String>> pillars, String [] names){
        for(String p: pillars.keySet()){
            if(null == names){
                System.out.println("pillar[" + p + "]:" + pillars.get(p).toString());
            }else{
                for(String name: names){
                    if(name.equals(p)){
                        System.out.println("pillar[" + p + "]:" + pillars.get(p).toString());
                        break;
                    }
                }
            }
        }
    }


    /**
     * merge sort recursive
     * @param src
     * @param from
     * @param to
     * @param workSpace
     * @param <T> <T extends Comparable<T>>
     */
    static <T extends Comparable<T>> void mergeSort(T [] src, int from, int to, T[] workSpace){
        if(from + 3 > to){
            bubbleSort(src, from, to);
            return;
        }
        mergeSort(src, from, (from + to) / 2, workSpace);
        mergeSort(src, (from + to) / 2 + 1, to, workSpace);
        merge(src, from, (from + to) / 2, to, workSpace);
    }

    static <T extends Comparable<T>> void merge(T[] src, int from, int m, int to, T[] workSpace) {
        int from2 = m + 1;
        int from1 = from;
        for(int i = from1; i < to + 1; i++){
            if(from1 <= m && (from2 > to || src[from1].compareTo(src[from2]) < 0)){
                workSpace[i] = src[from1];
                from1++;
            }else{
                workSpace[i] = src[from2];
                from2++;
            }
        }
        System.arraycopy(workSpace, from, src, from, to - from + 1);
//        System.out.println("**After merge: src=" + Arrays.toString(src) +", from " + from + ", to " + to);
    }

    static <T extends Comparable<T>> void bubbleSort(T [] src, int from, int to){
        for(int outter = from; outter < to + 1; outter++){
            for(int inner = outter; inner < to + 1; inner++){
                if(src[inner].compareTo(src[outter]) < 0){
                    T tmp = src[outter];
                    src[outter] = src[inner];
                    src[inner] = tmp;
                    tmp = null;
                }
            }
        }
//        System.out.println("After bubble Sort: src=" + Arrays.toString(src) +", from " + from + ", to " + to);
    }

    static int power(int x, int y){
        if(y == 1){
            return x;
        }
        int _y = y / 2;
        int r = x * x;
        r = power(r, _y);
        if(1 == y % 2){
            r *= x;
        }
        return r;
    }

    /**
     * baggage problem(recursive)
     * @param src
     * @param index
     * @param weight
     * @param result
     */
    static void bag(int [] src, int index, int weight, Stack<Integer> result){
        if(0 > weight){
            return;
        }
        if(0 == weight){
            System.out.println(result);
            return;
        }
        if(0 == index){
            if(weight == src[index]){
                result.push(src[index]);
                System.out.println(result);
                result.pop();
            }
            return;
        }
        bag(src, index - 1, weight, result);
        result.push(src[index]);
        bag(src, index - 1, weight - src[index], result);
        result.pop();
    }

    static void combination_full(String[] src, int index, Stack<String> result){
        if(0 == index){
            result.push(src[index]);
            System.out.println("[" + ++cc2 + "]" + result);
            result.pop();
            return;
        }

        result.push(src[index]);
        System.out.println("[" + ++cc2 + "]" + result);
        result.pop();

        combination_full(src, index - 1, result);

        result.push(src[index]);
        combination_full(src, index - 1, result);
        result.pop();
    }

    static void combination(String[] src, int index, int size, Stack<String> result){
        if(0 == size){
            System.out.println("[" + ++cc2 + "]" + result);
            return;
        }
        if(0 == index){
            if(1 == size){
                result.push(src[index]);
                System.out.println("[" + ++cc2 + "]" + result);
                result.pop();
            }
            return;
        }

        combination(src, index - 1, size, result);

        result.push(src[index]);
        combination(src, index - 1, size - 1, result);
        result.pop();
    }

    static Map<String, Stack<String>> crateHanioTower(int size){
        Map<String, Stack<String>> pillars = new HashMap<>();
        pillars.put("A", new Stack<String>());
        pillars.put("B", new Stack<String>());
        pillars.put("C", new Stack<String>());
        for(int i = size - 1; i > -1; i--){
            pillars.get("A").push("" + i);
        }
        return pillars;
    }

    static void test_hanio(int storey){
        Map<String, Stack<String>> pillars = crateHanioTower(storey);
        printTower(pillars, null);
        System.out.println("Begin Hanio..");
        hanio(storey, "A", "B", pillars, "");
        System.out.println("Finished Hanio.");
        printTower(pillars, null);
    }

    static void test_mergeSort(){
        Integer[] src = new Integer[]{
          20, -1, 13, 14, 0, 8, -12, 10, 16, 15
        };
        Integer[] workSpace = new Integer[src.length];
        System.out.println("[Before merge sort] src:" + Arrays.toString(src));
        mergeSort(src, 0, src.length - 1, workSpace);
        System.out.println("[After  merge sort] src:" + Arrays.toString(src));
    }

    static void test_bag(){
        int[] src = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        bag(src, src.length - 1, 10, new Stack<Integer>());
    }

    static int cc2 = 0;
    static void test_combination_full(){
        cc2 = 0;
        String[] src = new String[]{"A", "B", "C", "D", "E"};
        combination_full(src, src.length - 1, new Stack<String>());
        int expectSize = 0;
        for(int i = 1; i < src.length + 1; i++){
            expectSize += c_x_y(src.length, i);
        }
        if(cc2 != expectSize){
            System.out.println("ERROR, expectedSize=" + expectSize + ", actual=" + cc2);
        }
    }

    static void test_combination(){
        cc2 = 0;
        String[] src = new String[]{"A", "B", "C", "D", "E"};
        int size = 3;
        combination(src, src.length - 1, size, new Stack<String>());
        int expectSize = c_x_y(src.length, size);

        if(cc2 != expectSize){
            System.out.println("ERROR, expectedSize=" + expectSize + ", actual=" + cc2);
        }
    }

    static int c_x_y(int x, int y){
        return p_x_y(x, y) / p_x_y(y, y);
    }

    static int p_x_y(int x, int y){
        if(y > x){
            throw new IllegalArgumentException("y must <= x in combination");
        }
        if(y < 1 || x < 1){
            throw new IllegalArgumentException("x, y must > 0, x,y =" + x + ", y");
        }
        int r = 1;
        while(0 < y--){
            r *= x--;
        }
        return r;
    }

    static void test_c_x_y(){
        System.out.println(c_x_y(5, 2));
    }

    static void test_p_x_y(){
        System.out.println(p_x_y(4, 3));
    }

    static void test_power(){
        int x = 2;
        int y = 3;
        System.out.println("power(" + x + ", " + y + ")=" + power(x, y));
    }

    public static void main(String[] args) {
//        doAnagram_with_Stack_Queue("abcdefgh");
//        doAngram_recursive("abcdef");
//        test_hanio(5);
//        test_mergeSort();
//        test_power();
//        test_bag();
//        test_p_x_y();
//        test_c_x_y();
//       test_combination_full();
        test_combination();
    }

}

