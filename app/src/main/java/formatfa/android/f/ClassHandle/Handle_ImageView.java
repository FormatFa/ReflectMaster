package formatfa.android.f.ClassHandle;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import formatfa.android.f.ImageWindow;
import formatfa.android.f.WindowUtils;

public class Handle_ImageView extends ClassHandle {

    Context act;
    Object obj;

    public Handle_ImageView(Context act, Object obj) {
        super(act, obj);
        this.act = act;
        this.obj = obj;
    }

    @Override
    public void handle(LinearLayout layout) {
        Button button = new Button(context);
        button.setText("查看ImageView的Drawable");
        button.setTextColor(Color.RED);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                Drawable draw = ((ImageView) obj).getDrawable();


                if (draw == null) {
                    Toast.makeText(act, "null", Toast.LENGTH_SHORT).show();

                } else {

                    ImageWindow iw = new ImageWindow(null, null, act, draw);
                    iw.show(WindowUtils.getWm(act), WindowUtils.getLp());

                }
            }
        });
        layout.addView(button);
    }

}
