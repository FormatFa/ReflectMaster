package view.formatfa.ftexteditor.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

//import view.formatfa.ftexteditor.FileChooseListener;

public class FileChoose {
//
//    class FileAdapter extends BaseAdapter
//    {
//
//
//        public FileAdapter(Context context, File[] files) {
//            this.context = context;
//            this.files = files;
//        }
//
//        Context context;
//        File[] files;
//        @Override
//        public int getCount() {
//            return files.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return files[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//          TextView text = new TextView(context);
//          File f = files[position];
//          text.setTextSize(22);
//          if(f.isDirectory())
//          {
//              text.setTextColor(Color.BLUE);
//          }
//          text.setText(f.getName());
//            return text;
//        }
//    }
//    AlertDialog.Builder builder;
//
//    private Context context;
//
//   private LinearLayout layout;
//    private ListView list ;
//    File file;
//    File[] files;
//    private Dialog dialog;
//    SharedPreferences sharedPreferences ;
//    public FileChoose(Context context) {
//        this.context = context;
//
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String path = sharedPreferences.getString("path",null);
//        if(path!=null)
//        {
//            File f = new File(path);
//            if(!f.exists())file= Environment.getExternalStorageDirectory();
//            else file=f;
//        }
//        else
//        {
//            file= Environment.getExternalStorageDirectory();
//            if(new File(file,"apktoolhelper").exists())
//            {
//                file = new File(file,"apktoolhelper");
//            }
//        }
//
//
//        builder = new AlertDialog.Builder(context);
//        builder.setTitle("选择文件");
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(listener!=null)listener.onCancle();
//            }
//        });
//        initView();
//
//    }
//
//    private void upFile(File path)
//    {
//        if(!path.isDirectory())return;
//        files = path.listFiles();
//        if(files==null)return;
//        FileAdapter adapter = new FileAdapter(context,files);
//
//        list.setAdapter(adapter);
//        file=path;
//        sharedPreferences.edit().putString("path",path.getAbsolutePath()).commit();
//
//    }
//    private FileChooseListener listener;
//
//    public FileChooseListener getListener() {
//        return listener;
//    }
//
//    public void setListener(FileChooseListener listener) {
//        this.listener = listener;
//    }
//
//    private void initView() {
//        layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//
//        list = new ListView(context);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(files[position].isDirectory())
//                {
//                    upFile(files[position]);
//                }
//                else
//                {
//                    if(listener!=null)
//                    {
//                        listener.onChoose(files[position]);
//                    }
//                    dialog.dismiss();
//                }
//
//            }
//        });
//
//
//        Button up = new Button(context);
//        up.setText("上级目录");
//        up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                upFile(file.getParentFile());
//            }
//        });
//        layout.addView(up);
//        layout.addView(list);
//        builder.setView(layout);
//    }
//
//    public void show()
//    {
//        dialog=builder.show();
//    upFile(file);
//    }
//    public void show(File dir)
//    {
//        dialog=builder.show();
//        upFile(dir);
//    }
}
