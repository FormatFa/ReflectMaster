package formatfa.android.f.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Color;
import android.text.style.TextAppearanceSpan;
import android.R;

public class TextAdapter extends BaseAdapter {

    Context context;
    String[] items;

    public TextAdapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {

        return items.length;
    }

    @Override
    public Object getItem(int p1) {
        // TODO: Implement this method
        return items[p1];
    }

    @Override
    public long getItemId(int p1) {
        // TODO: Implement this method
        return items[p1].hashCode();
    }

    @Override
    public View getView(int p1, View p2, ViewGroup p3) {
        TextView tv = new TextView(context);
        tv.setTextSize(19);
        //	tv.setTextAppearance(R.style.TextAppearance_Medium);
        //tv.setTextSize(
        tv.setText(items[p1]);
        tv.setTextColor(Color.RED);
        // TODO: Implement this method
        return tv;
    }

}
