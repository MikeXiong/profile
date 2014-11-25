package mx.algorithm.tree;

/**
 * Created by hxiong on 9/30/14.
 */
public interface IGraphicTree<K extends Comparable<? super K>, V> {

    /**
     * @return root of the tree
     */
    IGraphicTreeNode getRoot();

    /**
     * insert data
     * @param key
     * @param data
     */
    void insert(K key, V data);

    /**
     * @return an empty tree
     */
    IGraphicTree newEmptyTree();
}
