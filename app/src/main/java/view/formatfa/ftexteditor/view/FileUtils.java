package view.formatfa.ftexteditor.view;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class FileUtils {


    public static String getPositionFromNoAbc(String s, int p) {
        if (p >= s.length()) return null;
        if (p == 0) return null;
        Stack<Character> i = new Stack<Character>();
        char[] data = s.toCharArray();
        int len = 0;
        for (int j = p; j >= 0; j += 1) {
            if ((data[j] >= 'a' && data[j] <= 'z') || (data[j] >= 'A' && data[j] <= 'Z')) {
                i.push(data[j]);
            } else
                break;
            len += 1;
        }
        char[] result = new char[len];
        len = 0;
        while (!i.empty()) {
            result[len] = i.pop();
        }
        return new String(result);

    }

    public static HashMap<String, String> readTable(String str) {

        HashMap<String, String> result = new HashMap<>();
        String[] lines = str.split("\n");
        for (String s : lines) {
            String[] kv = s.split("=");
            if (kv.length != 2) continue;
            result.put(kv[0], kv[1]);
        }
        return result;
    }

    public static boolean isW(char c)

    {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    //通过换行符分割字符
    public static List<String> splitByLine(String line, String code) {
        List<String> result = new ArrayList<>();
        int start = 0;
        int index = 0;
        System.out.println(line.length());
        while ((start = code.indexOf(line, index)) != -1) {

            if (index != start) {
                result.add(code.substring(index, start));
            } else
                result.add("");


            index = start + line.length();
        }

        if (index != code.length())
            result.add(code.substring(index, code.length()));

        if (code.endsWith(line))
            result.add("");

        return result;

    }

    public static char[] charInsert(char[] input, int position, char[] aim) {

        char[] newbuff = new char[input.length + aim.length];

        System.arraycopy(input, 0, newbuff, 0, position);
        System.arraycopy(aim, 0, newbuff, position, aim.length);

        System.arraycopy(input, position, newbuff, position + aim.length, input.length - position);

        return newbuff;

    }

    public static String readAssets(Context context, String name) throws IOException {

        return readStream(context.getAssets().open(name));

    }

    public static String readFile(String path) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readStream(stream);
    }

    public static String readStream(InputStream stream) {
        try {
            if (stream == null) return null;

            byte[] buff = new byte[stream.available()];
            stream.read(buff);
            stream.close();
            return new String(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
