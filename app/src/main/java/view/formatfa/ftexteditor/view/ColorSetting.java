package view.formatfa.ftexteditor.view;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;

public class ColorSetting {

    //背景
    private int background = Color.BLACK;
    //光标
    private int cursor = Color.RED;
    //选中行
    private int line = Color.GRAY;
    //选择的颜色
    private int selection = Color.argb(100, 25, 25, 250);
    private int lineNum = Color.GRAY;
    private int font = Color.WHITE;
    private int textSize = 45;


    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getFontColor() {
        return font;
    }

    public boolean isShowLineNumber() {
        return isShowLineNumber;
    }

    public void setShowLineNumber(boolean showLineNumber) {
        isShowLineNumber = showLineNumber;
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        isAutoScroll = autoScroll;
    }

    private boolean isShowLineNumber = true;
    private boolean isAutoScroll = false;
    private boolean isSuggets = false;


    public void parseConfig(String config) {

        if (config == null) return;
        HashMap<String, String> map = FileUtils.readTable(config);
        Log.e("getBackground", "config:" + map);
        for (String key : map.keySet()) {
            switch (key) {
                case "background":
                    background = Color.parseColor(map.get(key));
                    break;
                case "cursor":
                    cursor = Color.parseColor(map.get(key));
                    break;
                case "line":
                    line = Color.parseColor(map.get(key));
                    break;
                case "selection":
                    selection = Color.parseColor(map.get(key));
                    break;
                case "lineNum":
                    lineNum = Color.parseColor(map.get(key));
                    break;
                case "font":
                    font = Color.parseColor(map.get(key));
                    break;
            }


        }
    }

    public int getBackground() {
        Log.e("getBackground", "result:" + Color.red(background) + " g:" + Color.green(background));
        return background;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public boolean isSuggets() {
        return isSuggets;
    }

    public void setSuggets(boolean suggets) {
        isSuggets = suggets;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    @Override
    public String toString() {
        return "ColorSetting{" +
                "background=" + background +
                ", cursor=" + cursor +
                ", line=" + line +
                ", selection=" + selection +
                ", lineNum=" + lineNum +
                ", font=" + font +
                ", textSize=" + textSize +
                ", isShowLineNumber=" + isShowLineNumber +
                ", isAutoScroll=" + isAutoScroll +
                ", isSuggets=" + isSuggets +
                '}';
    }
}
