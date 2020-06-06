package formatfa.android.f.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;

import formatfa.android.f.Version;
import formatfa.android.f.Registers;

public class FieldAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private Field[] fields;

    private Field[] rawFields;

    private Object obj;

    public void setFields(Field[] fields) {

        this.fields = fields;
        rawFields = fields;
    }

    public FieldAdapter(Context context, Field[] fields, Object obj) {
        this.context = context;
        this.fields = fields;
        this.obj = obj;
        rawFields = fields;
    }

    public FieldAdapter(Context context, Field[] fields) {
        this.context = context;
        this.fields = fields;
    }

    @Override
    public int getCount() {

        return fields.length;
    }

    @Override
    public Object getItem(int p1) {

        return fields[p1];
    }

    @Override
    public long getItemId(int p1) {

        return fields[p1].hashCode();
    }

    @Override
    public View getView(int p1, View p2, ViewGroup p3) {
        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);
        TextView name = new TextView(context);
        TextView type = new TextView(context);
        TextView value = new TextView(context);

        name.setText(fields[p1].getName());

        type.setText(fields[p1].getType().getCanonicalName());
        if (fields[p1].isAccessible() == false) fields[p1].setAccessible(true);


        if (Version.checkVersion(null, "showVar")) {
            try {
                value.setText(Registers.getObjectString(fields[p1].get(obj)));
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            }
        }

        name.setTextColor(Color.WHITE);
        type.setTextColor(Color.GREEN);
        value.setTextColor(Color.WHITE);
        layout.addView(name);
        layout.addView(type);
        layout.addView(value);
        return layout;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults results = new FilterResults();
                List<Field> temp = new ArrayList<>();
                for (Field field : rawFields) {
                    if (field.getName().toUpperCase().contains(charSequence.toString().toUpperCase()))
                        temp.add(field);


                }
                results.values = temp.toArray(new Field[0]);
                results.count = temp.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                fields = (Field[]) filterResults.values;
                if (filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
