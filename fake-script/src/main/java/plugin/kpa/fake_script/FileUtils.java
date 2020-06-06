package plugin.kpa.fake_script;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtils {

    public static String readFile(String path)
    {
        try {
            InputStream stream = new FileInputStream(path);
            byte[] buff = new byte[stream.available()];
            int size = stream.read(buff);
            return new String(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
