package mx.classloader;

/**
 * Created by hxiong on 6/30/14.
 */
public class Main {

    public static void main(String[] args) {
        IExample e1 = ExampleFactory.newInstance();
        IExample e2 = null;
        while(true){
            e2 = ExampleFactory.newInstance();

            System.out.println("1)" + e1.message() + ": " + e1.plusPlus());
            System.out.println("1)" + e2.message() + ": " + e2.plusPlus());

            try {
                Thread.currentThread().sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
