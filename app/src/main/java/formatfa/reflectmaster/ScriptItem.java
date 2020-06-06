package formatfa.reflectmaster;

/**
 * Created by formatfa on 18-4-28.
 */

public class ScriptItem {

    private String name;
    private String code;

    private String packagename;

    public ScriptItem(String name, String code, String packagename) {
        this.name = name;
        this.code = code;
        this.packagename = packagename;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public ScriptItem(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
