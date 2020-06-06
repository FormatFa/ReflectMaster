package formatfa.reflectmaster;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import formatfa.android.f.FWindow;
import formatfa.android.f.Registers;
import formatfa.android.f.Version;
import formatfa.android.f.reflectmaster.Adapter.ApkAdapter;
import formatfa.android.f.reflectmaster.CoreInstall;
import formatfa.android.f.widget.ReflectView;
import formatfa.android.f.widget.ReflectView2;
import formatfa.android.f.widget.ViewLineClickListener;
import formatfa.android.f.widget.ViewLineView;
import formatfa.android.f.widget.WindowDialog;
import formatfa.android.f.widget.WindowList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipFile;

import org.json.JSONArray;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> p1, View v, final int position, long id) {

        final PackageInfo info = (PackageInfo) p1.getItemAtPosition(position);

        final boolean isSelect = packs.contains(info.packageName);

        View view = getLayoutInflater().inflate(R.layout.aim, null);

        final WindowDialog wd = new WindowDialog(this);

        final String pname = info.packageName;
        TextView select = view.findViewById(R.id.aim_ok);
        if (isSelect) {
            select.setText("取消选择这个软件");
        }
        select.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                wd.dismiss();
                if (!isSelect)
                    add(info, false);
                else {

                    for (int i = 0; i < packs.size(); i += 1) {
                        if (packs.get(i).equals(info.packageName)) {
                            packs.remove(i);
                            break;
                        }
                    }
                    save();
                    adapter.setAimPackage(packs);
                    adapter.notifyDataSetChanged();
                    tip("已取消" + packs.size());

                }
            }
        });

        view.findViewById(R.id.open).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = manager.getLaunchIntentForPackage(info.packageName);
                if (intent == null) {
                    Toast.makeText(MainActivity.this, "打开失败", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        view.findViewById(R.id.info).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                Intent intent = new Intent().setAction("android.settings.APPLICATION_DETAILS_SETTINGS"
                ).setData(Uri.fromParts("package",
                        info.packageName, null));
                startActivity(intent);

            }
        });
        view.findViewById(R.id.diy).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                Intent i = new Intent(MainActivity.this, ScriptManager.class);
                i.putExtra("mode", 1);
                i.putExtra("packagename", info.packageName);

                startActivity(i);

            }
        });

        wd.setTitle("确认配置");
        wd.setView(view);
        wd.show(800, 800);


    }


    public static final String s = "反射大师1.1\nauthor:FormatFa", h = "Ubb6o57-M2ziSR9K3Jy7c7rohXGWVyh0";

    public final static String KEY = "packs", cls = "dir";

    //private File packagesPath;


    void add(PackageInfo info, boolean isservice) {

//		ServiceInfo[] service = info.services;
//		JSONArray ja = new JSONArray();
//		if(isservice)
//		if(service!=null)
//			for(ServiceInfo i:service)
//			{if(i.name.startsWith("."))
//					ja.put(info.packageName+i.name);else
//					ja.put(i.name);}
//		String s=ja.toString();
        sp.edit().putString(KEY, info.packageName).commit();
        adapter.setAimPackage(packs);
//		if(isservice)
//		sp.edit().putString(info.packageName+"_service",s).commit();
//		else
//			sp.edit().putString(info.packageName+"_service","").commit();
//
        packs.add(info.packageName);
        save();

        new AlertDialog.Builder(this).setMessage("设置保存成功，请重新打开目标软件，hook包名" + info.packageName).setPositiveButton("确定", null).show();
        adapter.notifyDataSetChanged();

    }


    //	private void checkSd()
//	{
//		File file = Environment.getExternalStorageDirectory();
//		if(!file.canWrite())
//		{
//
//			tip(file.getAbsolutePath()+"看起来不能写入我。");return;
//		}
//		workDir = new File(file,CoreInstall. WORKNAME);
//		if(!workDir.exists())
//		{
//			if(!workDir.mkdirs()){tip("建立文件夹失败");return;}
//		}
//
//
//
//
//	}
    private void tip(String s) {

        new AlertDialog.Builder(this).setTitle("66").setMessage(s).setPositiveButton(android.R.string.ok, null).show();
    }

    private File workDir;
    private PackageManager manager;

    private SharedPreferences sp;


    private ListView list;
    private ApkAdapter adapter;

    private List<String> packs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater in = getMenuInflater();
        in.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit) {

            finish();
        }
        if (item.getItemId() == R.id.about) {

            about();

        } else if (item.getItemId() == R.id.register) {
            Intent i = new Intent();
            i.setClass(this, Register.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.group) {

            joinQQGroup("jKcW_juMAQbVCtIXsZwTf7DsFBSbri9b");


        } else if (item.getItemId() == R.id.core) {

            Intent i = new Intent(MainActivity.this, CoreInstall.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.script) {

            Intent i = new Intent(MainActivity.this, ScriptManager.class);
            i.putExtra("mode", 0);
            startActivity(i);

        } else if (item.getItemId() == R.id.setting) {

            View view = getLayoutInflater().inflate(R.layout.setting, null);

            view.setBackgroundColor(Color.BLACK);
            WindowDialog wd = new WindowDialog(this);
            final EditText se = (EditText) view.findViewById(R.id.setting_windowsize);

            se.setTextColor(Color.WHITE);
            se.setText(String.valueOf(sp.getInt("width", 700)));
            view.findViewById(R.id.preview).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    int i = Integer.parseInt(se.getText().toString());

                    WindowDialog wd = new WindowDialog(MainActivity.this);
                    ReflectView2 re = new ReflectView2(MainActivity.this, sp.getBoolean("rotate", true));

                    wd.setTitle("预览");
                    wd.setView(re);
                    wd.show(i, i);


                }
            });

            view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    int i = Integer.parseInt(se.getText().toString());

                    sp.edit().putInt("width", i).commit();
                    Toast.makeText(MainActivity.this, "ojbk", Toast.LENGTH_SHORT).show();


                }
            });

            CheckBox box = view.findViewById(R.id.setting_windowsearch);
            box.setChecked(sp.getBoolean("windowsearch", false));
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sp.edit().putBoolean("windowsearch", b).apply();
                    Registers.isUseWindowSearch = b;

                }
            });
            CheckBox showfloat = view.findViewById(R.id.setting_float);
            showfloat.setChecked(sp.getBoolean("float", true));
            showfloat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sp.edit().putBoolean("float", b).apply();


                }
            });


            CheckBox rotate = view.findViewById(R.id.setting_rotate);
            rotate.setChecked(sp.getBoolean("rotate", true));
            rotate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sp.edit().putBoolean("rotate", b).apply();


                }
            });

            CheckBox sysapp = view.findViewById(R.id.setting_sysapp);
            sysapp.setChecked(sp.getBoolean("sysapp", false));
            sysapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sp.edit().putBoolean("sysapp", b).apply();


                }
            });
            CheckBox newThread = view.findViewById(R.id.setting_thread);
            newThread.setChecked(sp.getBoolean("newthread", false));
            newThread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Registers.newThread = b;
                    sp.edit().putBoolean("newthread", b).apply();


                }
            });
            wd.setTitle("确认配置");
            wd.setView(view);
            wd.show(800, 800);


        } else if (item.getItemId() == R.id.update) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.formatfa.top"));
            startActivity(intent);

        }

        return true;
    }


    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {

            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    private void save() {
        StringBuilder sb = new StringBuilder();
        for (String s : packs) {
            sb.append(s);
            sb.append(",");
        }
        sp.edit().putString(KEY, sb.toString()).commit();


    }

    private void refresh() {
        packs = new ArrayList();
        String s = sp.getString(KEY, null);
        if (s == null) return;

        String[] all = s.split(",");
        for (String item : all) packs.add(item);


    }

    void showActivity() {
        Context act = this;
        PackageManager pm = act.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(act.getPackageName(), pm.GET_ACTIVITIES);

            ActivityInfo[] acs = info.activities;
            List<String> name = new ArrayList<String>();
            List<String> aname = new ArrayList<String>();

            for (ActivityInfo a : acs) {
                int id = a.labelRes;
                String actname;
                if (a.name.startsWith(".")) {
                    actname = a.packageName + a.name;
                } else {

                    actname = a.name;
                }
                aname.add(actname);
                if (id != 0) {
                    String labei = act.getResources().getString(id);
                    name.add(labei);
                } else {
                    name.add(actname);
                }

            }
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            WindowList wlist = new WindowList(act, wm);
            wlist.setItems(name);
            wlist.setListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                }
            });
            wlist.show();

        } catch (PackageManager.NameNotFoundException e) {
        }


    }

    WindowManager windowManager;

    private void getAllViews(Activity act) {
        List<View> list = getAllChildViews(act.getWindow().getDecorView());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = lp.TYPE_APPLICATION;
        lp.flags = lp.FLAG_NOT_FOCUSABLE;
        lp.width = -1;
        lp.height = -1;

        lp.format = PixelFormat.RGBA_8888;

        final ViewLineView vlv = new ViewLineView(this, list);
        vlv.setListener(new ViewLineClickListener() {

            @Override
            public void onClick(ViewLineView obj, List<View> views) {
                windowManager.removeView(vlv);
            }


            //	@Override
            public void onClick(ViewLineView obj, View view) {


            }

            @Override
            public void onLongClick(ViewLineView obj) {

            }
        });
        //vlv.setBackgroundColor(Color.TRANSPARENT);
        windowManager.addView(vlv, lp);
    }

    String viewMsg(View v) {
        return v.getClass().getCanonicalName() + "" + "X:" + v.getWidth() + " Y:" + v.getY();
    }

    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);

                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }


    boolean isFirst = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Version.isTestMode = true;
        Registers.nowAct = this;
        FWindow window = new FWindow(this, null);

        if (this.getActionBar() != null) {
            this.getActionBar().setSubtitle("1.9");
        }

        manager = this.getPackageManager();
        checkSupport();

        setContentView(R.layout.apklist);
        list = (ListView) findViewById(R.id.list);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(this);
        checkPer();


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        if (getActionBar() != null)
            getActionBar().setSubtitle("选择HOOK目标");


        try {
            sp = getSharedPreferences("package", MODE_WORLD_READABLE);
        } catch (SecurityException e) {
            sp = getSharedPreferences("package", MODE_PRIVATE);
            e.printStackTrace();
        }


        new loadAsync().execute(0);
        if (sp.getBoolean("first1.9", true)) {
            showHelp();

        }
        Registers.windowSize = sp.getInt("width", 700);

        Registers.rotate = sp.getBoolean("rotate", true);
        EditText sear = findViewById(R.id.seartext);
        list.requestFocus();
        sear.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    list.clearTextFilter();
                } else {
                    list.setFilterText(s.toString());
                }
            }
        });
    }

    String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    private void checkPer() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0)
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "获取读取手机信息的权限失败，，，，，", Toast.LENGTH_LONG).show();
                    return;
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showHelp() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("协议");
        ab.setMessage(R.string.about);
        ab.setPositiveButton("同意", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface p1, int p2) {
                sp.edit().putBoolean("first1.9", false).commit();
            }
        });
        ab.setNegativeButton("不同意", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface p1, int p2) {
                finish();
            }
        });
        ab.show();
    }

    private void checkSupport() {

        //showActivity();
        boolean support = true;
        try {
            manager.getPackageInfo("de.robv.android.xposed.installer", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            support = false;
        }

        if (!support)
            new AlertDialog.Builder(this).setMessage("这台设备好像没有安装Xposed框架").setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface p1, int p2) {
                    //finish();
                }
            }).show();

        else {

        }

    }


    class loadAsync extends AsyncTask {

        ProgressBar pd;

        private ArrayList<PackageInfo> apps;

        @Override
        protected void onPreExecute() {
            pd = (ProgressBar) MainActivity.this.findViewById(R.id.progress);


            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object[] p1) {

            apps = new ArrayList<PackageInfo>();

            for (PackageInfo info : manager.getInstalledPackages(PackageManager.GET_SERVICES | PackageManager.GET_PERMISSIONS | PackageManager.GET_META_DATA)) {
                //	ActivityInfo[] acs =   info.activities;

                boolean sysapp = sp.getBoolean("sysapp", false);
                if (sysapp == false && ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
                    continue;
                }


                if (info.packageName.equals(MainActivity.this.getPackageName())) {

                    String s = info.applicationInfo.loadLabel(manager).toString();


                    String k1 = new String(new char[]{

                            23478,
                            22659,
                            36139,
                            23506});


                    String k = new String(new char[]{21453,
                            23556,
                            22823,
                            24072});
                    if (!s.equals(k) || s.equals(k1)) {

                        s.substring(888);
                    }


                }
                apps.add(info);


            }
            refresh();
            Collections.sort(apps, new ApkListCompare(packs));

            adapter = new ApkAdapter(MainActivity.this, apps);

            //adapter.setAimPackage(Utils.sdtoString(packagesPath.getAbsolutePath()));

            adapter.setAimPackage(packs);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            pd.setVisibility(View.INVISIBLE);

            list.setAdapter(adapter);
            super.onPostExecute(result);
        }


    }

    void about() {
        WindowDialog wd = new WindowDialog(this);


        WebView web = new WebView(this);
        web.loadUrl("file:///android_asset/about.html");
        web.getSettings().setSupportZoom(true);
        wd.setView(web);


        //AlertDialog.Builder a=new AlertDialog.Builder(this);
        //a.setMessage("yy");a.show();


        wd.show(500, 600);


    }
}
