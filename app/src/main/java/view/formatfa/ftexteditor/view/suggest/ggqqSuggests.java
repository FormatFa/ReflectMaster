package view.formatfa.ftexteditor.view.suggest;

import java.util.ArrayList;
import java.util.List;

import view.formatfa.ftexteditor.view.ScriptView;

public class ggqqSuggests implements Suggests {


    private SuggestItem[] methods = {new SuggestItem("getCookie", "getCookies(String name)"),
            new SuggestItem("post", "post(String url,String jsonParam)")
    };

    @Override
    public List<SuggestItem> getSuggests(ScriptView view, String word, String line, int position) {
        if (word.isEmpty()) return null;
        List<SuggestItem> result = new ArrayList<>();
        if (word.startsWith("gege."))
            for (SuggestItem item : methods) {

                result.add(item);


            }

        return result;

    }
}
