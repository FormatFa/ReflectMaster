package view.formatfa.ftexteditor.view;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import view.formatfa.ftexteditor.view.light.Light;
import view.formatfa.ftexteditor.view.light.LightClip;
import view.formatfa.ftexteditor.view.suggest.SuggestAdapter;
import view.formatfa.ftexteditor.view.suggest.SuggestItem;
import view.formatfa.ftexteditor.view.suggest.Suggests;

public class ScriptView extends View implements View.OnTouchListener, View.OnLayoutChangeListener, AdapterView.OnItemClickListener {


    public ScriptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ScriptView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        debug("on layout change :" + bottom + " old:" + oldBottom);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        SuggestItem item = (SuggestItem) parent.getItemAtPosition(position);
        inputChars(item.getText().substring(suggestTemp.length()).toCharArray());
        closeSuggestWindow();


    }


    //文字选择类
    public class Selection {
        private int line = -1;
        private int charOffset = -1;

        public int getLine() {
            return line;
        }

        public boolean isValid() {
            return line != -1 && charOffset != -1;
        }

        @Override
        public String toString() {
            return "Selection{" +
                    "line=" + line +
                    ", charOffset=" + charOffset +
                    '}';
        }

        public void setLine(int line) {
            this.line = line;
        }

        public int getCharOffset() {
            return charOffset;
        }

        public void setCharOffset(int charOffset) {
            this.charOffset = charOffset;
        }
    }

    private boolean isOverLineSize() {

        if (dataRead.getLineSize(this) == -1) return false;
        if (nowY > dataRead.getLineSize(this) * lineHeight) {
            nowY = dataRead.getLineSize(this) * lineHeight;
            return true;
        } else
            return false;
    }

    //可见区域最长的字符
    int maxLineSize;

    private boolean isOverXSize() {

        if (dataRead.getLineSize(this) == -1) return false;
        if (drawStart + nowX + maxLineSize > viewWidth) {

            return true;
        } else
            return false;
    }


    public List<Selection> search(String str) {
        if (dataRead == null) return null;
        List<Selection> selections = new ArrayList<>();


        int i = 0;
        String line;
        while ((line = dataRead.getLine(this, i)) != null) {
            int p = line.indexOf(str);
            if (p != -1) {
                Selection se = new Selection();
                se.setLine(i);
                se.setCharOffset(p);
                selections.add(se);
            }

            i += 1;
        }


        return selections;

    }


    //自动滑动
    class autoScroll extends AsyncTask<Float, Float, Float> {

        private boolean isx = false;

        long time = 200;

        public boolean isIsx() {
            return isx;
        }

        long startTime;

        public void setIsx(boolean isx) {
            this.isx = isx;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
            isAutoScrolling = true;
        }

        @Override
        protected void onProgressUpdate(Float... values) {

            if (!isAutoScrolling) return;
            invalidate();

        }

        @Override
        protected Float doInBackground(Float... floats) {
//
//
//            while(false) {
//
//
//                try {
//                    Thread.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }

            return 0.0f;
        }

        @Override
        protected void onPostExecute(Float aFloat) {


            super.onPostExecute(aFloat);
        }
    }

    String tag = "ScriptView";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //手势监听
    class Gesture implements GestureDetector.OnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            closeSuggestWindow();
            if (dataRead == null) return false;
            isAutoScrolling = false;
            //获取去相对是第几行
            int p = (int) (e.getY() / lineHeight);

            if (dataRead.getLineSize(ScriptView.this) != -1) {
                if (p + getStartLine() > dataRead.getLineSize(ScriptView.this)) return false;
            }
            //,获取当前行显示的字符，p是相对的还要加上开始的行数
            String string = getDisplayText(p + getStartLine());


            /*判断点击的是哪
            1.获取显示的字符的所有宽高
            2.循环相加，直到当前点击的坐标在这个字符里
            3.就判断点击的是这个字符，跳出循环
             */
            float[] lens = new float[1];

            //System.out.println("get text width:"+Arrays.toString(lens));
            int totalx = 0;
            int tempCursor = 0;
            int x = (int) e.getX() - drawStart;


            float lastf = 0;
            //第一个字符前面的坐标cursor是0
            for (int j = 0; j < string.length(); j += 1) {
                paint.getTextWidths(string.substring(j, j + 1), lens);
                lastf = lens[0];
                /*

                formatfa
                点几了 o的位置，cursor = 1,在点击的字母的前面


                 */

                totalx += lens[0];

                if (totalx > x) {

                    cursorX = tempCursor;
                    cursorY = p;
                    System.out.println("click x:" + x + " sigle tap caculate x,y:" + cursorY + " char ar :" + cursorX);

                    //字符前的位移没，减去当前字符的宽度
                    totalx -= lens[0];


                    if (viewListener != null)
                        viewListener.onLineClick(cursorY, string.charAt(cursorX), absoluteCharOffset);
                    break;
                }
                tempCursor += 1;
            }


            System.out.println("total x:" + totalx);
            //点到外面去了
            if (x > totalx + lastf) {

                if (lastf == 0) lastf = lineHeight;
                cursorX = tempCursor;
                cursorY = p;
                System.out.println("click x:" + x + " sigle tap caculate x,y:" + cursorY + " char ar :" + cursorX);

                //字符前的位移没，减去当前字符的宽度
                // totalx-=f;


                if (viewListener != null)
                    viewListener.onLineClick(cursorY, ' ', absoluteCharOffset);

            }


            //点击时的绝对行数。输入法输入时，要获取当前的位置才能插入字符。获取当前位置的方法就是getstartline+cursorY,但是如果用户滚动了，getstartline的值就会发生变化,所以u在点击时就要判断

            absoluteLine = getStartLine() + cursorY;
            absoluteCharOffset = getStartCharOffset(absoluteLine) + cursorX;
            String line = getLineText(absoluteLine);
            int linelen = 0;
            if (line != null) linelen = line.length();
            if (absoluteCharOffset > linelen) absoluteCharOffset = linelen;


            //如果输入法没显示。就拉起输入法,inputconnection的finishComposingText方法被调用时就是输入法藏
            if (!isShowInput || !inputMethodManager.isActive(ScriptView.this)) {


                inputMethodManager.toggleSoftInput(0, 0);

                isShowInput = true;

            }

            invalidate();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            isAutoScrolling = false;

            tempX = distanceX;
            tempY = distanceY;
            if (dataRead == null) return false;
            System.out.println("onScroll:" + distanceX + " " + distanceY);
            System.out.println("getLineTotalHeight:" + dataRead.getLineSize(ScriptView.this) * lineHeight);
            //   ScriptView.this.layout(getLeft(),);

            isOverLineSize();
            scroll((int) distanceX, (int) distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            showMenu();

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            tempX = velocityX;
            tempY
                    = velocityY;
            if (getColorSetting().isAutoScroll()) {

                System.out.println("on fling :" + velocityX + " very：" + velocityY);
                if (!autoScroll.isCancelled()) autoScroll.cancel(true);
                boolean isX = Math.abs(velocityX) > Math.abs(velocityY);
                autoScroll = new autoScroll();
                autoScroll.setIsx(isX);
                autoScroll.execute(isX ? tempX : tempY);
            }
            return true;
        }
    }


    float tempX = 0;
    float tempY = 0;

    public void showMenu() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());

        ab.setItems(new String[]{"复制", "粘帖", "删除", "取消选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        String s = getSelectionString();

                        if (s != null)
                            manager.setPrimaryClip(ClipData.newPlainText("ScriptView", s));
                        break;
                    case 1:
                        ClipboardManager m = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        if (m.getPrimaryClip() == null) return;
                        if (m.getPrimaryClip().getItemCount() == 0) {
                            Toast.makeText(getContext(), "没有发现已复制的文本", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (m.getPrimaryClip().getItemCount() == 1) {
                            inputStrings(m.getPrimaryClip().getItemAt(0).getText().toString());
                        } else {
                            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                            List<String> datas = new ArrayList<>();

                            for (int i = 0; i < m.getPrimaryClip().getItemCount(); i += 1) {
                                datas.add(m.getPrimaryClip().getItemAt(i).getText().toString());
                            }
                            ab.setItems(datas.toArray(new String[0]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("取消", null).show();
                        }
                        break;

                    case 2:
                        removeSelectionString();
                        invalidate();
                    case 3:
                        setSelectionEnd(-1, -1);
                        setSelectionStart(-1, -1);
                        invalidate();
                        break;
                }
            }
        }).setPositiveButton("取消", null).show();


    }


    private List<String> lines;
    private String code;


    private Paint paint = new Paint(), nowbackPaint, cursorPaint, linePaint, selectionPaint;

    private int lineHeight = 25;


    public ColorSetting getColorSetting() {
        if (colorSetting == null) colorSetting = new ColorSetting();
        return colorSetting;
    }

    public void setColorSetting(ColorSetting colorSetting) {
        this.colorSetting = colorSetting;
    }


    private int textWidths = 0;
    private String lineSep = "\n";

    private boolean isViewFinish = false;
    private int viewWidth = 0, viewHeight = 0;

    int cursorX = -1, cursorY = -1;
    //绝对的行数，当点击时才改变
    int absoluteLine = 0, absoluteCharOffset = 0;
    //每个字符的大小不一样，现在点击时就计算好
    //Rect cursorRect=new Rect();
    //backgroupd的
    Rect backRect = new Rect();

    Rect selectionRect = new Rect();
    //隐藏的那些字符的大小
    private ScriptViewListener viewListener;

    //各种颜色设置
    private ColorSetting colorSetting = new ColorSetting();

    //i开始画的地方，因为要显示行数那些
    private int drawStart;


    //----------------------配置数据-------------


    private boolean isLight = true;
    private boolean canNewLine = true;

    //简单光标
    private boolean isismpleCursor = false;

    private boolean isChange = false;

    public boolean isIsismpleCursor() {
        return isismpleCursor;
    }

    public void setIsismpleCursor(boolean isismpleCursor) {
        this.isismpleCursor = isismpleCursor;
    }

    public boolean isLight() {
        return isLight;
    }

    public void setLight(boolean light) {
        isLight = light;
    }

    public void setCanNewLine(boolean canNewLine) {
        this.canNewLine = canNewLine;
    }


    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public void scroll(int x, int y) {
        nowY += y;
        if (nowY < 0) {
            nowY = 0;
        }
        nowX += x;
        if (nowX < 0) {
            nowX = 0;
        }
        System.out.println(nowY);
        invalidate();

    }

    private Selection selectionStart = new Selection(), selectionEnd = new Selection();

    //选择
    public void setSelectionStart(int line, int charoff) {
        selectionStart.setLine(line);
        selectionStart.setCharOffset(charoff);

    }

    public void setSelectionEnd(int line, int charoff) {

        selectionEnd.setLine(line);
        if (charoff != 0)
            selectionEnd.setCharOffset(charoff - 1);
        else
            selectionEnd.setCharOffset(charoff);

    }

    public String getSelectionString() {

        if (!selectionStart.isValid() || !selectionEnd.isValid()) return null;
        StringBuilder stringBuilder = new StringBuilder();
        int startLine = selectionStart.getLine();
        int endLine = selectionEnd.getLine();
        if (endLine < startLine) {
            return null;
        }
        for (int i = startLine; i <= endLine; i += 1) {
            String line = getLineText(i);
            int charstart = 0;
            int charend = line.length();
            if (i == startLine) charstart = selectionStart.getCharOffset();
            if (i == endLine) {
                charend = selectionEnd.getCharOffset();
                if (charend < line.length()) charend += 1;
            }
            stringBuilder.append(line.substring(charstart, charend));
            if (i != startLine)
                stringBuilder.append(dataRead.getLineSep(this));
        }
        return stringBuilder.toString();

    }

    public void removeSelectionString() {
        if (!selectionStart.isValid() || !selectionEnd.isValid()) return;

        int startLine = selectionStart.getLine();
        int endLine = selectionEnd.getLine();
        if (endLine < startLine) {
            return;
        }
        String line;
        int charstart = 0;
        int charend = 0;
        if (startLine == endLine) {

            line = getLineText(startLine);
            charend = selectionEnd.getCharOffset();
            if (charend < line.length())
                charend += 1;
            String s = line.substring(0, selectionStart.getCharOffset()) + line.substring(charend);
            dataRead.setLine(this, startLine, s);
        } else {

            //处理选择的第一行
            line = getLineText(startLine);
            charstart = selectionStart.getCharOffset();
            String s = line.substring(0, charstart);

            dataRead.setLine(this, startLine, s);
            int i = startLine + 1;
            while (i != endLine) {
                dataRead.removeLine(this, startLine + 1);
                i += 1;

            }
            line = getLineText(endLine - 1);
            charend = selectionEnd.getCharOffset();
            if (charend < line.length()) charend += 1;


            System.err.println(getLineText(endLine - 1) + "end line:" + line);
            s = line.substring(charend);
            dataRead.setLine(this, startLine + 1, s);


        }


        //        for(int i = startLine;i<=endLine;i+=1)
//        {
//            String line = getLineText(i);
//            int charstart = 0;
//            int charend = line.length();
//            if(i==startLine)
//            {
//                charstart=selectionStart.getCharOffset();
//                String s  = getLineText(i).substring(0,charstart);
//                if(i==endLine)
//                {
//                    charend=selectionEnd.getCharOffset();
//                    if(charend<line.length())charend+=1;
//                    s+= getLineText(i).substring(charend);
//                }
//                dataRead.setLine(this,i,s);
//
//            }
//            else
//            if(i==endLine)
//            {
//                charend=selectionEnd.getCharOffset();
//                if(charend<line.length())charend+=1;
//                System.out.println(line+"删除最后一行:,charend"+charend);
//                if((charend<line.length())){
//                String s = getLineText(i).substring(charend);
//                dataRead.setLine(this,i,s);}
//                else
//                    System.out.println("charedn > lineleng");
//            }
//            else
//            {
//                dataRead.removeLine(this,startLine+1);
//                endLine-=1;
//                i-=1;
//            }
//        }

    }

    public Selection getNowSelection() {

        Selection selection = new Selection();
        selection.setLine(absoluteLine);
        selection.setCharOffset(absoluteCharOffset);
        return selection;
    }


    public ScriptViewListener getViewListener() {
        return viewListener;
    }

    public void setViewListener(ScriptViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }


    //
    private void debug(String s) {
        Log.d(tag, s);
    }

    //状态栏高度,绘制时要加上这个高度
    private int statusBarHeight = 0;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        initFont();
        this.code = code;
        initLine();

    }


    public void setCode(DataRead read) {
        initFont();
        this.dataRead = read;
        absoluteLine = 0;
        nowX = 0;
        nowY = 0;
        absoluteCharOffset = 0;
        cursorY = 0;
        cursorX = 0;

        drawStart = getTotalStringSize(linePaint, String.valueOf(dataRead.getLineSize(this)));

        invalidate();
    }

    private Gesture gesture;
    private GestureDetector gestureDetector;


    //当前的y位移。滑动事件监听时会相应的加减，画文字时根据这个计算是哪行。即nowY/lineHeight
    private int nowY = 0;
    private int nowX = 0;


    //input ，
    private InputMethodManager inputMethodManager;
    private boolean isShowInput = false;

    private DataRead dataRead;
    private Light light;

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    long drawTime, lightTime;


    //自动滑动
    boolean isAutoScrolling = false;


    autoScroll autoScroll = new autoScroll();
    int gvelocityY = 0;

    public ScriptView(Context context) {
        super(context);


        init(context);


    }

    public void moveSelection() {


    }

    public void processKeyEvent(KeyEvent event) {

        System.out.println("sendKeyEvent:" + event.toString());
        System.out.println("key code:" + event.getKeyCode());
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DEL:
                    delete(absoluteLine, absoluteCharOffset, 1);

                    break;
                case KeyEvent.KEYCODE_ENTER:
                    inputStrings(lineSep);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:


                    moveOffset(-1);
                    invalidate();
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    moveOffset(1);
                    invalidate();
                    break;
                case KeyEvent.KEYCODE_SHIFT_LEFT:
                    if (selectionEnd.getLine() == -1)
                        selectionEnd.setLine(absoluteLine);
                    if (selectionEnd.getCharOffset() == -1)
                        selectionEnd.setCharOffset(absoluteCharOffset);
                    selectionEnd.setCharOffset(absoluteCharOffset);
                    if (absoluteCharOffset == 0 && absoluteLine != 0) {
                        selectionEnd.setLine(selectionEnd.getLine() - 1);
                        if (getLineText(selectionEnd.getLine()).length() != 0)
                            selectionEnd.setCharOffset(getLineText(selectionEnd.getLine()).length() - 1);
                    } else {
                        selectionEnd.setCharOffset(selectionEnd.getCharOffset() - 1);
                    }


                    break;
                default:
                    lastKeyCode = event.getKeyCode();
                    // inputChar(event.getDisplayLabel());
                    inputChar((char) event.getUnicodeChar());
            }

        }
    }

    private void init(Context context) {
        this.addOnLayoutChangeListener(this);
        statusBarHeight = ViewUtils.getStatusBarHeight(context);
        colorSetting = new ColorSetting();

        this.setOnTouchListener(this);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //键盘输入,asci值内cc。可显示字符

                if (event.getAction() == event.ACTION_DOWN) {
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DEL:
                            delete(absoluteLine, absoluteCharOffset, 1);
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            moveOffset(-1);
                            invalidate();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            moveOffset(1);
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            break;
                        default:
                            //    inputStrings(event.getCharacters());
                            inputChar((char) event.getUnicodeChar());
                    }
                }
                debug("on view key :" + keyCode);
                return false;
            }
        });
        gesture = new Gesture();
        gestureDetector = new GestureDetector(context, gesture);

        this.post(new Runnable() {
            @Override
            public void run() {
                viewWidth = ScriptView.this.getWidth();
                viewHeight = ScriptView.this.getHeight();
                isViewFinish = true;
            }
        });

        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.post(new Runnable() {
            @Override
            public void run() {
                viewWidth = ScriptView.this.getWidth();
                viewHeight = ScriptView.this.getHeight();
                isViewFinish = true;
                loadSuggestsPop();
            }
        });


    }


    //---------------------------------------


    class queryWord {
        String line;
        int position;

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    //查询列表
    class querySuggestTask extends AsyncTask<queryWord, String, List<SuggestItem>> {
        private String word;

        public querySuggestTask() {


        }


        @Override
        protected List<SuggestItem> doInBackground(queryWord... queryWords) {

            if (suggestTemp == null) return null;
            publishProgress(word);
            List<SuggestItem> result = suggests.getSuggests(ScriptView.this, suggestTemp, queryWords[0].getLine(), queryWords[0].getPosition());

            return result;
        }

        @Override
        protected void onPostExecute(List<SuggestItem> strings) {
            super.onPostExecute(strings);

            if (suggestWindow.isShowing()) suggestWindow.dismiss();
            if (strings == null || strings.size() == 0) {
                closeSuggestWindow();
                return;
            }

            suggestWindow.showAtLocation(ScriptView.this, Gravity.TOP, 0, cursorY * lineHeight);

            SuggestAdapter adapter = new SuggestAdapter(getContext(), strings);
            suggestView.setAdapter(adapter);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            suggestText.setText(suggestTemp);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    public Suggests getSuggests() {
        return suggests;
    }

    public void setSuggests(Suggests suggests) {
        this.suggests = suggests;
    }

    private Suggests suggests;
    PopupWindow suggestWindow;
    private ListView suggestView;
    private TextView suggestText;
    private String suggestTemp;

    private void closeSuggestWindow() {
        if (suggestWindow.isShowing()) {
            suggestWindow.dismiss();
        }
        suggestTemp = null;
    }

    private void loadSuggestsPop() {
        suggestWindow = new PopupWindow(this, (viewWidth / 4) * 3, lineHeight * 4);
        suggestView = new ListView(getContext());

        suggestView.setOnItemClickListener(this);

        suggestText = new TextView(getContext());
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(suggestText);
        layout.addView(suggestView);


        suggestWindow.setContentView(layout);
        suggestWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


    }


    public DataRead getDataRead() {
        return dataRead;
    }

    public void setDataRead(DataRead dataRead) {
        this.dataRead = dataRead;
    }

    public void initFont() {

        //this.setBackgroundColor(Color.BLACK);
        paint = new Paint();
        paint.reset();
        float[] textWidth = new float[1];


        paint.getTextWidths("梁", textWidth);
        textWidths = (int) textWidth[0];

        nowbackPaint = new Paint();
        cursorPaint = new Paint();
        cursorPaint.setStrokeWidth(5);
        linePaint = new Paint();

        selectionPaint = new Paint();


        setSetting(colorSetting);

        //要设置这个，不然监听不了输入法的输入
        ScriptView.this.setFocusableInTouchMode(true);
        ScriptView.this.requestFocus();


    }

    public void setSetting(ColorSetting setting) {
        if (setting != null)
            this.colorSetting = setting;
        paint.setColor(colorSetting.getFontColor());
        nowbackPaint.setColor(colorSetting.getLine());
        linePaint.setColor(colorSetting.getLineNum());
        selectionPaint.setColor(colorSetting.getSelection());
        cursorPaint.setColor(colorSetting.getCursor());

        //   this.setBackgroundColor(colorSetting.getBackground());

        paint.setTextSize(colorSetting.getTextSize());
        linePaint.setTextSize(colorSetting.getTextSize());

        Paint.FontMetrics metrics = paint.getFontMetrics();
        lineHeight = (int) Math.ceil(metrics.descent - metrics.top);
        Log.e(tag, "getLineWidth:" + lineHeight + " descent:" + metrics.descent);

        drawStart = (getTotalStringSize(linePaint, "3") * 4);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("on measure:" + widthMeasureSpec + " height:" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initLine() {

        lines = new ArrayList<String>();
        String[] s = FileUtils.splitByLine(lineSep, code).toArray(new String[0]);
        //code.split(lineSep);
        lines.addAll(Arrays.asList(s));


        absoluteLine = 0;
        absoluteCharOffset = 0;
        cursorY = 0;
        cursorX = 0;

        drawStart = (getTotalStringSize(linePaint, String.valueOf(dataRead.getLineSize(this))) * 4);
    }


    //获取指定行的文字

    private String getNowLineText() {
        return getLineText(absoluteLine);
    }

    private String getLineText(int p) {
        if (dataRead == null) return null;
        return dataRead.getLine(this, p);
    }

    //但钱最左边显示的是那个字符
    private int getStartCharOffset(int linenum) {


        String line = getLineText(linenum);
        if (line == null) return 0;
        float items[] = new float[1];

        float result = 0;
        int offset = 0;
        int c = Math.abs(nowX);
        for (int i = 0; i < line.length(); i += 1) {
            paint.getTextWidths(line.substring(i, i + 1), items);
            result += items[0];
            if (result > c) {
                break;
            }
            offset += 1;
        }
        return offset;
        //  return  nowX/lineHeight;
    }

    private String getDisplayText(int p) {
        //根据x坐标获取相对的字符开始位移
        long a = System.currentTimeMillis();
        int startChar = getStartCharOffset(p);

        debug("get start cosrt:" + (System.currentTimeMillis() - a));
        // startChar+=getStartCharOffset(p);
        String str = getLineText(p);
        if (str == null) return "";
        if (str.length() >= startChar)
            str = str.substring(startChar);
        else
            str = "";
        return str;
    }

    public int getNowCharLength() {
        int result = getTotalStringSize(paint, getNowChar());
        if (result == 0)
            result = textWidths;
        return result;
    }

    public String getNowChar() {
        String s = getLineText(absoluteLine);
        if (absoluteCharOffset < s.length())
            return s.substring(absoluteCharOffset, absoluteCharOffset + 1);
        else return "";
    }

    private int getStartLine() {
        return nowY / lineHeight;
    }

    //一页多少行
    public int getPageLines() {
        return viewHeight / lineHeight;
    }

    //光标在可见区域时到屏幕左边的距离
    private int getCursorofffsetTostart() {

        int start = getStartCharOffset(absoluteLine);
        if (start >= absoluteCharOffset) {
            System.out.println(start + "显示字符大于绝对的位置" + absoluteCharOffset);
            return 0;
        }
        String line = getLineText(absoluteLine);

        //粘帖后可能当前绝对路径的字符变短
        if (absoluteCharOffset < line.length()) {

            line = line.substring(start, absoluteCharOffset);
            System.out.println("获取光标位移,歌后行:" + line);
        } else if (absoluteCharOffset == line.length())
            line = line.substring(start);

        return getTotalStringSize(paint, line);
    }

    //获取一段字符的总x
    private int getTotalStringSize(Paint paint, String x) {
        float items[] = new float[1];

        float result = 0;
        for (int i = 0; i < x.length(); i += 1) {
            paint.getTextWidths(x.substring(i, i + 1), items);
            result += items[0];
        }
        return (int) result;
    }

    private int[] getTotalStringSizeAndMaxCharNumberInScreen(Paint paint, String x) {
        int[] re = new int[2];
        re[1] = -1;
        float items[] = new float[1];

        float result = 0;
        int count = 0;
        for (int i = 0; i < x.length(); i += 1) {
            paint.getTextWidths(x.substring(i, i + 1), items);
            result += items[0];
            if (result > viewWidth && re[1] == -1) {
                re[1] = count;
                break;
            }
            count += 1;
        }
        re[0] = (int) result;
        return re;
    }

    public String getInfo() {
        return "最后滑动的y速度:" + tempY + "\n" +
                "最后滑动的X速度:" + tempX + "\n" +
                "状态栏高度_statusBarHeight:" + statusBarHeight + "\n\n" +
                "可以换行:" + canNewLine + "\n\n" +
                "行高_lineheight:" + lineHeight + "\n\n" +
                "View高度_viwHeight:" + viewHeight + "v\n" +
                "View宽度_viewWidth:" + viewWidth + "\n\n" +
                "当前相对的Y_nowY:" + nowY + "\n\n" +
                "当前相对的X_nowX:" + nowX + "\n\n" +
                "绝对行数_absoluteLine:" + absoluteLine + "\n\n" +
                "绝对字符数_absoluteCharOffset:" + absoluteCharOffset + "\n\n" +
                "哪个字符开始的_getstarCharOffset;" + getStartCharOffset(absoluteLine) + "\n\n" +
                "读取的总行数_dataRead.getLinesize:" + ((dataRead == null) ? "null" : dataRead.getLineSize(this)) + "\n\n" +
                "当前光标可见行_getDisplayText:" + "" + "\n\n" +
                "换行符_lineSep:" + lineSep + "\n\n" +
                "相对的开始行_getStartLine:" + getStartLine() + "\n\n" +
                "相对字符唯一:_cursorX" + cursorX + "\n\n" +
                "相对行数位移:_cursorY" + cursorY + "\n\n" +
                "最后接受的键盘key代码_lastKeyCode:" + lastKeyCode + "\n\n" +
                "字符读取引擎介绍_getDescrpt:" + (dataRead == null ? "" : dataRead.getDescript()) + "\n\n" +
                "\n" +
                "最新一次绘制消耗总时间_drawTime:" + drawTime + "毫秒， 高亮使用时间:" + lightTime + "毫秒" + "\n\n" +
                "选择的字符_getSelection:" + getSelectionString() + "\n\n" +
                "选择开始_selectionstart：" + selectionStart.toString() + " 结束_getselectuon:" + selectionEnd.toString() + "\n"
                + "主题:" + colorSetting.toString()

                ;


    }

    //private int getStartL
    @Override
    protected void onDraw(Canvas canvas) {

        maxLineSize = 0;
        //setBackgroundColor(0x2b2b2b);
        canvas.drawRGB(Color.red(getColorSetting().getBackground()), Color.green(getColorSetting().getBackground()), Color.blue(getColorSetting().getBackground()));
        if (dataRead == null) {

            canvas.drawText("没有指定数据读取器", 0, lineHeight, paint);
            return;
        }
        lightTime = 0;
        drawTime = 0;
        long temp = System.currentTimeMillis();


        if (absoluteCharOffset < 0) absoluteCharOffset = 0;

        if (!isViewFinish) {
            System.out.println("on draw ,but view not runnale finidsh yet");
            return;
        }

        int drawTotal = viewHeight / lineHeight;
        int startLine = nowY / lineHeight;
        debug("ondraw,drawTotal:" + drawTotal + " startLine =" + startLine);


        int l = 0;

        for (int i = startLine; i < drawTotal + startLine; i += 1) {
            if (i >= dataRead.getLineSize(this))
                continue;

            int y = (l * lineHeight);

            //行号
            canvas.drawText(String.valueOf(i), 0, y + lineHeight, linePaint);
            //nowline是相对的，所以i要减去开始的


            selectionRect.top = y;
            selectionRect.bottom = y + lineHeight;
            //选择&&selectionStart.getCharOffset()<=selectionEnd.getCharOffset()


            if (i == absoluteLine) {

                backRect.set(0 + drawStart, y, viewWidth + drawStart, y + lineHeight);
                canvas.drawRect(backRect, nowbackPaint);

                // /top bottom地方应该和背景一样

                if (cursorX != -1 && getStartCharOffset(absoluteLine) <= absoluteCharOffset) {
                    debug("now x:" + nowX + " offset:" + getCursorofffsetTostart());
                    //backrect是为了和背景在统一战线

                    int cursorW = 2;
                    if (!isismpleCursor) cursorW = getNowCharLength();


                    long t = System.currentTimeMillis();
                    if (absoluteCharOffset != getLineText(absoluteLine).length())
                        canvas.drawRect(getCursorofffsetTostart() + drawStart, backRect.top, getCursorofffsetTostart() + cursorW + drawStart, backRect.bottom, cursorPaint);
                    else
                        canvas.drawRect(getCursorofffsetTostart() + drawStart, backRect.top, getCursorofffsetTostart() + cursorW + drawStart, backRect.bottom, cursorPaint);


                    debug("drawCurr as:" + (System.currentTimeMillis() - t));
//                    }else
//                    canvas.drawRect(getCursorofffsetTostart() + drawStart, backRect.top, getCursorofffsetTostart() +getNowCharLength()+ drawStart, backRect.bottom, cursorPaint);
                }
            }

            if (selectionStart.isValid() && selectionEnd.isValid())
                if (i == selectionStart.getLine()) {

                    if (selectionStart.getCharOffset() >= getStartCharOffset(i)) {

                        int endchar = getLineText(i).length();
                        if (i == selectionEnd.getLine()) {
                            endchar = selectionEnd.getCharOffset();
                            if (endchar < getLineText(i).length()) endchar += 1;
                        }
                        if (selectionStart.getCharOffset() + endchar <= getLineText(i).length()) {
                            int selelen = getTotalStringSize(paint, getLineText(i).substring(selectionStart.getCharOffset(), endchar));

                            selectionRect.left = drawStart + getTotalStringSize(paint, getLineText(i).substring(getStartCharOffset(i), selectionStart.getCharOffset()));
                            //,selectionEnd.getCharOffset()

                            selectionRect.right = drawStart + getTotalStringSize(paint, getLineText(i).substring(getStartCharOffset(i), selectionStart.getCharOffset())) + selelen;

                            canvas.drawRect(selectionRect, selectionPaint);
                        }

                    }

                } else if (i > selectionStart.getLine() && i < selectionEnd.getLine()) {
                    selectionRect.left = 0;
                    selectionRect.right = viewWidth;
                    canvas.drawRect(selectionRect, selectionPaint);
                    debug("当前行大于开始并小于最后");

                } else if (i == selectionEnd.getLine()) {
                    if (getStartCharOffset(i) <= selectionEnd.getCharOffset()) {

                        int start = getStartCharOffset(i);
                        if (i == selectionStart.getLine()) start = selectionStart.getCharOffset();


                        int endchar = selectionEnd.getCharOffset();
                        if (endchar < getLineText(i).length()) endchar += 1;


                        int selelen = getTotalStringSize(paint, getLineText(i).substring(start, endchar));

                        System.out.println("选择的文字这行要显示e选择 len:" + getLineText(i).substring(start, selectionEnd.getCharOffset()));
                        selectionRect.left = drawStart;
                        //,selectionEnd.getCharOffset()

                        selectionRect.right = drawStart + selelen;

                        canvas.drawRect(selectionRect, selectionPaint);

                    }
                }
            long yl = System.currentTimeMillis();
            String text = getDisplayText(i);

            int[] as = getTotalStringSizeAndMaxCharNumberInScreen(paint, text);
            debug("get String Total:" + (System.currentTimeMillis() - yl));

            System.out.println("get display text:" + text + " as:" + Arrays.toString(as));
            if (text.length() != 0 && as[1] != -1)
                text = text.substring(0, as[1]);
            //int size = getTotalStringSize(paint,text);
            // if(size>maxLineSize)maxLineSize=size;
            //因为是基于baseline画得，baseline在下面所以加上高度,这里的图https://blog.csdn.net/g0ose/article/details/80547461
            debug("start draw：" + text);
            long tl = System.currentTimeMillis();

            canvas.drawText(text, 0 + drawStart, y + lineHeight, paint);

            debug("drawText cost:" + (System.currentTimeMillis() - tl));

            if (isLight) {
                //高亮
                long lightstart = System.currentTimeMillis();
                int color = paint.getColor();
                if (light != null) {
                    List<LightClip> clips = light.light(text);
                    if (clips != null)

                        for (LightClip cl : clips) {

                            String line = text.substring(cl.getStart(), cl.getEnd());
                            paint.setColor(cl.getColor());
                            canvas.drawText(line, drawStart + getTotalStringSize(paint, text.substring(0, cl.getStart())), y + lineHeight, paint);
                        }
                    paint.setColor(color);

                }

                lightTime += (System.currentTimeMillis() - lightstart);
            }


            l += 1;
        }


        drawTime = System.currentTimeMillis() - temp;
        //画光标kk
        ///k忽视
        // canvas.drawLine(cursorX*lineHeight,cursorY*lineHeight,cursorX*lineHeight,cursorY*(lineHeight+1),cursorPaint);


        String line = getLineText(absoluteLine);
        if (line == null) line = "";

        if (viewListener != null)
            viewListener.onLineClick(absoluteLine, ((line.length() > absoluteCharOffset) ? line.charAt(absoluteCharOffset) : ' '), absoluteCharOffset);
        super.onDraw(canvas);
    }

    //------------------------

    /*

    abcdfd
    0
    fd


     */


    public void gotoLineAndOffset(int line, int charoff) {
        absoluteCharOffset = charoff;
        absoluteLine = line;

        nowY = absoluteLine * lineHeight;
        invalidate();


    }

    public void inputChars(char[] ch) {

        isChange = true;
        String line = getNowLineText();
        if (line == null) line = "";
        debug("input char:" + Arrays.toString(ch) + " line :" + line + " absoluteCharOffset:" + absoluteCharOffset);


        int position = absoluteCharOffset;


        //插入字符
        char[] newbuff = null;
        if (line.length() == 0)
            newbuff = ch;
        else
            newbuff = FileUtils.charInsert(line.toCharArray(), position, ch);

        debug("new line:" + new String(newbuff));
        String newline = new String(newbuff);

        //按行分割
        String[] seps = null;
        if (canNewLine) {
            seps = FileUtils.splitByLine(lineSep, newline).toArray(new String[0]);
        } else {
            seps = new String[]{newline};
        }
        //newline.split(lineSep);
        //单行
        if (seps.length == 0)
            dataRead.setLine(this, absoluteLine, newline);
        else {

            //啥来着，忘了
            dataRead.setLine(this, absoluteLine, seps[seps.length - 1]);
            for (int i = 0; i < seps.length - 1; i += 1) {
                dataRead.addLine(this, absoluteLine, seps[seps.length - 2 - i]);
            }
        }


        //line=getNowLineText();
        //光标要后以,计算下一个字符的光标位置


        float[] a = new float[1];


        System.out.println("line size:" + line.length() + " position:" + position);
        if (seps.length > 1) {
            System.out.println("mulity line:" + Arrays.toString(seps));
            cursorX += seps[0].toCharArray().length;
            //绝对路径也假
            absoluteCharOffset = seps[0].toCharArray().length - 1;
            line = seps[0];
        } else {
            cursorX += ch.length;
            //绝对路径也假
            absoluteCharOffset += ch.length;
        }
        //line是0就是那行是空的
        if (line.length() > 0) {


            // position=absoluteCharOffset;
            //  if(position==1&&line.length()==1)position=0;
            //  else
            position = absoluteCharOffset;

            System.out.println(position + getNowLineText() + " " + absoluteCharOffset);
            //如果是最后一个字符,position就是长度了
            if (position == newline.length()) {
                position -= 1;
            }
            if (seps.length > 1) {
                //在第一个字符前面换行时
                if (seps[0].isEmpty()) {
                    absoluteLine -= 1;
                    absoluteCharOffset = 0;
                } else {
                    absoluteLine += 1;
                    absoluteCharOffset = 0;
                }
            }
            paint.getTextWidths(newline, position, position + 1, a);

        } else {
            //应该加上当前行的
//            cursorX += ch.length;
//            //绝对路径也假
//            absoluteCharOffset+=ch.length;
            //当是0个字符的时候，计算新输入的
            paint.getTextWidths(new String(ch), ch.length - 1, ch.length, a);
        }


        //Toast.makeText(getContext(),"  size:"+a[0]+ "ei :"+cursorRect.width(),Toast.LENGTH_SHORT).show();


        invalidate();


        if (suggests != null) {
            if (suggestTemp == null) suggestTemp = new String(ch);
            else
                suggestTemp += new String(ch);

            queryWord word = new queryWord();
            word.setLine(getLineText(absoluteLine));
            word.setPosition(absoluteCharOffset);

            new querySuggestTask().execute(word);
        }


    }

    public void inputStrings(String str) {
        if (str == null) return;
//        String nowLine= getNowLineText();
//
//
//
//        String ls[] = str.split("\n");
//
//        for(String s:ls)
//        {
//         lines.add(s);
//        }
        // invalidate();
        inputChars(str.toCharArray());
    }

    public void inputChar(char ch) {

        inputChars(new char[]{ch});


    }

    //删除position前面的
    public void delete(int line, int position, int len) {
        if (len == 1 && suggestTemp != null) {
            if (suggestTemp.length() > 0) {
                suggestTemp = suggestTemp.substring(0, suggestTemp.length() - 1);
                new querySuggestTask().execute(new queryWord());
            }
        }


        if (position == 0) {
            if (absoluteLine == 0) {
                String now = getLineText(line);
                if (position + len <= now.length())
                    dataRead.setLine(this, line, now.substring(0, position) + now.substring(position + len));

            } else {
                absoluteCharOffset = dataRead.getLine(this, line - 1).length() + 1;
                if (absoluteLine != 0)
                    //+dataRead.getLine(this,line)
                    dataRead.setLine(this, absoluteLine - 1, dataRead.getLine(this, line - 1) + dataRead.getLine(this, line));
                dataRead.setLine(this, absoluteLine, dataRead.getLine(this, line - 1));

                dataRead.removeLine(this, line - 1);
                absoluteLine -= 1;
            }

        } else {

            String now = getLineText(line);
            if (position + len <= now.length())
                dataRead.setLine(this, line, now.substring(0, position - 1) + now.substring(position - 1 + len));
            else if (len == 1) {
                dataRead.setLine(this, line, now.substring(0, position - 1));
            }


        }
        moveOffset(-1);
        invalidate();

        isChange = true;
    }

    public void moveOffset(int offset) {

        int linesize = getLineText(absoluteLine).length();
        if (absoluteCharOffset + offset > linesize) {
            absoluteCharOffset = 0;
            absoluteLine += 1;
            if (absoluteLine >= dataRead.getLineSize(this)) {
                dataRead.addLine(this, absoluteLine, "");
            }
        } else if (absoluteCharOffset == 0 && absoluteLine != 0 && offset < 0) {
            String shangmian = getLineText(absoluteLine - 1);
            if (shangmian.length() != 0)
                absoluteCharOffset = shangmian.length() - 1;
            else
                absoluteCharOffset = shangmian.length();
            absoluteLine -= 1;
        } else
            absoluteCharOffset += offset;


        if (absoluteCharOffset < 0) absoluteCharOffset = 0;

    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public boolean checkInputConnectionProxy(View view) {
        return true;
    }

    //键盘最后一个输入的key
    int lastKeyCode = 0;

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {

        debug("onCreateiNPUTcONection");
        return new InputConnection() {
            @Override
            public CharSequence getTextBeforeCursor(int n, int flags) {


                return null;
            }

            @Override
            public CharSequence getTextAfterCursor(int n, int flags) {
                return null;
            }

            @Override
            public CharSequence getSelectedText(int flags) {

                return null;
            }

            @Override
            public int getCursorCapsMode(int reqModes) {
                return 0;
            }

            @Override
            public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
                return null;
            }

            @Override
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                return false;
            }

            @Override
            public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
                return false;
            }

            @Override
            public boolean setComposingText(CharSequence text, int newCursorPosition) {
                return false;
            }

            @Override
            public boolean setComposingRegion(int start, int end) {
                return false;
            }

            @Override
            public boolean finishComposingText() {
                debug("finishComposingText");

                isShowInput = false;
                return true;
            }

            @Override
            public boolean commitText(CharSequence text, int newCursorPosition) {
                inputStrings(text.toString());
                System.out.println("commitText:" + text + " newCurposition:" + newCursorPosition);
                return true;
            }

            @Override
            public boolean commitCompletion(CompletionInfo text) {
                return false;
            }

            @Override
            public boolean commitCorrection(CorrectionInfo correctionInfo) {
                return false;
            }

            @Override
            public boolean setSelection(int start, int end) {

                debug("set selection:" + start);

                return false;
            }

            @Override
            public boolean performEditorAction(int editorAction) {
                return false;
            }

            @Override
            public boolean performContextMenuAction(int id) {
                return false;
            }

            @Override
            public boolean beginBatchEdit() {
                return false;
            }

            @Override
            public boolean endBatchEdit() {
                return false;
            }

            @Override
            public boolean sendKeyEvent(KeyEvent event) {
                System.out.println("sendKeyEvent:" + event.toString());
                System.out.println("key code:" + event.getKeyCode());
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DEL:
                            delete(absoluteLine, absoluteCharOffset, 1);

                            break;
                        case KeyEvent.KEYCODE_ENTER:
                            inputStrings(lineSep);
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:


                            moveOffset(-1);
                            invalidate();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            moveOffset(1);
                            invalidate();
                            break;
                        default:
                            lastKeyCode = event.getKeyCode();
                            inputChar(event.getDisplayLabel());
                    }


                }
                return true;
            }

            @Override
            public boolean clearMetaKeyStates(int states) {
                return false;
            }

            @Override
            public boolean reportFullscreenMode(boolean enabled) {
                return false;
            }

            @Override
            public boolean performPrivateCommand(String action, Bundle data) {
                return false;
            }

            @Override
            public boolean requestCursorUpdates(int cursorUpdateMode) {
                return false;
            }

            @Override
            public Handler getHandler() {
                return null;
            }

            @Override
            public void closeConnection() {
                debug("close connection");

            }


            @Override
            public boolean commitContent(@NonNull InputContentInfo inputContentInfo, int flags, @Nullable Bundle opts) {
                debug("commitContetn" + inputContentInfo);
                return true;
            }
        };
    }
}
