package mx.algorithm.hash;

import mx.algorithm.tree_rb.BRTreeFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxiong on 10/9/14.
 */
public class Main {

    static void test_HashTable_LinearProbing(){
        HashTable_LinearProbing<Integer> ht = new HashTable_LinearProbing<>(10);
        System.out.println("Empty ht=\n\t:" + ht);
        int key = 0;
        int data = 0;
        key = 50;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 40;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = 51;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = 40;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 51;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 58;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 59;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 48;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 51;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = 48;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
    }

    static void test_HashTable_SecondProbing(){
        HashTable_SecondProbing<Integer> ht = new HashTable_SecondProbing<>(10);
        System.out.println("Empty ht=\n\t:" + ht);
        int key = 0;
        int data = 0;
        key = 50;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 51;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 40;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 40;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = 40;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 55;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 59;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 45;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 45;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);

    }

    static void test_HashTable_DoubleHash(){
        HashTable_DoubleHash<Integer> ht = new HashTable_DoubleHash<>(10, 7, false);
        System.out.println("Empty ht=\n\t:" + ht);
        int key = 0;
        int data = 0;
        key = 50;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 51;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 40;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 30;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 20;
        data = -1 * key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 45;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = 30;
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);

        System.out.println("=============>Test auto inflate<=============");
        HashTable_DoubleHash<Integer> ht2 = new HashTable_DoubleHash<>(10, 7, true);
        System.out.println("Empty ht=\n\t:" + ht2);
        for(int i = 0; i < ht2.capacity; i++){
            key = i;
            data = -1 * i;
            ht2.insert(key, data);
        }
        System.out.println("ht2\n\t:" + ht2);
        key = 20;
        data = -1 * key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
    }

    static void test_HashTable_LinearProbing2(){
        System.out.println("=============>Test honer hash<=============");
        HashTable_LinearProbing2<String, String> ht = new HashTable_LinearProbing2<>(10, false);
        System.out.println("Empty ht=\n\t:" + ht);
        String key = null;
        String data = null;
        key = "a";
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = "b";
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = "A";
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = "ABCDEFGHIJKLMNOPQ\tabcdxyzopqrstuvwmn";
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = "K";
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        ht.delete(key);
        System.out.println("ht.delete(" + key + ");ht=\n\t:" + ht);
        key = "K";
        data = key + key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);

        System.out.println("=============>Test collapse hash<=============");
        HashTable_LinearProbing2<String, String> ht2 = new HashTable_LinearProbing2<>(10, true);
        System.out.println("Empty ht2=\n\t:" + ht2);

        key = "1";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
        key = "2";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
        key = "4";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
        key = "1201011118976453121";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
        key = "1201011118976453122";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
        ht2.delete(key);
        System.out.println("ht2.delete(" + key + ");ht2=\n\t:" + ht2);
        key = "1201011118976453122";
        data = key;
        ht2.insert(key, data);
        System.out.println("ht2.insert(" + key + ", " + data + ");ht2=\n\t:" + ht2);
    }

    static void test_HashTable_Chain(){
        HashTable_Chain<Integer, Integer> ht = new HashTable_Chain<>(10);
        int key = 0;
        int data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 10;
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 100;
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 50;
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
        key = 20;
        data = key;
        ht.insert(key, data);
        System.out.println("ht.insert(" + key + ", " + data + ");ht=\n\t:" + ht);
    }

    public static void main(String[] args) {
//        test_HashTable_LinearProbing();
//        test_HashTable_SecondProbing();
//        test_HashTable_DoubleHash();
//        test_HashTable_LinearProbing2();
        test_HashTable_Chain();
    }
}

class Util{
    final static int nextPrime(int num){
        int nextNum = num;
        while(!isPrime(++nextNum));
        return nextNum;
    }

    final static  int prePrime(int num){
        int preNum = num;
        while(true){
            if(0 < (--preNum)){
                if(isPrime(preNum))break;
            }else{
                throw new RuntimeException("Can not find a prime: num=" + num);
            }
        }
        return preNum;
    }

    final static boolean isPrime(int num){
        for(int i = 2; (int)Math.pow(i, 2) < num; i++){
            if(0 == num % i){
                return false;
            }
        }
        return true;
    }
}

class HashTable_LinearProbing<E>{

    static class Item<E>{
        final static int INVALID_KEY = -1;
        int key = INVALID_KEY;
        E data = null;

        Item(int key, E data){
            this.key = key;
            this.data = data;
        }

        final void invalid(){
            this.key = INVALID_KEY;
        }

        boolean isInvalid(){
            return INVALID_KEY == this.key;
        }

        boolean isTheSame(int k){
            return this.key == k;
        }

        @Override
        public String toString() {
            return "{" + this.key + ", " + this.data + "}";
        }
    }

    Item<E>[] store = null;
    int capacity = 17;

    HashTable_LinearProbing(){
        this.store = new Item[this.capacity];
    }

    HashTable_LinearProbing(int capacity){
        this.capacity = capacity;
        this.store = new Item[this.capacity];
    }

    int hash(int num){
        return num % this.capacity;
    }

    void insert(int key, E obj){
        int index = this.hash(key);
        Item<E> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isInvalid()){
            index++;

            index %= this.capacity;
            if(count++ > this.capacity){
                throw new RuntimeException("This hash table has full!");
            }
        }
        itm = new Item<>(key, obj);
        this.store[index] = itm;
    }

    E find(int key){
        Item<E> itm = this.finItem(key);
        return null == itm ? null : itm.data;
    }

    E delete(int key){
        Item<E> itm = this.finItem(key);
        if(null != itm){
            itm.invalid();
        }
        return null == itm ? null : itm.data;
    }

    /**
     * must modified by protected or public, other wise the subclass
     * can not override it
     * @param key
     * @return
     */
    protected Item<E> finItem(int key){
        int index = this.hash(key);
        Item<E> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isTheSame(key)){
            index++;
            index %= this.capacity;
            if(count++ > this.capacity){
                break;
            }
        }
        return itm;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        for(Item item: this.store){
            buff.append(item).append(",");
        }
        buff.append("]");
        return buff.toString();
    }
}

class HashTable_SecondProbing<E> extends HashTable_LinearProbing<E>{

    int size = 0;

    HashTable_SecondProbing(int capacity){
        if(Util.isPrime(capacity)){
            this.capacity = capacity;
        }else{
            System.out.println("capacity must be prime in second probing hash table. try find next prime." +
                    " capacity=" + capacity);
            this.capacity = Util.nextPrime(capacity);
            System.out.println("actual capacity is:" + this.capacity);
        }

        this.store = new Item[this.capacity];
    }

    void insert(int key, E obj){
        if(this.capacity <= this.size){
            throw new RuntimeException("This hash table has full !");
        }
        if(null == obj){
            throw new RuntimeException("null is unsupported.");
        }
        int index = this.hash(key);
        Item<E> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isInvalid()){
              //it may need more than capacity times to find the right place
//            if(count++ > this.capacity){
//                throw new RuntimeException("This hash table has full! count=" + (count - 1));
//            }
            count ++;
            index += Math.pow(count, 2);
            index %= this.capacity;
        }
        itm = new Item<>(key, obj);
        this.store[index] = itm;
        size ++;
    }

    E delete(int key){
        E obj = super.delete(key);
        if(null != obj){
            this.size --;
        }
        return obj;
    }

    protected Item<E> finItem(int key){
        int index = this.hash(key);
        Item<E> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isTheSame(key)){
//            if(count++ > this.capacity){
//                break;
//            }
            count ++;
            index += Math.pow(count, 2);
            index %= this.capacity;
        }
        return itm;
    }
}

class HashTable_DoubleHash<E> extends HashTable_LinearProbing<E>{

    int size = 0;

    int primeSeedForSecondHash = 1;

    boolean autoExpand = false;

    HashTable_DoubleHash(int capacity, int primeSeedForSecondHash, boolean autoExpand){
        if(Util.isPrime(capacity)){
            this.capacity = capacity;
        }else{
            System.out.println("capacity must be prime in second probing hash table. try find next prime." +
                    " capacity=" + capacity);
            this.capacity = Util.nextPrime(capacity);
            System.out.println("actual capacity is:" + this.capacity);
        }
        if(primeSeedForSecondHash >= this.capacity){
            throw new RuntimeException("The primeSeedForSecondHash must less than capacity. primeSeedForSecondHash="
                    + primeSeedForSecondHash + ", capacity=" + this.capacity);
        }
        if(!Util.isPrime(primeSeedForSecondHash)){
            throw new RuntimeException("It's better to use prime here. primeSeedForSecondHash=" + primeSeedForSecondHash);
        }

        this.primeSeedForSecondHash = primeSeedForSecondHash;
        this.store = new Item[this.capacity];
        this.autoExpand = autoExpand;
    }

    void inflate(){
        Item<E>[] oldStore = this.store;
        System.out.println("Before inflate: this\n\t=" + this +".\n\t capacity=" + this.capacity + ", size=" + this.size);

        this.capacity = Util.nextPrime(this.capacity);

        //array not support generic
        this.store = new Item[this.capacity];

        this.size = 0;
        for(Item<E> itm: oldStore){
            if(null == itm || itm.isInvalid()){
                continue;
            }
            this.insert(itm.key, itm.data);
        }
        System.out.println("After inflate: this\n\t=" + this +".\n\t capacity=" + this.capacity + ", size=" + this.size);
    }

    int hashStepLength(int key){
        return this.primeSeedForSecondHash - key % this.primeSeedForSecondHash;
    }

    void insert(int key, E obj){
        if(this.capacity <= this.size){
            if(this.autoExpand){
                this.inflate();
            }else{
                throw new RuntimeException("This hash table has full !");
            }
        }
        if(null == obj){
            throw new RuntimeException("null is unsupported.");
        }
        int index = this.hash(key);
        int step = this.hashStepLength(key);
//        System.out.println("step=" + step);
        Item<E> itm = null;
        while(null != (itm = this.store[index])
                && !itm.isInvalid()){
            index += step;
            index %= this.capacity;
        }
        itm = new Item<>(key, obj);
        this.store[index] = itm;
        size ++;
    }

    E delete(int key){
        E obj = super.delete(key);
        if(null != obj){
            this.size --;
        }
        return obj;
    }

    protected Item<E> finItem(int key){
        int index = this.hash(key);
        int step = this.hashStepLength(key);
        Item<E> itm = null;
        while(null != (itm = this.store[index])
                && !itm.isTheSame(key)){
            index += step;
            index %= this.capacity;
        }
        return itm;
    }
}

class HashTable_LinearProbing2<K, V>{
    static class Item<K, V>{
        final K INVALID_KEY = null;

        K key = null;
        V data = null;

        Item(K key, V data){
            this.key = key;
            this.data = data;
        }

        final void invalid(){
            this.key = INVALID_KEY;
        }

        boolean isInvalid(){
            return INVALID_KEY == this.key;
        }

        boolean isTheSame(K k){
            return !this.isInvalid() && this.key.equals(k);
        }

        @Override
        public String toString() {
            return "{" + this.key + ", " + this.data + "}";
        }
    }

    final static int SCALE = 32;

    Item<K, V>[] store = null;
    int capcity = -1;

    boolean isCollapse = false;

    HashTable_LinearProbing2(int capcity, boolean isCollapse){
        if(Util.isPrime(capcity)){
            capcity = Util.nextPrime(capcity);
        }
        this.capcity = capcity;
        this.store = new Item[this.capcity];
        this.isCollapse = isCollapse;
    }

    int hashWithHoner(String key){
        int hashVal = 0;
        for(int i = 0; i < key.length(); i++){
            hashVal = ((hashVal << 5) + (key.charAt(i))) % this.capcity;
        }
        System.out.println("key=" + key + ", hashVal=" + hashVal);
        return hashVal;
    }

    int hashWithCollapse(String key){
        int len = (this.capcity + "").length();
        List<String> keys = new ArrayList<>();
        int begin = 0;
        int end = begin;
        while(begin < key.length()){
            end = begin + len;
            end = end > key.length() ? key.length() : end;
            keys.add(key.substring(begin, end));
            begin = end;
        }
        System.out.println("groups=" + keys);
        int hashVal = 0;
        for(String k: keys){
            hashVal = (hashVal + Integer.parseInt(k)) % this.capcity;
        }

        System.out.println("key=" + key + ", hashVal=" + hashVal);
        return hashVal;
    }

    int hashString(String key){
        if(this.isCollapse){
            return this.hashWithCollapse(key);
        }else{
            return this.hashWithHoner(key);
        }
    }

    int hash(K key){
        if(key instanceof String){
            String key_ = (String)key;
            return this.hashString(key_);
        }else{
            return key.hashCode() % this.capcity;
        }
    }

    void insert(K key, V data){
        int index = this.hash(key);
        Item<K, V> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isInvalid()){
            index ++;
            if(count++ > this.capcity){
                throw new RuntimeException("Hash table is full.");
            }
            index %= this.capcity;
        }
        this.store[index] = new Item<>(key, data);
    }

    V find(K key){
        Item<K, V> itm = this.findItem(key);
        return null == itm ? null : itm.data;
    }

    V delete(K key){
        Item<K, V> itm = this.findItem(key);
        if(null != itm){
            itm.invalid();
        }
        return null == itm ? null : itm.data;
    }

    Item<K, V> findItem(K key){
        int index = this.hash(key);
        Item<K, V> itm = null;
        int count = 0;
        while(null != (itm = this.store[index])
                && !itm.isTheSame(key)){
            index ++;
            if(count++ > this.capcity){
                break;
            }
            index %= this.capcity;
        }
        return itm;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        for(Item item: this.store){
            buff.append(item).append(",");
        }
        buff.append("]");
        return buff.toString();
    }
}

class HashTable_Chain<K extends Comparable<? super K>, V>{

    BRTreeFacade<K, V>[] store = null;
    int capacity = -1;

    HashTable_Chain(int capacity){
        this.capacity = capacity;
        this.store = new BRTreeFacade[this.capacity];
    }

    int hash(K key){
        return key.hashCode() % this.capacity;
    }

    void insert(K key, V data){
        int index = this.hash(key);
        BRTreeFacade<K, V> itm = this.store[index];
        if(null == itm){
            itm = new BRTreeFacade<>();
            this.store[index] = itm;
        }
        itm.insert(key, data);
    }

    V find(K key){
        int index = this.hash(key);
        BRTreeFacade<K, V> itm = this.store[index];
        V obj = null;
        if(null != itm){
            obj = itm.find(key);
        }
        return obj;
    }

    V delete(K key){
        throw new RuntimeException("delete is too complex to support in BRTree, this function is unsupported currently.");
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        for(BRTreeFacade tree: this.store){
            buff.append(tree).append(",");
        }
        buff.append("]");
        return buff.toString();
    }
}


