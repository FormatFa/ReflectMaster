package formatfa.android.f;

import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

public class FaviroteWindow {

    Context context;
    WindowManager wm;
    WindowManager.LayoutParams lp;

    private LinearLayout layout;
    private EditText beizhu;
    private EditText name;

    public FaviroteWindow(Context context, int p) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);


        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        beizhu = new EditText(context);
        beizhu.setHint("备注");
        beizhu.setText("......");


        name = new EditText(context);
        name.setHint("key");
        name.setText(String.valueOf(p));


    }


}
