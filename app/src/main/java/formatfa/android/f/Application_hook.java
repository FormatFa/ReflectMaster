package formatfa.android.f;

import android.app.Service;
import android.view.WindowManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import android.app.Application;

public class Application_hook extends XC_MethodHook {

    XC_LoadPackage.LoadPackageParam lpparam;


    public Application_hook(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;

    }

    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

    }

    @Override
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
        XposedBridge.log(lpparam.packageName + "service has hook by F");

        Registers.add((Application) param.thisObject, param.thisObject);
        //FWindow win=new FWindow(lpparam,param);
        // TODO: Implement this method
        super.afterHookedMethod(param);
    }


}
