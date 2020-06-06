package plugin.kpa.scriptparser.Script;

/**
 * Created by formatfa on 18-5-1.
 */

public class BooleanVar extends FVar {
    public BooleanVar(int type, Object object) {
        super(type, object);
    }

    public static FVar parseBoolean(String code)
    {
        if("true".equals(code))
        {
            return new BooleanVar(FVar.TYPE_BOOLEAN,true);
        }
        else
        {
            return new BooleanVar(FVar.TYPE_BOOLEAN,false);
        }
    }
}
