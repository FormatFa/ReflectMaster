package plugin.kpa.scriptparser.Script;

/**
 * Created by formatfa on 18-4-24.
 */

public class InvokeMethodResult {

    private boolean hasMethod=true;
    private Object object;

    public boolean isHasMethod() {
        return hasMethod;
    }

    public void setHasMethod(boolean hasMethod) {
        this.hasMethod = hasMethod;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
