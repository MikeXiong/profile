package mx.lang;

import java.io.*;

/**
 * boostarap classloader: java/lib C++
 * extention classloader: java/ext
 * system classloader: classpath
 * Context classloader;
 * URL classloader:
 * Created by hxiong on 4/25/14.
 */
public class TestClassLoader {

    static void testSimpleCustomizeClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        SimpleCustomizeClassLoader cl = new SimpleCustomizeClassLoader("C:\\Mike\\workspace\\IdeaProjects\\algorithm\\out\\production\\tutorial");
        Class cls = cl.loadClass("mx.lang.AA");
        AA aa = (AA) cls.newInstance();
        aa.print();
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        testSimpleCustomizeClassLoader();
    }
}

class SimpleCustomizeClassLoader extends ClassLoader{

    private String filePath;

    public SimpleCustomizeClassLoader(String filePath){
        this.filePath = filePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.getClassPath(name)));
            ByteArrayOutputStream baas = new ByteArrayOutputStream();){

            byte [] buffer = new byte[1024];
            int c = -1;
            while(true){
                c = bis.read(buffer);
                if(0 > c){
                    break;
                }
                baas.write(buffer, 0, c);
            }
            byte [] data = baas.toByteArray();
            return this.defineClass(name, data, 0, data.length);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        throw new ClassNotFoundException("failed to find class");
    }

    private String getClassPath(String name) {
        return this.filePath + File.separator + name + ".class";
    }
}

