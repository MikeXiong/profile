package mx.classloader;

/**
 * Created by hxiong on 6/30/14.
 */
public class Example implements IExample {

    private int counter;

    private final static long [] cache = new long [10000000];


    @Override
    //change the return value of this function when test
    //then recompile this class
    public String message() {
        return "Version 1";
    }

    @Override
    public int plusPlus() {
        return counter++;
    }
}
