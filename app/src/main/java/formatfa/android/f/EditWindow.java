package formatfa.android.f;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by formatfa on 18-4-29.
 */


public class EditWindow extends Window {

    public interface EditWindowListener {
        public void onEdited(String str);


    }

    public EditWindowListener getListener() {
        return listener;
    }

    public void setListener(EditWindowListener listener) {
        this.listener = listener;
    }

    private EditWindowListener listener;

    @Override
    public void show(final WindowManager manager, WindowManager.LayoutParams lp) {
        final EditText value = new EditText(act);
        value.setTextColor(Color.RED);
        final LinearLayout layout = new LinearLayout(act);
        layout.setBackgroundColor(Color.BLACK);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout buttonLayout = new LinearLayout(act);

        Button close = new Button(act);
        close.setText("关闭");
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                manager.removeView(layout);
            }
        });
        buttonLayout.addView(close);


        Button ok = null;


        ok = new Button(act);
        ok.setText("确定");
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                if (listener != null) {
                    listener.onEdited(value.getText().toString());
                }
                manager.removeView(layout);
            }
        });


        buttonLayout.addView(ok);

        layout.addView(buttonLayout);


        TextView msg = new TextView(act);
        msg.setTextColor(Color.BLUE);
        msg.setText(this.msg);
        if (text != null)
            value.setText(String.valueOf(text));


        lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        layout.addView(msg);
        layout.addView(value);
        manager.addView(layout, lp);

    }

    private String msg, text;

    public EditWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, String msg, String text) {
        super(lpparam, param, act, null);

        this.msg = msg;
        this.text = text;
        ;
    }

}
