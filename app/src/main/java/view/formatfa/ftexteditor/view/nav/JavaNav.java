package view.formatfa.ftexteditor.view.nav;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.formatfa.ftexteditor.view.DataRead;
import view.formatfa.ftexteditor.view.ScriptView;

public class JavaNav implements Nav {
    @Override
    public String[] getNavTitle() {
        return new String[]{"String", "Method"};
    }

    @Override
    public List<NavResult> getNav(ScriptView view, List<String> lines, int position) {


        if (position == 0) {
            return loadStrings(view.getDataRead());
        } else if (position == 1) {

            return loadMethodAndField(view.getDataRead());
        }


        return null;
    }


    public List<NavResult> loadMethodAndField(DataRead read) {

        if (read == null) return null;
        List<NavResult> res = new ArrayList<>();

        String[] patterns = {"(public|protected|privte|static)\\s+.*?\\s+.*?\\(.*?\\)",};
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
}
