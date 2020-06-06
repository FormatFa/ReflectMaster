package plugin.kpa.scriptparser.Script;

import junit.framework.TestCase;

/**
 * Created by formatfa on 18-5-30.
 */
public class FParserTest extends TestCase {
    public void testParseIf() throws Exception {

        FParser fParser = new FParser();
        fParser.setDebug(true);
        fParser.addScriptListener(new ScriptListener() {
            @Override
            public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args) {
               InvokeMethodResult result = new InvokeMethodResult();
               switch (name)
               {
                   case "print":
                       System.out.println("输出:"+args[0]);
                   break;
                   default:
                       result.setHasMethod(false);
               }

                return result;
            }

            @Override
            public void parserLog(String log) {

            }
        });
        fParser.setDebug(false);
        fParser.parse("p='00'");
        fParser.parse("if _p!='00' :print('fff')");

        fParser.parse("method add(a,b):if _a>4 :{print('daub');print(_a);print(_b);print(_a+_b) end");
        fParser.parse("add(5,7)");
    }

}