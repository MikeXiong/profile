package mx.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by hxiong on 6/30/14.
 */
public class ExampleFactory {

    public static IExample newInstance(){
        URL url = ClassLoader.getSystemResource("");
        System.out.println(url.toString());
        URLClassLoader tmp = new URLClassLoader(new URL[]{url}){
            @Override
            //must overload the default load class here, force it find class every time
            //otherwise it will use the previous finded one
            public Class loadClass(String name) throws ClassNotFoundException {
                if("mx.classloader.Example".equals(name)){
                    return findClass(name);
                }else{
                    return super.loadClass(name);
                }
            }
        };
        try {
            Class cls  = tmp.loadClass("mx.classloader.Example");
            return (IExample) cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
