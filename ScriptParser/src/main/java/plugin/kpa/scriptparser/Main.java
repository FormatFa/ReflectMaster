package plugin.kpa.scriptparser;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import plugin.kpa.scriptparser.Script.Dog;
import plugin.kpa.scriptparser.Script.FParser;

public class Main {

    public static void main(String[] args)
    {

//

        for(Method m:Dog.class.getMethods())
        System.out.println(m.toGenericString());
        for(Field m:Dog.class.getDeclaredFields())
            System.out.println("ddd:"+m.toGenericString());


        Pattern pattern = Pattern.compile("a([+|\\-|*|/|>|<])(.*)");
        Matcher test = pattern.matcher("a*_b");
        if(test.find()) {
            System.out.println(test.group());
           // return;
        }else
        {

        }

        FParser parser = new FParser();
        parser.addScriptListener(new TestScript());



        try {
            parser.parse("  a=1+3");
            parser.parse("b=4*99");
            parser.parse("if _a==4 :print('test')");
            parser.parse("if _a==4 :print('nice');print('jjj')");
            parser.parse("getPath=getMethod('java.io.File','public java.lang.String java.io.File.getPath()')");
            parser.parse("construct=getConstruct('java.io.File','public java.io.File(java.lang.String)')");
            parser.parse("file=constructObject(_construct,'\\/sdcard\\/file')");
//            //函数二级嵌套失败..
//            parser.parse("result=invokeMethod(_getPath,_file)");
//            parser.parse("print(_result)");

            parser.parse("method mymethod(str,yyy):print('test'+_str);print(33+_yyy)end");
            parser.parse("mymethod('ddd',44)");
//            parser.parse("b=1");
//            parser.parse("_b=_b+1");
//            parser.parse("print(_b)");
//            parser.parse("for(a=0;_a<5;_a=_a+1):print('heooe');print('dd'+_a)");

            Dog dog = new Dog("formatfa",20);
            dog.setName("lf");
            dog.setAge(10);
            //set field test
            //private java.lang.String plugin.kpa.scriptparser.Script.Dog.name
            parser.getVm().setValue("dog",dog);
            System.out.println(dog.toString());
            parser.parse("setField('plugin.kpa.scriptparser.Script.Dog','private java.lang.String plugin.kpa.scriptparser.Script.Dog.name',_dog,'dragon')");
            parser.parse("print(_dog)");
            System.out.println(dog.toString());

          //  parser.parse("stop");

            parser.parse("test=getField('plugin.kpa.scriptparser.Script.Dog','private java.lang.String plugin.kpa.scriptparser.Script.Dog.name',_dog)");

            parser.parse("print('dd'+_test)");



            parser.parse("kk=10");
            parser.parse("print('ttt'+_kk)");


            parser.parse("method add(a,b):print(_a+_b)end");

            parser.parse("add(2,8)");
         //   parser.parse("print('第一个参数的值是:'+_arg0)");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }


    }
}
