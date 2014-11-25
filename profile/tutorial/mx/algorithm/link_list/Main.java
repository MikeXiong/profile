package mx.algorithm.link_list;

/**
 * Created by hxiong on 7/15/14.
 */
public class Main {

    static void test_FirstLastList(){
        System.out.println("test_FirstLastList BEGIN!");
        FirstLastList list = new FirstLastList();
        list.insertFirst(10);
        System.out.println("1:" + list);
        list.insertLast(100);
        System.out.println("2:" + list);
        list.insertFirst(9);
        list.insertFirst(8);
        System.out.println("3:" + list);
        list.deleteFirst();
        list.deleteFirst();
        System.out.println("4:" + list);
        System.out.println("test_FirstLastList END!");
        System.out.println();
    }

    static void test_DoublyList(){
        System.out.println("test_DoublyList BEGIN!");
        DoublyList<Integer> list = new DoublyList<>();
        list.insertFirst(10);
        System.out.println("1:" + list.toStringForward());
        System.out.println("1:" + list.toStringBackward());
        System.out.println();
        list.insertLast(100);
        System.out.println("2:" + list.toStringForward());
        System.out.println("2:" + list.toStringBackward());
        System.out.println();
        list.insertFirst(9);
        list.insertFirst(8);
        System.out.println("3:" + list.toStringForward());
        System.out.println("3:" + list.toStringBackward());
        System.out.println();
        list.insertAfter(9, 101);
        System.out.println("4:" + list.toStringForward());
        System.out.println("4:" + list.toStringBackward());
        System.out.println();
        list.deleteFirst();
        list.deleteFirst();
        System.out.println("5:" + list.toStringForward());
        System.out.println("5:" + list.toStringBackward());
        System.out.println();
        list.deleteLast();
        System.out.println("6:" + list.toStringForward());
        System.out.println("6:" + list.toStringBackward());
        System.out.println();
        list.delete(100);
        System.out.println("7:" + list.toStringForward());
        System.out.println("7:" + list.toStringBackward());
        System.out.println();
        list.delete(-1);
        System.out.println("8:" + list.toStringForward());
        System.out.println("8:" + list.toStringBackward());
        System.out.println("test_DoublyList END!");
        System.out.println();
    }

    static void test_InterIterator(){
        System.out.println("test_InterIterator BEGIN!");
        DoublyList<Integer> list = new DoublyList<>();
        InterIterator<Integer> itor = list.iterator();
        itor.insertBefore(10);
        System.out.println("1:" + list);
        itor.insertAfter(9);
        System.out.println("2:" + list);
        while(itor.hasNext()){
            System.out.println("3.1: obj=" + itor.next());
        }
        System.out.println("3:" + list);
        System.out.println();
        itor.reset();
        int c = 0;
        while(itor.hasNext()){
            itor.next();
            itor.insertBefore(c += 2);
            System.out.println("4.1:" + list);
        }
        System.out.println("4:" + list);
        System.out.println();
        itor.reset();
        c = 0;
        while(itor.hasNext()){
            itor.next();
            itor.insertAfter(c += 20);
            itor.next();
            System.out.println("5.1:" + list);
        }
        System.out.println("5:" + list);
        System.out.println();
        itor.reset();
        c = 0;
        while(itor.hasNext()){
            itor.next();
            itor.delete();
            System.out.println("6.1:" + list);
        }
        System.out.println("6:" + list);
        System.out.println("test_InterIterator END!");
        System.out.println();
    }

    static void test_CyclingList(){
        System.out.println("test_InterIterator BEGIN!");
        CyclingList<Integer> list = new CyclingList<>();
        CyclingList<Integer>.Iterator<Integer> itor = list.iterator();
        itor.push(10);
        itor.push(9);
        System.out.println("1:" + list);
        System.out.println("2.1: pop():" + itor.pop());
        System.out.println("2:" + list);
        itor.push(11);
        System.out.println("3:" + list);
        while(!list.isEmpty()){
            System.out.println("4.1: pop():" + itor.pop());
        }
        System.out.println("4:" + list);
        System.out.println("test_InterIterator END!");
        System.out.println();
    }

    static void test_Josephus(int people, final int REMOVE){
        System.out.println("test_Josephus BEGIN!");
        CyclingList<Integer> list = new CyclingList<>();
        CyclingList<Integer>.Iterator<Integer> itor = list.iterator();
        for(int i = 7; i > 0; i--){
            itor.push(i);
        }
        System.out.println("list:" + list);
        int count = 0;
        while(!list.isEmpty()){
            ++count;
            if(REMOVE == count){
                System.out.print(itor.delete() + ",");
                count = 0;
            }else{
                itor.next();
            }
        }
        System.out.println();
        System.out.println("test_Josephus END!");
        System.out.println();
    }

    public static void main(String[] args) {
//        test_FirstLastList();
//        test_DoublyList();
//        test_InterIterator();
//        test_CyclingList();
        test_Josephus(7, 4);
    }
}

class Link{
    Object data = null;
    Link next = null;

    Link(){}

    Link(Object data, Link next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[data:" + data + "]";
    }
}

class FirstLastList{

    Link first = null;
    Link last = null;

    public void insertFirst(Object obj){
        Link node = new Link();
        node.data = obj;
        if(this.isEmpty()){
            this.first = node;
            this.last = node;
        }else{
            node.next = this.first;
            this.first = node;
        }
    }

    public void insertLast(Object obj){
        Link node = new Link();
        node.data = obj;
        if(this.isEmpty()){
            this.first = node;
            this.last = node;
        }else{
            this.last.next = node;
            this.last = node;

        }
    }

    public Object deleteFirst(){
        if(this.isEmpty()){
            return null;
        }
        Link _first = this.first;
        if(null != this.first.next){
            this.first = this.first.next;
        }else{
            this.first = this.last = null;
        }
        _first.next = null;
        return _first;
    }

    //TODO:: FirstLastList can not delete last element directly
    //DQueue should be a better choice
    public Object deleteLast(){
        return null;
    }

    public boolean isEmpty(){
        return null == this.first;
    }

    @Override
    public String toString() {
        if(this.isEmpty()){
            return "The FastLastList is empty";
        }
        String str = "";
        Link node = this.first;
        while(null != node){
            str += node.toString() + ", ";
            node = node.next;
        }
        return str;
    }
}
class DoublyLink<T>{
    T data = null;
    DoublyLink previous = null;
    DoublyLink next = null;

    DoublyLink(){}

    DoublyLink(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "[" + data + "]";
    }
}
class DoublyList<T>{
    DoublyLink<T> first = null;
    DoublyLink<T> last = null;


    public void insertFirst(T obj){
        DoublyLink node = new DoublyLink(obj);
        if(this.isEmpty()){
            this.first = this.last = node;
        }else{
            node.next = this.first;
            this.first.previous = node;
            this.first = node;
        }
    }

    public void insertLast(T obj){
        DoublyLink node = new DoublyLink(obj);
        if(this.isEmpty()){
            this.first = this.last = node;
        }else{
            this.last.next = node;
            node.previous = this.last;
            this.last = node;
        }
    }

    public void insertAfter(T key, T obj){
        DoublyLink node = this.first;
        while(null != node){
            if(key.equals(node.data)){
                this._insertAfter(node, obj);
                break;
            }
            node = node.next;
        }
    }

    private void _insertAfter(DoublyLink node, T obj) {
        if(isLast(node)){
            this.insertLast(obj);
        }else{
            DoublyLink newNode = new DoublyLink(obj);
            node.next.previous = newNode;
            newNode.next = node.next;
            node.next = newNode;
            newNode.previous = node;
        }
    }

    public T deleteFirst(){
        if(this.isEmpty()){
            return null;
        }else{
            DoublyLink<T> _first = this.first;
            if(null != _first.next){
                this.first.next.previous = null;
                this.first = this.first.next;
            }else{
                this.first = this.last = null;
            }
            return _first.data;
        }
    }

    public T deleteLast(){
        if(this.isEmpty()){
            return null;
        }else{
            DoublyLink<T> _last = this.last;
            if(null != _last.previous){
                this.last.previous.next = null;
                this.last = this.last.previous;
            }else{
                this.first = this.last = null;
            }
            return _last.data;
        }
    }

    public boolean delete(T key){
        DoublyLink<T> node = this.first;
        boolean success = false;
        while(null != node){
            if(success = this._delete(node, key)){
                break;
            }
            node = node.next;
        }
        return success;
    }

    private boolean _delete(DoublyLink<T> node, T key) {
        if(key.equals(node.data)){
            if(this.isFirst(node)){
                this.deleteFirst();
            }else if(this.isLast(node)){
                this.deleteLast();
            }else{
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
            return true;
        }else{
            return false;
        }
    }


    public boolean isEmpty(){
        return null == this.first;
    }

    private boolean isLast(DoublyLink node){
        return null == node.next;
    }

    private boolean isFirst(DoublyLink node){
        return null == node.previous;
    }

    public String toStringForward(){
        DoublyLink<T> node = this.first;
        String str = "forward:";
        while(null != node){
            str += node.toString() + ",";
            node = node.next;
        }
        return str;
    }

    public String toStringBackward(){
        DoublyLink<T> node = this.last;
        String str = "backward:";
        while(null != node){
            str += node.toString() + ",";
            node = node.previous;
        }
        return str;
    }

    @Override
    public String toString() {
        return this.toStringForward();
    }

    public InterIterator<T> iterator(){
        return new InterIterator<>(this);
    }
}

class InterIterator<T>{

    DoublyLink<T> current = null;
    DoublyList<T> list = null;

    public InterIterator(DoublyList l){
        this.list = l;
    }

    public T next(){
        DoublyLink<T> next = this._nextNode();
        if(null == next){
            return null;
        }else{
            return next.data;
        }
    }

    private DoublyLink<T> _nextNode(){
        if(null == this.current){
            this.current = list.first;
        }else{
            this.current = this.current.next;
        }
        return this.current;
    }

    public boolean hasNext(){
        if(null == current){
            return !list.isEmpty();
        }else{
            return null != current.next;
        }
    }

    public void reset(){
        this.current = null;
    }

//    public boolean isEnd(){
//        return this.hasNext();
//    }

    /**
     * Is pointer to last element
     * @return
     */
    public boolean isLast(){
        return !this.isBegin() && null == this.current.next;
    }

    private boolean isBegin(){
        return null == this.current;
    }

    /**
     * Is pointer to first element
     * @return
     */
    public boolean isFirst(){
        return !this.isBegin() && null == this.current.previous;
    }

    /**
     * insert an object after current node
     * the pointer stay in current node
     * @param obj
     */
    public void insertAfter(T obj){
        if(this.isBegin()){
            this.list.insertFirst(obj);
        }else if(this.isLast()){
            this.list.insertLast(obj);
        }else{
            DoublyLink<T> node = new DoublyLink<>(obj);
            node.next = this.current.next;
            this.current.next.previous = node;
            node.previous = this.current;
            this.current.next = node;
        }
    }

    /**
     * insert an object before current node
     * the pointer stay in current node
     * @param obj
     */
    public void insertBefore(T obj){
        if(this.isBegin()){
            this.list.insertFirst(obj);
        }else if(this.isFirst()){
            this.list.insertFirst(obj);
        }else{
            DoublyLink<T> node = new DoublyLink<>(obj);
            node.previous = this.current.previous;
            if(null != this.current.previous){
                node.previous.next = node;
            }
            node.next = this.current;
            this.current.previous = node;
        }
    }

    /**
     * delete current node if exist and move pointer to next node
     * if this node is the last on, the pointer will point to null(just like be reset)
     * @return
     */
    public boolean delete(){
        if(null == this.current){
            return false;
        }else if(null == this.current.previous){//first node
            this.list.deleteFirst();
        }else if(null == this.current.next){//last node
            this.list.deleteLast();
        }else{
            if(null != this.current.previous){
                this.current.previous.next = this.current.next;
            }
            if(null != this.current.next){
                this.current.next.previous = this.current.previous;
            }

            DoublyLink<T> toRemove = this.current;
            this.current = this.current.next;
            //must remove the reference here, otherwise it will cause memory leak
            toRemove.previous = toRemove.next = null;

        }
        return true;
    }
}

class CyclingList<T>{
    //use tail as the name
    Link tail = null;
    Link header = null;
    int size = 0;

    public boolean isEmpty(){
        return null == this.tail;
    }

    public int size(){
        return this.size;
    }

    public Iterator<T> iterator(){
        return new Iterator<>();
    }

    @Override
    public String toString() {
        int count = 0;
        String str = "[";
        Iterator<T> itor = this.iterator();
        while(count++ < this.size()){
            str += itor.data() + ", ";
            itor.next();
        }
        return str + "]";
    }

    class Iterator<T>{

        public T data(){
            if(isEmpty()){
                return null;
            }else{
                return (T) tail.data;
            }
        }

        public T next(){
            if(isEmpty()){
                return null;
            }else{
                tail = tail.next;
                header = header.next;
                return (T) tail.data;
            }
        }

        private void _insertIntoEmpty(Link node){
            header = tail = node;
            node.next = node;
        }

        public void insertBefore(T obj){
            Link node = new Link();
            node.data = obj;
            if(isEmpty()){
                this._insertIntoEmpty(node);
            }else{
                node.next = tail.next;
                tail.next = node;
            }
            size++;
        }

        public void insertAfter(T obj){
            Link node = new Link();
            node.data = obj;
            if(isEmpty()){
                this._insertIntoEmpty(node);
            }else{
                node.next = tail;
                tail = node;
                header.next = tail;
            }
            size++;
        }

        private Link _find(T obj){
            Link current = tail;
            int count = 0;
            while(count++ < size()){
                if(current.data.equals(obj)){
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        public boolean find(T obj){
            return null != this._find(obj);
        }

        private Link _delete(){
            if(isEmpty()){
                return null;
            }
            Link toDelete = tail;
            if(1 == size()){
                header = tail = null;
            }else{
                tail = tail.next;
                header.next = tail;
            }
            toDelete.next = null;
            size--;
            return toDelete;
        }

        public boolean deleteSuccess(){
            return null != this._delete();
        }

        public T delete(){
            Link node = this._delete();
            if(null == node){
                return null;
            }else{
                return (T) node.data;
            }
        }

        public void push(T obj){
            this.insertAfter(obj);
        }

        public T pop(){
            Link last = this._delete();
            if(null == last){
                return null;
            }else{
                return (T) last.data;
            }
        }
    }
}

class Cell<T>{
    T data = null;
    Cell<T> upper = null;
    Cell<T> left = null;


    public Cell(T obj){
        this.data = obj;
    }
}

class MatrixLinkedList<T>{

    Cell<T> first = null;
    int width = 0;
    int height = 0;

    public MatrixLinkedList(int width, int height){
        this.width = width;
        this.height = height;
    }

    class Iterator<T>{


    }
}


