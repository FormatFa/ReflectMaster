package formatfa.android.f.reflectmaster.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    //	public static  int getid(String str)
//	{
//		int result = 0;
//		for(char c:str.toCharArray())
//		{
//			int i = c;
//			result+=i;
//
//
//
//		}
//		return result*2;
//	}
    public static int get(String str) {
        int result = 0;
        for (int c : str.toCharArray()) {
            int i = c;
            result += i;


        }
        return result * 2;
    }

    public static void installAssetsApk(Context c, String name) {

        AssetManager am = c.getAssets();
        InputStream is = null;


        try {
            is = am.open(name);
        } catch (IOException e) {
            return;
        }

        byte[] buff = null;

        try {
            buff = new byte[is.available()];
        } catch (IOException e) {
        }

        try {
            is.read(buff);
        } catch (IOException e) {
        }


        FileOutputStream fos = null;
        try {
            fos = c.openFileOutput(name, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
        } catch (FileNotFoundException e) {
        }
        try {
            fos.write(buff);
        } catch (IOException e) {
        }

        try {
            fos.flush();
        } catch (IOException e) {
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri u = Uri.fromFile(c.getFileStreamPath(name));

        i.setDataAndType(u, "application/vnd.android.package-archive");
        c.startActivity(i);

    }


    public static String sdtoString(String path) {


        File f = new File(path);

        InputStream is = null;

        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            return null;
        }


        byte[] buff;
        try {
            buff = new byte[is.available()];
        } catch (IOException e) {
            return null;
        }

        try {
            is.read(buff);
        } catch (IOException e) {
            return null;
        }

        try {
            is.close();
        } catch (IOException e) {
            return null;
        }

        return new String(buff);
    }

    public static Boolean stringtosd(String str, String path) {
        File f = new File(path);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            return false;
        }
        try {
            os.write(str.getBytes());
        } catch (IOException e) {
            return false;
        }
        try {
            os.close();
        } catch (IOException e) {

            return false;
        }
        return true;
    }

    public static String download(String url) {

        String result = null;


        HttpURLConnection connection;

        URL Url = null;


        try {

            Url = new URL(url);
            connection = (HttpURLConnection) Url.openConnection();

        } catch (Exception e) {
            return null;
        }

        try {
            InputStream is = connection.getInputStream();

            byte[] buff = getBytesByInputStream(is);
            result = new String(buff);
            is.close();

        } catch (IOException e) {
        }


        return result;
    }

    public static String donwloadFile(String url, String out) {

        HttpURLConnection connection;

        URL Url = null;


        try {

            Url = new URL(url);
            connection = (HttpURLConnection) Url.openConnection();

        } catch (Exception e) {
            return e.toString();
        }

        try {
            InputStream is = connection.getInputStream();

            OutputStream os = new FileOutputStream(out);
            writeStream(is, os);

        } catch (IOException e) {
            return e.toString();
        }


        return null;


    }

    public static boolean writeStream(InputStream is, OutputStream os) throws IOException {
        if (is == null) return false;
        byte[] buff = new byte[1024 * 4];

        int leng = 0;

        while ((leng = is.read(buff)) != -1) {
            os.write(buff, 0, leng);

        }
        os.flush();
        os.close();

        return true;
    }

    public static byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public static void Fdialog(Context c, Object title, Object msg, Object button1, Object button2, DialogInterface.OnClickListener listener1, DialogInterface.OnClickListener listener2) {
        String t, m = null, b1, b2;
        t = title instanceof String ? (String) title : (c.getString((int) title));
        if (msg != null)
            m = msg instanceof String ? (String) msg : (c.getString((Integer) msg));
        b1 = button1 instanceof String ? (String) button1 : (c.getString((Integer) button1));

        b2 = button1 instanceof String ? (String) button2 : (c.getString((Integer) button2));


        Fdialog(c, t, m, b1, b2, listener1, listener2);

    }

}
