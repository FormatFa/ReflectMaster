package view.formatfa.ftexteditor.view.suggest;

public class SuggestItem {

    private String text;
    private String descript;

    public SuggestItem(String text, String descript) {
        this.text = text;
        this.descript = descript;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
