package formatfa.android.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.PixelFormat;

public class ImageWindow extends Window {
    Bitmap bmp = null;

    @Override
    public void show(final WindowManager manager, WindowManager.LayoutParams lp) {
        if (object.getClass().getCanonicalName().equals(""))
            return;

        if (object instanceof Bitmap)
            bmp = (Bitmap) object;
        else if (object instanceof Drawable) {
            bmp = drawableToBitmap((Drawable) object);
        }

        final LinearLayout root = new LinearLayout(act);
        root.setOrientation(LinearLayout.VERTICAL);
        LinearLayout buttonLayout = new LinearLayout(act);

        ActionWindow ac = new ActionWindow(act, WindowUtils.getWm(act), WindowUtils.getLp(), root);


        Button save = new Button(act);
        save.setText("保存");
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                String path = String.format("/sdcard/%s%d.png", object.getClass().getName(), System.currentTimeMillis());
                String s = "保存成功:" + path;
                try {
                    bmp.compress(Bitmap.CompressFormat.PNG, 10, new FileOutputStream(path));
                } catch (FileNotFoundException e) {
                    s = e.toString();
                }
                Toast.makeText(act, s, Toast.LENGTH_SHORT).show();
            }
        });
        buttonLayout.addView(save);

//		Button cancel = new Button(act);
//		cancel.setText("cancel");
//		cancel.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					manager.removeView(root);
//				}
//			});
//		buttonLayout.addView(cancel);
//		

        root.addView(ac.getActionBar());
        root.addView(buttonLayout);


        ImageView image = new ImageView(act);

        root.addView(image);

        lp.width = -1;
        lp.height = -1;
        if (bmp != null) {
            image.setImageBitmap(bmp);
        } else if (object instanceof Drawable) {
            image.setImageDrawable((Drawable) object);
        } else
            Toast.makeText(act, "null.....", Toast.LENGTH_SHORT).show();
        manager.addView(root, lp);
    }

    public ImageWindow(XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param, Context act, Object object) {
        super(lpparam, param, act, object);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽   
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        if (w < 0 || h < 0) return null;
        // 取 drawable 的颜色格式   
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap   
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布   
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中   
        drawable.draw(canvas);
        return bitmap;
    }


}
