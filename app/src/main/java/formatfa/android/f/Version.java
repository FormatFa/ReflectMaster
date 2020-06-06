package formatfa.android.f;

import android.content.Context;
import android.widget.Toast;

import formatfa.android.f.reflectmaster.Utils.Utils;

public class Version {
    ///	public static boolean showVar=true,invokeMethod=true,editField=true;
    public static boolean isTestMode;

    public static boolean checkVersion(Context c, String key) {
        //	if(c!=null)
//			Toast.makeText(c,Entry.register+"j'j"+Utils.get(Entry.id),Toast.LENGTH_LONG).show();
        boolean isSupport = false;
        if (!isTestMode)
            switch (key) {
                case "showVar":
//				if(Entry.statu==1)
//				{
//					return true;
//				}
//				else
//
                    isSupport = true;

                    break;

                case "invokeMethod":
                    if (Entry.statu == 1) {
                        if (Entry.register.equals("" + Utils.get(Entry.id))) return true;
                        else
                            return false;
                    } else
                        isSupport = false;
                    break;

                case "editField":
                    if (Entry.statu == 1) {
                        return (Entry.register.equals("" + Utils.get(Entry.id)));

                    } else
                        isSupport = false;
                    break;


            }
        if (!isSupport) {
            if (c != null)
                Toast.makeText(c, "未注册版本不支持此功能", Toast.LENGTH_LONG).show();
        }
        if (isTestMode) {
            isSupport = true;
        }
        return isSupport;

    }

}
