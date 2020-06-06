package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-22.
 */

public class IntVar extends  FVar {

    public IntVar(int type, Object object) {
        super(type, object);
    }
    public static IntVar parseIntVar(String code)
    {
        Matcher m = ReUtils.matcher(FParser.stringPattern,code);
        IntVar var = new IntVar(FVar.TYPE_STRING,Integer.parseInt(code));
        return  var;

    }
}
