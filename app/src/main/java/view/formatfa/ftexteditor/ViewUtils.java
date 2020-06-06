package view.formatfa.ftexteditor;

import android.content.Context;

import java.lang.reflect.Field;

public class ViewUtils {
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            //   Log.d(TAG, "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }

    }
}
