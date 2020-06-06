package formatfa.android.f.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;

public class ViewLineView extends View implements OnTouchListener {

    //click start
    long startTime;

    public void setListener(ViewLineClickListener listener) {
        this.listener = listener;
    }

    public ViewLineClickListener getListener() {
        return listener;
    }

    @Override
    public boolean onTouch(View p1, MotionEvent p2) {
        int action = p2.getAction();


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_UP:
                long time = System.currentTimeMillis() - startTime;
                if (time < 1000) {
                    if (listener != null) {
                        Rect rect = new Rect();
                        int x = (int) p2.getRawX();
                        int y = (int) p2.getRawY();
                        List<View> result = new ArrayList<View>();
                        //碰撞检测
                        for (int i = views.size() - 1; i >= 0; i -= 1) {
                            View v = views.get(i);
                            v.getGlobalVisibleRect(rect);


                            if (x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom) {
                                //String te="";
                                //if(v instanceof TextView)te=((TextView)v).getText().toString();
                                //	Toast.makeText(context,te+"\n"+v.getClass().getCanonicalName()+"\nX:"+x+"Y:"+y+"\nLeft:"+rect.toShortString(),0).show();
                                result.add(v);

                                //break;
                            }

                        }
                        listener.onClick(ViewLineView.this, result);


                    }
                } else if (time > 2000) {
                    if (listener != null) listener.onLongClick(ViewLineView.this);
                }
                break;


        }

        return true;
    }


    ViewLineClickListener listener;


    Context context;
    List<View> views;

    Paint paint;

    public ViewLineView(Context context, List<View> views) {
        super(context);
        this.context = context;
        this.views = views;
        setOnTouchListener(this);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.BLUE);
        this.setBackgroundColor(Color.argb(00, 0, 0, 0));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (View v : views) {
            if (v instanceof ViewGroup)
                paint.setColor(Color.BLUE);
            else
                paint.setColor(Color.RED);
            //drawRect(canvas,v);
            Rect rect = new Rect();
            v.getGlobalVisibleRect(rect);
            rect.top -= getStatusBarHeight();
            rect.bottom -= getStatusBarHeight();
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    void drawRect(Canvas ca, View view) {
        int x = (int) view.getX();
        int y = (int) view.getY();
        int w = view.getWidth();
        int h = view.getHeight();
        ca.drawLine(x, y, x + w, y, paint);
        ca.drawLine(x, y + h, x + w, y + h, paint);

        ca.drawLine(x, y, x, y + h, paint);
        ca.drawLine(x + w, y, x + w, y + h, paint);


    }

    private int getStatusBarHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);

        return height;
    }

}
