package view.formatfa.ftexteditor.view;

public interface SavaListener {

    void onSaveStart(ScriptView view, DataSave save);

    void onSaveDone(ScriptView view, DataSave save);
}
