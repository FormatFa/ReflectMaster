package formatfa.android.f.ClassHandle;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import formatfa.android.f.FieldWindow;
import formatfa.android.f.Registers;
import formatfa.android.f.widget.WindowList;
import formatfa.reflectmaster.Register;

/**
 * Created by formatfa on 18-4-22.
 */

public class Handle_Set extends ClassHandle {

    Set set;

    public Handle_Set(Context context, Object obj) {
        super(context, obj);
        this.context = context;
        this.obj = obj;
        set = (Set) obj;

    }

    @Override
    public void handle(LinearLayout layout) {


        Button button = new Button(context);
        button.setText("查看Set列表");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                showList();
            }
        });
        layout.addView(button);

    }

    void showList() {
        final WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        WindowList wl = new WindowList(context, wm);
        List<String> st = new ArrayList<String>();

        final List<Object> objects = new ArrayList<>();
        final Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object ob = iterator.next();
            st.add(i + " " + Registers.getObjectString(ob));
            objects.add(ob);
        }
        wl.setItems(st);
        wl.setListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {

                FieldWindow.newWindow(null, null, context, objects.get(p3), wm);
            }
        });
        wl.setTitle("SetHandle,len:" + objects.size());
        wl.show();

    }
}
