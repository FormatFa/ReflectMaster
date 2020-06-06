package plugin.kpa.scriptparser.Script;

/**
 * Created by formatfa on 18-4-22.
 */

public interface ScriptListener {


public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args);
public void parserLog(String log);

}
