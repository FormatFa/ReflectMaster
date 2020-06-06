package view.formatfa.ftexteditor.view.nav;

import java.util.List;

import view.formatfa.ftexteditor.view.ScriptView;

public interface Nav {
    String[] getNavTitle();

    List<NavResult> getNav(ScriptView view, List<String> lines, int position);


}
