package formatfa.android.f.reflectmaster.Adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import formatfa.reflectmaster.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApkAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<PackageInfo> rawApps;
    private List<PackageInfo> apps;

    private PackageManager pm;
    private HashMap<String, Drawable> icons;
    private HashMap<String, String> names;

    private List<String> aimPackage;

    public ApkAdapter(Context context, List<PackageInfo> apps) {
        this.context = context;
        this.apps = apps;
        rawApps = apps;
        pm = context.getPackageManager();
        icons = new HashMap<String, Drawable>();
        names = new HashMap<String, String>();

        for (PackageInfo info : apps) {
            names.put(info.packageName, info.applicationInfo.loadLabel(pm).toString());
            icons.put(info.packageName, info.applicationInfo.loadIcon(pm));

        }
    }

    public void setAimPackage(List<String> aimPackage) {
        this.aimPackage = aimPackage;
    }

    public List<String> getAimPackage() {
        return aimPackage;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<PackageInfo> apps = new ArrayList<PackageInfo>();
                for (PackageInfo info : rawApps) {
                    if (info.packageName.contains(constraint.toString().toUpperCase()) || info.applicationInfo.loadLabel(pm).toString().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        apps.add(info);
                    }
                }

                FilterResults results = new FilterResults();
                results.count = apps.size();
                results.values = apps;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results.count > 0) {
                    apps = (List<PackageInfo>) results.values;
                    ApkAdapter.this.notifyDataSetChanged();
                } else {
                    ApkAdapter.this.notifyDataSetInvalidated();
                }
                ApkAdapter.this.notifyDataSetChanged();
            }
        };
    }


    class ViewHolder {

        ImageView image;

        TextView name;
    }

    @Override
    public int getCount() {

        return apps.size();
    }

    @Override
    public Object getItem(int p1) {

        return apps.get(p1);
    }

    @Override
    public long getItemId(int p1) {
        // TODO: Implement this method
        return apps.get(p1).hashCode();
    }

    @Override
    public View getView(int p1, View p2, ViewGroup p3) {
        ViewHolder vh = null;
        if (p2 == null) {
            vh = new ViewHolder();
            LayoutInflater in = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            p2 = in.inflate(R.layout.item_apk, null);
            vh.image = (ImageView) p2.findViewById(R.id.icon);
            vh.name = (TextView) p2.findViewById(R.id.name);
            p2.setTag(vh);
        } else
            vh = (ViewHolder) p2.getTag();
        Drawable d = icons.get(apps.get(p1).packageName);
        String n = names.get(apps.get(p1).packageName);

        vh.image.setImageDrawable(d);
        vh.name.setText(n);
        if (vh.name.getPaint().getColor() == Color.RED) {

        }
        boolean has = false;
        if (aimPackage != null) {
            for (String pack : aimPackage)
                if (pack.equals(apps.get(p1).packageName))
                    has = true;
        }

        if (has)
            vh.name.setTextColor(Color.RED);
        else vh.name.setTextColor(Color.WHITE);
        return p2;
    }


}
