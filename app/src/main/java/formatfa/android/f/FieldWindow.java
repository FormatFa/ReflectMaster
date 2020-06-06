package formatfa.android.f;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import formatfa.android.f.Adapter.FieldAdapter;
import formatfa.android.f.ClassHandle.Handle_Set;
import formatfa.android.f.ClassHandle.Handle_View;
import formatfa.android.f.Data.MyShared;
import formatfa.android.f.widget.WindowList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.ViewGroup;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Set;

import formatfa.android.f.ClassHandle.Handle_ArrayList;
import formatfa.android.f.ClassHandle.Handle_ViewGroup;

import android.graphics.drawable.Drawable;
import android.os.Build;

import formatfa.android.f.ClassHandle.Handle_ImageView;

import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import formatfa.android.f.ClassHandle.Handle_TextView;
import formatfa.reflectmaster.Register;
import formatfa.reflectmaster.ScriptItem;
import plugin.kpa.scriptparser.Script.FParser;
import plugin.kpa.scriptparser.Script.InnerScriptListener;
import plugin.kpa.scriptparser.Script.InvokeMethodResult;
import plugin.kpa.scriptparser.Script.ScriptListener;

public class FieldWindow extends Window implements OnItemClickListener, OnItemLongClickListener {

    @Override
    public boolean onItemLongClick(final AdapterView<?> p1, View p2, final int p3, long p4) {

        WindowManager am = (WindowManager) act.getSystemService(act.WINDOW_SERVICE);
        WindowList wlist = new WindowList(act, am);
        wlist.setTitle("变量操作");
        wlist.setItems(new String[]{"编辑", "收藏", "复制变量名称", "复制类名和变量名称", "复制变量名称和类型"/*,"持久化修改(构造函数后使用while(true)不断修改}"*/});
        wlist.setListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adap, View view, int posi, long l) {

                Field m = (Field) p1.getItemAtPosition(p3);
                if (posi == 0) {
                    if (m.isAccessible() == false) m.setAccessible(true);

                    if (Version.checkVersion(act, "editField") == true) {
                        EditFieldWindow ew = new EditFieldWindow(lpparam, param, act, object, fields[p3], EditFieldWindow.TYPE_EDIT);
                        ew.show(wm, lp);
                    }
//						tr
//						{
//					Object		re=m.invoke(object, new Class[]{});
//					Toast.makeText(act,"result;"+re,Toast.LENGTH_LONG).show();
//						}
//						catch (Exception e)
//						{Toast.makeText(act,e.toString(),Toast.LENGTH_LONG).show();}
//						


                } else if (posi == 1) {
                    favorite(p3, m);
                } else if (posi == 2) {
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", m.toGenericString()));
                    Toast.makeText(act, "复制成功:" + m.toGenericString(), Toast.LENGTH_SHORT).show();

                } else if (posi == 3) {
                    String s = m.getName() + " " + m.getType().getName();
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", s));
                    Toast.makeText(act, "复制成功:" + s, Toast.LENGTH_SHORT).show();

                } else {

                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", "'" + m.getDeclaringClass().getCanonicalName() + "'," + "'" + m.toGenericString() + "'"));
                    Toast.makeText(act, "复制成功:" + m.toGenericString(), Toast.LENGTH_SHORT).show();


                }

            }
        });
        wlist.show();


        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
        try {
            newWindow(lpparam, param, act, ((Field) p1.getItemAtPosition(p3)).get(object), wm);


        } catch (Exception e) {
            Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public static void newWindow(final XC_LoadPackage.LoadPackageParam lpparam, final XC_MethodHook.MethodHookParam param, final Context act, final Object object, final WindowManager wm) {

        try {
            if (object.getClass().getCanonicalName().endsWith(("[]")) && !Registers.isBaseArray(object.getClass().getCanonicalName())) {
                Toast.makeText(act, "这是一个数组来的", Toast.LENGTH_SHORT).show();


                final Object[] ob = (Object[]) object;

                int len = ob.length;
                if (len > 30) {
                    len = 30;
                    Toast.makeText(act, "长度过大，只显示前30个", Toast.LENGTH_SHORT).show();

                }
                String[] arr = new String[len + 1];
                arr[0] = "直接查看数组";
                for (int i = 0; i < ob.length; i += 1) {
                    arr[i] = i + " " + Registers.getObjectString(ob[i]);
                }
                WindowList list = new WindowList(act, wm);
                list.setItems(arr);
                list.setListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, int position, long p4) {
                        if (position == 0)
                            newFieldWindow(lpparam, param, act, object, wm);
                        else
                            newFieldWindow(lpparam, param, act, ob[position - 1], wm);
                    }
                });
                list.setTitle("选择其中一个对象，总共有:" + ob.length);
                list.show();


            } else
                newFieldWindow(lpparam, param, act, object, wm);

        } catch (IllegalArgumentException e) {
            Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT).show();


        }


    }

    public static void newFieldWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, Object object, WindowManager wm) {
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = lp.TYPE_APPLICATION;

        try {

            FieldWindow newf = new FieldWindow(lpparam, param, act, object);
            newf.show(wm, lp);
        } catch (IllegalArgumentException e) {
            Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT);
        }
    }

    private ListView list;

    private Field[] fields;

    private WindowManager wm;
    private WindowManager.LayoutParams lp;
    boolean isundeclear = false;
    private FieldAdapter adapter;
    //获取父类类名
    private Class superCls;

    @Override
    public void show(final WindowManager amanager, final WindowManager.LayoutParams lpp) {


        lp = new WindowManager.LayoutParams();
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
        if (object == null) {
            Toast.makeText(act, "is null....", Toast.LENGTH_SHORT).show();
        }
        wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);

        //lp.width=-2;
        //lp.height=-2;
        final LinearLayout layout = new LinearLayout(act);

        ActionWindow ac = new ActionWindow(act, wm, lp, layout, !Registers.isUseWindowSearch);

        if (!Registers.isUseWindowSearch)
            ac.setSearchCallback(new ActionSearchCallback() {
                @Override
                public void onTextChange(EditText edit, String text) {

                    if (TextUtils.isEmpty(text))
                        list.clearTextFilter();
                    else
                        list.setFilterText(text);
                }
            });
        layout.setBackgroundColor(Color.BLACK);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(ac.getActionBar());
        final TextView clsname = new TextView(act);
//		Toast.makeText(act,"获取类名:"+object.getClass())
        clsname.setText(object.getClass().getCanonicalName());
        clsname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (superCls == null) {
                    superCls = object.getClass().getSuperclass();
                } else
                    superCls = superCls.getClass();
                if (superCls == null) superCls = object.getClass();
                clsname.setText(superCls.getCanonicalName());
            }
        });
        clsname.setTextColor(Color.RED);
        clsname.setBackgroundColor(Color.WHITE);
        layout.addView(clsname);

        LinearLayout buttonLayout = new LinearLayout(act);


//		Button close = new Button(act);
//		close.setText("close");
//		close.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					manager.removeView(layout);
//				}
//			});
//		buttonLayout.addView(close);
//			
//		
        Button metbod = new Button(act);
        metbod.setText("函数");
        metbod.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                MethodWindow mw = new MethodWindow(lpparam, param, act, object);

                mw.show(wm, lp);
            }
        });
        buttonLayout.addView(metbod);


        metbod = new Button(act);
        metbod.setText("构造函数");
        metbod.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                ConstructorWindow mw = new ConstructorWindow(lpparam, param, act, object);

                mw.show(wm, lp);
            }
        });
        buttonLayout.addView(metbod);


        Button undeclared = new Button(act);
        undeclared.setText("undeclared");
        undeclared.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                if (isundeclear) {
                    fields = object.getClass().getDeclaredFields();
                    adapter.setFields(fields);
                    adapter.notifyDataSetChanged();
                    isundeclear = false;
                } else {
                    fields = object.getClass().getFields();
                    adapter.setFields(fields);
                    adapter.notifyDataSetChanged();
                    isundeclear = true;
                }


            }
        });
        buttonLayout.addView(undeclared);
        Button fa = new Button(act);
        fa.setText("收藏");
        fa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                showFavrite();
            }
        });
        buttonLayout.addView(fa);


        if (Registers.isUseWindowSearch) {
            fa = new Button(act);
            fa.setText("搜索");
            fa.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    EditWindow window = new EditWindow(lpparam, param, act, "搜索变量", "");
                    window.setListener(new EditWindow.EditWindowListener() {
                        @Override
                        public void onEdited(String str) {
                            list.setFilterText(str);
                        }
                    });
                    window.show(amanager, lp);
                }
            });
            buttonLayout.addView(fa);


        }


        Button add = new Button(act);
        add.setText("临时保存起来");
        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                Registers.add(act, object);
            }
        });
        buttonLayout.addView(add);


        if (object instanceof Drawable || object instanceof Bitmap) {

            Toast.makeText(act, "这可能是一个Bitmap图片", Toast.LENGTH_SHORT).show();
            Button viewImage = new Button(act);
            viewImage.setText("查看图片");
            viewImage.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    ImageWindow img = new ImageWindow(lpparam, param, act, object);
                    img.show(wm, lp);
                }
            });
            buttonLayout.addView(viewImage);


        }

        {
            //Toast.makeText(act,"这是一个字符,可以复制的:"+object,Toast.LENGTH_SHORT).show();
            Button operation = new Button(act);
            operation.setText("复制值");
            operation.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    ClipboardManager cm = (ClipboardManager) act.getSystemService(act.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("test", "" + object));
                    Toast.makeText(act, "复制成功:" + object, Toast.LENGTH_SHORT).show();
                }
            });
            buttonLayout.addView(operation);
        }


        if ("byte[]".equals(object.getClass().getCanonicalName())) {

            Toast.makeText(act, "这是一个byte数组,可以将其写入到sd卡", Toast.LENGTH_SHORT).show();
            Button operation = new Button(act);
            operation.setText("写入");
            operation.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    try {
                        OutputStream os = new FileOutputStream("/sdcard/Reflect.data");
                        os.write((byte[]) object);
                    } catch (Exception e) {
                        Toast.makeText(act, "错误", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(act, "写入成功:" + "/sdcard/Reflect.data", Toast.LENGTH_SHORT).show();
                }
            });
            buttonLayout.addView(operation);


        } else if (object instanceof ArrayList)
            new Handle_ArrayList(act, object).handle(buttonLayout);
        else if (object instanceof ViewGroup)
            new Handle_ViewGroup(act, object).handle(buttonLayout);
        else if (object instanceof ImageView)
            new Handle_ImageView(act, object).handle(buttonLayout);
        else if (object instanceof TextView)
            new Handle_TextView(act, object).handle(buttonLayout);
        else if (object instanceof Set) {
            new Handle_Set(act, object).handle(buttonLayout);
        }
        if (object instanceof View) {
            new Handle_View(act, object).handle(buttonLayout);
        }


        Button button = new Button(act);
        button.setText("脚本测试");
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                ScriptWindow sc = new ScriptWindow(lpparam, param, act, object, null);
                sc.show(wm, null);
            }
        });
        buttonLayout.addView(button);


        button = new Button(act);
        button.setText("我的脚本");
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                try {
                    loadScriptButton();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonLayout.addView(button);

        button = new Button(act);
        button.setText("查找类");
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                findClass();
            }
        });
        buttonLayout.addView(button);

        HorizontalScrollView ho = new HorizontalScrollView(act);
        ho.addView(buttonLayout);
        layout.addView(ho);


        list = new ListView(act);
        list.setTextFilterEnabled(true);
        list.setFastScrollEnabled(true);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        fields = object.getClass().getDeclaredFields();

        adapter = new FieldAdapter(act, fields, object);
        list.setAdapter(adapter);

        layout.addView(list);


        View line = new View(act);
        ViewGroup.LayoutParams he = new ViewGroup.LayoutParams(-1, 9);
        line.setBackgroundColor(Color.BLUE);
        line.setLayoutParams(he);

        wm.addView(layout, lp);
    }

    private void findClass() {

        EditWindow editWindow = new EditWindow(lpparam, param, act, "输入完整类名", "android.app.Activity");
        editWindow.setListener(new EditWindow.EditWindowListener() {
            @Override
            public void onEdited(String str) {

                try {
                    Class cls = object.getClass().getClassLoader().loadClass(str);
                    if (cls == null && lpparam != null) cls = lpparam.classLoader.loadClass(str);
                    try {
                        FieldWindow.newFieldWindow(lpparam, param, act, cls.newInstance(), wm);
                    } catch (Exception e) {
                        Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (ClassNotFoundException e) {
                    Toast.makeText(act, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        editWindow.show(wm, lp);
    }

    private void loadScriptButton() throws JSONException {
        XSharedPreferences sharedPreferences = new XSharedPreferences("formatfa.reflectmaster", "package");
        sharedPreferences.reload();


        List<String> names = new ArrayList<>();
        String data = sharedPreferences.getString("script", null);
        final ArrayList<ScriptItem> items = new ArrayList<>();
        if (data != null) {
            JSONArray ja = null;
            try {
                ja = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < ja.length(); i += 1) {
                JSONObject object = ja.getJSONObject(i);
                items.add(new ScriptItem(object.getString("name"), object.getString("code")));
                names.add(object.getString("name"));


            }
        }
        WindowList listView = new WindowList(act, wm);
        listView.setItems(names);
        listView.setTitle("本地脚本");
        listView.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                ScriptWindow sc = new ScriptWindow(lpparam, param, act, object, items.get(i).getCode());
                sc.show(wm, null);
            }
        });
        listView.show();


    }

    private void execute(String code) {


//
//		FParser parser = new FParser();
//		parser.setInnerScriptListener(new InnerScriptListener(lpparam.classLoader));
//
//		parser.getVm().setValue("context",act);
//		parser.getVm().setValue("this",object);
//		parser.addScriptListener(new ScriptListener() {
//			@Override
//			public InvokeMethodResult invokeMethod(FParser parser, String name, Object[] args) {
//
//
//
//				return null;
//			}
//
//			@Override
//			public void parserLog(String log) {
//
//			}
//		});
//		try {
//			parser.parseCodes(code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//

    }

    private SharedPreferences sp;
    private MyShared myShared;

    void favorite(final int p, final Field m) {

        final LinearLayout layout = new LinearLayout(act);
        layout.setOrientation(LinearLayout.VERTICAL);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = lp.TYPE_APPLICATION;
        lp.flags = lp.FLAG_NOT_TOUCH_MODAL;
        lp.width = 250;
        lp.height = -1;
        layout.setBackgroundColor(Color.DKGRAY);

        final EditText edit = new EditText(act);
        edit.setText(m.getName());
        edit.setHint("备注");

        Button save = new Button(act);
        save.setText("收藏");
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                myShared.putData(edit.getText().toString(), m.toGenericString());
                wm.removeView(layout);
                myShared.save();
                Toast.makeText(act, "似乎收藏成功了", Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(edit);
        layout.addView(save);

        wm.addView(layout, lp);

    }

    void showFavrite() {

        WindowList wlist = new WindowList(act, wm);
        final HashMap data = myShared.getData();
        final List<String> name = myShared.getKeys();

        wlist.setItems(name);
        wlist.setTitle("我的收藏");
        wlist.setListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                int p = -1;
                String me = (String) data.get(name.get(p3));

//					Toast.makeText (act,"长度:"+p,Toast.LENGTH_SHORT).show();

                for (int i = 0; i < fields.length; i += 1) {
                    if (fields[i].toGenericString().equals(me)) {
                        p = i;
                        break;
                    }
                }
                if (p == -1 || p > fields.length - 1) {
                    Toast.makeText(act, "长度错误," + p, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(act, "长度:" + p, Toast.LENGTH_SHORT).show();
                list.setSelection(p);
            }
        });
        wlist.show();
    }


    void fieldDialog() {

        LinearLayout layout = new LinearLayout(act);
        layout.setOrientation(LinearLayout.VERTICAL);

        CheckBox cnull = new CheckBox(act);
        cnull.setText("not include null");

        layout.addView(cnull);
        CheckBox cundeclare = new CheckBox(act);
        cnull.setText("undeclare");

        layout.addView(cundeclare);


    }

    Field[] filter(boolean isnotull, boolean isundeclare) {
        List<Field> result = new ArrayList<Field>();


        for (Field field : fields) {

            if (isnotull) {
                try {
                    if (field.get(object) == null) continue;
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                }
            }
        }
        return result.toArray(new Field[0]);
    }

    public FieldWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, Object object) {
        super(lpparam, param, act, object);

        sp = act.getSharedPreferences(object.getClass().getCanonicalName(), act.MODE_PRIVATE);
        myShared = new MyShared(sp, "fieldfaviroylte2");
    }


}
