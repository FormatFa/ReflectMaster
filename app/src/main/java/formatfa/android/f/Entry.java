package formatfa.android.f;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XSharedPreferences;

import formatfa.reflectmaster.MainActivity;
import formatfa.reflectmaster.ScriptItem;

public class Entry implements IXposedHookLoadPackage {


    public static final String PACKAGENAME = "formatfa.android.f.reflectmaster";

    public static String id;
    public static String register;
    public static int statu;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XSharedPreferences sp = new XSharedPreferences("formatfa.reflectmaster", "package");
        sp.reload();
        String[] s = sp.getString(MainActivity.KEY, "").split(",");
        //XposedBridge.log("f hook:"+lpparam.packageName);
        boolean is = false;
        for (String g : s) {

            if (g.equals(lpparam.packageName)) {
                is = true;
                break;
            }
        }
        if (!is) {
            return;
        }
        Registers.isUseWindowSearch = sp.getBoolean("windowsearch", false);
        Registers.isFloating = sp.getBoolean("float", true);
        Registers.newThread = sp.getBoolean("newthread", false);


        id = sp.getString("fid", "");
        statu = sp.getInt("statu", 0);
        register = sp.getString("register", "");

        XposedBridge.log("aim hooked");
        Registers.windowSize = sp.getInt("width", 700);
        Registers.rotate = sp.getBoolean("rotate", true);
        XposedBridge.log("set Window size:" + Registers.windowSize);


        LogUtils.loge("the aim app had hook");
        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onCreate", Bundle.class, new onCreate_Hook(lpparam));
        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onResume", new onResume_Hook());
        XposedHelpers.findAndHookMethod("android.app.Dialog", lpparam.classLoader, "onKeyDown", int.class, KeyEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int keyCode = (int) param.args[0];
                Dialog dialog = (Dialog) param.thisObject;


                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    FWindow win = new FWindow(Registers.nowAct, dialog);
                    //win.setDialog(dialog);

                }
            }
        });


        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onKeyDown", int.class, KeyEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int keyCode = (int) param.args[0];


                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    Registers.nowAct = (Activity) param.thisObject;
                    FWindow win = new FWindow(lpparam, param);

                }
            }
        });


        //自定义hook

        String data = sp.getString("script2", null);
        XposedBridge.log("diy script :" + data);
        if (data != null) {
            JSONArray ja = new JSONArray(data);
            for (int i = 0; i < ja.length(); i += 1) {
                JSONObject object = ja.getJSONObject(i);


                String packagename = object.getString("packagename");
                if (!lpparam.packageName.equals(packagename)) {
                    XposedBridge.log("no:" + packagename.toString());
                    continue;
                }
                ;
                String name = object.getString("name");

                //判断是before还是after
                int mode = 0;
                if (name.startsWith("af"))
                    mode = 1;
                name = name.substring(2);
                String code = object.getString("code");
                String[] names = name.split(" ");
                ;
                Object[] objects = new Object[names.length - 2 + 1];

                for (int e = 0; e < objects.length - 1; e += 1) {
                    objects[e] = getCls(lpparam.classLoader, names[e + 2]);
                }

                //before
                objects[objects.length - 1] = new DiyHookCallBack(lpparam, code, mode);
                XposedBridge.log("find and hook:" + names[1]);

                XposedHelpers.findAndHookMethod(names[0], lpparam.classLoader, names[1], objects);


            }


        }


        //XposedHelpers.findAndHookMethod("android.app.Activity",lpparam.classLoader,"onRestart",new onCreate_Hook(lpparam));

//		String servi = sp.getString(lpparam.packageName+"_service",null);
//		LogUtils.loge("service hook:"+servi);
//		if(servi!=null&&"".equals(servi)==false)
//		{
//		JSONArray ja = new JSONArray(servi);
//		for(int i =0;i<ja.length();i+=1)
//		{
//			XposedHelpers.findAndHookMethod(ja.getString(i),lpparam.classLoader,"onCreate",new Service_hook(lpparam));
//			
//		}
//		}


//		String methodHook = sp.getString(lpparam.packageName+"_method",null);
//		LogUtils.loge("method hook:"+methodHook);
//		
//		if(methodHook!=null&&"".equals(methodHook)==false)
//		{
//			XposedBridge.log("method:"+methodHook);
//			String[] lines = methodHook.split("\n");
//			
//			for(String a :lines)
//			{
//				//class name,method mame,print type
//				String[] info = a.split(" ");
//				
//				if(info.length<3)continue;
//			Object[] params = new Object[info.length-1];
//			for(int i =0;i<info.length-2;i+=1)
//			{
//				params[i]=lpparam.classLoader.loadClass(info[i+2]);
//			}
//				params[params.length-1]=new DiyHookCallBack("");
//			XposedHelpers.findAndHookMethod(info[0],lpparam.classLoader,info[1],params);
//				
//				
//				
//			}
//			
//			
//			
//			
//		}
//		else
//			XposedBridge.log("method:"+methodHook);
        //method.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
        //XposedHelpers.findAndHookMethod("android.app.Fragment",lpparam.classLoader,"onCreateView",LayoutInflater.class,ViewGroup.class, Bundle.class,new Fragment_hook(lpparam));
//		if(lpparam.packageName.equals("tv.danmaku.bili"))
//		XposedHelpers.findAndHookConstructor("com.bilibili.bangumi.api.uniform.BangumiUniformSeason.Right",lpparam.classLoader,  new XC_MethodHook(){
//				@Override
//				protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable
//				{
//					new Thread(new Runnable(){
//
//							@Override
//							public void run()
//							{
//								while(true){
//									XposedHelpers.setBooleanField(param.thisObject,"allowDownload",true);
//									XposedHelpers.setBooleanField(param.thisObject,"areaLimit",false);
//								}
//							}
//						}).start();
//
//				}
//
//			});

    }


    private Class getCls(ClassLoader loader, String conciaType) throws ClassNotFoundException {


        XposedBridge.log("getCls:" + conciaType);
        switch (conciaType) {
            case "int":
                return int.class;

            case "boolean":
                return boolean.class;

            case "long":
                return long.class;

            case "byte":
                return byte.class;


            default:
                return loader.loadClass(conciaType);


        }


    }


}
