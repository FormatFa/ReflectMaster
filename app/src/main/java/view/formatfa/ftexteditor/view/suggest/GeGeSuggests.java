package view.formatfa.ftexteditor.view.suggest;

import java.util.ArrayList;
import java.util.List;

import view.formatfa.ftexteditor.view.ScriptView;

public class GeGeSuggests implements Suggests {


    private SuggestItem[] methods = {new SuggestItem("getMethod", "获取函数(methodDesctipt,"),
            new SuggestItem("print", "对象输出(obj"),
            new SuggestItem("copytext", "复制文本(obj)"),
            new SuggestItem("fieldwindow", "打开对象窗口(obj)"),
            new SuggestItem("gettempvar", "获取临时变量(int position)"),
            new SuggestItem("getMethod", "获取函数(classname,signature)"),
            new SuggestItem("getField", "获取字段(classname,signature)"),
            new SuggestItem("setField", "设置字段(classname,signature,obj,value)"),
            new SuggestItem("getArrayAt", "获取数组(obj,int p)"),

            new SuggestItem("getConstruct", "获取构造(name,signature)"),
            new SuggestItem("constructObject", "构造(construct,param...)"),
            new SuggestItem("invokeMethod", "函数调用(method,param...)"),
            new SuggestItem("newInstance", "函数调用(name)"),
            new SuggestItem("炫明垃圾", "垃圾"),


            new SuggestItem("火神在世灭天", "火神在世灭天（obj)"),
            new SuggestItem("格式化法大神", "QQ1758759399"),
            new SuggestItem("幻灵吊打炫明", "QQ1758759399"),

    };

    @Override
    public List<SuggestItem> getSuggests(ScriptView view, String word, String line, int position) {

        if (word.isEmpty()) return null;
        List<SuggestItem> result = new ArrayList<>();
        for (SuggestItem item : methods) {
            if (item.getText().startsWith(word.toLowerCase()))
                result.add(item);


        }


        return result;
    }
}
