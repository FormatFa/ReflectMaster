package formatfa.android.f.Adapter;

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

public class MethodAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private Method[] methods;

    private Method[] rawMethod;


    public void setMethods(Method[] ms) {
        this.methods = ms;
        rawMethod = ms;
    }

    public MethodAdapter(Context context, Method[] fields) {
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

        returntype.setText(methods[p1].getGenericReturnType().toString());
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
                List<Method> temp = new ArrayList<>();
                for (Method method : rawMethod) {
                    if (method.getName().toUpperCase().contains(charSequence.toString().toUpperCase()))
                        temp.add(method);


                }
                results.values = temp.toArray(new Method[0]);
                results.count = temp.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                methods = (Method[]) filterResults.values;
                if (filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
