package mx.algorithm.tree_234;

import mx.algorithm.tree.IGraphicTree;
import mx.algorithm.tree.IGraphicTreeNode;
import mx.algorithm.tree.SwingTreeGraphicer;

import java.util.*;

/**
 * Created by hxiong on 9/26/14.
 */
public class Main {

    static void showTree234(){
        Tree234<Integer, Integer> tree = testTree234();
        for(int i = 10; i < 17; i++){
            tree.insert(i, null);
        }
        tree.insert(-3, null);
        System.out.println("------");
        Util.print(tree);
        Util.DFS_In_Print(tree);

        SwingTreeGraphicer graphicer = new SwingTreeGraphicer(tree);
        tree.insert(20, null);
        graphicer.update(tree);
    }

    static Tree234<Integer, Integer> testTree234(){

        Tree234<Integer, Integer> tree = new Tree234<>();

        int num = 0;
        System.out.println("=========>1<=========");
        num = 1;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = 2;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = 3;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);

        System.out.println("=========>2<=========");
        num = 4;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = 5;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = 6;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);

        System.out.println("=========>3<=========");
        num = 0;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = -1;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);
        num = -2;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Util.print(tree);
        Util.DFS_In_Print(tree);

        return tree;
    }

    public static void main(String[] args) {
//        testTree234();
        showTree234();
    }
}

class Tree234<K extends Comparable<? super K>, V>
    implements IGraphicTree<K, V>{

    @Override
    public IGraphicTreeNode getRoot() {
        return this.root;
    }

    static class Node<K extends Comparable<? super K>, V>
        implements IGraphicTreeNode<K, V>{
        static class Item<K, V>{
            K key = null;
            V data = null;

            Item<K, V> nextEquals = null;

            Item(K key, V data){
                this.key = key;
                this.data = data;
            }

            @Override
            public String toString() {
                return "<" + this.key + ">";
            }
        }

        final static int NUM = 4;

        Item<K, V>[] items = new Item[NUM - 1];
        Node<K, V>[] childs = new Node[NUM];

        void addItem(K key, V data){
            Item<K, V> item = new Item<>(key, data);
//            for(int i = 0; i < this.items.length; i++){
//                if(null == this.items[i]){
//                    items[i] = item;
//                    break;
//                }
//            }
            this.addItem(item);
        }

        void addItem(Item item){
            int maxIndex = 0;
            for(int i = 0; i < this.items.length; i++){
                maxIndex = i;
                if(null == this.items[i]){
                    items[i] = item;
                    break;
                }
            }
            Arrays.sort(items, 0, maxIndex + 1, new Comparator<Item<K, V>>() {
                @Override
                public int compare(Item<K, V> o1, Item<K, V> o2) {
                    return o1.key.compareTo(o2.key);
                }
            });
        }

        void addChild(Node<K, V> node){
            int maxIndex = 0;
            for(int i = 0; i < this.childs.length; i++){
                maxIndex = i;
                if(null == this.childs[i]){
                    this.childs[i] = node;
                    break;
                }
            }

            Arrays.sort(this.childs, 0, maxIndex + 1, new Comparator<Node<K, V>>() {
                @Override
                public int compare(Node<K, V> o1, Node<K, V> o2) {
                    return o1.items[0].key.compareTo(o2.items[0].key);
                }
            });
        }

        boolean insertIfIsEqualsKey(K key, V data){
            for(int i = 0; i < this.items.length; i++){
                if(null == this.items[i])return false;
                if(0 == this.items[i].key.compareTo(key)){
                    Item tmp = this.items[i];
                    while(null != tmp.nextEquals){
                        tmp = tmp.nextEquals;
                    }
                    tmp.nextEquals = new Item(key, data);;
                    return true;
                }
            }
            return false;
        }

        boolean hasFull(){
            return null != this.items[this.items.length - 1];
        }

        boolean isLeaf(){
            return null == this.childs[0];
        }

        @Override
        public List<K> getKeys() {
            List<K> rs = new ArrayList<>();
            for(int i = 0; i < items.length; i++){
                if(null == items[i]){
//                    break;
                    rs.add(null);
                }else{
                    rs.add(items[i].key);
                }
            }
            return rs;
        }

        @Override
        public List<V> getDatas() {
            List<V> rs = new ArrayList<>();
            for(int i = 0; i < items.length; i++){
                if(null == items[i]){
//                    break;
                    rs.add(null);
                }else{
                    rs.add(items[i].data);
                }
            }
            return rs;
        }

        @Override
        public int getMaxChildNum() {
            return NUM;
        }

        @Override
        public List<IGraphicTreeNode<K, V>> getAllChildrenIncludeNull() {
            List<IGraphicTreeNode<K, V>> list = new ArrayList<>(NUM);
            for(int i = 0; i < this.childs.length; i++){
                list.add(this.childs[i]);
            }
            return list;
        }

        public List<IGraphicTreeNode<K, V>> getChildren(){
            List<IGraphicTreeNode<K, V>> list = new ArrayList<>(NUM);
            for(int i = 0; i < this.childs.length; i++){
                if(null == this.childs[i]){
                    break;
                }
                list.add(this.childs[i]);
            }
            return list;
        }

        List<Item> getItems(){
            List<Item> list = new ArrayList<>(NUM - 1);
            for(int i = 0; i < this.items.length; i++){
                if(null == this.items[i]){
                    break;
                }
                list.add(this.items[i]);
            }
            return list;
        }

        @Override
        public String toString() {
            StringBuilder buff = new StringBuilder("{");
            for(int i = 0; i < this.items.length; i++){
                if(null == this.items[i])break;
                buff.append(this.items[i]).append(",");
            }
            buff.append("(");
            for(int i = 0; i < this.childs.length; i++){
                if(null == this.childs[i]){
                    buff.append(" - ");
                }else{
                    buff.append(" + ");
                }
            }
            buff.append(")");
            buff.append("}");
            return buff.toString();
        }
    }


    Node<K, V> root = null;



    public void insert(K key, V data){
        if(null == this.root){
            this.root = new Node<>();
            this.root.addItem(key, data);
        }else{
            this.insert(null, this.root, key, data);
        }
    }

    @Override
    public IGraphicTree newEmptyTree() {
        return new Tree234();
    }

    void insert(Node<K, V> pare, Node<K, V> current, K key, V data){

        if(current.insertIfIsEqualsKey(key, data))return;

        if(!current.hasFull()){
            if(current.isLeaf()){
                current.addItem(key, data);
            }else{
                if(0 < current.items[0].key.compareTo(key)){
                    //first child must always exist
                    this.insert(current, current.childs[0], key, data);
                }else if(0 > current.items[0].key.compareTo(key)){
                    if(null == current.items[1] || 0 < current.items[1].key.compareTo(key)){
                        //second child must always exist
                        this.insert(current, current.childs[1], key, data);
                    }else if(0 > current.items[1].key.compareTo(key)){
                        //third child must always exist
                        this.insert(current, current.childs[2], key, data);
                    }
                    //never has 4th child as it's not full
                }else{
                    //never go here
                    throw new RuntimeException("1.equals key must be resolved already! key=" + key);
                }
            }

        }else{
            if(0 < current.items[1].key.compareTo(key)){
                splitNode(pare, current, key, data);
                this.insert(pare, current, key, data);
            }else if(0 > current.items[1].key.compareTo(key)){
                Node<K, V> right = splitNode(pare, current, key, data);
                this.insert(pare, right, key, data);
            }else{
                //never go here
                throw new RuntimeException("2.equals key must be resolved already! key=" + key);
            }
        }
    }

    Node<K, V> splitNode(Node<K, V> pare, Node<K, V> current, K key, V data){
        Node<K, V> rightSilbing = new Node<>();
        rightSilbing.items[0] = current.items[2];
        current.items[2] = null;
//        rightSilbing.childs[0] = current.childs[2];
//        rightSilbing.childs[1] = current.childs[3];
        rightSilbing.addChild(current.childs[2]);
        rightSilbing.addChild(current.childs[3]);
        current.childs[2] = current.childs[3] = null;

        if(null == pare){
            //current node is root
            assert this.root == current;

            Node<K, V> newRoot = new Node<>();
            pare = this.root = newRoot;
            pare.addChild(current);
        }
        pare.addChild(rightSilbing);

        pare.addItem(current.items[1]);
        current.items[1] = null;

        return rightSilbing;
    }
}

class Util{
    static void print(Tree234 tree){
        List<Tree234.Node> lay1 = new ArrayList<>();
        List<Tree234.Node> lay2 = new ArrayList<>();
        lay1.add(tree.root);
        int lay = 0;
        while(!lay1.isEmpty()){
            System.out.println("\tHeight:" + lay++ + "\t :" + lay1);
            for(Tree234.Node n: lay1){
                lay2.addAll(n.getChildren());
            }
            lay1 = lay2;
            lay2 = new ArrayList<>();
        }
    }

    static void DFS_In_Print(Tree234 tree){
        System.out.println("DFS_In_Print\t:" + DFS_In(tree));
    }

    static String DFS_In(Tree234 tree){
        StringBuilder buff = new StringBuilder();
        DFS_In(tree.root, buff);
        return buff.toString();
    }

    static void DFS_In(Tree234.Node node, StringBuilder buff){
        int tmp = 0;
        List<Tree234.Node.Item> items = node.getItems();
        if(node.isLeaf()){
            tmp = items.size();
            for(int i = 0; i < tmp; i++){
                buff.append(items.get(i).key).append(",");
            }
        }else{
            List<Tree234.Node> childs = node.getChildren();
            tmp = childs.size();
            for(int i = 0; i < tmp; i++){
                switch (i){
                    case 0 : DFS_In(childs.get(i), buff);
                        buff.append(items.get(i).key).append(",");
                        break;
                    case 1 : DFS_In(childs.get(i), buff);
                        if(i < items.size()){
                            buff.append(items.get(i).key).append(",");
                        }
                        break;
                    case 2 : DFS_In(childs.get(i), buff);
                        if(i < items.size()){
                            buff.append(items.get(i).key).append(",");
                        }
                        break;
                    case 3 : DFS_In(childs.get(i), buff);
                        break;
                    default:
                        //should never go here.
                        throw new RuntimeException("should never go here.");
                }
            }
        }
    }
}
