package plugin.kpa.scriptparser;

import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InvokeMethodResult;
import plugin.kpa.scriptparser.Script.ScriptListener;

/**
 * Created by formatfa on 18-4-22.
 */

public class TestScript implements ScriptListener{


    @Override
    public InvokeMethodResult invokeMethod(FParser parser,String name, Object[] args) {

        InvokeMethodResult result = new InvokeMethodResult();

        if("print".equals(name))
        {
            System.err.println("print test:"+args[0]);
        }
        else if("".equals(name))
        {

        }

        else
        {
            result.setHasMethod(false);
        }

        return result;
    }

    @Override
    public void parserLog(String log) {

    }
}
