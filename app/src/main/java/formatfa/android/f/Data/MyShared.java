package formatfa.android.f.Data;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyShared {

    private SharedPreferences sp;


    HashMap<String, String> data;
    String key;

    public MyShared(SharedPreferences sp, String key) {
        this.sp = sp;
        this.key = key;

    }

    public HashMap getData() {
        loadData();

        return data;
    }

    public List<String> getKeys() {
        if (data == null) loadData();
        List<String> re = new ArrayList<String>();
        for (String key : data.keySet()) {
            re.add(key);
        }
        return re;
    }

    public void putData(String key, String value) {
        if (data == null) loadData();
        data.put(key, value);

    }

    public void save() {

        JSONArray ja = new JSONArray();
        for (String key : data.keySet()) {

            JSONObject jo = new JSONObject();
            try {
                jo.put("key", key);
                jo.put("value", data.get(key));
            } catch (JSONException e) {
            }

            ja.put(jo);

        }
        sp.edit().putString(key, ja.toString()).commit();

    }

    void loadData() {
        data = new HashMap<String, String>();

        String datas = sp.getString(key, null);
        if (datas == null) return;
        try {
            JSONArray ja = new JSONArray(datas);
            for (int i = 0; i < ja.length(); i += 1) {

                JSONObject item = ja.getJSONObject(i);

                data.put(item.getString("key"), item.getString("value"));

            }


        } catch (JSONException e) {
        }


    }


}
