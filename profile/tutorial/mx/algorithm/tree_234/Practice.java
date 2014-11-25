package mx.algorithm.tree_234;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by hxiong on 9/28/14.
 */
public class Practice {

    /**
     * 1: b
     * 2: is a balance tree
     * 3: child2
     * 4: false
     * 5: a, if is root need 2 node
     * 6: split
     * 7: abcd
     * 8: 2
     * 9: 2 black + 2 red
     * 10: d
     * 11: O(LG2N)
     * 12: d
     * 13: n, many of times it's even
     * 14: true
     * 15: a
     */


    static void test_Tree23(){
        Tree23<Integer, Integer> tree = new Tree23<>();

        int num = 0;
        System.out.println("=========>1<=========");
        num = 10;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = 20;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = 30;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);

        System.out.println("=========>2<=========");
        num = 40;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = 50;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = 60;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);

        System.out.println("=========>3<=========");
        num = 70;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = -1;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
        num = -2;
        tree.insert(num, null);
        System.out.println("tree.insert(" + num + ", null);");
        Tree23_Util.print(tree);
        Tree23_Util.DFS_In_Print(tree);
    }

    public static void main(String[] args) {
        test_Tree23();
    }
}

class Tree23<K extends Comparable<? super K>, V>{
    static class Node<K extends  Comparable<? super K>, V>{
        static class Item<K extends  Comparable<? super K>, V>
                implements Comparable<Item<K, V>>{
            K key = null;
            V data = null;

            Item<K, V> nextEquals = null;

            Item(K key, V data){
                this.key = key;
                this.data = data;
            }

            @Override
            public int compareTo(Item<K, V> o) {
                return this.key.compareTo(o.key);
            }
        }

        final static int NUM = 3;

        Item<K, V>[] items = new Item[NUM - 1];
        Node<K, V>[] childs = new Node[NUM];


        boolean isFull(){
            return null != this.items[NUM - 2];
        }

        boolean isLeaf(){
            //null child order should always start 0 then 1 then 2
            return null == this.childs[0];
        }

        void addItem(K key, V data){
            Item item = new Item(key, data);
            this.addItem(item);
        }

        void addItem(Item item){
            int maxIndex = 0;
            for(int i = 0; i < this.items.length; i++){
                maxIndex ++;
                if(null == this.items[i]){
                    this.items[i] = item;
                    break;
                }
            }
            Arrays.sort(this.items, 0, maxIndex);
        }

        void emptyCilds(){
            for(int i = 0; i < this.childs.length; i++){
                this.childs[i] = null;
            }
        }

        boolean insertIfIsEquals(K key, V data){
            for(int i = 0; i < this.items.length; i++){
                if(null == this.items[i]){
                    break;
                }else{
                    if(0 == this.items[i].key.compareTo(key)){
                        Item tmp = this.items[i];
                        while(null != tmp.nextEquals){
                            tmp = tmp.nextEquals;
                        }
                        tmp.nextEquals = new Item(key, data);
                        return true;
                    }
                }
            }
            return false;
        }

        List<Node> getChildren(){
            List<Node> list = new ArrayList<>(NUM);
            for(int i = 0; i < this.childs.length; i++){
                if(null == this.childs[i]){
                    break;
                }
                list.add(this.childs[i]);
            }
            return list;
        }

        List<Item> getItems(){
            List<Item> list = new ArrayList<>(NUM);
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
                if(null == this.items[i]){
                    break;
                }else{
                    buff.append("<").append(this.items[i].key).append(">,");
                }
            }
            buff.append("(");
            for(int i = 0; i < this.childs.length; i++){
                if(null == this.childs[i]){
                    buff.append(" - ");
                }else{
                    buff.append(" + ");
                }
            }
            buff.append(")")
                    .append("}");
            return buff.toString();
        }
    }


    Node<K, V> root = null;

    void insert(K key, V data){
        if(null == this.root){
            this.root = new Node<>();
            this.root.addItem(key, data);
        }else{
            this.insert(new Stack<Node<K, V>>(), this.root, key, data);
        }
    }

    void insert(Stack<Node<K, V>> pareStack, Node<K, V> current, K key, V data){
        if(current.insertIfIsEquals(key, data)){
            return;
        }

        if(current.isLeaf()){
            if(!current.isFull()){
                current.addItem(key, data);
            }else{
                splitAndInsert(pareStack, current, new Node.Item(key, data));
            }
        }else{
            pareStack.push(current);
            if(0 < current.items[0].key.compareTo(key)){
                //child 0 must always exist
                this.insert(pareStack, current.childs[0], key, data);
            }else if(0 > current.items[0].key.compareTo(key)){
                if(null == current.items[1] || 0 < current.items[1].key.compareTo(key)){
                    //child 1 must always exist
                    this.insert(pareStack, current.childs[1], key, data);
                }else if(0 > current.items[1].key.compareTo(key)){
                    //child 2 must always exist
                    this.insert(pareStack, current.childs[2], key, data);
                }else{
                    //never go here
                    throw new RuntimeException("1.should never go here.");
                }
            }else{
                //never go here
                throw new RuntimeException("2.should never go here.");
            }
        }
    }

    Node[] splitAndInsert(Stack<Node<K, V>>pareStack, Node current, Node.Item item){
        Node right = null;
        Node left = null;
        Node.Item upper = null;
        Node pare = pareStack.empty() ? null : pareStack.peek();

        boolean isLeftChild = false;
        if(null != pare){
            if(0 < pare.items[0].compareTo(current.items[0])){
                isLeftChild = true;
            }else{
                isLeftChild = false;
            }
        }

        if(0 < current.items[0].compareTo(item)){
            left = new Node();
            left.items[0] = item;
            right = current;
            upper = current.items[0];
            right.items[0] = current.items[1];
            right.items[1] = null;
        }else if(0 > current.items[0].compareTo(item) && 0 < current.items[1].compareTo(item)){
            left = current;
            right = new Node();
            right.items[0] = current.items[1];
            current.items[1] = null;
            upper = item;
        }else{
            left = current;
            right = new Node();
            right.items[0] = item;
            upper = current.items[1];
            current.items[1] = null;
        }

        current.emptyCilds();

        if(null == pare){
            pare = this.root = new Node<>();
            pare.addItem(upper);
            pare.childs[0] = left;
            pare.childs[1] = right;
        }else if(!pare.isFull()){
            pare.addItem(upper);
            if(isLeftChild){
                pare.childs[2] = pare.childs[1];
                pare.childs[0] = left;
                pare.childs[1] = right;
            }else{
                pare.childs[1] = left;
                pare.childs[2] = right;
            }
        }else{
            pareStack.pop();
            if(0 < pare.items[0].compareTo(left.items[0])){
                Node[] reservedChilds = new Node[]{
                  pare.childs[1], pare.childs[2]
                };
                Node[] ns = splitAndInsert(pareStack, pare, upper);
                ns[0].childs[0] = left;
                ns[0].childs[1] = right;
                ns[1].childs[0] = reservedChilds[0];
                ns[1].childs[1] = reservedChilds[1];
            }else if(0 < pare.items[1].compareTo(left.items[0])){
                Node[] reservedChilds = new Node[]{
                        pare.childs[0], pare.childs[2]
                };
                Node[] ns = splitAndInsert(pareStack, pare, upper);
                ns[0].childs[0] = reservedChilds[0];
                ns[0].childs[1] = left;
                ns[1].childs[0] = right;
                ns[1].childs[1] = reservedChilds[1];
            }else{
                Node[] reservedChilds = new Node[]{
                        pare.childs[0], pare.childs[1]
                };
                Node[] ns = splitAndInsert(pareStack, pare, upper);
                ns[0].childs[0] = reservedChilds[0];
                ns[0].childs[1] = reservedChilds[1];
                ns[1].childs[0] = left;
                ns[1].childs[1] = right;
            }
        }

        return new Node[]{left, right};
    }

}

class Tree23_Util{
    static void print(Tree23 tree){
        List<Tree23.Node> lay1 = new ArrayList<>();
        List<Tree23.Node> lay2 = new ArrayList<>();
        lay1.add(tree.root);
        int lay = 0;
        while(!lay1.isEmpty()){
            System.out.println("\tHeight:" + lay++ + "\t :" + lay1);
            for(Tree23.Node n: lay1){
                lay2.addAll(n.getChildren());
            }
            lay1 = lay2;
            lay2 = new ArrayList<>();
        }
    }

    static void DFS_In_Print(Tree23 tree){
        System.out.println("DFS_In_Print\t:" + DFS_In(tree));
    }

    static String DFS_In(Tree23 tree){
        StringBuilder buff = new StringBuilder();
        DFS_In(tree.root, buff);
        return buff.toString();
    }

    static void DFS_In(Tree23.Node node, StringBuilder buff){
        int tmp = 0;
        List<Tree23.Node.Item> items = node.getItems();
        if(node.isLeaf()){
            tmp = items.size();
            for(int i = 0; i < tmp; i++){
                buff.append(items.get(i).key).append(",");
            }
        }else{
            List<Tree23.Node> childs = node.getChildren();
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
