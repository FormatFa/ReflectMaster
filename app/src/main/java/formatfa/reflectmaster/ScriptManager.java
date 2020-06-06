package formatfa.reflectmaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import formatfa.android.f.FWindow;
import formatfa.android.f.Version;

/**
 * Created by formatfa on 18-4-28.
 */

public class ScriptManager extends Activity {

    SharedPreferences sharedPreferences;

    private ListView listView;


    int mode = 0;
    private String[] modeKey = {"script", "script2"};
    private String[] modeTitle = {"测试脚本", "自定义XposedHook"};


    private String pkg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Version.isTestMode = true;
        FWindow window = new FWindow(this, null);


        mode = getIntent().getIntExtra("mode", 0);
        pkg = getIntent().getStringExtra("packagename");
        if (getActionBar() != null) {
            getActionBar().setSubtitle(modeTitle[mode]);
        }
        setContentView(R.layout.scriptmanager);
        listView = findViewById(R.id.list);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(-1, null, null, null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edit(i, items.get(i).getName(), items.get(i).getCode(), items.get(i).getPackagename());
            }
        });
        init();
        try {
            loadscript();
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void init() {

        try {
            sharedPreferences = getSharedPreferences("package", MODE_WORLD_READABLE);
        } catch (SecurityException e) {
            sharedPreferences = getSharedPreferences("package", MODE_PRIVATE);
            e.printStackTrace();
        }


    }


    private List<ScriptItem> items;

    private void loadscript() throws JSONException {
        List<String> names = new ArrayList<>();
        String data = sharedPreferences.getString(modeKey[mode], null);

        items = new ArrayList<>();
        if (data != null) {
            JSONArray ja = new JSONArray(data);
            for (int i = 0; i < ja.length(); i += 1) {
                JSONObject object = ja.getJSONObject(i);

                if (mode == 1) {
                    if (!pkg.equals(object.getString("packagename"))) continue;
                }
                if (mode == 0)
                    items.add(new ScriptItem(object.getString("name"), object.getString("code")));
                else
                    items.add(new ScriptItem(object.getString("name"), object.getString("code"), object.getString("packagename")));


                if (object.has("packagename"))
                    names.add(object.getString("packagename") + " " + object.getString("name"));
                else
                    names.add(object.getString("name"));


            }
        } else {

            ScriptItem test = null;
            if (mode == 0) {
                test = new ScriptItem("View变量移除", "#view移除原理,获取当前View的父View,调用父View的removeView方法\n" +
                        "#c初始化，获取getParent方法和removeView方法\n" +
                        "getParent=getMethod('android.view.View','public final android.view.ViewParent android.view.View.getParent()')\n" +
                        "print(_getParent)\n" +
                        "removeView=getMethod('android.view.ViewGroup','public void android.view.ViewGroup.removeView(android.view.View)')\n" +
                        "#打印测试\n" +
                        "print(_removeView)\n" +
                        "#函数调用，引用之前的变量要用_开头\n" +
                        "viewParent=invokeMethod(_getParent,_this)\n" +
                        "\n" +
                        "invokeMethod(_removeView,_viewParent,_this)");
                items.add(test);
                names.add(test.getName());
            } else {

            }


        }
        if (names.size() == 0) {
            Toast.makeText(this, "脚本数目为零，按菜单键添加", Toast.LENGTH_LONG).show();

        }
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));


    }

    public void edit(final int p, final String name, final String code, final String pkg) {
        //    CodeDialog dialog = new CodeDialog(this);

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.scriptedit, null);
        final EditText editname = view.findViewById(R.id.name);
        final EditText editcode = view.findViewById(R.id.code);

        final TextView textView = view.findViewById(R.id.pkg);
        if (pkg != null) {
            textView.setText("目标软件:" + pkg);
        }
        if (mode == 0)
            editname.setHint("脚本名字");
        else {
            editname.setHint("要hook的类和方法");
        }
        editcode.setHint("代码");
        if (name != null) editname.setText(name);
        if (code != null) editcode.setText(code);


        //dialog.setCode(code);
        //dialog.show();
        ab.setView(view).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if (mode == 1 && (!(editname.getText().toString().startsWith("af") || editname.getText().toString().startsWith("be")))) {
                    Toast.makeText(ScriptManager.this, "hook的函数和类的那串字符请加个af或者be开头来表示是在hook前执行脚本或者在hook后执行脚本", Toast.LENGTH_SHORT).show();

                    edit(p, editname.getText().toString(), editcode.getText().toString(), pkg);
                    return;

                }
                try {
                    set(p, editname.getText().toString(), editcode.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).setNegativeButton("取消", null).setNeutralButton("删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                items.remove(p);
                try {
                    save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).show();


    }

    public void set(int p, String name, String code) throws JSONException {


        ScriptItem item = null;
        if (mode == 0) item = new ScriptItem(name, code);
        else
            item = new ScriptItem(name, code, pkg);
        if (p == -1)
            items.add(item);
        else
            items.set(p, item);
        save();

    }

    private void save() throws JSONException {
        JSONArray ja = new JSONArray();
        for (ScriptItem i : items) {

            JSONObject object = new JSONObject();
            object.put("name", i.getName());
            object.put("code", i.getCode());
            if (mode == 1) {

                object.put("packagename", i.getPackagename());
            }
            ja.put(object);
        }
        sharedPreferences.edit().putString(modeKey[mode], ja.toString()).commit();
        loadscript();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater in = getMenuInflater();
        in.inflate(R.menu.script, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            edit(-1, null, null, null);


        }
        return super.onOptionsItemSelected(item);
    }
}
