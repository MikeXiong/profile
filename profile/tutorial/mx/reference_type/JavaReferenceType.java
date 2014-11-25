package mx.reference_type;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hxiong on 4/23/14.
 */
public class JavaReferenceType {

    /**
     * WeakReferece refer to WeakedHashMap
     * StringReference > WeakReference > PhantomReference
     */

    static void test_SimpleCache(){
        SourceFactory<String, Integer> factory  = new SimpleSourceFactory();
        final SimpleCache<String, Integer> cache      = new SimpleCache<>(factory);
        final CyclicBarrier barrier             = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("[Last Thread] Begin");
                System.gc();
                System.out.println("**********[Last Thread] force gc finished.");
                for(int i = 0; i < 5; i++){
                    System.out.println("[Last Thread] get value from cache: key=" + i + ", value=" + cache.get(i + ""));
                }

            }
        });
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++){
            final int _id = i == 3 ? 0 : i;
            final int threadNo = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread[" + threadNo + "], Begin !");
                    int v = cache.get(_id + "");
                    System.out.println("Thread[" + threadNo + "], get from cache: key=" + _id + ", value=" + v);
                    try {
                        barrier.await();
                        System.out.println("Thread[" + threadNo + "], End !");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //don't forget shutdown or it will waiting new task forever
        exec.shutdown();

    }

    public static void main(String[] args) {
        test_SimpleCache();
    }
}

/**
 * use soft reference to latency clear buffer
 */
class ImageData{
    private String path;
    private SoftReference<byte[]> dataRef = null;

    public ImageData(String path){
        this.path = path;
    }

    private byte[] readData(){
        return new byte[1024 * 1024];
    }

    /**
     * SoftReference for cache
     * @return
     */
    public byte[] getData(){
        byte[] buff = dataRef.get();
        if(null == buff || 1 > buff.length){
            buff     = this.readData();
            dataRef = new SoftReference<>(buff);
        }
        return buff;
    }
}

/**
 * use Phantom reference to ensure cache clear before apply new size buffer
 */
class PhantomBuffer{
    private byte [] data = new byte[0];
    private ReferenceQueue<byte[]> queue = new ReferenceQueue<>();
    private PhantomReference<byte[]> ref = new PhantomReference<>(data, queue);

    public byte[] get(int size){
        if(size < 0){
            throw new IllegalArgumentException("the size should not be negative");
        }
        if(size > this.data.length){
            System.gc();
            try {
                //blocking until the queue is not empty
                queue.remove();
                //phantom reference can not be cleared automatically
                ref.clear();
                ref = new PhantomReference<>(this.data = new byte[size], queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}

interface SourceFactory<K, T>{
    T load(K key);
    K key(T obj);
}

class SimpleSourceFactory implements  SourceFactory<String, Integer>{
    private Map<String, Integer> store = new HashMap<>();
    private Map<Integer, String> keyMap = new HashMap<>();
    private static Integer v = 0;

    @Override
    public synchronized  Integer load(String key) {
        Integer rs = store.get(key);
        if(null ==  rs){
            rs     = ++v;
            store.put(key, rs);
            this.keyMap.put(rs, key);
            System.out.println("create and store new source, key=" + key + ", value=" + rs);
        }else{
            System.out.println("use exist source, key=" + key + ", value=" + rs);
        }
        return rs;
    }

    @Override
    public String key(Integer obj) {
        return this.keyMap.get(obj);
    }
}

class SimpleCache<K, T>{

    private final Map<K, ReferenceWrapper<K, T>> cache  = new ConcurrentHashMap<>();

    private final ReferenceQueue<T> refQueue      = new ReferenceQueue<>();

    private SourceFactory<K, T> factory     = null;


    public SimpleCache (SourceFactory<K, T> factory){
        this.factory = factory;
    }

    /**
     * even if concurrentedHashMap has used, still need use synchronized here;
     * @param key
     * @return
     */
    public synchronized T get(K key){
        ReferenceWrapper<K, T> ref = this.cache.get(key);
        if(null == ref){
            T t = this.factory.load(key);
            ref = this.cache(this.factory.key(t), t);
        }
        return ref.get();
    }

    private ReferenceWrapper<K, T>  cache(K key, T obj){
        this.cleanCache();
        ReferenceWrapper<K, T> ref = new ReferenceWrapper<>(key, obj, refQueue);
        this.cache.put(key, ref);
        return ref;
    }


    private void cleanCache(){
        ReferenceWrapper ref = null;
        while((ref = (ReferenceWrapper)this.refQueue.poll()) != null){
            cache.remove(ref.key());
            ref = null;
        }
    }

    private class ReferenceWrapper<K, T> extends SoftReference<T>{
        private K key = null;

        public ReferenceWrapper(K key, T t, ReferenceQueue<T> queue){
            super(t, queue);
            this.key = key;
        }

        public T get(){
            return super.get();
        }

        public K key(){
            return this.key;
        }

    }
}
