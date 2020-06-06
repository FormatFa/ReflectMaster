package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by formatfa on 18-4-22.
 */

public class ReUtils {
    public static Matcher matcher (String reg, String cide)
    {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(cide);
        matcher.find();
        return  matcher;
    }

}
