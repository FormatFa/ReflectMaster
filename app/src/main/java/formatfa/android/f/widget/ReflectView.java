package formatfa.android.f.widget;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.util.Log;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;

public class ReflectView extends View {

    String tag = "AReflectView";
    Context context;


    int width = -1;
    int height = -1;
    int defaultH = 170;


    boolean isFinishDraw;

    Paint paint;

    public ReflectView(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public ReflectView(Context context, android.util.AttributeSet attrs) {

        super(context, attrs);
        Log.e(tag, "onLayoutConstruct");


        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);

        paint.setStrokeWidth(8);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = mymeasure(widthMeasureSpec, defaultH);
        height = mymeasure(heightMeasureSpec, defaultH);

        Log.e(tag, "onMeasure:" + width);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (canstart() == false) {

        }
        setMeasuredDimension(width, height);
    }

    boolean canstart() {
        return width != -1 && height != -1;
    }

    int mymeasure(int value, int defaultvalue) {

        if (MeasureSpec.getMode(value) == MeasureSpec.EXACTLY) {
            return MeasureSpec.getSize(value);
        } else {
            return defaultvalue;
        }
    }


    Position[] points;

    int offests[][] = {
            {3, 4},
            {2, 4},
            {1, 3},
            {0, 2},
            {0, 1}
    };

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(tag, "onDraw:" + width);
        super.onDraw(canvas);
        canvas.drawARGB(0, 255, 255, 255);
        //canvas.drawRect(new Rect(0,0,width,height),paint);


        points = new Position[5];


        points[0] = new Position(width / 2, 0);


        points[1] = new Position(0, width / 3);
        points[2] = new Position(width, width / 3);


        points[3] = new Position(0, height);
        points[4] = new Position(width, height);


        for (int i = 0; i < offests.length; i += 1) {
            canvas.drawLine(points[i].getX(), points[i].getY(), points[offests[i][0]].getX(), points[offests[i][0]].getY(), paint);

            canvas.drawLine(points[i].getX(), points[i].getY(), points[offests[i][1]].getX(), points[offests[i][1]].getY(), paint);


        }

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
