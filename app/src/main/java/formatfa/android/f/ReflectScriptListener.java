package formatfa.android.f;

import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.XposedBridge;
import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InvokeMethodResult;
import plugin.kpa.scriptparser.Script.ScriptListener;

/**
 * Created by formatfa on 18-4-26.
 */

public class ReflectScriptListener implements ScriptListener {


    ScriptWindow window;

    public ReflectScriptListener(ScriptWindow window) {
        this.window = window;
    }


    @Override
    public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args) {
        InvokeMethodResult result = new InvokeMethodResult();


        switch (name) {
            case "print":
                Message msg = new Message();
                msg.what = 1;
                msg.obj = args[0];
                window.handler.sendMessage(msg);
                XposedBridge.log("" + args[0]);

                break;
            case "toast":
                Message w = new Message();
                w.what = 3;
                w.obj = args[0];
                window.handler.sendMessage(w);

                break;
            //内置函数，复制值到剪切版
            case "copytext":
                Message msk = new Message();
                msk.what = 4;
                msk.obj = args[0];
                window.handler.sendMessage(msk);
                break;
            case "fieldwindow":
                Message ms = new Message();
                ms.what = 2;
                ms.obj = args[0];
                window.handler.sendMessage(ms);
                break;
            case "gettempvar":

                result.setObject(getTempVar(args));
                break;
            //不断修改一个变量的值
            case "setFieldWhile":

            default:
                result.setHasMethod(false);

        }

        return result;
    }

    //获取保存在临时变量里的object
    private Object getTempVar(Object args[]) {
        System.out.println("get temp var:" + args[0]);
        int offset = (int) args[0];

        if (offset < Registers.objects.size())
            return Registers.objects.get(offset);
        else
            return null;
    }

    @Override
    public void parserLog(String log) {

        // textView.append(log);
        // textView.append("\n");
    }
}
