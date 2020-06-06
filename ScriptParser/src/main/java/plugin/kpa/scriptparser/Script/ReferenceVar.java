package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-22.
 */

public class ReferenceVar extends  FVar {


    public static FVar parseReference(FParser parser, String code)
    {
        Matcher matcher = ReUtils.matcher(FParser.referencePattern,code);
        String varname = matcher.group(1);
        if(!parser.getVm().getVars().containsKey(varname))
        {
            parser.log("嘗試獲取:"+varname+"，但變量池中似乎沒有這個變量");
        }
        ReferenceVar var = new ReferenceVar(FVar.TYPE_REFERENCE,parser.getVm().getVars().get(varname));

        return var;

    }

    public ReferenceVar(int type, Object object) {
        super(type, object);
    }
}
