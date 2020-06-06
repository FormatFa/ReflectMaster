package formatfa.android.f.ClassHandle;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import formatfa.android.f.widget.WindowList;

import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Adapter;

import formatfa.android.f.FieldWindow;

public class Handle_ArrayList extends ClassHandle {

    ArrayList list;

    @Override
    public void handle(LinearLayout layout) {
        Button button = new Button(context);
        button.setText("查看List列表");
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

    public Handle_ArrayList(Context context, Object obj) {
        super(context, obj);
        this.context = context;
        this.obj = obj;
        list = (ArrayList) obj;
    }


    void showList() {
        final WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        WindowList wl = new WindowList(context, wm);
        List<String> st = new ArrayList<String>();

        for (Object o : list) st.add(o.toString());
        wl.setItems(st);
        wl.setListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                FieldWindow.newWindow(null, null, context, list.get(p3), wm);
            }
        });
        wl.setTitle("ArrayListHandle,len:" + list.size());
        wl.show();

    }
}
