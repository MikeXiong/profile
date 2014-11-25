package mx.proxy;

/**
 * Created by Administrator on 14-8-4.
 */
public class IServiceImpl implements IService  {
    @Override
    public String doService(String msg) {
        System.out.println(this.getClass().getName() + " invoke. msg=" + msg);

        return "Hello " + msg;
    }
}
