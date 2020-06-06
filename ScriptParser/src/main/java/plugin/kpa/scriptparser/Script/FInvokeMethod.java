package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by formatfa on 18-4-22.
 */

public class FInvokeMethod {

    private String name;
    private String params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    String[] getParamsArray()
    {
        return params!=null?params.split(","):null;
    }
    public  static FInvokeMethod parseMethod(String code) {
        FInvokeMethod method = new FInvokeMethod();
        Pattern pattern = Pattern.compile(FParser.methodPattern);
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            method.setName(matcher.group(1));
            method.setParams(matcher.group(2));

        }
        System.out.println("parseMethod Result:" + method.toString());
        return method;
    }

    @Override
    public String toString() {
        return "FInvokeMethod{" +
                "name='" + name + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
