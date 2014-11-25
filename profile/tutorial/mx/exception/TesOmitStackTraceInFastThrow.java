package mx.exception;

/**
 * Created by hxiong on 4/25/14.
 */
public class TesOmitStackTraceInFastThrow {
    /**
     * since 1.5
     *
     * he compiler in the server VM now provides correct stack backtraces for all "cold" built-in exceptions.
     * For performance purposes, when such an exception is thrown a few times,
     * the method may be recompiled. After recompilation, the compiler may choose a faster tactic using preallocated
     * exceptions that do not provide a stack trace. To disable completely the use of preallocated exceptions,
     * use this new flag: -XX:-OmitStackTraceInFastThrow.
     *
     * java -server  -XX:-OmitStackTraceInFastThrow off (has full stack trace always)
     * java -server  -XX:+OmitStackTraceInFastThrow on [default]
     * @param args
     */
    public static void main(String[] args) {
        throw new NullPointerException("Test exception");
    }
}
