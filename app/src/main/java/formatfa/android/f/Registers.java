package formatfa.android.f;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.widget.Button;

import android.util.Log;

public class Registers {
    public static Activity nowAct;

    //旋转
    public static boolean rotate;

    public static int windowSize;
    public static boolean isUseWindowSearch, isFloating = true, newThread = false;
    public static List<Object> objects = new ArrayList<Object>();
    public static List<Object> serviceobjects = new ArrayList<Object>();


    public static void add(Context context, Object obj) {
        if (objects.size() > 50) return;
        if (objects.contains(obj)) {
            return;
        }
        for (Object o : objects) {
            if (o == null) objects.remove(o);
        }
        int p = objects.size();

        objects.add(obj);
        if (context != null)
            Toast.makeText(context, "添加到临时存储器v" + p + "成功！", Toast.LENGTH_SHORT).show();
    }

    public static void addService(Context context, Object obj) {

        for (Object o : serviceobjects) {
            if (o == null) serviceobjects.remove(o);
        }
        int p = serviceobjects.size();

        serviceobjects.add(obj);
        if (context != null)
            Toast.makeText(context, "添加一个新启动的Service到v" + p + "成功！", Toast.LENGTH_SHORT).show();
    }


    public static String getObjectString(Object obj) {

        String result = null;

        if (obj != null) {
            String clz = obj.getClass().getCanonicalName();

            if (clz != null)
                switch (clz) {
                    case "java.lang.String":
                        result = (String) obj;
                        break;
                    case "java.lang.Boolean":
                        result = String.valueOf((Boolean) obj);
                        break;
                    case "java.lang.Integer":
                        result = String.valueOf((Integer) obj);
                        break;
                    case "java.lang.Long":
                        result = String.valueOf((Long) obj);
                        break;
                    case "java.lang.Short":
                        result = String.valueOf((Short) obj);
                        break;
                    case "java.lang.Character":

                        result = String.valueOf((Character) obj);
                        break;
                    case "byte":
                        result = String.valueOf((Byte) obj);

                        break;

                    default:
                        if (obj instanceof TextView) {
                            result = ((TextView) obj).getText().toString();
                        } else if (obj instanceof Button) {
                            result = ((Button) obj).getText().toString();
                        } else if (obj instanceof EditText) {
                            result = ((EditText) obj).getText().toString();
                        } else if (obj instanceof ArrayList) {
                            ArrayList li = (ArrayList) obj;
                            result = "" + li.size();
                            if (li.size() > 0) result += "," + li.get(0);
                        } else if (obj instanceof List) {
                            List li = (List) obj;
                            result = "" + li.size();
                            if (li.size() > 0) result += "," + li.get(0);
                        }
                }

        } else
            result = "null";
        if (result == null) return "";
        return result;
    }


    //将基本类型的字符转换为对象,寄存器的值 startwith $Fnumber ,like $F0
    public static Object baseTypeParse(String conciaType, String value) {
        Object result = null;
        if (value.startsWith("$F")) {

            result = objects.get(Integer.parseInt(value.substring(2)));

            return result;
        }
        switch (conciaType) {
            case "int":
                result = Integer.parseInt(value);
                break;
            case "boolean":
                if ("true".equals(value))
                    result = true;
                else
                    result = false;
                break;
            case "long":
                result = Long.parseLong(value);
                break;
            case "byte":
                result = Byte.parseByte(value);
                break;


            default:
                result = value;


        }
        return result;
    }

    public static Class parseClass(String clz, ClassLoader loader) throws ClassNotFoundException {
        Class result;
        switch (clz) {

            case "int":
                result = int.class;
                break;
            case "boolean":
                result = boolean.class;
                break;
            case "long":
                result = long.class;
                break;
            case "short":
                result = boolean.class;
                break;
            case "char":
                result = char.class;
                break;
            case "byte":
                result = byte.class;
                break;
            default:
                result = loader.loadClass(clz);
        }
        return result;
    }


    public static boolean isBaseArray(String cancio) {


        return "int[]".equals(cancio) || "byte[]".equals(cancio) || "short[]".equals(cancio) || "long[]".equals(cancio) || "char[]".equals(cancio);
    }
}
