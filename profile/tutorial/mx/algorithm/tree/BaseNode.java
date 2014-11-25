package mx.algorithm.tree;

/**
 * Created by hxiong on 9/25/14.
 */
public class BaseNode <K extends Comparable<? super  K>, V> {
   public K key = null;
   public V data = null;
   public BaseNode<K, V> left = null;
   public BaseNode<K, V> right = null;
}
