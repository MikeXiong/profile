package mx.helper;

/**
 * Created by hxiong on 7/2/14.
 */
public class Helper {

    static<T> String array2String(T[] array){
        String str = "[";
        for(int i = 0; i < array.length; i++){
            if(0 < i){
                str += ",";
            }
            str += array[i];
        }
        return str + "]";
    }

    static <T> void println(T[] array){
        System.out.println(array2String(array));
    }


}
