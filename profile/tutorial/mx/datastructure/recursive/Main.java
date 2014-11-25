package mx.datastructure.recursive;

import java.util.*;

/**
 * Created by Administrator on 14-8-17.
 */
public class Main {



    static void doAnagram(String str){
        Queue<String> queue = new ArrayDeque<String>();
        Map<Integer, String> indexMap = new HashMap<Integer, String>();
        Map<Integer, String> map = new HashMap<Integer, String>();
        Map<String, Boolean> reservedMap = new HashMap<String, Boolean>();
        Stack<String> stack = new Stack<String>();
//        stack.push("");
        for(int i = 0; i < str.length(); i++){
            stack.add(String.valueOf(str.charAt(i)));
            indexMap.put(i, String.valueOf(str.charAt(i)));
            map.put(i, String.valueOf(str.charAt(i)));
            reservedMap.put(String.valueOf(str.charAt(i)), true);
        }

        while(!stack.isEmpty()){
            if(0 == queue.size()){
                System.out.println(stack);
                String s = stack.pop();
                queue.add(s);
//                indexMap.remove(stack.size());
//                reservedMap.remove(s);
                continue;
            }
            String s = queue.peek();
            int i = stack.size();
            if(s.equals(map.get(i))){
                String s1 = stack.pop();
                queue.add(s1);
                continue;
            }else{
                String s1 = queue.poll();
                stack.push(s1);
                map.put(i, s1);
            }

        }
    }

    public static void main(String[] args) {
        doAnagram("abcd");
    }
}
