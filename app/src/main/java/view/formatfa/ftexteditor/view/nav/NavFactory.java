package view.formatfa.ftexteditor.view.nav;

public class NavFactory {

    public static final String[] navs = {"java", "smali"};


    public static Nav getNav(String name) throws Exception {

        switch (name) {
            case "java":
                return new JavaNav();
            case "smali":
                return new SmaliNav();


        }

        throw new Exception("no such Nav Exception:" + name);
    }


}
