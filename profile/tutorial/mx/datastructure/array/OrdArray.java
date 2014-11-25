package mx.datastructure.array;

import java.util.Arrays;

/**
 * Created by Administrator on 14-6-26.
 */
public class OrdArray {

    private int [] store = null;

    private int length = 0;


    public OrdArray(int max) {
        this.store = new int[max];
    }

    /**
     * @param v
     * @return the index of v in store if find, else return -1;
     */
    public int find(int v){
        int lowBound = 0;
        int highBound = this.length - 1;

        int curIn = 0;
        while(true){
            curIn = (lowBound + highBound) / 2;
            if(v == store[curIn]){
                return curIn;
            }
            if(lowBound == highBound){
                break;
            }
            if(this.store[curIn] > v){
                highBound = curIn - 1;
            }else if(this.store[curIn] < v){
                lowBound = curIn + 1;
            }
        }
        return -1;
    }

    public int binaryFindInsertIndex(int v){
        int lowBound = 0;
        int highBound = this.length - 1;

        int curIn = 0;
        int count = 0;
        while(true){
            curIn = (lowBound + highBound) / 2;
//            System.out.println("curIn=" + curIn + ", lowBound=" + lowBound + ", highBound=" + highBound);
            if(v == store[curIn]){
                break;
            }
            //must >= , == is not enough
            if(lowBound >= highBound){
                break;
            }
            if(this.store[curIn] > v){
                highBound = curIn - 1;
            }else if(this.store[curIn] < v){
                lowBound = curIn + 1;
            }

//            if(count++ > 10)break;
        }
        if(v == store[curIn]){
            throw new RuntimeException("The value exist in array already, index=" + curIn);
        }else if(v > store[curIn]){
            curIn++;
        }else {
            //do nothing
        }
        return curIn;
    }

    /**
     * @param v
     * @return the index inserted if success, else throw ArrayIndexOutOfBoundsException
     */
    public int insert(int v){
        if(this.length >= this.store.length){
            throw new ArrayIndexOutOfBoundsException("the count of elements in array has reach max");
        }
        int index = -1;
        while(++index < this.length && this.store[index] < v);


        for(int i = this.length; i > index; i--){
           this.store[i] = this.store[i - 1];
        }

        store[index] = v;
        this.length++;
        return index;
    }

    /**
     *
     * @param v
     * @return the index v in array, else return -1
     */
    public int delete(int v){
        int index = -1;
        while(++index < this.length && this.store[index] != v);

        if(index == this.length)return -1;

        for(int i = index; i < this.length - 1; i++){
            this.store[i] = this.store[i + 1];
        }

        this.length--;
        return index;
    }

    public int length(){
        return this.length;
    }


    public void print(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "OrdArray{" +
                "store=" + Arrays.toString(store) +
                ", length=" + length +
                '}';
    }

}


class OrdArrayApp{

    static int[] removeDuplicate(int [] src){
        int [] tmp = new int[src.length];
        int idx = 0;
        int j = -1;
        for(int i = 0; i < src.length; i++){
            idx = -1;
            while(++idx <= j && src[i] != tmp[idx]);
            if(idx > j){
                tmp[++j] = src[i];
            }
        }
        int rs[] = new int[++j];
        for(int i = 0; i < rs.length; i++){
            rs[i] = tmp[i];
        }
        return rs;
    }

    public static void main(String[] args) {
        OrdArray a = new OrdArray(10);
        for(int i = 0; i < 10; i++){
            a.insert(i * 2);
        }
        a.print();
        try{
            a.insert(22);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("find -1:" + a.find(-1));
        System.out.println("find 100:" + a.find(100));
        System.out.println("find 8:" + a.find(8));

        System.out.println("delete 8:" + a.delete(8));
        System.out.println("delete 100:" + a.delete(100));
        System.out.println("delete -1:" + a.delete(-1));

        a.print();

        a.insert(5);

        a.print();

        System.out.println("binaryFindInsertIndex(9):" + a.binaryFindInsertIndex(9));
        System.out.println("binaryFindInsertIndex(9):" + a.binaryFindInsertIndex(11));

        int [] unDup = removeDuplicate(new int[] {
           2, 1, 100, 4, 6, 1, 1, 3, 2
        });
        for(int i = 0; i < unDup.length; i++){
            System.out.print(unDup[i] + ",");
        }
        System.out.println();


    }
}


