package mx.algorithm.tree_rb;

/**
 * Created by hxiong on 10/11/14.
 */
public class BRTreeFacade<K extends Comparable<? super K>, V>{

    BRTree<K, V> tree = null;

    public BRTreeFacade(){
        this.tree = new BRTree<>();
    }

    public void insert(K key, V data){
        this.tree.insert(key, data);
    }

    public V find(K key){
        return this.tree.find(key);
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        Util.DFS_In_Key_Pretty(this.tree.root, buff);

        buff.append("]");
        return buff.toString();
    }
}
