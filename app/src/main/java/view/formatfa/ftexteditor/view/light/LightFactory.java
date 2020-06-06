package view.formatfa.ftexteditor.view.light;

public class LightFactory {

    public static final String lights[] = {"gege", "java", "xml", "smali"};

    public static Light getLight(String name) throws Exception {
        switch (name) {
            case "java":
                return new JavaLight();
            case "xml":
                return new XmlLight();
            case "smali":
                return new SmaliLight();
            case "gege":
                return new GeGeLight();
            default:
                throw new Exception("no such light");

        }


    }
}
