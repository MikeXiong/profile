package mx.algorithm;

/**
 * Created by hxiong on 3/5/14.
 */
public class QuickSort {


    static void swap(int [] data, int i, int j){
        if(i == j)return;
        data[i] = data[j] + data[i];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }

    static int partition(int [] data, int start, int end){
        int p  = (start + end) / 2;
        int i       = start;
        while(i <= end){
            if(i < p && data[i] > data[p]){
                swap(data, i, p);
            } else if(i > p && data[i] < data[p]){
                swap(data, i, p);
                p = p + 1;
                swap(data, i, p);
            }
            i++;
        }
        return p;
    }

    static int partition2(int [] data, int start, int end){
        int p       = (start + end) / 2;
        int pivot   = data[p];
        swap(data, p, end);

        int i = p   = start;
        while(i <= end){
            if(data[i] < pivot){
                swap(data, p++, i);
            }
            i++;
        }
        swap(data, p, end);

        return p;
    }

    static void testPartition(){
        int [] data     = {1, 10 , 6, 12, 0, 12, 5, 3};
        int [] data2    = {1, 10 , 6, 12, 0, 12, 5, 3};
        int i           = partition2(data2, 0, data.length - 1);
        System.out.println("i =  " + i);
        PrintHelper.print(data2);
        i               = partition(data, 0, data.length - 1);
        System.out.println("i =  " + i);
        PrintHelper.print(data);
    }

    static void sortSub(int [] data, int start, int end){
        if(start < end){
            int i   = partition(data, start, end);
            sortSub(data, start, i);
            sortSub(data, i + 1, end);
        }
    }

    static void sort(int [] data){
        sortSub(data, 0, data.length - 1);
    }


    public static void main(String[] args) {
        int [] data1   = TestHelper.arrayToBeTest();
        Integer [] data2   = TestHelper.arrayToBeTest_Integer();
        PrintHelper.print(data1);

//        testPartition();

        sort(data1);
        PrintHelper.print(data1);

        Sort.quicksort(data2);
        PrintHelper.print(data2);
    }

/**
 * an standard class contract from WIKI
 */
/*
 * more efficient implements for quicksort. <br />
 * use left, center and right median value (@see #median()) for the pivot, and
 * the more efficient inner loop for the core of the algorithm.
 */
static class Sort {

        public static final int CUTOFF = 11;

        /**
         * quick sort algorithm. <br />
         *
         * @param arr an array of Comparable items. <br />
         */
        public static <T extends Comparable<? super T>> void quicksort( T[] arr ) {
            quickSort( arr, 0, arr.length - 1 );
        }


        /**
         * get the median of the left, center and right. <br />
         * order these and hide the pivot by put it the end of
         * of the array. <br />
         *
         * @param arr an array of Comparable items. <br />
         * @param left the most-left index of the subarray. <br />
         * @param right the most-right index of the subarray.<br />
         * @return T
         */
        public static <T extends Comparable<? super T>> T median( T[] arr, int left, int right ) {

            int center = ( left + right ) / 2;

            if ( arr[left].compareTo( arr[center] ) > 0 )
                swapRef( arr, left, center );
            if ( arr[left].compareTo( arr[right] ) > 0 )
                swapRef( arr, left, right );
            if ( arr[center].compareTo( arr[right] ) > 0 )
                swapRef( arr, center, right );

            swapRef( arr, center, right - 1 );
            return arr[ right - 1 ];
        }

        /**
         * internal method to sort the array with quick sort algorithm. <br />
         *
         * @param arr an array of Comparable Items. <br />
         * @param left the left-most index of the subarray. <br />
         * @param right the right-most index of the subarray. <br />
         */
        private static <T extends Comparable<? super T>> void quickSort( T[] arr, int left, int right ) {
            if ( left + CUTOFF <= right  ) {
                //find the pivot
                T pivot = median( arr, left, right );

                //start partitioning
                int i = left, j = right - 1;
                for ( ; ; ) {
                    while ( arr[++i].compareTo( pivot ) < 0 ) ;
                    while ( arr[--j].compareTo( pivot ) > 0 ) ;
                    if ( i < j )
                        swapRef( arr, i, j );
                    else
                        break;
                }

                //swap the pivot reference back to the small collection.
                swapRef( arr, i, right - 1 );

                quickSort( arr, left, i - 1 );		//sort the small collection.
                quickSort( arr, i + 1, right );		//sort the large collection.

            } else {
                //if the total number is less than CUTOFF we use insertion sort instead (cause it much more efficient).
                insertionSort( arr, left, right );
            }
        }


        /**
         * method to swap references in an array.<br />
         *
         * @param arr an array of Objects. <br />
         * @param idx1 the index of the first element. <br />
         * @param idx2 the index of the second element. <br />
         */
        public static <T> void swapRef( T[] arr, int idx1, int idx2 ) {
            T tmp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = tmp;
        }


        /**
         * method to sort an subarray from start to end
         * 		with insertion sort algorithm. <br />
         *
         * @param arr an array of Comparable items. <br />
         * @param start the begining position. <br />
         * @param end the end position. <br />
         */
        public static <T extends Comparable<? super T>> void insertionSort( T[] arr, int start, int end ) {
            int i;
            for ( int j = start + 1; j <= end; j++ ) {
                T tmp = arr[j];
                for ( i = j; i > start && tmp.compareTo( arr[i - 1] ) < 0; i-- ) {
                    arr[ i ] = arr[ i - 1 ];
                }
                arr[ i ] = tmp;
            }
        }
    }
}
