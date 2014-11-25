package mx.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 14-8-4.
 */
public class TestIServiceProxy {

    public static void main(String[] args) {
        IService isImpl = new IServiceImpl();
        PerformancHandler h = new PerformancHandler(isImpl);
        IService is = (IService) Proxy.newProxyInstance(TestIServiceProxy.class.getClassLoader(), new Class[]{
                IService.class
        }, h);
        String s = is.doService("word");
        System.out.println(s);
    }
}
