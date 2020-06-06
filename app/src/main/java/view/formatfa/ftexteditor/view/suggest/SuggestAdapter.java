package view.formatfa.ftexteditor.view.suggest;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SuggestAdapter extends BaseAdapter {

    private Context context;
    private List<SuggestItem> result;

    public SuggestAdapter(Context context, List<SuggestItem> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return result.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);
        TextView text = new TextView(context);


        text.setTextColor(Color.BLACK);
        text.setText(result.get(position).getText());
        TextView desc = new TextView(context);

        desc.setTextColor(Color.GRAY);
        desc.setText(result.get(position).getDescript());
        layout.addView(text);
        layout.addView(desc);

        return layout;
    }
}
