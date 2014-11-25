package mx.algorithm.tree;

import java.util.*;

/**
 * Created by hxiong on 9/3/14.
 */
public class Main {

    static void testTree(){
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        int[] keys = new int[] {5, 10, -1, 11, 0, 8, 2, 7, 3};
        for(int i = 0; i < keys.length; i++){
            tree.insert(keys[i], keys[i]);
        }
        Queue<BinarySearchTree.Node<Integer, Integer>> queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("\t\tqueue=" + queue);
        System.out.println("tree.search_DFS(keys[3])=" + tree.search_DFS(keys[3]));

        System.out.println("tree.findAndRmoveMin(tree.root.left)=" + tree.findAndRmoveMin(tree.root.left, tree.root, true));
        queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("=1=>After[findAndRmoveMin(tree.root.left)]::\n\tqueue=" + queue);

        tree.insert(-1, 7);
        queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("=2=>After[tree.insert(-1, 7)]::\n\tqueue=" + queue);

        tree.delete(2);
        queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("=3=>After[tree.delete(2)]::\n\tqueue=" + queue);

        tree.delete(5);
        queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("=4=>After[tree.delete(5)]::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.ergodic_BFS(queue);
        System.out.println("=5=>ergodic_BFS::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.ergodic_DFS(queue, BinarySearchTree.Order.PREORDER);
        System.out.println("=6.1=>ergodic_DFS(Pre)::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.preOrder(queue);
        System.out.println("=6.2=>preOrder::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.ergodic_DFS(queue, BinarySearchTree.Order.INORDER);
        System.out.println("=7.1=>ergodic_DFS(In)::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.inOrder(queue);
        System.out.println("=7.2=>inOrder::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.ergodic_DFS(queue, BinarySearchTree.Order.POSTORDER);
        System.out.println("=8.1=>ergodic_DFS(Pos)::\n\tqueue=" + queue);

        queue = new LinkedList<>();
        tree.postOrder(queue);
        System.out.println("=8.2=>postOrder::\n\tqueue=" + queue);

    }


    public static void main(String[] args) {
        testTree();
    }
}


class BinarySearchTree<K extends Comparable<? super K>, V>{
    Node<K, V> root = null;

    /**
     * Depth-First-Search(DFS), recursive was used here
     * @param key
     * @return
     */
    public V search_DFS(K key){
        Node<K, V> n = this.search_DFS(key, this.root);
        return null == n ? null : n.data;
    }

    Node<K, V> search_DFS(K key, Node<K, V> node){
        if(null == node){
            return null;
        }
        if(0 == node.key.compareTo(key)){
            return node;
        }else if(0 < node.key.compareTo(key)){
            return search_DFS(key, node.left);
        }else{
            return search_DFS(key, node.right);
        }
    }

    /**
     * Breath-First-Search(BFS), queue was used here
     * @param key
     * @return
     */
    public V search_BFS(K key){
        Node<K, V> node = this.search_BFS(key, this.root, null);
        return null ==  node ? null : node.data;
    }


    /**
     * find minimal node and remove it.
     * @param node
     * @param pareNode
     * @param isLeft
     * @return
     */
    Node<K, V> findAndRmoveMin(Node<K, V> node, Node<K, V> pareNode, boolean isLeft){
        if(null == node){
            return null;
        }
        if(null == node.left){
            if(isLeft){
                //node is leaf of parent's first right child's left children
                pareNode.left = node.right;
            }else{
                //node is parent's first right child
                pareNode.right = node.right;
            }
            node.right = null;
            return node;
        }else{
            return findAndRmoveMin(node.left, node, true);
        }

    }

    /**
     * delete an node according key
     * @param key
     * @return
     */
    public V delete(K key){
        return this.delete(key, this.root, null, true).data;
    }

    Node<K, V> delete(K key, Node<K, V> node, Node<K, V> pareNode, boolean isLeft){
        if(null == node){
            return null;
        }
        if(0 == node.key.compareTo(key)){
            Node<K, V> r = this.findAndRmoveMin(node.right, node, false);
            if(null == r){//if no minimal node found from right child, which means this node has no right children
                if(null != pareNode){//if this node is the root, no parent node
                    if(isLeft){
                        pareNode.left = node.left;
                    }else{
                        pareNode.right = node.left;
                    }
                }
                //update the root pinter
                if(this.isRoot(node)){
                    this.root = node.left;
                }
            }else{
                r.right = node.right;
                r.left = node.left;
                if(null != pareNode){
                    if(isLeft){
                        pareNode.left = r;
                    }else{
                        pareNode.right = r;
                    }
                }
                //update the root pinter
                if(this.isRoot(node)){
                    this.root = r;
                }
            }

            node.right = node.left = null;
            return node;
        }else if(0 < node.key.compareTo(key)){
            return delete(key, node.left, node, true);
        }else{
            return delete(key, node.right, node, false);
        }
    }

    boolean isRoot(Node<K, V> node){
        return null == this.root ? false : null == node ? false : 0 == this.root.key.compareTo(node.key);
    }

    /**
     * insert a node
     * @param key
     * @param value
     */
    public void insert(K key, V value){
        if(null == this.root){
            this.root = new Node<>(key, value);
        }else{
            this.insert(key, value, this.root, null, true);
        }
    }

    void insert(K key, V value, Node<K, V> node, Node<K, V> pareNode, boolean isLeft){
        if(null == node){
            Node<K, V> n = new Node<>(key, value);
            if(isLeft){
                pareNode.left = n;
            }else{
                pareNode.right = n;
            }
            return;
        }
        if(0 < node.key.compareTo(key)){
            this.insert(key, value, node.left, node, true);
        }else{
            this.insert(key, value, node.right, node, false);
        }
    }

    /**
     * update the data of a node
     * @param key
     * @param value
     * @return
     */
    public V update(K key, V value){
        Node<K, V> n = this.search_DFS(key, this.root);
        if(null == n){
            return null;
        }else{
            V v = n.data;
            n.data = value;
            return v;
        }
    }

    /**
     * inOrder ergodic(DFS)
     * @param result a sorted(by Key) queue.
     */
    public void inOrder(Queue<Node<K, V>> result){
        this.inOrder(this.root, result, false, Order.INORDER);
    }

    /**
     * preOrder ergodic(DFS)
     * @param result
     */
    public void preOrder(Queue<Node<K, V>> result){
        this.inOrder(this.root, result, false, Order.PREORDER);
    }

    /**
     * postOrder ergodic(DFS)
     * @param result
     */
    public void postOrder(Queue<Node<K, V>> result){
        this.inOrder(this.root, result, false, Order.POSTORDER);
    }

    enum Order{
        PREORDER, INORDER, POSTORDER;
    }

    void inOrder(Node<K, V> node, Queue<Node<K, V>> result, boolean isPrint, Order oder){
        if(null == node){
            return;
        }else{
            if(Order.PREORDER == oder){
                if(isPrint){
                    System.out.print(node.keyAndData());
                }
                result.add(node);
            }

            this.inOrder(node.left, result, isPrint, oder);

            if(Order.INORDER == oder){
                if(isPrint){
                    System.out.print(node.keyAndData());
                }
                result.add(node);
            }

            this.inOrder(node.right, result, isPrint, oder);

            if(Order.POSTORDER == oder){
                if(isPrint){
                    System.out.print(node.keyAndData());
                }
                result.add(node);
            }
        }
        if(isPrint && this.isRoot(node)){
             System.out.println();
        }
    }

    /**
     * ergodic(BFS)
     * @param result
     */
    public void ergodic_BFS(Queue<Node<K, V>> result){
        this.search_BFS(null, this.root, result);
    }

    /**
     * use queue for BFS
     * @param key
     * @param node
     * @param result
     * @return
     */
    Node<K, V> search_BFS(K key, Node<K, V> node, Queue<Node<K,V>> result){
        Queue<Node<K, V>>queue = new LinkedList<>();
        queue.add(node);
        Node<K, V> finder = null;
        while(!queue.isEmpty()){
            Node<K, V> n = queue.poll();
            if(null != result){
                result.add(n);
            }
            if(null != key && 0 == n.key.compareTo(key)){
                finder = n;
            }
            if(null != n.left){
                queue.add(n.left);
            }
            if(null != n.right){
                queue.add(n.right);
            }
        }
        return finder;
    }

    /**
     * ergodic(DFS)
     * @param result
     * @param order
     */
    public void ergodic_DFS(Queue<Node<K, V>> result, Order order){
        this.ergodic_DFS(this.root, result, order);
    }

    enum STATE{
        NULL, LEFT, RIGHT;
    }

    /**
     * use stack for DFS
     * @param node
     * @param result
     * @param order
     */
    void ergodic_DFS(Node<K, V> node, Queue<Node<K, V>> result, Order order){
        Stack<Node<K, V>> stack = new Stack<>();
        Stack<STATE> states = new Stack<>();
        stack.push(node);
        states.push(STATE.NULL);
        while(!stack.isEmpty()){
            STATE state = states.lastElement();
            Node<K, V> n = stack.lastElement();
            switch (state){
                case NULL: {
                    if(Order.PREORDER == order){
                        result.add(stack.lastElement());
                    }

                    states.pop();
                    states.push(STATE.LEFT);
                    if(null != n.left){
                        stack.push(n.left);
                        states.push(STATE.NULL);
                    }
                    break;
                }
                case LEFT: {
                    if(Order.INORDER == order){
                        result.add(stack.lastElement());
                    }

                    states.pop();
                    states.push(STATE.RIGHT);
                    if(null != n.right){
                        stack.push(n.right);
                        states.push(STATE.NULL);
                    }
                    break;
                }
                case RIGHT: {
                    if(Order.POSTORDER == order){
                        result.add(stack.lastElement());
                    }

                    stack.pop();
                    states.pop();
                    break;
                }
                default: throw new RuntimeException("should never go here!, state=" + state);
            }
        }
    }

    /**
     * Tree Node
     * @param <K> Key
     * @param <V> Data
     */
    static class Node<K, V>{
        V data = null;
        K key  = null;
        Node<K, V> left = null;
        Node<K, V> right = null;

        Node(K k, V v){
            this.key = k;
            this.data = v;
        }

        String keyAndData(){
            return "[key:" + this.key + ", data:" + this.data + "]";
        }

        @Override
        public String toString() {
            return  "[" + (null == left ? "" : "<-") +
                    "key:" + this.key + ", data:" + this.data
                    + (null == right ? "" : "->") + "]";
        }
    }
}