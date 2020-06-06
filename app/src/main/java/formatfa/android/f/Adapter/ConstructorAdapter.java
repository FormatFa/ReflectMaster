package formatfa.android.f.Adapter;

/**
 * Created by formatfa on 18-5-12.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConstructorAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private Constructor[] methods;

    private Constructor[] rawMethod;


    public void setMethods(Constructor[] ms) {
        this.methods = ms;
        rawMethod = ms;
    }

    public ConstructorAdapter(Context context, Constructor[] fields) {
        this.context = context;
        this.methods = fields;

        rawMethod = methods;
    }

    @Override
    public int getCount() {
        // TODO: Implement this method
        return methods.length;
    }

    @Override
    public Object getItem(int p1) {
        // TODO: Implement this method
        return methods[p1];
    }

    @Override
    public long getItemId(int p1) {
        // TODO: Implement this method
        return methods[p1].hashCode();
    }

    @Override
    public View getView(int p1, View p2, ViewGroup p3) {
        LinearLayout layout = new LinearLayout(context);


        layout.setOrientation(LinearLayout.VERTICAL);
        TextView name = new TextView(context);
        TextView params = new TextView(context);
        TextView returntype = new TextView(context);

        //  returntype.setText(String.valueOf(methods[p1].ge()));
        name.setText(methods[p1].getName());

        StringBuilder sb = new StringBuilder();
        for (Type p : methods[p1].getGenericParameterTypes()) {
            sb.append(p.toString() + ",");
        }
        params.setText(sb.toString());
        //params.setText(fields[p1].gLl);
        if (!methods[p1].isAccessible()) methods[p1].setAccessible(true);

        name.setTextColor(Color.WHITE);
        params.setTextColor(Color.GREEN);
        returntype.setTextColor(Color.RED);
        layout.addView(name);
        layout.addView(params);
        layout.addView(returntype);
        return layout;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults results = new FilterResults();
                List<Constructor> temp = new ArrayList<>();
                for (Constructor method : rawMethod) {
                    if (method.getName().toUpperCase().contains(charSequence.toString().toUpperCase()))
                        temp.add(method);


                }
                results.values = temp.toArray(new Method[0]);
                results.count = temp.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                methods = (Constructor[]) filterResults.values;
                if (filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}

