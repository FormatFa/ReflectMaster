package formatfa.android.f;

import android.app.Activity;

import de.robv.android.xposed.XC_MethodHook;

public class onResume_Hook extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);

        Registers.nowAct = (Activity) param.thisObject;


    }
}
