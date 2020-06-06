package view.formatfa.ftexteditor.view.nav;

public class NavResult {

    private String title;
    private int line;
    private int charOffset;
    private int len;

    public NavResult(String title, int line, int charOffset, int len) {
        this.title = title;
        this.line = line;
        this.charOffset = charOffset;
        this.len = len;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLine() {
        return line;
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

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
