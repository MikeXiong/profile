package mx.algorithm.sort;

/**
 * Created by hxiong on 7/1/14.
 */
public class BasicSort {

    static <T> void swap(int i, int j, T[] array){
        T  tmp      = array[i];
        array[i]    = array[j];
        array[j]    = tmp;
    }

    /**
     * think about why can not use <T extends Comparable<? extends T>> here
     * @param i
     * @param j
     * @param array
     * @param <T>
     * @return true if swapped
     */
    static <T extends Comparable<? super T>> boolean swapIfLargeThan(int i, int j, T[] array){
        if(i == j)return false;
        if(0 < array[i].compareTo(array[j])){
            swap(i, j, array);
            return true;
        }
        return false;
    }

    static <T extends Comparable<? super T>> void bubbleSort(T[] array){
        int outterP = array.length - 1;
        int innerP  = 0;

        while(0 < outterP ){
            innerP = 0;

            while(innerP < outterP){
                swapIfLargeThan(innerP, innerP + 1, array);
                innerP++;
            }

            outterP--;
        }
    }

    static <T extends Comparable<? super T>> void selectSort(T[] array){
        int maxP = 0;
        T max = null;
        for(int outterP = array.length - 1; 0 < outterP; outterP--){
            for(int innerP = 0; innerP <= outterP; innerP++ ){
                if(0 == innerP || 0 > max.compareTo(array[innerP])){
                    maxP = innerP;
                    max = array[maxP];
                }
            }
            swap(maxP, outterP, array);
        }
    }

    static <T extends Comparable<? super T>> void insertSort(T[] array){
        T select = null;
        for(int selectP = 0; selectP < array.length; selectP++){
            select  = array[selectP];
            for(int innerP = selectP - 1; -1 < innerP; innerP--){
                if(0 > array[innerP].compareTo(select)){
                    array[innerP + 1] = select;
                    break;
                }
                array[innerP + 1] = array[innerP];
                if(innerP == 0){
                    array[innerP] = select;
                }
            }
        }
    }

    /**
     * http://zh.wikipedia.org/zh/%E5%A5%87%E5%81%B6%E6%8E%92%E5%BA%8F
     * http://www.cnblogs.com/kkun/archive/2011/11/23/2260295.html
     * @param array
     * @param <T>
     */
    static <T extends  Comparable<? super T>> void oddEvenSort(T[] array){
        boolean needSort = true;
        while(needSort){
            needSort = false;
            for(int i =  0; (i + 1) < array.length; i += 2){
                if(swapIfLargeThan(i, i + 1, array)){
                    needSort = true;
                }
            }
            for(int i =  1; (i + 1) < array.length; i += 2){
                if(swapIfLargeThan(i, i + 1, array)){
                    needSort = true;
                }
            }
        }
    }

    static String array2String(Object[] array){
        String str = "[";
        for(int i = 0; i < array.length; i++){
            if(0 < i){
                str += ",";
            }
            str += array[i];
        }
        return str + "]";
    }

    static <T> void println(T[] array){
        System.out.println(array2String(array));
    }

    static void testBubbleSort(){
        System.out.println("test bubble sort BEGIN !");
//        Integer[] orig = new Integer[]{
//          100, 1, -2, 0, 9, 50, 101, 10
//        };
        A[] orig = new A[]{
          new A(100), new A(1), new A(-2), new A(0), new A(9), new A(50), new A(101), new A(10)
        };
//        C[] orig = new C[]{
//            new C(100), new C(1), new C(-2), new C(0), new C(9), new C(50), new C(101), new C(10)
//        };
        println(orig);
        bubbleSort(orig);
        println(orig);
        System.out.println("test bubble sort END !");
    }

    static void testSelectSort(){
        System.out.println("test select sort BEGIN !");
        Integer[] orig = new Integer[]{
            100, 1, -2, 0, 9, 50, 101, 10
        };
        println(orig);
        selectSort(orig);
        println(orig);
        System.out.println("test select sort END !");
    }

    static void testInsertSort(){
        System.out.println("test insert sort BEGIN !");
        Integer[] orig = new Integer[]{
                100, 1, -2, 0, 9, 50, 101, 10
        };
        println(orig);
        insertSort(orig);
        println(orig);
        System.out.println("test insert sort END !");
    }

    static void testOddEvenSort(){
        System.out.println("test oddEven sort BEGIN !");
        Integer[] orig = new Integer[]{
                100, 1, -2, 0, 9, 50, 101, 10
        };
        println(orig);
        insertSort(orig);
        println(orig);
        System.out.println("test oddEven sort END !");
    }

    public static void main(String[] args) {
//        testBubbleSort();
//        testSelectSort();
//        testInsertSort();
        testOddEvenSort();
    }


}

class A extends B implements Comparable<B>{

    int i = -1;

    public A(int i){
        super(i);
        this.i = i;
    }

    @Override
    public int compareTo(B o) {
        return this.i - o.i;
    }
}


class B {

    int i = -1;

    public B(int i){
        this.i = i;
    }

    @Override
    public String toString() {
        return this.i + "";
    }
}

//
//class C extends A implements Comparable<C>{
class C extends A {
//    int i = -1;

    public C(int i){
        super(i);
    }

//    @Override
//    public int compareTo(C o) {
//        return this.i - o.i;
//    }
}


