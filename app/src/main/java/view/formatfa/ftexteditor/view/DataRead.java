package view.formatfa.ftexteditor.view;

public interface DataRead {

    String getLine(ScriptView view, int position);

    String getString(ScriptView view);

    void save(ScriptView view);

    String getLineSep(ScriptView view);

    int getLineSize(ScriptView view);

    boolean setLine(ScriptView view, int p, String str);

    boolean addLine(ScriptView view, int p, String str);

    boolean removeLine(ScriptView view, int p);

    String getDescript();

    String getName();

    void setName(String newname);

    boolean load();

}
