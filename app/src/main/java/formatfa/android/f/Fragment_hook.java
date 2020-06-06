package formatfa.android.f;

import android.view.WindowManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Fragment_hook extends XC_MethodHook {

    XC_LoadPackage.LoadPackageParam lpparam;


    WindowManager wm;


    public Fragment_hook(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;

    }

    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

    }

    @Override
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {


        XposedBridge.log(lpparam.packageName + "  Fragment has hook by F");

        Registers.add(null, param.thisObject);
        //FWindow win=new FWindow(lpparam,param,param.thisObject);

        // TODO: Implement this method
        super.afterHookedMethod(param);
    }

}
