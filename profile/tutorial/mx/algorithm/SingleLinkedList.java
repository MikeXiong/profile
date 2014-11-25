package mx.algorithm;

/**
 * Created by hxiong on 6/25/14.
 */
public class SingleLinkedList {

    static class Node<T>{
        T data;
        Node<T> next = null;

        public Node(T t){
            this.data = t;
        }

        void append(Node<T> n){
            this.next = n;
        }

        @Override
        public String toString() {
            return "[Node:" + this.data.toString() + "]";
        }
    }

    static Node reverseRecusive(Node header){
        Node tmp = header;
        Node last = null;
        while(true){
            Node next = tmp.next;
            if(null == next){
                break;
            }
            if(null == next.next){
                last = next;
                next.next = tmp;
                tmp.next = null;
                reverseRecusive(header);
                break;
            }
            tmp = tmp.next;
        }
        return last;
    }

    static Node<Integer> createList(){
        Node<Integer> header = null;
        Node<Integer> pre = header;
        for(int i = 0; i < 10; i++){
            if(0 == i){
                pre = header = new Node<Integer>(i);
            }else{
                Node<Integer> n = new Node<>(i);
                pre.next = n;
                pre = n;
            }
        }

        return header;
    }

    static void print(Node<Integer> header){
        Node<Integer> tmp = header;
        while(null != tmp){
            System.out.println(tmp);
            tmp = tmp.next;
        }
    }


    public static void main(String[] args) {
        Node<Integer> h = createList();
        print(h);
        System.out.println();
        Node<Integer> h2 = reverseRecusive(h);
        print(h2);
    }
}
