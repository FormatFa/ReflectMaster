package formatfa.android.f;

import android.app.Activity;
import android.view.WindowManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class onCreate_Hook extends XC_MethodHook {

    private XC_LoadPackage.LoadPackageParam lpparam;


    WindowManager wm;


    public onCreate_Hook(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;

    }

    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

    }

    @Override
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
        XposedBridge.log(lpparam.packageName + " has hook by F:" + param.thisObject.getClass().getSimpleName());

        Registers.nowAct = (Activity) param.thisObject;
        FWindow win = new FWindow(lpparam, param);
        super.afterHookedMethod(param);
    }


}
