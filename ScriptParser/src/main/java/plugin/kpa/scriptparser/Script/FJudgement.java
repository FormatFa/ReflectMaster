package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by formatfa on 18-4-22.
 */

public class FJudgement {

    //判断代码是神码类型
    public static boolean isMethod(String code)
    {
        return  is(code,FParser.methodPattern);

    }


    public static boolean isOperator(String code)
    {
        return is(code,FParser.operatorPattern);

    }
    public static boolean isEquals(String code)
    {
        return is(code,FParser.equalsPattern);

    }
    public static boolean isIf(String code)
    {
        return is(code,FParser.ifPattern);
    }
    public static boolean isString(String code)
    {
        return  is(code,FParser.stringPattern);
    }
    public static boolean isReference(String code)
    {
        return is(code,FParser.referencePattern);
    }
    public static boolean isInte(String code)
    {
        return is(code,FParser.intPattern);
    }

    public static boolean isFor(String code)
    {
        return is(code, FParser.forPattern);
    }
    public static boolean isMethodDefine(String code)
    {
        return is(code,FParser.methodDefinePattern);
    }
    public static boolean isGive(String code)
    {
        return  is(code,FParser.givePattern);
    }
    public static boolean is(String code,String pattern)
    {
     //   System.out.println(pattern);
        Pattern p= Pattern.compile("^"+pattern+"$");
        Matcher matcher = p.matcher(code);
        return matcher.find();
    }


}
