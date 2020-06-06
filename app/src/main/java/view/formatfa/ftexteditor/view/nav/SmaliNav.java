package view.formatfa.ftexteditor.view.nav;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.formatfa.ftexteditor.view.DataRead;
import view.formatfa.ftexteditor.view.ScriptView;

public class SmaliNav implements Nav {
    @Override
    public String[] getNavTitle() {
        return new String[]{"字符", "函数"};
    }

    @Override
    public List<NavResult> getNav(ScriptView view, List<String> lines, int position) {

        if (position == 0)
            return loadStrings(view.getDataRead());
        else if (position == 1) {
            return loadMethod(view.getDataRead());
        }
        return null;
    }

    public List<NavResult> loadStrings(DataRead read) {

        if (read == null) return null;
        List<NavResult> res = new ArrayList<>();

        String[] patterns = {"\"(.*?)\"",};
        for (String pa : patterns) {
            Pattern pattern = Pattern.compile(pa);


            for (int i = 0; i < read.getLineSize(null); i += 1) {

                Matcher matcher = pattern.matcher(read.getLine(null, i));
                if (matcher.find()) {
                    res.add(new NavResult(matcher.group(), i, matcher.start(), matcher.end() - matcher.start()));
                }

            }


        }


        return res;

    }

    private Pattern methodPattern = Pattern.compile("\\.method\\s(public|protected|private)\\s.*?\\(.*?\\).*?");

    private List<NavResult> loadMethod(DataRead read) {


        if (read == null) return null;
        List<NavResult> res = new ArrayList<>();


        for (int i = 0; i < read.getLineSize(null); i += 1) {

            String s = read.getLine(null, i);
            if (s.startsWith(".method")) {
                res.add(new NavResult(s, i, 0, s.length()));
            }


        }


        return res;

    }
}
