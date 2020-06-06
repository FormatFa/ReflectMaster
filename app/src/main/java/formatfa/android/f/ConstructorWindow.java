package formatfa.android.f;

/**
 * Created by formatfa on 18-5-12.
 */

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import formatfa.android.f.Adapter.ConstructorAdapter;
import formatfa.android.f.Adapter.MethodAdapter;
import formatfa.android.f.Data.MyShared;
import formatfa.android.f.widget.WindowList;;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import formatfa.android.f.Adapter.MethodAdapter;
import formatfa.android.f.Data.MyShared;
import formatfa.android.f.widget.WindowList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.view.View.OnLongClickListener;

public class ConstructorWindow extends Window implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(final AdapterView<?> p1, View p2, final int p3, long p4) {
        WindowManager am = (WindowManager) act.getSystemService(act.WINDOW_SERVICE);
        WindowList wlist = new WindowList(act, am);
        wlist.setTitle("函数操作");
        wlist.setItems(new String[]{"运行", "收藏", "复制函数名称", "复制类名和函数名", "复制类和函数名(hook脚本使用)"});
        wlist.setListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adap, View view, int posi, long l) {

                Constructor m = (Constructor) p1.getItemAtPosition(p3);
                if (posi == 0) {
                    if (m.isAccessible() == false) m.setAccessible(true);

                    if (Version.checkVersion(act, "invokeMethod") == true) {
                        runMethod(m);
                    }
//						try
//						{
//					Object		re=m.invoke(object, new Class[]{});
//					Toast.makeText(act,"result;"+re,Toast.LENGTH_LONG).show();
//						}
//						catch (Exception e)
//						{Toast.makeText(act,e.toString(),Toast.LENGTH_LONG).show();}
//						


                } else if (posi == 1) {

                } else if (posi == 2) {
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", m.toGenericString()));
                    Toast.makeText(act, "复制成功:" + m.toGenericString(), Toast.LENGTH_SHORT).show();

                } else if (posi == 3) {
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", "'" + m.getDeclaringClass().getCanonicalName() + "'," + "'" + m.toGenericString() + "'"));
                    Toast.makeText(act, "复制成功:" + m.toGenericString(), Toast.LENGTH_SHORT).show();

                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getDeclaringClass().getCanonicalName());
                    sb.append(" ");
                    sb.append(m.getName());
                    ;
                    for (Class clz : m.getParameterTypes()) {
                        sb.append(" ");
                        sb.append(clz.getCanonicalName());

                    }
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", sb.toString()));
                    Toast.makeText(act, "复制成功:" + m.toGenericString(), Toast.LENGTH_SHORT).show();


                }
            }
        });
        wlist.show();

    }


    private Object[] values;
    private EditText[] valuesEdit = null;
    private int p = 0;

    private SharedPreferences shared;
    private MyShared myShared;

    void runMethod(final Constructor m) {

        values = new Object[]{};
        m.getParameterTypes();
        WindowManager am = (WindowManager) act.getSystemService(act.WINDOW_SERVICE);

        final LinearLayout l = new LinearLayout(act);
        l.setBackgroundColor(Color.BLACK);
        lp.width = Registers.windowSize;
        lp.height = Registers.windowSize;
        ActionWindow ac = new ActionWindow(act, wm, lp, l);


        l.setOrientation(LinearLayout.VERTICAL);
//		LinearLayout bl = new LinearLayout(act);
//		bl.setOrientation(LinearLayout.HORIZONTAL);
//		Button close = new Button(act);
//		close.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					wm.removeView(l);
//				}
//			});
//		close.setText("close");
//		bl.addView(close);
//		
        l.addView(ac.getActionBar());

        if (m.getParameterTypes().length > 0) {
            values = new Object[m.getParameterTypes().length];

            valuesEdit = new EditText[m.getParameterTypes().length];
            p = 0;
            for (Class type : m.getParameterTypes()) {
                //LinearLayout item = new LinearLayout(act);
                //item.setOrientation(LinearLayout.HORIZONTAL);

                valuesEdit[p] = new EditText(act);
                valuesEdit[p].setTextColor(Color.RED);
                valuesEdit[p].setHint(type.getCanonicalName());

//			Button button = new Button(act);
//			button.setText("插入临时变量");
                valuesEdit[p].setOnLongClickListener(new listener(valuesEdit[p]));
                //item.addView(valuesEdit[p]);
                //item.addView(button);
                l.addView(valuesEdit[p]);
                p += 1;

            }
        }
        Button run = new Button(act);
        run.setText("运行");
        run.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {

                if (m.getParameterTypes().length > 0)
                    for (int i = 0; i < m.getParameterTypes().length; i += 1) {
                        Class c = m.getParameterTypes()[i];
                        String s = valuesEdit[i].getText().toString();
                        if (s.startsWith("$F")) {
                            values[i] = Registers.objects.get(Integer.parseInt(s.substring(2)));
                        } else {
                            switch (c.getCanonicalName()) {
                                case "int":
                                    int va = Integer.parseInt(s);
                                    values[i] = va;
                                    break;
                                case "boolean":
                                    if (s.equals("true")) values[i] = true;
                                    else values[i] = false;
                                    break;
                                case "long":
                                    long lo = Long.parseLong(s);
                                    values[i] = lo;
                                    break;
                                default:
                                    values[i] = s;
                            }

                        }


                    }

                Object result = null;
                try {
                    result = m.newInstance(values);
                } catch (Exception e) {
                    Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT).show();
                }

                wm.removeView(l);
                LogUtils.loge("构造:" + m.getName() + " result:" + result);

                if (result == null) {
                    Toast.makeText(act, "result is null", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(act, result.getClass().getCanonicalName() + " ,正在打开结果,result is" + result, Toast.LENGTH_SHORT).show();
                    //FieldWindow fw = new FieldWindow(lpparam,param, act,result);
                    //fw.show(wm,lp);

                    FieldWindow.newWindow(lpparam, param, act, result, wm);


                }
            }
        });
        l.addView(run);
        lp.width = 400;
        lp.height = 400;
        lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        wm.addView(l, lp);
    }

    class listener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View p1) {
            runMethod_showVar(edit);
            return true;
        }


        EditText edit;

        public listener(EditText edit) {
            this.edit = edit;
        }

        public void onClick(View p1) {

        }


    }

    private void runMethod_showVar(final EditText edit) {

        WindowList elist = new WindowList(act, wm);
        elist.setTitle("selece var");
        List<String> name = new ArrayList<String>();
        for (Object o : Registers.objects) {

            name.add(o.getClass().getCanonicalName());

        }
        elist.setItems(name);
        elist.setListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                edit.setText("$F" + p3);

            }
        });
        elist.show();

    }


    private Constructor[] methods;
    private WindowManager wm;
    private WindowManager.LayoutParams lp;
    private ActionWindow acw;

    private ListView list;
    private ConstructorAdapter adapter;
    private boolean isundeclear = false;

    @Override
    public void show(final WindowManager manager, WindowManager.LayoutParams lpq) {
        wm = manager;
        this.lp = new WindowManager.LayoutParams();
        lp.type = lp.TYPE_APPLICATION;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        else {
            if (Registers.isUseWindowSearch)
                lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            else
                lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        }
        lp.width = Registers.windowSize;
        lp.height = Registers.windowSize;

        final LinearLayout root = new LinearLayout(act);

        TextView title = new TextView(act);
        title.setText("函数");
        title.setTextColor(Color.RED);
        root.addView(title);


        acw = new ActionWindow(act, manager, lp, root, !Registers.isUseWindowSearch);

        if (!Registers.isUseWindowSearch)
            acw.setSearchCallback(new ActionSearchCallback() {
                @Override
                public void onTextChange(EditText edit, String text) {
                    if (TextUtils.isEmpty(text))
                        list.clearTextFilter();
                    else
                        list.setFilterText(text);
                }
            });

        root.addView(acw.getActionBar());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        list = new ListView(act);
        list.setTextFilterEnabled(true);
        list.setFastScrollEnabled(true);
        LinearLayout buttonLayout = new LinearLayout(act);


//		title.setBackgroundColor(Color.WHITE);
//		Button close = new Button(act);
//		close.setText("关闭");
//		close.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					manager.removeView(root);
//				}
//			});
//		buttonLayout.addView(close);
//		
        Button undeclare = new Button(act);
        undeclare.setText("undeclare");
        undeclare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {


                if (isundeclear) {
                    methods = object.getClass().getDeclaredConstructors();
                    adapter.setMethods(methods);
                    adapter.notifyDataSetChanged();
                    isundeclear = false;
                } else {
                    methods = object.getClass().getConstructors();
                    adapter.setMethods(methods);
                    adapter.notifyDataSetChanged();
                    isundeclear = true;
                }


            }
        });
        buttonLayout.addView(undeclare);


        Button fa = null;

        if (Registers.isUseWindowSearch) {
            fa = new Button(act);
            fa.setText("搜索");
            fa.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View p1) {
                    EditWindow window = new EditWindow(lpparam, param, act, "搜索函数", "");
                    window.setListener(new EditWindow.EditWindowListener() {
                        @Override
                        public void onEdited(String str) {
                            list.setFilterText(str);
                        }
                    });
                    window.show(manager, lp);
                }
            });
            buttonLayout.addView(fa);


        }
        HorizontalScrollView ho = new HorizontalScrollView(act);
        ho.addView(buttonLayout);
        root.addView(ho);


        methods = object.getClass().getDeclaredConstructors();
        adapter = new ConstructorAdapter(act, methods);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        root.addView(list);

        //lp.width=-1;
        //lp.height=-1;


        root.addView(WindowUtils.getLineView(act));
        manager.addView(root, lp);


        shared = act.getSharedPreferences(object.getClass().getCanonicalName(), act.MODE_PRIVATE);
        myShared = new MyShared(shared, "favorite2");
    }

    public ConstructorWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, Object object) {

        super(lpparam, param, act, object);
    }

}
