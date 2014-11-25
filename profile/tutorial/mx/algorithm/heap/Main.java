package mx.algorithm.heap;

import mx.algorithm.tree.IGraphicTree;
import mx.algorithm.tree.IGraphicTreeNode;
import mx.algorithm.tree.SwingTreeGraphicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hxiong on 10/11/14.
 */
public class Main {

    static void test_ArrayHeapStrategy(){
        Heap<Integer, Integer> heap = new Heap<>(new ArrayHeapStrategy());
        System.out.println("heap:" + heap);
        int key, data = 0;
        key = 1;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 10;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 5;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 3;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 8;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 20;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);

        SwingTreeGraphicer graphicer = new SwingTreeGraphicer(heap);
    }

    static void test_LinkHeapStrategy(){
        Heap<Integer, Integer> heap = new Heap<>(new LinkHeapStrategy());
        System.out.println("heap:" + heap);
        int key, data = 0;
        key = 2;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 10;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 5;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 3;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 8;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 20;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);

        int k = heap.removeAndReturnKey();
        System.out.println("heap.removeAndReturnKey()=" + k + ";heap:" + heap);
        k = heap.removeAndReturnKey();
        System.out.println("heap.removeAndReturnKey()=" + k + ";heap:" + heap);

        key = 20;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key + ", " + data + ");heap:" + heap);
        key = 10;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key + ", " + data + ");heap:" + heap);
        key = 15;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        key = 11;
        data = key;
        heap.insert(key, data);
        System.out.println("heap.insert(" + key +", " + data + ");heap:" + heap);
        int newKey = 0;
        //case 1: root stay in max
        key = 20;
        newKey = 30;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);
        //case 2: middle node become smaller
        key = 11;
        newKey = 6;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);
        //case 3 middle node become lager
        key = 8;
        newKey = 40;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);
        //case 4 root become smaller
        key = 40;
        newKey = 1;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);
        //case 5 leaf become lager
        key = 1;
        newKey = 100;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);
        //case 5 leaf become smaller
        key = 2;
        newKey = 1;
        heap.change(key, newKey);
        System.out.println("heap.change(" + key +", " + newKey + ");heap:" + heap);

        SwingTreeGraphicer graphicer = new SwingTreeGraphicer(heap);
    }

    static void test_HeapSort(){
        Integer[] src = {100, 20, 101, -10, 28, 3, 4, 10, -10};
        System.out.println("Before HeapSort:\tsrc=" + Arrays.toString(src));
        HeapSort.sort(src);
        System.out.println("After HeapSort:\tsrc=" + Arrays.toString(src));
    }

    static void test_calculatePath(){
        for(int i = 2; i < 10; i++){
            System.out.println(i + "-path:\t" + LinkHeapStrategy.calculatePath(i));
        }
    }

    public static void main(String[] args) {
//        test_ArrayHeapStrategy();
//        test_calculatePath();
//        test_LinkHeapStrategy();
        test_HeapSort();
    }
}


class Heap<K extends Comparable<? super K>, V> implements IGraphicTree<K, V>{

    static class Item<K extends Comparable<? super K>, V> implements IGraphicTreeNode<K, V>{
        final static int MAX_CHILDS = 2;

        K key = null;
        V data = null;

        Item(K k, V v){
            this.key = k;
            this.data = v;
        }

        boolean isTheSamKey(K key){
            return this.key.equals(key);
        }

        V updateData(V data){
            V old = this.data;
            this.data = data;
            return old;
        }

        @Override
        public String toString() {
            return "{key:" + this.key + ", data:" + this.data + "}" ;
        }

        @Override
        public List<K> getKeys() {
            List<K> keys = new ArrayList<>();
            keys.add(this.key);
            return keys;
        }

        @Override
        public List<V> getDatas() {
            List<V> datas = new ArrayList<>();
            datas.add(this.data);
            return datas;
        }

        @Override
        public int getMaxChildNum() {
            return MAX_CHILDS;
        }

        @Override
        public List<IGraphicTreeNode<K, V>> getAllChildrenIncludeNull() {
            return (List)(this.childrenIncludeNull);
        }

        /**
         * a helper function to support getAllChildrenIncludeNull
         * as children information is missing in item itself definition.
         * every time when the client(Heap here) use this class
         * client is responsible to update it's items children.
         * some of subclass of this class may need this function,
         * some others may not need it, if no need it recommended
         * to override it and let it throw Exception.
         */
        List<Item<K, V>> childrenIncludeNull = null;
        void updateChildrenIncludeNull(List<Item<K, V>> childs){
            this.childrenIncludeNull = childs;
        }
    }

    private HeapStrategy<K, V> strategy = null;

    Heap(HeapStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public IGraphicTreeNode getRoot() {
        return this.strategy.getRoot();
    }

    @Override
    public IGraphicTree newEmptyTree() {
        if(this.strategy instanceof ArrayHeapStrategy){
            return new Heap(new ArrayHeapStrategy());
        }else if(this.strategy instanceof LinkHeapStrategy){
            return new Heap(new LinkHeapStrategy());
        }
        else{
            throw new RuntimeException("Under constructor.");
        }
    }

    @Override
    public void insert(K key, V data){
        this.strategy.insert(key, data);
    }

    void change(K key, K newKey){
        this.strategy.change(key, newKey);
    }

    K removeAndReturnKey(){
        Item<K, V> itm = this.strategy.remove();
        return null == itm ? null : itm.key;
    }

    V removeAndReturnData(){
        Item<K, V> itm = this.strategy.remove();
        return null == itm ? null : itm.data;
    }

    @Override
    public String toString() {
        return this.strategy.toString();
    }
}

interface HeapStrategy<K extends Comparable<? super K>, V>{
    void insert(K key, V data);

    void change(K key, K newKey);

    Heap.Item<K, V> remove();

    Heap.Item<K, V> getRoot();
}

/**
 * ! equals key not been considered to support
 * @param <K>
 * @param <V>
 */
class ArrayHeapStrategy<K extends Comparable<? super K>, V> implements HeapStrategy<K, V>{

    List<Heap.Item<K, V>> store = new ArrayList<>();

    @Override
    public void insert(K key, V data) {
        Heap.Item<K, V> item = new Heap.Item<>(key, data);
        this.store.add(item);
        this.heapfyUp(item, this.store.size() - 1);
        this.updateChildrenOfItem();
    }

    void heapfyUp(Heap.Item<K, V> item, int index) {
        int pareIndex = 0;
        while(0 < index){
            pareIndex = (index - 1) / 2;
            if(0 > this.store.get(pareIndex).key.compareTo(item.key)){
                this.store.set(index, this.store.get(pareIndex));
            }else{
                break;
            }
            index = pareIndex;
        }
        this.store.set(index, item);
    }

    @Override
    public void change(K key, K newKey) {
        int size = this.store.size();
        Heap.Item<K, V> item = null;
        for(int i = 0; i < size; i++){
            if((item = this.store.get(i)).isTheSamKey(key)){
                item.key = newKey;
                this.heapfyUp(item, i);
                this.heapfyDown(this.store.get(i), i);
                this.updateChildrenOfItem();
                break;
            }
        }
    }

    @Override
    public Heap.Item<K, V> remove() {
        if(this.store.isEmpty()){
            return null;
        }else{
            Heap.Item<K, V> item = this.store.get(0);
            if(1 < this.store.size()){
                this.store.set(0, this.store.remove(this.store.size() - 1));
                heapfyDown(this.store.get(0), 0);
            }else{
                this.store.remove(0);
            }
            this.updateChildrenOfItem();
            return item;
        }
    }

    @Override
    public Heap.Item<K, V> getRoot() {
        return this.store.isEmpty() ? null : this.store.get(0);
    }

    void heapfyDown(Heap.Item<K, V> item, int index) {
        int leftIndex = 0;
        int size = this.store.size();
        while(index < size){
            leftIndex = 2 * index + 1;
            if(leftIndex >= size){
                this.store.set(index, item);
                break;
            }
            Heap.Item<K, V> left = this.store.get(leftIndex);
            Heap.Item<K, V> maxChild = left;
            if(leftIndex + 1 < size){
                Heap.Item<K, V> right = this.store.get(leftIndex + 1);
                if(0 < maxChild.key.compareTo(right.key)){

                }else{
                    maxChild = right;
                }
            }

            if(0 < item.key.compareTo(maxChild.key)){
                this.store.set(index, item);
                break;
            }else{
                this.store.set(index, maxChild);
                index = maxChild == left ? leftIndex : leftIndex + 1;
            }
        }
    }

    void updateChildrenOfItem(){
        int size = this.store.size();
        for(int i = 0; i < size; i++){
            Heap.Item<K, V> item = this.store.get(i);
            List<Heap.Item<K, V>> childs = new ArrayList<>();
            item.updateChildrenIncludeNull(childs);
            //left
            if(size > 2 * i + 1){
                childs.add(this.store.get(2 * i + 1));
            }else{
                childs.add(null);
            }
            //right
            if(size > 2 * i + 2){
                childs.add(this.store.get(2 * i + 2));
            }else{
                childs.add(null);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        int size = this.store.size();
        if(0 < size){
            List<Heap.Item<K, V>> items = new ArrayList<>(1);
            List<Integer> indices = new ArrayList<>(1);
            items.add(this.store.get(0));
            indices.add(0);
            this.prettyString(items, indices, buff, 1);
        }
        buff.append("]");
        return buff.toString();
    }

    void prettyString(List<Heap.Item<K, V>> pares, List<Integer> indices, StringBuilder buff
        , int height){
        if(pares.isEmpty()){
            return;
        }
        int size = pares.size();
        List<Heap.Item<K, V>> childs = new ArrayList<>(2 * size);
        List<Integer> childIndices = new ArrayList<>(2 * size);
        buff.append("\n\t" + height + ":");
        int length = this.store.size();
        for(int i = 0; i < size; i++){
            buff.append(pares.get(i)).append("\t");
            int index = indices.get(i);
            if(length > (index = 2 * index + 1)){
                childs.add(this.store.get(index));
                childIndices.add(index);
            }
            if(length > (index = index + 1)){
                childs.add(this.store.get(index));
                childIndices.add(index);
            }
        }
        this.prettyString(childs, childIndices, buff, height + 1);
    }
}

/**
 * ! equals key not been considered to support
 * @param <K>
 * @param <V>
 */
class LinkHeapStrategy<K extends Comparable<? super K>, V> implements HeapStrategy<K, V>{

    static class Item<K extends Comparable<? super K>, V> extends Heap.Item<K, V>{
        final static int DIRECT_LEFT = 0;
        final static int DIRECT_RIGHT = 0;

        Item<K, V> left = null;
        Item<K, V> right = null;

        Item(K key, V v){
            super(key, v);
        }

        @Override
        public List<IGraphicTreeNode<K, V>> getAllChildrenIncludeNull() {
            List<IGraphicTreeNode<K, V>> list = new ArrayList<>();
            list.add(left);
            list.add(right);
            return list;
        }

        @Override
        void updateChildrenIncludeNull(List<Heap.Item<K, V>> childs) {
            throw new RuntimeException("Never use this function in this sub class");
        }

        boolean isLeaf(){
            return null == this.left && null == this.right;
        }
    }

    Item<K, V> root = null;
    int size = 0;

    @Override
    public void insert(K key, V data) {
        Item<K, V> node = new Item<>(key, data);
        if(null == this.getRoot()){
            this.root = node;
        }else{
            List<Integer> path = calculatePath(this.size + 1);
            Item<K, V> pare = (Item)this.getRoot();
            int len = path.size() - 1;
            for(int i = 0; i < len; i++){
                pare = Item.DIRECT_LEFT == path.get(i) ? pare.left : pare.right;
            }
            if(Item.DIRECT_LEFT == path.get(len)){
                pare.left = node;
            }else{
                pare.right = node;
            }
//            this.insertFromRoot(path, node);
            this.heapifyDownFromRoot(path, node);
        }
        this.size++;
    }

    /**
     * node has not been add as last node yet
     * @param path
     * @param node
     */
    void insertFromRoot(List<Integer> path, Item<K, V> node){
        Item<K, V> pare = null;
        Item<K, V> current = (Item)this.getRoot();
        int len = path.size();
        boolean isLeafChld = false;
        for(int i = -1; i < len; i++){
            if(null == current){
                if(isLeafChld){
                    pare.left = node;
                }else{
                    pare.right = node;
                }
                return;
            }
            if(0 < current.key.compareTo(node.key)){
                //do nothing
            }else{
                Item<K, V> tmp = current;

                node.left = current.left;
                node.right = current.right;

                if(null != pare){
                    if(isLeafChld){
                        pare.left = node;
                    }else{
                        pare.right = node;
                    }
                }else{
                    this.root = node;
                }


                current = node;

                tmp.left = null;
                tmp.right = null;
                node = tmp;
            }
            pare = current;

            isLeafChld = path.get(i + 1) == Item.DIRECT_LEFT;
            current = path.get(i + 1) == Item.DIRECT_LEFT ? current.left : current.right;
        }
    }

    /**
     * node has been added as last node
     * @param path
     * @param node
     */
    void heapifyDownFromRoot(List<Integer> path, Item<K, V> node){
        Item<K, V> current = (Item)this.getRoot();
        if(null == current){
            this.root = node;
            return;
        }
        int len = path.size();
        boolean isLeftChld = false;
        Item<K, V> newNode = node;
        Item<K, V> newNodeLeft = node.left;
        Item<K, V> newNodeRight = node.right;
        Item<K, V> pare = current;
        Item<K, V> tmp = null;
        if(0 > pare.key.compareTo(node.key)){
            node.left = pare.left;
            node.right = pare.right;
            this.root = node;
            tmp = pare;
            pare = node;
            node = tmp;
            node.left = newNodeLeft;
            node.right = newNodeRight;
        }

        for(int i = 0; i < len; i++){
            if(path.get(i) == Item.DIRECT_LEFT){
                current = pare.left;
                isLeftChld = true;
            }else{
                current = pare.right;
                isLeftChld = false;
            }

            if(current == newNode){
                if(isLeftChld){
                    pare.left = node;
                }else{
                    pare.right = node;
                }
                break;
            }

            if(0 > current.key.compareTo(node.key)){
                node.left = current.left;
                node.right = current.right;
                if(isLeftChld){
                    pare.left = node;
                }else{
                    pare.right = node;
                }
                tmp = current;
                current = node;
                node = tmp;
                node.left = newNodeLeft;
                node.right = newNodeRight;
            }else{

            }
            pare = current;
        }
    }

    static List<Integer> calculatePath(int size){
        List<Integer> path = new ArrayList<>();
        while(1 <= size){
            path.add(0, size % 2);
            size /= 2;
        }
        path.remove(0);
        return path;
    }

    @Override
    public void change(K key, K newKey) {
        Item<K, V> pare = null;
        Item<K, V> found = null;
        List<Integer> path = new ArrayList<>();

        Item<K, V>[] founds = this.find(null, (Item)this.getRoot(), key, path);
        found = null == founds ? null : founds[1];
        pare = null == founds ? null: founds[0];
        if(null != found){
            found.key = newKey;
            if(null != pare){
                if(0 < pare.key.compareTo(found.key)){
                    this.heapifyDownFromNodeIfNeeded(found, pare, path.get(path.size() - 1) == Item.DIRECT_LEFT);
                }else{
                    this.heapifyDownFromRoot(path, found);
                }
            }else{
                this.heapifyDownFromNodeIfNeeded(found, pare, false);
            }
        }
    }

    Item<K, V>[] find(Item<K, V> pare, Item<K, V> current, K key, List<Integer> return_path){
        if(current.isTheSamKey(key)){
            return new Item[]{
                    pare, current};
        }else{
            if(null != current.left){
                return_path.add(Item.DIRECT_LEFT);
                return find(current, current.left, key, return_path);
            }else if(null != current.right){
                return_path.add(Item.DIRECT_RIGHT);
                return find(current, current.right, key, return_path);
            }else{
                return null;
            }
        }
    }

    void heapifyDownFromNodeIfNeeded(Item<K, V> node, Item<K, V> pare, boolean isLeftChild){
        if(node.isLeaf()){
            return;
        }
        Item<K, V> maxChild = node.left;
        if(null != node.right){
            if(0 < node.right.key.compareTo(maxChild.key)){
                maxChild = node.right;
            }
        }
        if(0 > node.key.compareTo(maxChild.key)){
            //swap node and maxChild
            if(null != pare){
                if(isLeftChild){
                    pare.left = maxChild;
                }else{
                    pare.right = maxChild;
                }
            }else{
                this.root = maxChild;
            }
            Item<K, V> l = maxChild.left;
            Item<K, V> r = maxChild.right;
            if(maxChild == node.left){
                maxChild.left = node;
                maxChild.right = node.right;
                isLeftChild = true;
            }else{
                maxChild.right = node;
                maxChild.left = node.left;
                isLeftChild = false;
            }
            node.left = l;
            node.right = r;
            this.heapifyDownFromNodeIfNeeded(node, maxChild, isLeftChild);
        }
    }


    @Override
    public Heap.Item<K, V> remove() {
        Item<K, V> tmp = (Item)this.getRoot();
        if(null == tmp){
            return null;
        }
        if(tmp.isLeaf()){
            this.root = null;
        }

        this.moveUp(null, tmp.left, tmp.right, false);

        this.size--;
        return tmp;
    }

    void moveUp(Item<K, V> pare, Item<K, V> currentsLeft, Item<K, V> currentsRight, boolean isLeftChild){
        if(null == currentsLeft && null == currentsRight){
            return;
        }
        Item<K, V> nodeMoveUp = null;
        if(null == currentsRight || 0 < currentsLeft.key.compareTo(currentsRight.key)){
            nodeMoveUp = currentsLeft;
        }else{
            nodeMoveUp = currentsRight;
        }
        Item<K, V> left = nodeMoveUp.left;
        Item<K, V> right = nodeMoveUp.right;
        if(nodeMoveUp == currentsLeft){
            nodeMoveUp.right = currentsRight;
            //must not set left as left is itself
            nodeMoveUp.left = null;
        }else{
            nodeMoveUp.left = currentsLeft;
            //must not set right as right is itself
            nodeMoveUp.right = null;
        }

        //if pare == null, current node is root
        if(null != pare){
            if(isLeftChild){
                pare.left = nodeMoveUp;
            }else{
                pare.right = nodeMoveUp;
            }
        }else{
            this.root = nodeMoveUp;
        }
        this.moveUp(nodeMoveUp, left, right, nodeMoveUp == currentsLeft);
    }

    @Override
    public Heap.Item<K, V> getRoot() {
        return this.root;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("{");
        List<Item<K, V>> currents = new ArrayList<>();
        List<Item<K, V>> childs = new ArrayList<>();
        currents.add((Item)this.getRoot());
        while(!currents.isEmpty()){
            buff.append("\n\t");
            for(Item<K, V> node: currents){
                if(null != node){
                    buff.append(node).append("\t");
                    childs.addAll((List)node.getAllChildrenIncludeNull());
                }
            }
            List<Item<K, V>> tmp = currents;
            currents = childs;
            tmp.clear();
            childs = tmp;
        }
        buff.append("}");
        return buff.toString();
    }
}

class HeapSort{

    static<T extends Comparable<? super T>> void sort(T[] src){
        for(int i = 0; i < src.length; i++){
            heapify(src, 0, i);
        }
        System.out.println("After heapify:\tsrc=" + Arrays.toString(src));
        for(int i = 0; i < src.length; i++){
            swapChildIfneeded(src, i, src.length - 1);
        }
    }

    static <T extends Comparable<? super T>> void heapify(T[] src, int from, int end){
        int pareIndex = 0;
        while(end > from){
            pareIndex = (end - 1) / 2;
            if(pareIndex < from){
                break;
            }
            if(0 > src[pareIndex].compareTo(src[end])){
                //swap
                T tmp = src[pareIndex];
                src[pareIndex] = src[end];
                src[end] = tmp;
            }
            end = pareIndex;
        }
    }

    static <T extends Comparable<? super T>> void swapChildIfneeded(T [] src, int from, int end){
        int left = 2 * from + 1;
        int right = 2 * from + 2;
        if(left > end){
            return;
        }else{
            if(right > end){
                return;
            }else{
                if(0 < src[right].compareTo(src[left])){
                    T tmp = src[right];
                    src[right] = src[left];
                    src[left] = tmp;
                    trickDown(src, right, end);
                }
            }
        }
    }

    static <T extends Comparable<? super T>> void trickDown(T [] src, int from, int end){
        int left = 2 * from + 1;
        int right = 2 * from + 2;
        int maxChildIndex = left;
        if(right <= end){
            maxChildIndex = 0 < src[right].compareTo(src[left]) ? right : left;
        }
        if(end >= maxChildIndex && 0 > src[from].compareTo(src[maxChildIndex])){
            T tmp = src[from];
            src[from] = src[maxChildIndex];
            src[maxChildIndex] = tmp;
            trickDown(src, maxChildIndex, end);
        }
    }
}

