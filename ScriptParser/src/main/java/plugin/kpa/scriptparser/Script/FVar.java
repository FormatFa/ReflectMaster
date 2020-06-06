package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-22.
 */

public abstract class FVar {

    //变量，BOOLEANL类型，只u有truefalse。直接判断字符是不是true or false?
    public static int TYPE_INT=0,TYPE_STRING=1,TYPE_UNKNOW=2,TYPE_REFERENCE=3,TYPE_BOOLEAN=4;
    public static String[] typeString={"整数","字符","未知","引用","布尔类型"};
    private int type;
    private Object object;

    public FVar(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public static FVar parseVar(String code)
    {


        return  null;

    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "FVar{" +
                "type=" +typeString[type] +
                ", object=" + object +
                '}';
    }
}
