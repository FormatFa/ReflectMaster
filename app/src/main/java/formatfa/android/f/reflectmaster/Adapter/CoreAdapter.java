package formatfa.android.f.reflectmaster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import formatfa.reflectmaster.R;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoreAdapter extends BaseAdapter {

    public CoreAdapter(Context context, String data) throws Exception {
        this.context = context;
        this.data = data;
        if (data == null) throw new Exception("Data null Exception");
        JSONArray ja = new JSONArray(data);
        if (ja == null) return;
        cores = new ArrayList<CoreItem>();
        for (int i = 0; i < ja.length(); i += 1) {
            CoreItem item = new CoreItem();
            JSONObject ob = ja.getJSONObject(i);

            item.setName(ob.getString("name"));
            item.setInfo(ob.getString("info"));
            item.setPrice(ob.getString("pirce"));
            cores.add(item);
        }


    }

    class ViewHolder {

        TextView price;

        TextView info;
    }

    public class CoreItem {
        private String name;
        private String price;
        private String info;


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    private Context context;

    private String data;
    private List<CoreItem> cores;

    @Override
    public int getCount() {

        return cores.size();
    }

    @Override
    public Object getItem(int p1) {

        return cores.get(p1);
    }

    @Override
    public long getItemId(int p1) {

        return cores.get(p1).hashCode();
    }

    @Override
    public View getView(int p1, View p2, ViewGroup p3) {
        ViewHolder vh = null;
        if (p2 == null) {
            vh = new ViewHolder();
            LayoutInflater in = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            p2 = in.inflate(R.layout.item_script, null);
            vh.price = (TextView) p2.findViewById(R.id.price);
            vh.info = (TextView) p2.findViewById(R.id.info);
            p2.setTag(vh);
        } else
            vh = (ViewHolder) p2.getTag();
        vh.info.setText(cores.get(p1).getInfo());
        vh.price.setText(cores.get(p1).getPrice() + "元");
        return p2;
    }

}
