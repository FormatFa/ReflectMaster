package k.plugin.test;

import java.lang.reflect.Field;

public class MyClass {
    class t
    {

        public Object[] args;
    }
    public static  int getid(String str)
    {
        int result = 0;
        for(char c:str.toCharArray())
        {
            int i = c;
            result+=i;



        }
        return result;
    }
    public static void test(Object ... obj)
    {

        System.out.println(((Object[])obj[0]).length);
        System.out.println(obj[obj.length-1]);

    }
    public static void main(String[] args0)
    {
        float[] a=new float[]{};
        System.out.println(a.getClass().getName());
        for(Field f: a.getClass().getFields())
        {System.out.println(f.getType().getName());

        }
        test(new Object[]{});
System.out.println(getid("971101001141111051004611511711211211111411646118554611910510010310111646651121126711110911297116841011201168610510111912352975651514998563286466968464646463246464646464673683248444845484448323555102485548485250329711211258105100471051001253577422791736858565452485353484948505052504957"));
    }
}
