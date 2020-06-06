package plugin.kpa.scriptparser.Script;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by formatfa on 18-4-24.
 * 内置的脚本监听执行器
 *
 */

public class InnerScriptListener implements ScriptListener
{


    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public InnerScriptListener(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    //在适配反射大师时，发现直接使用getCLass().getClassLoader获取到的是放射大师的，醉了，所以加个接口开放给
    private ClassLoader classLoader = null;

    public ClassLoader getXposedClassLoader() {
        return xposedClassLoader;
    }

    public void setXposedClassLoader(ClassLoader xposedClassLoader) {
        this.xposedClassLoader = xposedClassLoader;
    }

    private ClassLoader xposedClassLoader;

    @Override
    public InvokeMethodResult invokeMethod(FParser parser,String name, Object[] args) {
        Object result = null;
        InvokeMethodResult invokeresult = new InvokeMethodResult();


        if(parser.getVm().getMethods().containsKey(name))
        {
            try {
                return parser.getVm().getMethods().get(name).invoke(parser,args);
            } catch (Exception e) {
                parser.log(e.toString());
                e.printStackTrace();
            }
        }
        switch (name)
        {
            case "getMethod":
                try {
                    result = getMethod(args);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case "getField":
                try {
                    result= getField(args);
                } catch (Exception e) {
                    parser.log(e.toString());
                }

                break;
            case "setField":
                try {
                    setField(args);
                } catch (Exception e) {
                    parser.log(e.toString());
                }
                break;
            case "getArrayAt":

                result=getArrayAt(args);
                break;

            case "getConstruct":
                try {
                    result = getConstruct(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    parser.log(e.toString());
                }

                break;
            case "constructObject":
                try {
                    result = constructObject(args);
                }  catch (Exception e) {
                    parser.log(e.toString());
                }

                break;
            case "invokeMethod":
                try {
                    result = invokeMethod(args);
                }  catch (Exception e) {
                    parser.log(e.toString());
                }

                break;
            case "newInstance":
                try {
                    result=newInstance(args);
                } catch (Exception e) {
                   parser.log(e.toString());
                }
                break;
            default:
                invokeresult.setHasMethod(false);

        }

        invokeresult.setObject(result);

        return invokeresult;
    }

    @Override
    public void parserLog(String log) {

    }

    private Object newInstance(Object[] obj) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String name= (String) obj[0];
        Class cls = classLoader.loadClass(name);

        if(cls==null)cls=getClassLoader().loadClass(name);

        return  cls.newInstance();


    }

    //获取数组的数据
    private Object getArrayAt(Object[] obj)
    {
        Object[] datas = (Object[]) obj[0];
        int position = (int) obj[1];
        return datas[position];


    }
    private Object setField(Object[] obj) throws ClassNotFoundException, IllegalAccessException {

        if(obj.length!=4)
        {
            System.err.println("setfield参数错误");
            return  null;
        }
        String name = (String)obj[0];
        Class cls = classLoader.loadClass(name);

        if(cls==null)cls=getClassLoader().loadClass(name);
        String fieldname = (String)obj[1];
        Object o = obj[2];

        Object value = obj[3];
        Field field = null;

        boolean isFindMethod =false;
        for(Field m: cls.getFields())
        {
            if(m.toGenericString().equals(fieldname))
            {
                field = m;
                isFindMethod = true;
            }
        }
        if(isFindMethod==false)
            for(Field m: cls.getDeclaredFields())
            {
                if(m.toGenericString().equals(fieldname))
                {
                    field = m;
                }
            }

        if(field.isAccessible()==false)field.setAccessible(true);
        field.set(o,value);
        System.err.println("set Field ok");
        return null;

    }
    //类名,fieldname,object
    private Object getField(Object[] obj) throws ClassNotFoundException, IllegalAccessException {

        if(obj.length!=3)
        {
            System.err.println("getfield参数错误");
            return  null;
        }
        String name = (String)obj[0];
        Class cls = classLoader.loadClass(name);


        if(cls==null)cls=getClassLoader().loadClass(name);
        String fieldname = (String)obj[1];
        Object o = obj[2];

        Field field = null;

        boolean isFindMethod =false;
        for(Field m: cls.getFields())
        {
            if(m.toGenericString().equals(fieldname))
            {
                field = m;
                isFindMethod = true;
            }
        }
        if(isFindMethod==false)
        for(Field m: cls.getDeclaredFields())
        {
            if(m.toGenericString().equals(fieldname))
            {
                field = m;
            }
        }

        if(field.isAccessible()==false)field.setAccessible(true);
        return field.get(o);

    }
    private Object constructObject(Object[] ob) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Constructor me = (Constructor) ob[0];
        Object[] params =null;

        if(ob.length!=1) {params=new Object[ob.length - 1];
            System.arraycopy(ob,1,params,0,ob.length-1);
        }
        if(!me.isAccessible())me.setAccessible(true);
        return  me.newInstance(params);

    }
    private Object invokeMethod(Object[] ob) throws InvocationTargetException, IllegalAccessException {


        Method me = (Method) ob[0];

        Object[] params =null;

        if(ob.length!=2) {params=new Object[ob.length - 2];
        System.arraycopy(ob,2,params,0,ob.length-2);
        }
        if(!me.isAccessible())me.setAccessible(true);
        return  me.invoke(ob[1],params);

    }

    private Object getConstruct(Object[] objs) throws ClassNotFoundException {
        String name = (String) objs[0];
        String signature = (String) objs[1];
        Class cls = classLoader.loadClass(name);

        if(cls==null)cls=getClassLoader().loadClass(name);
        boolean isFindMethod = false;


        Constructor method = null;

        for(Constructor m: cls.getConstructors())
        {
            if(m.toGenericString().equals(signature))
            {
                method = m;
                isFindMethod = true;
            }
        }
        for(Constructor m: cls.getDeclaredConstructors())
        {
            if(m.toGenericString().equals(signature))
            {
                method = m;
                isFindMethod = true;
            }
        }

        return method;

    }
    private Object getMethod(Object[] objs) throws ClassNotFoundException {
        String name = (String) objs[0];
        String signature = (String) objs[1];
        Class cls = classLoader.loadClass(name);

        if(cls==null)cls=getClassLoader().loadClass(name);
        boolean isFindMethod = false;


        if(cls==null)System.err.println("getMethod时查找类失败:"+name);
        Method method = null;



            for (Method m : cls.getMethods()) {
                if (m.toGenericString().equals(signature)) {
                    method = m;
                    isFindMethod = true;
                }
            }
            for (Method m : cls.getDeclaredMethods()) {
                if (m.toGenericString().equals(signature)) {
                    method = m;
                    isFindMethod = true;
                }
            }



        return method;

    }
}
