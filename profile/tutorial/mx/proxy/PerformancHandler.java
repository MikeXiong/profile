package mx.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 14-8-4.
 */
public class PerformancHandler implements InvocationHandler {
    private Object target = null;

    public PerformancHandler(Object obj){
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(this.getClass().getName() + "::" + proxy.getClass().getName() + " before invoked.");
        Object rs = method.invoke(this.target, args);
        System.out.println(this.getClass().getName() + "::" + proxy.getClass().getName() + " after invoked.");
        return rs;
    }
}
