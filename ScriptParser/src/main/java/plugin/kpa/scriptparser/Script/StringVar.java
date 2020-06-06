package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-22.
 */

public class StringVar extends  FVar {

    public static StringVar parseStringVar(String code)
    {
        Matcher m = ReUtils.matcher(FParser.stringPattern,code);
        StringVar var = new StringVar(FVar.TYPE_STRING,m.group(1));
        return  var;

    }
    public StringVar(int type, Object object) {
        super(type, object);
    }
}
