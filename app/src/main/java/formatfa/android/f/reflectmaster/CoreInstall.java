package formatfa.android.f.reflectmaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import formatfa.android.f.reflectmaster.Adapter.CoreAdapter;
import formatfa.android.f.reflectmaster.Utils.Utils;
import formatfa.android.f.reflectmaster.Widget.EditTextDialog;
import formatfa.android.f.reflectmaster.Widget.OnEditTextListener;
import formatfa.reflectmaster.R;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager.NameNotFoundException;

public class CoreInstall extends Activity implements OnItemClickListener, OnClickListener {

    @Override
    public void onClick(View p1) {
        if (p1.getId() == R.id.xposed) {
            Utils.installAssetsApk(this, "XposedShell.apk");
        }
    }


    @Override
    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
        CoreAdapter.CoreItem item = (CoreAdapter.CoreItem) p1.getItemAtPosition(p3);

        new downloadTask(item.getName()).execute(item.getName());


    }


    private ListView list;
    private TextView xposed;
    private TextView core;
    private PackageManager packageManager;

    public static String WORKNAME = "ReflectMaster";
    private String url = "http://api.formatfa.top/ReflectMaster/core/getCore.php";

    private SharedPreferences sp;
    private File workDir;
    private File xposedPath;
    private File corePath;


    boolean isTest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.core);
        list = (ListView) findViewById(R.id.corelist);
        xposed = (TextView) findViewById(R.id.xposed);
        core = (TextView) findViewById(R.id.core);
        list.setOnItemClickListener(this);
        xposed.setOnClickListener(this);
        sp = getSharedPreferences("package", MODE_WORLD_READABLE);


        if (isTest) {
            PackageManager pm = getPackageManager();


            try {
                PackageInfo in = pm.getPackageInfo("formatfa.xposed.f", PackageManager.GET_ACTIVITIES);

                sp.edit().putString("corepath", in.applicationInfo.publicSourceDir).commit();
                tip("更新bug目录:" + in.applicationInfo.publicSourceDir);
            } catch (NameNotFoundException e) {
                tip(e.toString());
            }


        }


        checkStatu();
        checkSd();
        new loadList().execute(0);
    }

    private void checkSd() {
        File file = Environment.getExternalStorageDirectory();
        if (!file.canWrite()) {

            tip(file.getAbsolutePath() + "看起来不能写入我。");
            return;
        }
        workDir = new File(file, WORKNAME);
        if (!workDir.exists()) {
            if (!workDir.mkdirs()) {
                tip("建立文件夹失败");
                return;
            }
        }
        xposedPath = new File(workDir, "modulepath");
        if (sp.getString("core", null) != null)
            corePath = new File(workDir, sp.getString("core", null) + ".zip");

    }

    private void checkStatu() {
        //=PreferenceManager.getDefaultSharedPreferences(this);
        packageManager = getPackageManager();

        try {
            PackageInfo info = packageManager.getPackageInfo("formatfa.shell.f.reflectmaster", 0);
            xposed.setText("模块已安装");
        } catch (NameNotFoundException e) {
            xposed.setTextColor(Color.RED);
            xposed.setText("模块未安装,点击安装");
        }

        core.setText("当前核心:" + sp.getString("core", "未指定"));


    }


    class loadList extends AsyncTask {

        private ProgressBar pd;
        String data;

        @Override
        protected void onPreExecute() {
            Toast.makeText(CoreInstall.this, "start", Toast.LENGTH_LONG);
            pd = (ProgressBar) findViewById(R.id.progress);
            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object[] p1) {
            data = Utils.download(url);
            if (data == null) return "load data err";


            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            pd.setVisibility(View.INVISIBLE);
            Toast.makeText(CoreInstall.this, "test:" + data, Toast.LENGTH_LONG);

            CoreAdapter core = null;
            try {
                core = new CoreAdapter(CoreInstall.this, data);
            } catch (Exception e) {
                CoreInstall.this.core.setText(e.toString());
            }
            if (core != null) list.setAdapter(core);
            else
                Toast.makeText(CoreInstall.this, "err", Toast.LENGTH_SHORT);

            //xposed.setText(""+data);
            super.onPostExecute(result);
        }


    }

    class downloadTask extends AsyncTask {

        private String name;

        private String kami;

        public downloadTask(String name, String kami) {
            this.name = name;
            this.kami = kami;
        }

        public downloadTask(String name) {
            this.name = name;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object[] p1) {
            corePath = new File(workDir, name + ".zip");


            //check file
            if (corePath.exists()) return "exists";

            String url = Utils.download("http://app.formatfa.top/ReflectMaster/Core/downloadcore.php?name=" + name + "&kami=" + kami);
            if (url == null) return "获取在线配置失败";

            if (url.startsWith("http://") == false) return url;


            String result = null;
            //"http://app.formatfa.top/ReflectMaster/Files/"+dexName
            result = Utils.donwloadFile(url, corePath.getAbsolutePath());

            if (result != null)
                return "下载核心失败:" + result;
            corePath.setReadable(true);
            sp.edit().putString("kami" + name, "kami:" + (corePath.lastModified() - 10)).commit();

//			dexName=name+".apk";
//			result=Utils.donwloadFile("http://app.formatfa.top/ReflectMaster/Files/"+name,new File(workDir,name).getAbsolutePath());
//
//			if(  result!=null)
//				return "下载模块失败:"+result;
//			
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            String re = (String) result;

            if (re != null) {
                if (re.startsWith("needkami")) {
                    inputKami(name);
                } else if (re.equals("exists")) {

                    installCore(name);
                } else {
                    tip(re);
                }


            } else {
                installCore(name);
                tip("下载完成.");
            }
            super.onPostExecute(result);
        }


    }

    private void installCore(String key) {

        if (isTest) {
            PackageManager pm = getPackageManager();


            try {
                PackageInfo in = pm.getPackageInfo("formatfa.xposed.f", PackageManager.GET_ACTIVITIES);

                sp.edit().putString("corepath", in.applicationInfo.publicSourceDir).commit();
                tip("更新bug目录:" + in.applicationInfo.publicSourceDir);
            } catch (NameNotFoundException e) {
                tip(e.toString());
            }


        } else {
            sp.edit().putString("core", key).commit();
            sp.edit().putString("corepath", new File(workDir, key + ".zip").getAbsolutePath()).commit();

            tip("已安装核心:" + key);

        }

        checkStatu();

    }

    private void inputKami(final String name) {
        EditTextDialog ed = new EditTextDialog(this);
        ed.setMessage("请输入卡密获取下载连接");
        ed.setTitle("付费核心下载");
        ed.setListener(new OnEditTextListener() {

            @Override
            public void onEdited(String str) {
                new downloadTask(name, str).execute(0);
            }
        });
        ed.getDialog().setNeutralButton("购买卡密(QQ)", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface p1, int p2) {

                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2049896440";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (ActivityNotFoundException e) {
                    tip("似乎还没有安装手机QQ...");
                }
            }
        });

        ed.show();


    }

    private void downApk() {


    }

    private void tip(String s) {

        new AlertDialog.Builder(this).setTitle("66").setMessage(s).setPositiveButton(android.R.string.ok, null).show();
    }
}
