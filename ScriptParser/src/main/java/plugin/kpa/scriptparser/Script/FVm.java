package plugin.kpa.scriptparser.Script;

import java.util.HashMap;

/**
 * Created by formatfa on 18-4-22.
 */

public class FVm {

    //变量保存的表
    private HashMap<String,Object> vars;

    private HashMap<String,FMethod> methods;

    FParser fParser;


    public HashMap<String, FMethod> getMethods() {
        return methods;
    }

    public HashMap<String, Object> getVars() {
        return vars;
    }

    public void defindMethod(String name,FMethod me){
        fParser.log("定义函数:"+me.toString());
        methods.put(name,me);
    }

    public void setVars(HashMap<String, Object> vars) {
        this.vars = vars;
    }
    public void setValue(String key,Object va)
    {
        fParser.log("当前变量池:"+vars);
        if(vars.containsKey(key))
        {
            fParser.log("修改变量:" + key + "->" + va);
            vars.remove(key);
            vars.put(key,va);
        }
        else{
            fParser.log("添加变量:"+key+"->"+va);
            vars.put(key,va);
        }

    }

    public FVm(FParser fParser) {
        this.fParser = fParser;
        vars = new HashMap<>();
        methods = new HashMap<>();
    }
}
