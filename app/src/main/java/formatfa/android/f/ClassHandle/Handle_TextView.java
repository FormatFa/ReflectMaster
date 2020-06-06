package formatfa.android.f.ClassHandle;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.content.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import formatfa.android.f.WindowUtils;
import formatfa.android.f.widget.EditWindow;
import formatfa.android.f.widget.EditWindowListener;


public class Handle_TextView extends ClassHandle {

    @Override
    public void handle(LinearLayout layout) {
        String s = text.getText().toString();
        if (s.length() > 50) s = s.substring(25);
        Button button = new Button(context);
        button.setText("复制:" + s);
        button.setTextColor(Color.RED);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("test", text.getText().toString()));
                Toast.makeText(context, "复制成功:" + text.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        Button setText = new Button(context);
        setText.setText("修改文字");
        setText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                EditWindow editWindow = new EditWindow(null, null, context, "输入新文字", text.getText().toString(), new EditWindowListener() {
                    @Override
                    public void onEdit(String value) {
                        text.setText(value);
                    }
                });
                editWindow.show(WindowUtils.getWm(context


                ), WindowUtils.getLp());


            }
        });


        layout.addView(button);
        layout.addView(setText);
    }

    private Context context;
    private Object obj;
    TextView text;

    public Handle_TextView(Context context, Object obj) {
        super(context, obj);
        this.context = context;
        this.obj = obj;
        text = (TextView) obj;


    }

}
