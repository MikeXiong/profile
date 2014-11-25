package mx.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hxiong on 9/25/14.
 */
public class TreeUtil {

    static String ergodic(Node n, boolean onlyLeaf, boolean needBracket, ORDER order){
        String str = "";

        str = appendNodeStr(str, n, onlyLeaf, order, ORDER.PRE);

        if(null != n.left){
            if(needBracket && ORDER.IN == order)str += "(";
            str += ergodic(n.left, onlyLeaf, needBracket, order);
            if(needBracket && ORDER.IN == order)str += ")";
        }

        str = appendNodeStr(str, n, onlyLeaf, order, ORDER.IN);

        if(null != n.right){
            if(needBracket && ORDER.IN == order)str += "(";
            str += ergodic(n.right, onlyLeaf, needBracket, order);
            if(needBracket && ORDER.IN == order)str += ")";
        }

        str = appendNodeStr(str, n, onlyLeaf, order, ORDER.POST);

        return str;
    }

    static String appendNodeStr(String str, Node n, boolean onlyLeaf, ORDER order, ORDER expectOrder){
        if(onlyLeaf){
            if(isLeaf(n)){
                if(expectOrder == order){
                    str += n;
                }
            }
        }else{
            if(expectOrder == order){
                str += n;
            }
        }
        return str;
    }

    static boolean isLeaf(Node n){
        return null == n.left && null == n.right;
    }

    static String BFS(Node n){
        Queue<Node> queue = new LinkedList<>();
        queue.add(n);
        String s = "";
        Node tmp = null;
        while(!queue.isEmpty()){
            s += (tmp = queue.poll()).toString();
            if(null != tmp.left){
                queue.add(tmp.left);
            }
            if(null != tmp.right){
                queue.add(tmp.right);
            }
        }
        return s;
    }
}
