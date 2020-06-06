package formatfa.android.f.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import formatfa.android.f.ActionWindow;

import android.graphics.PixelFormat;

public class WindowDialog {


    Context context;

    WindowManager windowManager;

    WindowManager.LayoutParams layoutParams;


    LinearLayout rootLayout;
    //LinearLayout buttonLayout;
    LinearLayout contain;
    Button close;
    Button move;
    Button resize;
    TextView titleView;


    final int miniHeight = 30;

    int startX, startY, nowX, nowY;


    public WindowDialog(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        layoutParams = new WindowManager.LayoutParams();

        layoutParams.type = layoutParams.TYPE_APPLICATION;
        //layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.width = 600;
        layoutParams.height = 600;


        rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        titleView = new TextView(context);
        titleView.setTextColor(Color.RED);
//		
//		buttonLayout=new LinearLayout(context);
//		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
//		
//		
//		close = new Button(context);
//		close.setText("X");
//		close.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View p1)
//				{
//					windowManager.removeView(rootLayout);
//				}
//			});
//			
//			
//		move = new Button(context);
//		move.setText("<---->");
//		
//		move.setOnTouchListener(new OnTouchListener(){
//
//				@Override
//				public boolean onTouch(View p1, MotionEvent p2)
//				{
//					
//					switch(p2.getAction()){
//						case MotionEvent.ACTION_DOWN:
//							startX = (int)p2.getRawX();
//							startY =(int) p2.getRawY();
//							break;
//							
//						
//						case MotionEvent.ACTION_MOVE:
//							nowX = (int)p2.getRawX();
//							nowY = (int)p2.getRawY();
//							
//							layoutParams.x+=nowX-startX;
//							layoutParams.y+=nowY-startY;
//							
//							startX =nowX;
//							startY = nowY;
//							windowManager.updateViewLayout(rootLayout,layoutParams);
//							
//							break;
//							
//						
//						case MotionEvent.ACTION_UP:
//							break;
//							
//						
//					}
//					return false;
//				}
//			});
//			
//		resize = new Button(context);
//		resize.setText("[  ]");
//		resize.setOnTouchListener(new OnTouchListener(){
//
//				@Override
//				public boolean onTouch(View p1, MotionEvent p2)
//				{
//					
//					switch(p2.getAction()){
//						case MotionEvent.ACTION_DOWN:
//							startX = (int)p2.getRawX();
//							startY =(int) p2.getRawY();
//							break;
//
//
//						case MotionEvent.ACTION_MOVE:
//							nowX = (int)p2.getRawX();
//							nowY = (int)p2.getRawY();
//
//							layoutParams.width+=nowX-startX;
//							layoutParams.height+=nowY-startY;
//
//							
//							startX =nowX;
//							startY = nowY;
//							windowManager.updateViewLayout(rootLayout,layoutParams);
//
//							break;
//
//
//						case MotionEvent.ACTION_UP:
//							break;
//
//
//					}
//					return false;
//				}
//			});
//		ViewGroup.LayoutParams buttonParam = buttonLayout.getLayoutParams();
//	//	buttonParam.height=30;
//		buttonLayout.addView(close);
//		buttonLayout.addView(move);
//		buttonLayout.addView(resize);
//		
        contain = new LinearLayout(context);


        ActionWindow ac = new ActionWindow(context, windowManager, layoutParams, rootLayout);


        rootLayout.addView(ac.getActionBar());

        rootLayout.addView(titleView);
        rootLayout.addView(contain);

    }

    public void setView(View view) {

        contain.removeAllViews();
        contain.addView(view);

    }

    public void show() {
        windowManager.addView(rootLayout, layoutParams);

    }

    public void dismiss() {
        windowManager.removeView(rootLayout);
    }

    public void show(int width, int heigth) {
        layoutParams.width = width;
        layoutParams.height = heigth;
        show();
    }

    public void setTitle(String str) {

        titleView.setText(str);

    }

    public TextView getTitleView() {
        return titleView;
    }
    //public LinearLayout getButtonLayout(){return buttonLayout;}

}
