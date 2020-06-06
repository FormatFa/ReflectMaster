package formatfa.android.f;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

import java.io.File;
import java.util.Locale;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InnerScriptListener;
import plugin.kpa.scriptparser.Script.InvokeMethodResult;
import plugin.kpa.scriptparser.Script.ScriptListener;

public class DiyHookCallBack extends XC_MethodHook {

    private XC_LoadPackage.LoadPackageParam packparam;

    private String str;

    private int mode;

    public DiyHookCallBack(XC_LoadPackage.LoadPackageParam param, String str, int mode) {
        this.packparam = param;
        this.str = str;
        this.mode = mode;
    }

    public DiyHookCallBack(String str, int mode) {
        this.mode = mode;
        this.str = str;

    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

        if (mode == 1) return;

        parse(param);

        super.beforeHookedMethod(param);
    }

    private void parse(MethodHookParam param) {
        fkconfig conf = new fkconfig();
        fake fake = fk.newfake(conf);

        fk.set_callback(fake, new callback() {
            @Override
            public void on_error(fake f, String file, int lineno, String func, String str) {
                XposedBridge.log(String.format(Locale.CHINA, "line %d,func:%s,error:%s", lineno, func, str));

            }

            @Override
            public void on_print(fake f, String str) {

            }
        });

        fk.openbaselib(fake);
        fk.regclass(fake, ScriptWindow.io.class);
        fk.regclass(fake, MethodHookParam.class);


        if (str.startsWith("!")) {
            File file = null;
            if (str.startsWith("!/")) {
                file = new File(str.substring(1));

            } else {
                file = new File("/sdcard/rf", str.substring(1));
            }
            fk.parse(fake, file.getAbsolutePath());
        } else
            fk.parsestr(fake, str);
        fk.run(fake, "hook", param);
//
//		FParser parser = new FParser();
//		int p = 0;
//		parser.getVm().setValue("this",param.thisObject);
//
//		//关掉debug吧！
//		parser.setDebug(false);
//
//        parser.setInnerScriptListener(new InnerScriptListener(packparam.classLoader));
//
//        if(param.args!=null)
//		for(Object obj:param.args)
//		{
//			parser.getVm().setValue("arg"+p,obj);
//			p+=1;
//
//		}
//		parser.addScriptListener(new DiyListener(param));
//		try {
//			parser.parseCodes(str);
//		} catch (Exception e) {
//			XposedBridge.log(e.toString());
//		}

    }

    class DiyListener implements ScriptListener {
        private XC_MethodHook.MethodHookParam hook;

        public DiyListener(XC_MethodHook.MethodHookParam hook) {
            this.hook = hook;
        }

        @Override
        public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args) {

            InvokeMethodResult result = new InvokeMethodResult();
            switch (name) {
                case "print":
                    XposedBridge.log("print:" + args[0]);
                    break;

                case "setResult":
                    hook.setResult(args[0]);
                    break;
                case "getResult":
                    result.setObject(hook.getResult());
                case "setArg":
                    int p = (int) args[0];
                    hook.args[p] = args[1];
                default:
                    result.setHasMethod(false);
            }
            return result;
        }

        @Override
        public void parserLog(String log) {

        }


    }

    @Override
    protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {


        if (mode == 0) return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                parse(param);


            }
        }).start();

        super.afterHookedMethod(param);
    }


}
