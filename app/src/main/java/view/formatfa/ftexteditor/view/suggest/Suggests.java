package view.formatfa.ftexteditor.view.suggest;

import java.util.List;

import view.formatfa.ftexteditor.view.ScriptView;

public interface Suggests {

    List<SuggestItem> getSuggests(ScriptView view, String word, String line, int position);


}
