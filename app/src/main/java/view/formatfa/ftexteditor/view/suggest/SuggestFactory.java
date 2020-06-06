package view.formatfa.ftexteditor.view.suggest;

public class SuggestFactory {

    public static final String suggests[] = new String[]{"gege", "smali", "gegeqq"};

    public static Suggests getSuggest(String name) throws Exception {
        switch (name) {

            case "gege":
                return new GeGeSuggests();
            case "smali":
                return null;
            case "gegeqq":
                return new ggqqSuggests();
            default:
                throw new Exception("no such suggestss exception");


        }
    }


}
