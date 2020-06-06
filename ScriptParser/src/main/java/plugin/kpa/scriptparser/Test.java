package plugin.kpa.scriptparser;

import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InvokeMethodResult;
import plugin.kpa.scriptparser.Script.ScriptListener;

public class Test {

/*
脚本测试代码

 */
    public static void main(String[] args)
    {
        FParser parser = new FParser();
        parser.setDebug(true);

        //提供自定义函数解析器
        parser.addScriptListener(new ScriptListener() {
            //当遇到函数调时,name函数名，args参数

            @Override
            public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args) {
                //构造结果
                InvokeMethodResult result = new InvokeMethodResult();
                switch(name)
                {
                    //自定义的print函数
                    case "print":
                        //获取传入的第一个参数

                        System.err.println("自定义打印:"+args[0]);
                        break;
                        //带返回结果的函数
                    case "getDeviceID":
                        String id = "FormatFa id";
                        result.setObject(id);
                        break;


                    default:
                        //默认函数为找到,没有的话要设置为false
                        result.setHasMethod(false);
                        break;
                }
                return result;
            }

            @Override
            public void parserLog(String log) {

            }
        });

        try {




            //简单打印测试,调用自定义打印函数,语句不需要以;结束
            parser.parse("print('formatfa')");


            //调用带返回函数
            parser.parse("id=getDeviceID()");
            //引用变量以_开头加变量名字,支持+操作，操作会先判断是不是字符。是就直接拼接，不然就整数加减乘除
            parser.parse("print('get id is:'+_id)");


            //if语句测试
            parser.parse("age=10");
            //这个也能解析到，额，，
            parser.parse("gegeage=_age+10");
            parser.parse("print(_gegeage)");
            //if语句格式 if 表达式 :代码  多行代码以;分隔
            parser.parse("if _gegeage>10 :print('gege大于10');print('i am second code')");
            parser.parse("if _gegeage<10 :print('test')");

            /*

            操作符支持+,-,* ,/  ,> ,< ==. >= ,<=
             */



            //函数定义测试,只支持一行。一气呵成
            //无参数函数测试，method name():code...end
            parser.parse("method methodtest():print('i am no return method');print(20)end");

            //调用无参数函数
            parser.parse("methodtest()");

            //有参数 函数测试,Z字符_整数暂不支持,还要改下Fparser类的 public Object parseOperator(String code,boolean isequalsmode) throws Exception {函数
            parser.parse("method methodtest2(yourname,age):print('your name is:');print(_yourname);print('your age is :');print(_age)end");
            parser.parse("methodtest2('fORMATFA',21)");



            //for循环测试,不要i+=1,暂时不支持这个

            parser.parse("for(i=0;_i<10;_i=_i+=1):print(_i)");


            //内置函数的类操作那些,不管这个了。。。。


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
