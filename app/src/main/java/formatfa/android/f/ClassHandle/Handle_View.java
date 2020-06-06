package formatfa.android.f.ClassHandle;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import formatfa.android.f.FieldWindow;
import formatfa.android.f.WindowUtils;

public class Handle_View extends ClassHandle {

    @Override
    public void handle(LinearLayout layout) {
        Button viewClickListener = new Button(context);
        viewClickListener.setText("查看点击事件");
        viewClickListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (View) obj;
                try {
                    Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
                    getListenerInfo.setAccessible(true);
                    Field filed_OnClickListener = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
                    Object mListnerInfo = getListenerInfo.invoke(view);

                    Object mOnClickListener = filed_OnClickListener.get(mListnerInfo);
                    FieldWindow w = new FieldWindow(null, null, context, mOnClickListener);


                    w.show(WindowUtils.getWm(context), WindowUtils.getLp());


                } catch (Exception e) {
                    Toast.makeText(context, "读取点击事件异常:" + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });
        layout.addView(viewClickListener);
    }

    Context context;
    Object obj;

    public Handle_View(Context context, Object obj) {
        super(context, obj);
        this.context = context;
        this.obj = obj;
    }

}
