package mx.algorithm;

/**
 * Created by hxiong on 3/4/14.
 */
public class HeapSort {

    static int left(int i){
        return 2 * i + 1;
    }

    static int right(int i){
        return 2 * i + 2;
    }

    static int pare(int i){
        return (i - 1) / 2;
    }

    static void maxHeapfiy(int i, int [] data, int heapSize){
        int l   = left(i);
        int r   = right(i);
        int m   = i;
        if(l < heapSize && data[l] > data[i]){
            m   = l;
        }
        if(r < heapSize && data[r] > data[m]){
            m   = r;
        }

        if(m != i){
            int max = data[m];
            data[m] = data[i];
            data[i] =  max;
            maxHeapfiy(m, data, heapSize);
        }
    }


    static void buildMaxHeap(int [] data){
        int last    = pare(data.length - 1);
        for(int i = last; i >= 0; i--){
            maxHeapfiy(i, data, data.length);
        }
    }

    static void sort(int [] data){
        for(int i = data.length - 1; i >= 0; i--){
            int tmp = data[i];
            data[i] = data[0];
            data[0] = tmp;

            maxHeapfiy(0, data, i);
        }
    }




    public static void main(String[] args) {
        HeapSortHelper helper   = new HeapSortHelper();
        int [] beSorted         = TestHelper.arrayToBeTest();
        int[] sort              = TestHelper.arrayToBeTest();


        buildMaxHeap(beSorted);
        helper.buildMaxHeapify(sort);

        PrintHelper.print(sort);
        PrintHelper.print(beSorted);

        helper.heapSort(sort);
        sort(beSorted);

        PrintHelper.print(sort);
        PrintHelper.print(beSorted);

    }

    /**
     * an standard class of heap sort contract from WIKI
     */
   static class HeapSortHelper{
        /**
         * @param data
         */
        static void buildMaxHeapify(int[] data){
            //没有子节点的才需要创建最大堆，从最后一个的父节点开始
            int startIndex = getParentIndex(data.length - 1);
            //从尾端开始创建最大堆，每次都是正确的堆
            for (int i = startIndex; i >= 0; i--) {
                maxHeapify(data, data.length, i);
            }
        }

        /**
         * 创建最大堆
         * @param data
         * @param heapSize 需要创建最大堆的大小，一般在sort的时候用到，因为最多值放在末尾，末尾就不再归入最大堆了
         * @param index 当前需要创建最大堆的位置
         */
        static void maxHeapify(int[] data, int heapSize, int index){
            // 当前点与左右子节点比较
            int left = getChildLeftIndex(index);
            int right = getChildRightIndex(index);

            int largest = index;
            if (left < heapSize && data[index] < data[left]) {
                largest = left;
            }
            if (right < heapSize && data[largest] < data[right]) {
                largest = right;
            }
            //得到最大值后可能需要交换，如果交换了，其子节点可能就不是最大堆了，需要重新调整
            if (largest != index) {
                int temp = data[index];
                data[index] = data[largest];
                data[largest] = temp;
                maxHeapify(data, heapSize, largest);
            }
        }

        /**
         * 排序，最大值放在末尾，data虽然是最大堆，在排序后就成了递增的
         * @param data
         */
        static void heapSort(int[] data) {
            //末尾与头交换，交换后调整最大堆
            for (int i = data.length - 1; i > 0; i--) {
                int temp = data[0];
                data[0] = data[i];
                data[i] = temp;
                maxHeapify(data, i, 0);
            }
        }

        /**
         * 父节点位置
         * @param current
         * @return
         */
        static int getParentIndex(int current){
            return (current - 1) >> 1;
        }

        /**
         * 左子节点position 注意括号，加法优先级更高
         * @param current
         * @return
         */
        static int getChildLeftIndex(int current){
            return (current << 1) + 1;
        }

        /**
         * 右子节点position
         * @param current
         * @return
         */
        static int getChildRightIndex(int current){
            return (current << 1) + 2;
        }

       static void print(int[] data){
           int pre = -2;
           for (int i = 0; i < data.length; i++) {
               if (pre < (int)getLog(i+1)) {
                   pre = (int)getLog(i+1);
                   System.out.println();
               }
               System.out.print(data[i] + " |");
           }
       }

       static double getLog(double param){
           return Math.log(param)/Math.log(2);
       }
    }
}



