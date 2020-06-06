package formatfa.android.f.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import formatfa.android.f.Adapter.TextAdapter;

import java.util.List;

import formatfa.android.f.ActionWindow;
import formatfa.android.f.Registers;

import android.system.Os;
import android.os.Build;

public class WindowList implements OnItemClickListener {

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    @Override
    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
        if (listener != null) {
            listener.onItemClick(p1, p2, p3, p4);

        }
        manager.removeView(layout);
    }


    Context context;
    WindowManager manager;


    LinearLayout layout;
    LinearLayout buttonLayout;

    ListView lv;

    TextView titleview;
    OnItemClickListener listener;

    String title;

    public void addButton(Button button) {
        buttonLayout.addView(button);
    }

    public ListView getListView() {
        return lv;
    }

    ListAdapter adapter;


    public void setAdaptet(ListAdapter l) {
        adapter = l;
    }

    public void setItems(String[] items) {
        TextAdapter ta = new TextAdapter(context, items);
        setAdaptet(ta);
    }

    public void setItems(List<String> items) {
        setItems(items.toArray(new String[0]));
    }

    public WindowList(Context context, WindowManager manager) {
        this.context = context;
        this.manager = manager;
        layoutParam = new WindowManager.LayoutParams();
        layoutParam.width = Registers.windowSize;
        layoutParam.height = Registers.windowSize;
        layoutParam.x = 0;
        layoutParam.y = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            layoutParam.flags = layoutParam.FLAG_NOT_TOUCH_MODAL;
        else
            layoutParam.flags = layoutParam.FLAG_NOT_FOCUSABLE;
        layoutParam.type = layoutParam.TYPE_APPLICATION;

        layout = new LinearLayout(context);


        layout.setBackgroundColor(Color.DKGRAY);
        layout.setOrientation(LinearLayout.VERTICAL);

        ActionWindow ar = new ActionWindow(context, manager, layoutParam, layout);
        layout.addView(ar.getActionBar());
        titleview = new TextView(context);
        titleview.setTextColor(Color.WHITE);
        layout.addView(titleview);

        buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(buttonLayout);


        lv = new ListView(context);

        lv.setFastScrollEnabled(true);
        lv.setOnItemClickListener(this);
        layout.addView(lv);


    }

    public void show() {
        show(500, 500);
    }

    WindowManager.LayoutParams layoutParam;

    public void show(int w, int h) {
        layoutParam.width = w;
        layoutParam.height = h;
        Button close = new Button(context);
        close.setText("X");
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                manager.removeView(layout);
            }
        });
        addButton(close);

        titleview.setBackgroundColor(Color.BLUE);
        if (title != null) titleview.setText(title);
        if (adapter != null) lv.setAdapter(adapter);
        manager.addView(layout, layoutParam);
    }


}
