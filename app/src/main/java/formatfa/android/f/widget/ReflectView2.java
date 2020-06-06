package formatfa.android.f.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class ReflectView2 extends View {
    private Context context;

    private Paint paint;
    public static int width = 170;
    private int realwidth = 1460;
    boolean rotate;

    public boolean getRotate() {
        return rotate;
    }

    public void setRotate(boolean ro) {
        this.rotate = ro;
    }

    public ReflectView2(Context context, boolean rotate) {
        super(context);
        this.context = context;
        this.rotate = rotate;
        init();
    }

    Position p1, p2, p3, p4, p5, p6;
    int centx = width / 2;
    int centy = width / 2;

    private void debug(String s) {
        Log.d("ReflectView2", s);
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        int r = width / 2;

        int fone = width / 4;
        //根号3
        double g3 = 1.732;
        int angleWidth = (int) (r / 2 * g3);
        debug("angle widt:" + angleWidth);
        p1 = new Position(centx, 0);
        p2 = new Position(r - angleWidth, fone);
        p3 = new Position(r - angleWidth, fone * 3);
        p4 = new Position(centx, width);
        p5 = new Position(centx + angleWidth, fone * 3);
        p6 = new Position(centx + angleWidth, fone);
        debug("init");


        for (int i = 0; i < 7; i += 1) {
            ReflectView2.this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nowdraw += 1;
                    ReflectView2.this.invalidate();
                }
            }, (1000 / 6) * i);
        }

        if (rotate)
            new refThread().start();

    }

    long refFrame = 3000 / 36;

    class refThread extends Thread {
        @Override
        public void run() {
            while (true) {

                if (rotate) {
                    if (nowdraw >= 6) {
                        ReflectView2.this.post(new Runnable() {
                            @Override
                            public void run() {
                                ReflectView2.this.invalidate();
                            }
                        });

                    }
                    try {
                        refThread.sleep(refFrame);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    int mymeasure(int value, int defaultvalue) {

        if (MeasureSpec.getMode(value) == MeasureSpec.EXACTLY) {
            return MeasureSpec.getSize(value);
        } else {
            return defaultvalue;
        }
    }

    int nowdraw = 0;
    int nowrotate = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mesurew = mymeasure(widthMeasureSpec, realwidth);
        int mesureh = mymeasure(heightMeasureSpec, realwidth);
        setMeasuredDimension(mesurew, mesureh);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rotate) {
            if (nowrotate >= 360) {
                nowrotate = 0;
            }
            //开始旋转
            if (nowdraw >= 6) {
                canvas.rotate(nowrotate, centx, centy);
                nowrotate += 10;
            }
        }
        canvas.drawARGB(0, 255, 255, 255);


        canvas.drawCircle(centx, centy, width / 2, paint);
        canvas.drawCircle(centx, centy, (width / 2) - 9, paint);


        //1->3
        if (nowdraw >= 0)
            canvas.drawLine(p1.getX(), p1.getY(), p3.getX(), p3.getY(), paint);
        //3->5
        if (nowdraw >= 1)
            canvas.drawLine(p3.getX(), p3.getY(), p5.getX(), p5.getY(), paint);
        //5->1
        if (nowdraw >= 2)
            canvas.drawLine(p1.getX(), p1.getY(), p5.getX(), p5.getY(), paint);

        //2-4
        if (nowdraw >= 3)
            canvas.drawLine(p2.getX(), p2.getY(), p4.getX(), p4.getY(), paint);
        //4-6
        if (nowdraw >= 4)
            canvas.drawLine(p4.getX(), p4.getY(), p6.getX(), p6.getY(), paint);
        //6-2
        if (nowdraw >= 5)
            canvas.drawLine(p6.getX(), p6.getY(), p2.getX(), p2.getY(), paint);


    }


    class Position {

        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position() {

        }


        public void setX(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getY() {
            return y;
        }


    }

}
