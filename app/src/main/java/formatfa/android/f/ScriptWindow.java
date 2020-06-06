package formatfa.android.f;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import formatfa.reflectmaster.Register;
import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InnerScriptListener;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class ScriptWindow extends Window {

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:
                    getLog().append("" + msg.obj);
                    getLog().append("\n");
                    appendsize += 1;
                    if (appendsize > 200) {
                        appendsize = 0;
                        getLog().setText("");
                    }
                    break;
                case 2:
                    Object obj = msg.obj;
                    FieldWindow w = new FieldWindow(getLpparam(), getParam(), getAct(), obj);
                    w.show(null, null);
                    break;
                case 3:
                    Toast.makeText(getLog().getContext(), "" + msg.obj, Toast.LENGTH_SHORT).show();

                    break;
                case 4:
                    ClipboardManager manager = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
                    manager.setPrimaryClip(ClipData.newPlainText("ReflectMaster", "" + msg.obj));
                    Toast.makeText(getLog().getContext(), "复制obj成功:" + msg.obj, Toast.LENGTH_SHORT).show();

                    break;

            }
        }
    };
    private LinearLayout layout;

    private WindowManager.LayoutParams layoutParams;
    private ActionWindow actionWindow;
    private Button execute;
    private EditText script;

    private TextView log;

    public TextView getLog() {
        return log;
    }

    public Context getAct() {
        return act;
    }


    private int appendsize = 0;

    public static class rf {
        public static Object thiz;
        public static Context act;
        public static Handler handler;

        public static Object getThis() {
            return thiz;
        }

        public static Context getAct() {
            return act;
        }

        public static void window(Object obj) {
            FieldWindow w = new FieldWindow(null, null, getAct(), obj);
            w.show(null, null);
        }


        public static void copy(Object string) {

            ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
            if (cm != null)
                cm.setPrimaryClip(ClipData.newPlainText("test", "" + string));

        }

        public static void print(Object obj) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = obj;
            handler.sendMessage(msg);
        }

        public static Object getTempVar(int position) {
            if (position < Registers.objects.size())
                return Registers.objects.get(position);
            else
                return null;
        }


    }

    public static class io {
        public static String sleep(int second) {
            try {
                Thread.sleep(second);
            } catch (InterruptedException e) {
                return e.toString();
            }

            return null;
        }

        public static void xplog(Object obj) {
            XposedBridge.log(obj + "");
        }

        public static byte[] readbytes(String path) {
            try {
                InputStream stream = new FileInputStream(path);
                byte[] buff = new byte[stream.available()];
                stream.read(buff);
                return buff;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String readstring(String path) {
            String result = null;
            byte[] buff = null;
            if ((buff = readbytes(path)) != null) {
                result = new String(buff);
            }
            return result;
        }

        public static int exists(Object path) {
            File f = null;
            if (path instanceof String) {
                f = new File((String) path);
            } else if (path instanceof File) {
                f = (File) path;
            } else return 0;
            return f.exists() ? 1 : 0;
        }

        public static boolean writefile(String path, Object obj) {
            OutputStream os = null;
            if (obj == null) return false;
            try {
                os = new FileOutputStream(path);

                if (obj instanceof String) {
                    os.write(((String) obj).getBytes());
                } else if (obj instanceof byte[]) {
                    os.write((byte[]) obj);
                } else {
                    os.write(("" + obj).getBytes());
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }


    }

    @Override
    public void show(final WindowManager manager, WindowManager.LayoutParams lpl) {

        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = layoutParams.TYPE_APPLICATION;
        layoutParams.flags = layoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.width = Registers.windowSize;
        layoutParams.height = Registers.windowSize;
        layout = new LinearLayout(act);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);
        actionWindow = new ActionWindow(act, manager, layoutParams, layout);

        LinearLayout buttonLayout = new LinearLayout(act);


        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        execute = new Button(act);
        execute.setText("执行");
        execute.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                log.setText("");
                if (Registers.newThread) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            execute();
                        }
                    }).start();
                } else
                    execute();
            }
        });
        Button copy = new Button(act);
        copy.setText("黏贴");
        copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager1 = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
                if (manager1.getPrimaryClip() != null)
                    script.setText(manager1.getPrimaryClip().getItemAt(0).getText());
            }
        });

        Button copylog = new Button(act);
        copylog.setText("黏贴");
        copylog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("test", log.getText().toString()));
                Toast.makeText(act, "复制Log成功", Toast.LENGTH_SHORT).show();
            }
        });
        buttonLayout.addView(execute);
        buttonLayout.addView(copy);
        script = new EditText(act);
        script.setTextColor(Color.BLACK);
        log = new TextView(act);

        log.setTextColor(Color.RED);
        script.setMaxHeight(300);
        layout.addView(actionWindow.getActionBar());
        layout.addView(buttonLayout);
        layout.addView(script);

        ScrollView scrollView = new ScrollView(act);
        scrollView.addView(log);
        layout.addView(scrollView);

        manager.addView(layout, layoutParams);
        log.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                {
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", log.getText().toString()));
                    Toast.makeText(act, "复制Log成功", Toast.LENGTH_SHORT).show();

                    return true;
                }
            }
        });
        if (code == null) {
			/*
			func main
			rf.print("start...")
			end
			 */
            script.setText("func main()\n" +
                    "\t\t\trf.print(\"start...\")\n" +
                    "\t\t\tend");
        } else {
            script.setText(code);
        }
    }


    private void exeGeGe() {

        FParser parser = new FParser();


        parser.setInnerScriptListener(new InnerScriptListener(object.getClass().getClassLoader()));

        parser.getVm().setValue("context", act);
        parser.getVm().setValue("this", object);
        parser.addScriptListener(new ReflectScriptListener(this));
        try {
            parser.parseCodes(script.getText().toString());
        } catch (Exception e) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = e.toString();

            e.printStackTrace();
        }

    }


    private void executeFk() {
        rf.act = act;
        rf.thiz = object;
        rf.handler = handler;

        fkconfig conf = new fkconfig();
        fake mfake = fk.newfake(conf);

        fk.openbaselib(mfake);

        fk.set_callback(mfake, new callback() {
            @Override
            public void on_error(fake f, String file, int lineno, String func, String str) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = String.format(Locale.CHINA, "line %d,func:%s,error:%s", lineno, func, str);
                handler.sendMessage(msg);
            }

            @Override
            public void on_print(fake f, String str) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });
        fk.regclass(mfake, rf.class);
        fk.regclass(mfake, io.class);

        String str = script.getText().toString();

        if (str.startsWith("!")) {
            File file = null;
            if (str.startsWith("!/")) {
                file = new File(str.substring(1));

            } else {
                file = new File("/sdcard/rf", str.substring(1));
            }
            fk.parse(mfake, file.getAbsolutePath());
        } else
            fk.parsestr(mfake, str);

        fk.run(mfake, "main");

    }

    private void execute() {

        executeFk();


    }

    private XC_LoadPackage.LoadPackageParam lpparam;

    private XC_MethodHook.MethodHookParam param;
    private Context act;

    public XC_LoadPackage.LoadPackageParam getLpparam() {
        return lpparam;
    }

    public XC_MethodHook.MethodHookParam getParam() {
        return param;
    }

    private Object object;
    String code;

    public ScriptWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, Object object, String code) {
        super(lpparam, param, act, object);
        this.lpparam = lpparam;
        this.param = param;
        this.object = object;
        this.act = act;
        this.code = code;
    }


}
