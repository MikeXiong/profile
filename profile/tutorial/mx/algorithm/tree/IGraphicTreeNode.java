package mx.algorithm.tree;

import java.util.List;

/**
 * Created by hxiong on 9/30/14.
 */
public interface IGraphicTreeNode<K extends Comparable<? super K>, V> {

    /**
     * key list include null if exist
     * @return
     */
    List<K> getKeys();

    /**
     * data list include null if exist
     * @return
     */
    List<V> getDatas();

    /**
     * @return maximum number of children of this tree
     */
    int getMaxChildNum();

    /**
     * @return all children include null children
     */
    List<IGraphicTreeNode<K, V>> getAllChildrenIncludeNull();

}
