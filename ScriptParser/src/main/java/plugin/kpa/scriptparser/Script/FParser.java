package plugin.kpa.scriptparser.Script;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.regex.Matcher;


/**
 * Created by formatfa on 18-4-22.
 *
 *
 * r
 */

public class FParser {
    /*

    method(["var"])

     */

    private InnerScriptListener innerScriptListener;


    public void setInnerScriptListener(InnerScriptListener newinnerScriptListener) {

        scriptListeners.remove(innerScriptListener);
        scriptListeners.add(newinnerScriptListener);
    }

    public FParser() {

        //初始化vm,保存变量和自定义函数的地方
        vm = new FVm(this);
        //添加变量到变量池
        vm.setValue("null",null);

        //脚本监听
        scriptListeners = new ArrayList<>();

        innerScriptListener = new InnerScriptListener(this.getClass().getClassLoader());


        scriptListeners.add(innerScriptListener);
        //debug模式开启后会输出详细的解析过程
        if(!isDebug())
        System.out.println("代码执行过程debug已关闭，输入debug开启");
    }

    //函数调用
    public static final String methodPattern = "(\\w+)\\((.*)\\)";


    //函数定义 method(url):print('method'+url);print('method end')
    public static final String methodDefinePattern = "method\\s(\\w+)\\((.*?)\\):(.*)end";


    //不已(開頭，比如print($a$+$b$)，會先執行print($a$  +  $b$...醉了,采用['|w+]是可能有'test'+_str的情况
    public static final String operatorPattern = "(['|\\w]+?)([+|\\-|*|/|>|<])(.*)";
    public static final String equalsPattern = "(.*?)([=|\\!]=)(.*)";


    public static final String stringPattern = "'(.*?)'";
    public static final String intPattern = "(\\d+)";
    //引用类型，['name'],这种
    public static final String referencePattern = "_(\\w+)";

    //赋值操作
    public static final String givePattern = "(\\w+)\\=(.*)";

    public static final String ifPattern = "if\\s(.*?)\\s:(.*)";

    //for(initexp;条件exp;otherexp):codes....
    public static final String forPattern = "for\\((.*?);(.*?);(.*?)\\):(.*)";

    public static final String stopPattern="stop";

    public static final String debugPattern = "debug";
    private List<ScriptListener> scriptListeners;
    private FVm vm;


    public FVm getVm() {
        return vm;
    }

    public void setVm(FVm vm) {
        this.vm = vm;
    }

    public List<ScriptListener> getScriptListeners() {
        return scriptListeners;
    }


    private boolean isDebug=true;

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    //是否在函数里面，考虑到函数的参数名字可能会和变量重复，不过现在不管先
    //boolean isInMethod=false;

    public void addScriptListener(ScriptListener scriptListener) {
        this.scriptListeners.add(scriptListener);
    }

    public void log(String msg) {
        if (scriptListeners != null)
            for (ScriptListener l : scriptListeners)
                l.parserLog(msg);

        if(isDebug())
        System.out.println(msg);
    }

    //解析多行代码，已换行符分割开
    public void parseCodes(String codes) throws Exception {
        for (String code : codes.split("\n")) {
            if (code.startsWith("#")) continue;
            parse(code);
        }
    }

    //解析一行代码
    private boolean isStop = false;
    public Object parse(String code) throws Exception {

        if(isStop)
        {
            return null;
        }
        code = code.trim();
        log("解析代碼:" + code);


        //这些if，for这些关键字的应该先判断
        if(code.equals(stopPattern))
        {
            isStop=true;
            return null;
        }
        //开启代码debug
       else if(code.equals(debugPattern))
        {
            setDebug(true);
            return null;
        }
        else if (FJudgement.isIf(code)) {
            parseIf(code);
        } else if (FJudgement.isFor(code)) {
            parseFor(code);
        } else if (FJudgement.isMethodDefine(code)) {

            parseMethodDefine(code);

        } else if (FJudgement.isEquals(code))
            return parseOperator(code, true);
        else if (FJudgement.isGive(code)) {
            parseGive(code);
            return null;
        } else if (FJudgement.isMethod(code)) {

            return parseMethod(code);
        } else if (FJudgement.isOperator(code))
            return parseOperator(code, false);


            //解析函數

            //解析加減操作

        else if("true".equals(code)||"false".equals(code))
        {
            return parseVar(code,FVar.TYPE_BOOLEAN);
        }

        else if (FJudgement.isString(code))
            return parseVar(code, FVar.TYPE_STRING);
            //提取變量
        else if (FJudgement.isReference(code))
            return parseVar(code, FVar.TYPE_REFERENCE);
        else if (FJudgement.isInte(code)) {

            return parseVar(code, FVar.TYPE_INT);
        }


        return code;
    }


    public void parseFor(String code) throws Exception {
        Matcher matcher = ReUtils.matcher(forPattern, code);
        String exp1 = matcher.group(1);
        String exp2 = matcher.group(2);
        String exp3 = matcher.group(3);
        String codes = matcher.group(4);

        log("解析for循环,exp1:" + exp1 + " exp2:" + exp2 + " exp3:" + exp3 + " codes:" + codes);

        String invokecodes[] = codes.split(";");
        //解析第二句
        parse(exp1);
        while (true) {
            boolean result = (boolean) parse(exp2);
            log("for exp2 result:" + result);
            //当执行第二个语句返回false时，就是不成立时
            if (result == false) break;
            for (String c : invokecodes) parse(c);

            parse(exp3);


        }
    }

    //解析函数定义
    public void parseMethodDefine(String code)
    {

        FMethod method = FMethod.parseFMethod(code);
        System.out.print(method.toString());
        getVm().defindMethod(method.getName(),method);

    }

    public void parseGive(String code) throws Exception {



        Matcher matcher = ReUtils.matcher(givePattern,code);
        String left = matcher.group(1);
        String right = matcher.group(2);

        //左边可能是变量,
        if(FJudgement.isReference(left))
            left=left.substring(1);

        log("赋值操作:"+left+"->"+right);


        Object object = parse(right);
        log("赋值操作:"+left+"->"+object);
        getVm().setValue(left,object);



    }
    //解析变量
    public Object parseVar(String code,int type)
    {

        FVar vr =null;
        if(type==FVar.TYPE_INT)
        {
            vr = IntVar.parseIntVar(code);
        }
        else if(type==FVar.TYPE_STRING)
        {
            vr = StringVar.parseStringVar(code);
        }
        else if(type==FVar.TYPE_REFERENCE)

        {
            vr = ReferenceVar.parseReference(this,code);

        }
        else if(type==FVar.TYPE_BOOLEAN)
        {
            vr = BooleanVar.parseBoolean(code);
        }
        if(vr==null)
        {
            System.out.println("parse Var err:"+code);
            return null;
        }
        log("解析变量:"+vr.toString());
        return vr.getObject();
    }



    public Object parseOperator(String code,boolean isequalsmode) throws Exception {

        FOperator operator = FOperator.parseOperator(code,isequalsmode);

        log("解析操作:"+operator.toString());
        Object left = parse(operator.getLeft());
        Object right = parse(operator.getRight());
        String operator2 = operator.getOperator();

        //是字符的话直接拼接起来haha
        boolean isStringMode= left instanceof String || right instanceof  String;
        int va1 = 0;
        int va2=0;
        //都是整數模式


        if(!isStringMode)
        {


                //throw new Exception("解析操作:"+code+" 獲取到的左邊的值爲空");
                //


            //  //  throw new Exception("解析操作:"+code+" 獲取到的右邊的值爲空");
            if(left!=null)
            va1 = (int) left;
            if(right!=null)
            va2=(int)right;
        }
        log("解析操作，字符模式:"+isStringMode+" "+operator.toString()+"左值:"+left+" 右只:"+right);
        switch (operator2)
        {
            case "+":

                if(isStringMode)return (left==null?null:left.toString())+ (right==null?null: right.toString());
                return  va1+va2;

            case "-":
                if(isStringMode)
                {
                    throw new Exception("字符串不支持-操作");
                }
                return  va1-va2;

            case "*":
                if(isStringMode)
                {
                    throw new Exception("字符串不支持*操作");
                }
                return  va1*va2;

            case "/":
                if(isStringMode)
                {
                    throw new Exception("字符串不支持/操作");

                }
                return  va1/va2;
            case ">":
                return va1>va2;
            case ">=":
                return va1>=va2;
            case "<=":
                return va1>va2;
            case "<":
                return va1<va2;
            case "%":
                return va1%va2;
            case "==":
                if (left==null &&right==null)return  true;
                if(left==null&&right!=null)return false;
                if(left!=null&&right==null)return false;
                if(isStringMode)
                {
                    return left.toString().equals(right.toString());
                }
                return va1==va2;
            case "!=":

                if (left==null &&right==null)return  false;
                if(left==null&&right!=null)return true;
                if(left!=null&&right==null)return true;
                if(isStringMode)
                {
                    return !(left.toString().equals(right.toString()));
                }
                return va1!=va2;

        }

        return  null;
    }
    public Object parseMethod(String code) throws Exception {

        log("解析函数:"+code);
        FInvokeMethod method = FInvokeMethod.parseMethod(code);
        //取出函數名字和參數，參數可能是進一步的函數調用或者加減，調用總的parse進行操作
        String[] params = method.getParamsArray();
        Object[] args = new Object[params.length];

        for(int i = 0;i<args.length;i+=1)
        {
            args[i] = parse(params[i]);
        }





        Object result = null;
        boolean hasMethod = false;
        if(scriptListeners!=null)
            for(ScriptListener l:scriptListeners)
            {
                InvokeMethodResult invokeresult = l.invokeMethod(this,method.getName(),args);
                if(invokeresult!=null)
                    hasMethod = invokeresult.isHasMethod();
                if(hasMethod){result =invokeresult.getObject();break;}

            }
        if(!hasMethod){
            log("没找到函数:"+method.getName());
        }
        return result;
    }
    public void parseIf(String code) throws Exception {
        Matcher matcher = ReUtils.matcher(ifPattern,code);

        String rule = matcher.group(1);

        boolean istrue = (boolean)parse(rule);
        log("執行if語句:"+rule+":"+istrue);
        String ifcode = matcher.group(2);

        if(istrue) {
            String[] codes = ifcode.split(";");
            log("要執行的if:"+ Arrays.toString(codes));
            for (String item : codes) {
                parse(item);
            }
        }
    }

}
