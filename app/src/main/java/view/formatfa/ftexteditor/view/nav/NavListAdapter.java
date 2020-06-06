package view.formatfa.ftexteditor.view.nav;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NavListAdapter extends BaseAdapter {

    private Context context;
    private List<NavResult> results;

    public NavListAdapter(Context context, List<NavResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return results.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new TextView(context);
        }
        TextView tv = (TextView) convertView;
        //  tv.setBackgroundColor(Color.BLACK);
        //tv.setTextColor(Color.RED);

        //   tv.setTextSize(28);

        tv.setSingleLine(true);
        tv.setText(results.get(position).getTitle());


        return convertView;
    }
}
