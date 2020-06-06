package formatfa.android.f;

import android.view.WindowManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import android.app.Service;

public class Service_hook extends XC_MethodHook {

    private XC_LoadPackage.LoadPackageParam lpparam;


    private WindowManager wm;


    public Service_hook(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;

    }

    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

    }

    @Override
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
        XposedBridge.log(lpparam.packageName + "service has hook by F");

        Registers.addService((Service) param.thisObject, param.thisObject);
        //FWindow win=new FWindow(lpparam,param);
        // TODO: Implement this method
        super.afterHookedMethod(param);
    }


}
