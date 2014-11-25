package mx.algorithm.tree_rb;


import mx.algorithm.tree.IGraphicTree;
import mx.algorithm.tree.IGraphicTreeNode;
import mx.algorithm.tree.SwingTreeGraphicer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxiong on 9/18/14.
 */
public class Main {

    static void showTree(){
        BRTree<Integer, Integer> tree = new BRTree<Integer, Integer>();
        tree.insert(50, null);
        tree.insert(25, null);
        tree.insert(75, null);
        tree.insert(12, null);
        tree.insert(6, null);
        tree.insert(18, null);
        tree.insert(120, null);
        tree.insert(1, null);
        tree.insert(10, null);
        tree.insert(129, null);
        tree.insert(40, null);
        tree.insert(-1, null);
        tree.insert(-10, null);
        tree.insert(0, null);
        tree.insert(2, null);
        tree.insert(11, null);
        tree.insert(3, null);
        tree.insert(4, null);
        tree.insert(5, null);

        System.out.println("tree=\t" + tree);
        SwingTreeGraphicer graphicer = new SwingTreeGraphicer(tree);
    }

    static void test_BRTree(){
        System.out.println("=============>1<=============");
        BRTree<Integer, Integer> tree = new BRTree<Integer, Integer>();
        tree.insert(50, null);
        System.out.println("insert 50,\t tree=\t" + tree);
        tree.insert(25, null);
        System.out.println("insert 25,\t tree=\t" + tree);
        tree.insert(75, null);
        System.out.println("insert 75,\t tree=\t" + tree);
        tree.insert(12, null);
        System.out.println("insert 12,\t tree=\t" + tree);
        tree.insert(6, null);
        System.out.println("insert 6,\t tree=\t" + tree);


        System.out.println("=============>2<=============");
        BRTree<Integer, Integer> tree1 = new BRTree<Integer, Integer>();
        tree1.insert(50, null);
        System.out.println("insert 50,\t tree1=\t" + tree1);
        tree1.insert(25, null);
        System.out.println("insert 25,\t tree1=\t" + tree1);
        tree1.insert(75, null);
        System.out.println("insert 75,\t tree1=\t" + tree1);
        tree1.insert(12, null);
        System.out.println("insert 12,\t tree1=\t" + tree1);
        tree1.insert(18, null);
        System.out.println("insert 18,\t tree1=\t" + tree1);

        System.out.println("=============>3<=============");
        BRTree<Integer, Integer> tree2 = new BRTree<Integer, Integer>();
        tree2.insert(50, null);
        System.out.println("insert 50,\t tree2=\t" + tree2);
        tree2.insert(25, null);
        System.out.println("insert 25,\t tree2=\t" + tree2);
        tree2.insert(75, null);
        System.out.println("insert 75,\t tree2=\t" + tree2);
        tree2.insert(12, null);
        System.out.println("insert 12,\t tree2=\t" + tree2);
        tree2.insert(37, null);
        System.out.println("insert 37,\t tree2=\t" + tree2);
        tree2.insert(6, null);
        System.out.println("insert 6,\t tree2=\t" + tree2);
        tree2.insert(18, null);
        System.out.println("insert 18,\t tree2=\t" + tree2);
        tree2.insert(3, null);
        System.out.println("insert 3,\t tree2=\t" + tree2);

        System.out.println("=============>4<=============");
        BRTree<Integer, Integer> tree3 = new BRTree<Integer, Integer>();
        tree3.insert(50, null);
        System.out.println("insert 50,\t tree3=\t" + tree3);
        tree3.insert(25, null);
        System.out.println("insert 25,\t tree3=\t" + tree3);
        tree3.insert(75, null);
        System.out.println("insert 75,\t tree3=\t" + tree3);
        tree3.insert(12, null);
        System.out.println("insert 12,\t tree3=\t" + tree3);
        tree3.insert(37, null);
        System.out.println("insert 37,\t tree3=\t" + tree3);
        tree3.insert(31, null);
        System.out.println("insert 31,\t tree3=\t" + tree3);
        tree3.insert(43, null);
        System.out.println("insert 43,\t tree3=\t" + tree3);
        tree3.insert(28, null);
        System.out.println("insert 28,\t tree3=\t" + tree3);

        System.out.println("=============>5<=============");
        tree3.insert(20, null);
        System.out.println("insert 20,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(25, null);
        System.out.println("insert 25,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(26, null);
        System.out.println("insert 26,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(27, null);
        System.out.println("insert 27,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(21, null);
        System.out.println("insert 21,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(22, null);
        System.out.println("insert 22,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(23, null);
        System.out.println("insert 23,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(19, null);
        System.out.println("insert 19,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(15, null);
        System.out.println("insert 15,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(14, null);
        System.out.println("insert 14,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(13, null);
        System.out.println("insert 13,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(12, null);
        System.out.println("insert 12,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );
        tree3.insert(11, null);
        System.out.println("insert 11,\t tree3=\t" + tree3);
        System.out.println("tree3.root= t" + tree3.root );



    }


    public static void main(String[] args) {
//        test_BRTree();
        showTree();
    }
}

class Util{

    static void DFS_In(BRTree.Node n, StringBuilder sb){
        if(null == n){
            return ;
        }
        DFS_In(n.left, sb);

        sb.append(n);

        DFS_In(n.right, sb);
    }

    static void DFS_In_Key_Pretty(BRTree.Node n, StringBuilder sb){
        DFS_In_Key(n, sb);
        if('-' == sb.charAt(sb.length() - 1)){
            sb.replace(sb.length() - 1, sb.length(), "");
        }
    }

    static void DFS_In_Key(BRTree.Node n, StringBuilder sb){
        if(null == n){
            return ;
        }
        DFS_In_Key(n.left, sb);

        sb.append(n.key).append("-");

        DFS_In_Key(n.right, sb);
    }
}

class BRTree<K extends Comparable<? super K>, V> implements
        IGraphicTree<K, V>{
    @Override
    public IGraphicTreeNode getRoot() {
        return this.root;
    }

    static class Node<K extends Comparable<? super K>, V>
            implements IGraphicTreeNode<K, V> {
        final static String[] COLOR_NAME = {"RED", "BLACK"};

        K key = null;
        V data = null;
        byte color = Color.RED;

        //fast find parent because regression recall may happened after rotating
        Node<K, V> pare = null;

        Node<K, V> left = null;
        Node<K, V> right = null;

        //pointer to next node which key == current node's key
        Node<K, V> nextNodeWithEqualsKey = null;

        Node(K key, V data){
            this.key = key;
            this.data = data;
        }

        boolean isRed(){
            return Color.RED == this.color;
        }

        void changeColor(){
            this.color = Color.RED == this.color ? Color.BLACK : Color.RED;
        }

        @Override
        public String toString() {
            return "[" + (null == this.left ? "" : "<-")
                    + this.key + ":" + COLOR_NAME[this.color]
                    + (null == this.right ? "" : "->") + "]";
        }

        @Override
        public List<K> getKeys() {
            List<K> keys = new ArrayList<K>();
            keys.add(key);
            return keys;
        }

        @Override
        public List<V> getDatas() {
            List<V> datas = new ArrayList<V>();
            datas.add(data);
            return datas;
        }

        @Override
        public int getMaxChildNum() {
            return 2;
        }

        @Override
        public List<IGraphicTreeNode<K, V>> getAllChildrenIncludeNull() {
            List<IGraphicTreeNode<K, V>> cs = new ArrayList<IGraphicTreeNode<K, V>>();
//            if(null != this.left){
                cs.add(this.left);
//            }
//            if(null != this.right){
                cs.add(this.right);
//            }
            return cs;
        }

        static class Color{
           final static byte RED = 0;
           final static byte BLACK = 1;
       }
    }

    Node<K, V> root = null;

    V find(K key){
        //TODO
        return null;
    }


    public void insert(K key, V data){
        Node<K, V> toBeAdded = new Node<K, V>(key, data);
        if(null == this.root){
            this.root = toBeAdded;
            this.root.changeColor();
        }else{
            this.insert(this.root, toBeAdded);
        }
    }

    @Override
    public IGraphicTree newEmptyTree() {
        return new BRTree();
    }

    void insert(Node<K, V> current, Node<K, V> toBeAdded){
        if(//null != current &&
                0 == current.key.compareTo(toBeAdded.key)){
            toBeAdded.color = current.color;
            Node<K, V> tmp = current;
            while(null != tmp.nextNodeWithEqualsKey){
                tmp = tmp.nextNodeWithEqualsKey;
            }
            tmp.nextNodeWithEqualsKey = toBeAdded;
            //no need set the parent node
            //toBeAdded.pare = current.pare;
            return;
        }

        Node<K, V> pare = current.pare;
        if(0 < current.key.compareTo(toBeAdded.key) && null == current.left){
            current.left = toBeAdded;
            toBeAdded.pare = current;
            if(current.isRed()){
                rotating(pare.pare, pare, current, toBeAdded);
            }
            return;
        }else  if(0 > current.key.compareTo(toBeAdded.key) && null == current.right){
            current.right = toBeAdded;
            toBeAdded.pare = current;
            if(current.isRed()){
                rotating(pare.pare, pare, current, toBeAdded);
            }
            return;
        }

        if(null != current.left && null != current.right
                && current.left.isRed() && current.right.isRed()){
            current.left.changeColor();
            current.right.changeColor();
            if(null == pare){
                // current is root
            }else{
                current.changeColor();
                if(pare.isRed() && current.isRed()){

                    if(isOutterDescendants(pare.pare, pare, current)){
                        rotating(pare.pare.pare, pare.pare, pare, current);
//                            System.out.println("After rotating: tree is:" + this.toString());
//                            System.out.println("After change Color: tree is:" + this.toString() + ", c=" + pare);
                        //if is outter descendants parent replace grand parent, the structure after grand parent is changed
                        //start with parent
                        this.insert(pare, toBeAdded);
                    }else{
                        rotating(pare.pare.pare, pare.pare, pare, current);
//                            System.out.println("After change Color: tree is:" + this.toString() + ", c=" + current);
                        //if is inner descendants current replace grand parent, the structure after grand parent is changed
                        //start with current
                        this.insert(current, toBeAdded);
                    }
                    return;
                }
            }
        }
        if(0 < current.key.compareTo(toBeAdded.key)){
            this.insert(current.left, toBeAdded);
        }else{
            this.insert(current.right, toBeAdded);
        }
    }


    boolean isLeaf(Node<K, V> node){
        return null == node.left && null == node.right;
    }

    boolean isRoot(Node<K, V> node){
        return this.root == node;
    }


    void rotating(Node<K, V> grandPare, Node<K, V> pare,
                 Node<K, V> current, Node<K, V> toBeAdded){
        if(isOutterDescendants(pare, current, toBeAdded)){
            rotatingGrangPareWithOutterDescendants(grandPare, pare, current, toBeAdded);
        }else{
            rotatingGrandPareWithInnerDescendants(grandPare, pare, current, toBeAdded);
        }
    }

    static <K extends Comparable<? super  K>, V>
        boolean isOutterDescendants(Node<K, V> grandPare, Node<K, V> pare, Node<K, V> current){
        return (grandPare.left == pare && pare.left == current) || (grandPare.right == pare && pare.right == current);
    }

    static <K extends Comparable<? super  K>, V>
        boolean isInnerDescendants(Node<K, V> grandPare, Node<K, V> pare, Node<K, V> current){
        return !isOutterDescendants(grandPare, pare, current);
    }


    void rotatingGrangPareWithOutterDescendants(Node<K, V> grandPare, Node<K, V> pare,
                                                    Node<K, V> current, Node<K, V> toBeAdded){
        pare.changeColor();
        current.changeColor();
        replacePare(grandPare, pare, current);
        if(current.left == toBeAdded){

            if(null != current.right)
                current.right.pare = pare;
            pare.pare = current;

            //rotating ==>right
            //if toBeAdded is the new added one, current.right must = null
            //if to BeAdded is an node has color changed, current.right may != null
            pare.left = current.right;
            current.right = pare;
        }else{
            if(null != current.left)
                current.left.pare = pare;
            pare.pare = current;

            //rotating ==>left
            pare.right = current.left;
            current.left = pare;
        }

        if(this.root == pare){
            this.root = current;
        }
    }


    void rotatingGrandPareWithInnerDescendants(Node<K, V> grandPare, Node<K, V> pare,
                                                Node<K, V> current, Node<K, V> toBeAdded){
        rotatingToOutterDescendants(pare, current, toBeAdded);
        rotatingGrangPareWithOutterDescendants(grandPare, pare, toBeAdded, current);
    }

    static <K extends Comparable<? super  K>, V>
        void replacePare(Node<K, V> grandPare, Node<K, V> pare, Node<K, V> current){
        current.pare = grandPare;
        if(null != grandPare){
            if(grandPare.left == pare){
                grandPare.left = current;
            }else{
                grandPare.right = current;
            }
        }
    }

    static <K extends Comparable<? super  K>, V>
        void rotatingToOutterDescendants(Node<K, V> pare, Node<K, V> current, Node<K, V> toBeAdded){
        if(pare.left == current){

            toBeAdded.pare = pare;
            if(null != toBeAdded.left)
                toBeAdded.left.pare = current;
            current.pare = toBeAdded;

            //if toBeAdded is the new added one, toBeAdded.left must = null
            //if to BeAdded is an node has color changed, toBeAdded.left may != null
            pare.left = toBeAdded;
            current.right = toBeAdded.left;
            toBeAdded.left = current;

        }else{
            toBeAdded.pare = pare;
            if(null != toBeAdded.right)
                toBeAdded.right.pare = current;
            current.pare = toBeAdded;

            pare.right = toBeAdded;
            current.left = toBeAdded.right;
            toBeAdded.right = current;
        }
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        Util.DFS_In(this.root, buff);
        return buff.toString();
    }
}