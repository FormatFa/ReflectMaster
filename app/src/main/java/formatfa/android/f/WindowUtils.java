package formatfa.android.f;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.view.WindowManager;
import android.widget.TableRow.LayoutParams;
import android.os.Build;

public class WindowUtils {
    public static void err(Context context) {
        Toast.makeText(context, "测试版不提供此功能", Toast.LENGTH_SHORT).show();
    }

    public static WindowManager getWm(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(c.WINDOW_SERVICE);
        return wm;
    }

    public static WindowManager.LayoutParams getLp() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = lp.TYPE_APPLICATION;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        else
            lp.flags = lp.FLAG_NOT_FOCUSABLE;
        return lp;

    }

    public static View getLineView(Context context) {
        View line = new View(context);
        ViewGroup.LayoutParams he = new ViewGroup.LayoutParams(-1, 9);
        line.setBackgroundColor(Color.BLUE);
        line.setLayoutParams(he);
        return line;
    }

}
