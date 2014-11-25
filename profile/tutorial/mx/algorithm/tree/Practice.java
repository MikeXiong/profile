package mx.algorithm.tree;

import java.util.*;

/**
 * Created by hxiong on 9/15/14.
 */
public class Practice {
    /**
     * 1: O(lgN)
     * 2: b
     * 3: true
     * 4: 4
     * 5: c
     * 6: Tree / Node
     * 7: a
     * 8: c
     * 9: delete
     * 10: larger / right child
     * 11: b
     * 12: 2*n + 1
     * 13: true
     * 14: encoding/decoding
     * 15: c
     */
    private void practice(){}



    static void test_WordTree(){
        String str = "ABCDEFG";
        WordTree tree = WordTree.fromString(str);
        System.out.println(tree.inOrder(true));

    }

    static void test_BalanceWordTree(){
        String str = "ABCDEFGH";
        BalanceWordTree tree = BalanceWordTree.fromString(str);
        System.out.println(TreeUtil.ergodic(tree.root, false, false, ORDER.IN));
    }

    static void test_FullWordTree(){
        String str = "ABCDEFGH";
        FullWordTree tree = FullWordTree.fromString(str);
        System.out.println("BFS: " + TreeUtil.BFS(tree.root));
        System.out.println("DFS(IN): " + TreeUtil.ergodic(tree.root, false, false, ORDER.IN));
    }

    static void test_ExpressTree(){
        String postExpress = "ABC+*";
        postExpress = "EA/BD-C*+";
        ExpressTree tree = ExpressTree.fromPostExpress(postExpress);
        System.out.println("DFS(IN): \t " + TreeUtil.ergodic(tree.root, false, true, ORDER.IN));
        System.out.println("DFS(PST): \t " + TreeUtil.ergodic(tree.root, false, false, ORDER.POST));
        System.out.println("DFS(PRE): \t " + TreeUtil.ergodic(tree.root, false, false, ORDER.PRE));
        System.out.println("postExpress: " + postExpress);
    }

    static void test_HuffmanEnAndDecoder(){
        String src = "ABBCCCDDDDEEFFFFFFGHI*--++";
        HuffmanEnAndDecoder hed = new HuffmanEnAndDecoder();
        StringBuilder encodedSrc_print = new StringBuilder();
        String encodedSrc = hed.encode(src, encodedSrc_print, ',');
        System.out.println("encodedSrc\t=" + encodedSrc);
        System.out.println("encodedSrc_print\t=" + encodedSrc_print);
        String decodeSrc = hed.decode(encodedSrc);
        System.out.println("src\t\t\t=" + src);
        System.out.println("decodeSrc\t=" + decodeSrc);
        System.out.println("before encode size\t=" + src.length() * 8);
        System.out.println("after encode size\t=" + encodedSrc.length() / 8);
    }

    static void try2AppendStr(String s){
        s += "GaGa~~";
    }

    static void testApependStr(){
        String s = "ddddd";
        try2AppendStr(s);
        System.out.println(s);
    }


    public static void main(String[] args) {
//        test_WordTree();
//        test_BalanceWordTree();
//        test_FullWordTree();
//        test_ExpressTree();
        test_HuffmanEnAndDecoder();
    }
}

class Node{
    char data;

    Node left = null;
    Node right = null;

    Node(char c){
        this.data = c;
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }
}


class WordTree{
    Node root = null;

    static final char PLUS = '+';

    void insert(char c){
        Node n = new Node(c);
        if(null == this.root){
            this.root = n;
        }else{
            Node r = newPlusNode();
            r.left = this.root;
            r.right = n;
            this.root = r;
        }
    }


    static boolean hasChild(Node n, boolean isLeft){
        if(isLeft){
            return null != n.left;
        }else{
            return null != n.right;
        }
    }

    static boolean isPlus(Node n){
        return PLUS == n.data;
    }

    static Node newPlusNode(){
        return new Node(PLUS);
    }

    static WordTree fromString(String str){
        WordTree tree = new WordTree();
        for(int i = 0; i < str.length(); i++){
            tree.insert(str.charAt(i));
        }
        return tree;
    }

    String inOrder(boolean onlyLeaf){
        return TreeUtil.ergodic(this.root, onlyLeaf, false, ORDER.IN);
    }
}

class BalanceWordTree{

    Node root = null;

    final static char PLUS = '+';

    private BalanceWordTree(){}

    static BalanceWordTree fromString(String str){
        BalanceWordTree tree = new BalanceWordTree();
        List<Node> leafs = new ArrayList<>(str.length());
        for(int i = 0; i < str.length(); i++){
            leafs.add(new Node(str.charAt(i)));
        }
        tree.root = mergeTree(leafs, 1).get(0);
        return tree;
    }

    /**
     * it may need enhance in future
     * @param nodes
     * @param layer
     * @return
     */
    private static List<Node> mergeTree(List<Node> nodes, int layer){
        int size = nodes.size();
        if(size == 1){
            return nodes;
        }
        List<Node> ns = new ArrayList<>();
        for(int i = 0; i < size; i += 2){
//            Node root = newPlusNode();
            Node root = new Node(String.valueOf(layer).charAt(0));
            root.left = nodes.get(i);
            root.right = nodes.get(i + 1);
            ns.add(root);
        }
        return mergeTree(ns, layer + 1);
    }

    static Node newPlusNode(){
        return new Node(PLUS);
    }
}


class FullWordTree{
    Node root = null;

    private FullWordTree(){}

    static FullWordTree fromString(String str){
        FullWordTree tree = new FullWordTree();
        Queue<Node> queue = new LinkedList<>();
        int p = 0;
        tree.root = new Node(str.charAt(p));
        queue.add(tree.root);
        while(!queue.isEmpty()){
            Node n = queue.poll();
            if(str.length() > (2 * p + 1)){
                n.left = new Node(str.charAt(2 * p + 1));
                queue.add(n.left);
            }
            if(str.length() > 2 * (p + 1)){
                n.right = new Node(str.charAt(2 * (p + 1)));
                queue.add(n.right);
            }
            p++;
        }
        return tree;
    }
}

class ExpressTree{
    Node root = null;

    static enum OPERATOR{
        PLUS('+'), REDUCE('-'), MULT('*'), DEVIDE('/');

        private char c;
        private OPERATOR(char c){
            this.c = c;
        }

        char value(){
            return this.c;
        }

        static boolean isOperator(char c){
            OPERATOR[] oprs = OPERATOR.values();
            for(OPERATOR opr: oprs){
                if(opr.value() == c){
                    return true;
                }
            }
            return false;
        }
    }

    private ExpressTree(){}

    static ExpressTree fromPostExpress(String s){
        Stack<Node> stack = new Stack<>();
        char op = '0';
        for(int i = 0; i < s.length(); i++){
            op = s.charAt(i);
            if(OPERATOR.isOperator(op)){
                Node n = new Node(op);
                if(!stack.isEmpty()){
                    n.right  = stack.pop();
                }
                if(!stack.isEmpty()){
                    n.left = stack.pop();
                }
                stack.push(n);
            }else{
                stack.push(new Node(op));
            }
        }
        ExpressTree tree = new ExpressTree();
        tree.root = stack.pop();
        if(!stack.isEmpty()){
            throw new RuntimeException("failed to parse post express, s=" + s);
        }
        return tree;
    }
}

class HuffmanEnAndDecoder{
    static class HuffmanTree{
        static class Node implements Comparable{
            char c = 0;
            int frequency = 0;

            Node left = null;
            Node right = null;

            Node(char c, int frequency){
                this.c = c;
                this.frequency = frequency;
            }


            public int hashCode() {
                return (int) c;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Node node = (Node) o;

                if (c != node.c) return false;

                return true;
            }

            @Override
            public String toString() {
                return "[" + this.c + "]";
            }

            @Override
            public int compareTo(Object o) {
                if(o instanceof Node){
                    return this.frequency - ((Node)o).frequency;
                }else{
                    throw new RuntimeException("Object must be: Huffman.Node, o=" + o);
                }
            }
        }

        Node root = null;

        Map<Character, String> codeMap = null;

        final static char CODE_LEFT = '0';
        final static char CODE_RIGHT = '1';

        private HuffmanTree(Node rot){
            this.root = rot;
            this.initialCodeMap();
        }


        static HuffmanTree fromPriorityQueue(Queue<Node> priorityQueue){
            while(1 < priorityQueue.size()){
                Node left = priorityQueue.poll();
                Node right = priorityQueue.poll();
                Node root = new Node('&' , left.frequency + right.frequency);
                root.left = left;
                root.right = right;
                priorityQueue.add(root);
            }
            HuffmanTree tree = new HuffmanTree(priorityQueue.poll());

            return tree;
        }

        void initialCodeMap(){
            this.codeMap = new HashMap<>();
            this.mapCode(this.root, new StringBuilder(), this.codeMap);
        }

        void mapCode(Node root, StringBuilder codeBuff, Map<Character, String> codeMap){
            if(isLeaf(root)){
                codeMap.put(root.c, codeBuff.toString());
                return;
            }

            if(null != root.left){
                codeBuff.append(CODE_LEFT);
                mapCode(root.left, codeBuff, codeMap);
                codeBuff.deleteCharAt(codeBuff.length() - 1);
            }

            //do nothing

            if(null != root.right){
//                code.push(String.valueOf(CODE_RIGHT));
                codeBuff.append(CODE_RIGHT);
                mapCode(root.right, codeBuff, codeMap);
//                code.pop();
                codeBuff.deleteCharAt(codeBuff.length() - 1);
            }
        }

        String encode(char c){
            return this.codeMap.get(c);
        }

        private char findChar(String code){
            Node currentNode = this.root;
            for(int i = 0; i < code.length(); i++){
                char c = code.charAt(i);
                if(CODE_LEFT == c){
                    currentNode = currentNode.left;
                }else if(CODE_RIGHT == c){
                    currentNode = currentNode.right;
                }
            }
            return currentNode.c;
        }

        String decode(String code){
            StringBuilder sb = new StringBuilder();
            Node currentNode = this.root;
            for(int i = 0; i < code.length(); i++){
                char c = code.charAt(i);
                if(CODE_LEFT == c){
                    currentNode = currentNode.left;
                }else if(CODE_RIGHT == c){
                    currentNode = currentNode.right;
                }
                if(isLeaf(currentNode)){
                    sb.append(currentNode.c);
                    currentNode = this.root;
                }
            }
            return sb.toString();
        }

        static boolean isLeaf(Node node){
            return null == node.left && null == node.right;
        }

        static String codeStack2String(Stack<String> code){
            StringBuilder sb = new StringBuilder();
            for(String s: code){
                sb.append(s);
            }
            return sb.toString();
        }
    }

    /**
     * use huffman tree encode a String
     * @param src target String to be encoded
     * @param printResult debug usage, to display encoded String
     * @param seperator debug usage, seperator used in printResult
     * @return
     */
    String encode(String src, StringBuilder printResult, char seperator){
        PriorityQueue<HuffmanTree.Node> queue = calculateWordFrequency(src);
        this.htree = HuffmanTree.fromPriorityQueue(queue);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < src.length(); i++){
            sb.append(htree.encode(src.charAt(i)));
            printResult.append(htree.encode(src.charAt(i)))
                    .append('[').append(src.charAt(i)).append(']')
                    .append(seperator);
        }
        return sb.toString();
    }

    String decode(String bin){
        return this.htree.decode(bin);
    }

    static PriorityQueue<HuffmanTree.Node> calculateWordFrequency(String src){
        Map<Character, Integer> freqMap = new HashMap<>();
        Integer freq = null;
        for(int i = 0; i < src.length(); i++){
            if(null == (freq = freqMap.get(src.charAt(i)))){
                freq = 1;
            }else{
                freq += 1;
            }
            freqMap.put(src.charAt(i), freq);
        }

        System.out.println("freqMap=" + freqMap);

        PriorityQueue<HuffmanTree.Node> priorityQueue = new PriorityQueue<>
                (
//                        new Comparator<HuffmanTree.Node>() {
//            @Override
//            public int compare(HuffmanTree.Node o1, HuffmanTree.Node o2) {
//                return o1.frequency - o2.frequency;
//            }
//        }
                );

        Iterator<Map.Entry<Character, Integer>> itor =  freqMap.entrySet().iterator();
        Map.Entry<Character, Integer> entry = null;
        while(itor.hasNext()){
            entry = itor.next();
            priorityQueue.add(new HuffmanTree.Node(entry.getKey(), entry.getValue()));
        }

        System.out.println("priorityQueue=" + priorityQueue);
        return priorityQueue;
    }

    HuffmanTree htree = null;
}