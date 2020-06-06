package formatfa.android.f.ClassHandle;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import formatfa.android.f.FieldWindow;
import formatfa.android.f.widget.WindowList;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;

public class Handle_ViewGroup extends ClassHandle {

    @Override
    public void handle(LinearLayout layout) {
        Button button = new Button(context);
        button.setText("查看字View列表");
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                showList();
            }
        });
        layout.addView(button);
    }

    Context context;
    Object obj;

    public Handle_ViewGroup(Context context, Object obj) {
        super(context, obj);
        this.context = context;
        this.obj = obj;
    }

    void showList() {
        final WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        WindowList wl = new WindowList(context, wm);
        List<String> st = new ArrayList<String>();

        ViewGroup g = (ViewGroup) obj;
        final List<View> views = new ArrayList<View>();

        for (int i = 0; i < g.getChildCount(); i += 1) {

            views.add(g.getChildAt(i));
            st.add(g.getChildAt(i).getClass().getCanonicalName());

        }

        wl.setItems(st);
        wl.setListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                FieldWindow.newWindow(null, null, context, views.get(p3), wm);
            }
        });
        wl.setTitle("ViewGroupHandle,len:" + views.size());
        wl.show();

    }

}
