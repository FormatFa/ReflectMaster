package plugin.kpa.scriptparser.Script;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-26.
 */
//字定义函数的结构
public class FMethod {
   String name;
   String[] args;

   String codes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public static FMethod parseFMethod(String code)
   {

       FMethod method = new FMethod();
       Matcher matcher = ReUtils.matcher(FParser.methodDefinePattern,code);

       method.setName( matcher.group(1));
       method.setArgs(matcher.group(2).split(","));
       method.setCodes(matcher.group(3));
       return method;
   }

    @Override
    public String toString() {
        return "FMethod{" +
                "name='" + name + '\'' +
                ", args=" + Arrays.toString(args) +
                ", codes='" + codes + '\'' +
                '}';
    }

    public InvokeMethodResult invoke(FParser parser,Object[] args) throws Exception {


       InvokeMethodResult result = new InvokeMethodResult();



        if(args.length!=getArgs().length)
        {
           parser. log("调用函数:"+getName()+"错误，参数长度不符合");
           return result;
        }

        //添加函数的参数的值到vm变量池中

        for(int i = 0;i<args.length;i+=1)
        {
            parser.log("开始添加函数参数到变量池");
            parser.getVm().setValue(getArgs()[i],args[i]);

        }

       String [] codes = getCodes().split(";");
       for(String s:codes)
           parser.parse(s);


       return result;
   }

}
