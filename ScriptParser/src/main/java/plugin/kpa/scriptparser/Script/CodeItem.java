package plugin.kpa.scriptparser.Script;



/**
 * Created by formatfa on 18-4-22.
 */

public  abstract class CodeItem {
    public abstract  CodeItem parse(String code);

    public CodeItem(String pattern)
    {

    }
}
