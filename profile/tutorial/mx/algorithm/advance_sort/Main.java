package mx.algorithm.advance_sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by hxiong on 8/27/14.
 */
public class Main {

    static<T extends Comparable<? super T>> void shellSort(T[] src){
        int length = src.length;
        int maxIncrement = 1;
        while(maxIncrement < length){
            maxIncrement  = maxIncrement * 3 + 1;
        }
        _shellSort(src, maxIncrement);
    }

    static <T extends Comparable<? super T>> void _shellSort(T[] src, int increment){
        for(int start = 0; start < increment; start++){
            //insert sort
           insertSort(src, increment, start);
        }
        if(1 == increment){
            return;
        }else{
            _shellSort(src, (increment - 1) / 3);
        }
    }

    static <T extends Comparable<? super T>> void insertSort(T[] src, int increment, int start){
        T tmp = null;
        for(int outter = start; outter < src.length; outter += increment){
            for(int inner = outter; inner > start; inner -= increment){
                if(src[inner].compareTo(src[inner - increment]) < 0){
                    tmp = src[inner];
                    src[inner] = src[inner - increment];
                    src[inner - increment] = tmp;
                }else{
                    break;
                }
            }
        }
    }

    static <T extends Comparable<? super T>> void fastSort(T[] src, int start, int end){
        if(0 >= end - start){
            return;
        }
//        int m = partition(src, start, end);
        int m  = partition2(src, start, end);
        System.out.println("==>after partition (start, end, m)=(" + start + ", " + end + ", " + m + ")");
        fastSort(src, start, m - 1);
        fastSort(src, m + 1, end);
    }

    static <T extends Comparable<? super T>> int partition(T[] src, int start, int end){
        int m = (start + end) / 2;
        int left = start;
        int right = end;
        T tmp = null;
        T pivot = src[m];
        tmp = src[right];
        src[right--] = pivot;
        src[m] = tmp;

        System.out.println("-->in partition (start, end, m)=(" + start + ", " + end + ", " + m + ")");
        while(left != right){
            if(0 < src[left].compareTo(pivot)){
                tmp = src[left];
                src[left] = src[right];
                src[right--] = tmp;
                continue;
            }
            left++;
        }
        System.out.println("left:" + left + ", right:" + right);
        if(src[left].compareTo(pivot) > 0){
            tmp = src[end];
            src[end] = src[left];
            src[left] = tmp;
        }else{
            tmp = src[end];
            src[end] = src[++left];
            src[left] = tmp;
        }

        return left;
    }

    static <T extends Comparable<? super T>> int partition2(T[] src, int start, int end){
        int left = start - 1;
        int right = end;
        T pivot = src[right];
        while(true){
            while(++left <= end && src[left].compareTo(pivot) < 0);
            while(--right >= start && src[right].compareTo(pivot) > 0);

            if(left > right){
                break;
            }else{
                swap(src, left, right);
            }
        }
        swap(src, left, end);
        return left;
    }

    static <T extends Comparable<? super T>> void swap(T[] src, int left, int right){
        T tmp = src[left];
        src[left] = src[right];
        src[right] = tmp;
    }

    static void radixSort(int[] src, int scale){
        Queue<Integer>[] workArray = new LinkedList[scale];
        Queue<Integer>[] workArray2 = new LinkedList[scale];
        int radixIndex = 0;
        for(int i = 0; i < workArray.length; i++){
            workArray[i] = new LinkedList<>();
            workArray2[i] = new LinkedList<>();
        }
        for(int i = 0; i < src.length; i++){
            if(0 > src[i]){
                throw new RuntimeException("negative number is not supported currently! src[" + i + "]=" + src[i]);
            }
            int n = getNumberInRadix(src[i], radixIndex, scale);
            workArray[n].add(src[i]);
        }

        System.out.println("after initialize: workArray=" + arrayToString(workArray));

        boolean finished = false;
        Queue<Integer> tmp = null;
        Queue<Integer>[] tmpArray = null;
        int cc = 0;
        while(!finished){
            radixIndex++;
            finished = true;
            for(int i = 0; i < workArray.length; i++){
                tmp = workArray[i];
                while(!tmp.isEmpty()){
                    int n = getNumberInRadix(tmp.peek(), radixIndex, scale);
                    workArray2[n].add(tmp.poll());
                    if(0 != n){
                        finished = false;
                    }
                }
            }
            System.out.println("STEP(" + ++cc + "): workArray=" + arrayToString(workArray)
                    + ", workArray2=" + arrayToString(workArray2));
            tmpArray = workArray2;
            workArray2 = workArray;
            workArray = tmpArray;
        }

        print(workArray);

        tmp = workArray[0];
        int i = 0;
        while(!tmp.isEmpty()){
            src[i++] = tmp.poll();
        }
    }

    static int getNumberInRadix(int num, int radixIndex, int scale){
        //use a simple but not efficient way to implement it here.
        //More efficient algorithm is needed in product environment .
        int len = String.valueOf(num).length();
        return radixIndex > (len - 1) ? 0 : Integer.parseInt(String.valueOf(num).charAt(len - 1 - radixIndex) + "");
    }

    static void print(Queue<Integer>[] array){
        System.out.println(Arrays.toString(array));
    }

    static String arrayToString(Queue<Integer>[] array){
        return Arrays.toString(array);
    }

    static <T extends Comparable<? super T>> T findMax_n(T [] src, int start, int end, int max_n){
        if(1 > max_n || src.length < max_n){
            throw new RuntimeException("Illegal max_n:" + max_n);
        }
        int index = partition2(src, start, end);
        if(index == (src.length - max_n)){
            System.out.println("Max[" + max_n + "] is:" + src[index]);
            return src[index];
        }else if(index > (src.length - max_n)){
            return findMax_n(src, start, index - 1, max_n);
        }else{
            return findMax_n(src, index + 1, end, max_n);
        }
    }

    static void test_shellSort(){
        Integer[] src = new Integer[]{10, 100, 1, -2, -3, 10, 12};
        System.out.println("Before shellShort: src=" + Arrays.toString(src));
        shellSort(src);
        System.out.println("After shellShort: src=" + Arrays.toString(src));
    }

    static void test_insertSort(){
        Integer[] src = new Integer[]{10, 100, 1, -2, -3, 10, 12};
        System.out.println("Before insertSort: src=" + Arrays.toString(src));
        insertSort(src, 1, 0);
        System.out.println("After insertSort: src=" + Arrays.toString(src));
    }

    static void test_fastSort(){
//        Integer[] src = new Integer[]{10, 100, 1, -2, -3, 10, 12};
//        Integer[] src = new Integer[]{1, 1, 1, 1, 1, 1, 1};
//        Integer[] src = new Integer[]{1};
//        Integer[] src = new Integer[]{1, 1};
        Integer[] src = new Integer[]{1, 1, 2};
        System.out.println("Before fastSort: src=" + Arrays.toString(src));
        fastSort(src, 0, src.length - 1);
        System.out.println("After fastSort: src=" + Arrays.toString(src));
    }

    static void test_radixSort(){
        int[] src = new int[]{10, 100, 1, 22, 1233, 10, 12, 100000, 10086, 0, 1};
        System.out.println("Before insertSort: src=" + Arrays.toString(src));
        radixSort(src, 10);
        System.out.println("After insertSort: src=" + Arrays.toString(src));
    }

    static void test_getNumberInRadix(){
        int src = 1230456070;
        final int SCALE = 10;
        for(int i = 0; i < 12; i++){
            int n = getNumberInRadix(src, i, SCALE);
            System.out.println("getNumberInRadix(" + src + ", " + i + ", " + SCALE + ")=" + n);
        }
    }

    static void test_findMax_n(){
        Integer[] src = new Integer[]{10, 100, 1, -2, -3, 10, 12};
        System.out.println("Before findMax_n: src=" + Arrays.toString(src));
        Integer i = findMax_n(src, 0, src.length - 1, 2);
        fastSort(src, 0, src.length - 1);
        System.out.println("After fastSort: src=" + Arrays.toString(src));
    }

    public static void main(String[] args) {
//        test_insertSort();
//        test_shellSort();
//        test_fastSort();
//        test_getNumberInRadix();
//        test_radixSort();
        test_findMax_n();
    }

}
