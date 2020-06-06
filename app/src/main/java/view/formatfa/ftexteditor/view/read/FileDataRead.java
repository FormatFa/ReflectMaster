package view.formatfa.ftexteditor.view.read;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import view.formatfa.ftexteditor.view.DataRead;
import view.formatfa.ftexteditor.view.FileUtils;
import view.formatfa.ftexteditor.view.ScriptView;

public class FileDataRead implements DataRead {

    private void log(String tag) {
        Log.d("FileDataRead", tag);

    }

    public interface LoadListener {

        void onstartLoad(FileDataRead read);

        public void loaded(FileDataRead read);
    }

    private String path;

    private String data;
    private List<String> lines;
    private InputStream is;

    boolean isString = false;

    public FileDataRead(String path, boolean isString) {
        this.path = path;
        this.isString = isString;


    }

    public FileDataRead(String path) throws FileNotFoundException {
        this(new FileInputStream(path));
        this.path = path;


    }

    public LoadListener getListener() {
        return listener;
    }

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }

    private LoadListener listener;

    class loadTask extends AsyncTask {


        @Override
        protected void onPreExecute() {
            if (listener != null) {
                listener.onstartLoad(FileDataRead.this);
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            return 0;
        }

        @Override
        protected void onPostExecute(Object o) {

            if (listener != null) {
                listener.loaded(FileDataRead.this);
            }
        }
    }

    public FileDataRead(InputStream is) {
        this.path = path;

        this.is = is;


    }


    public String getFile() {
        return path;
    }

    public void start() {
        // new loadTask().execute(is);
    }

    @Override
    public String getLine(ScriptView view, int position) {
        if (position >= lines.size()) return null;
        if (position < 0) return null;
        return lines.get(position);
    }

    @Override
    public String getString(ScriptView view) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); i += 1) {

            builder.append(lines.get(i));
            if (lines.size() != 1 && i != lines.size() - 1) builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void save(ScriptView view) {


        File file = new File(path);

        file.renameTo(new File(file.getAbsolutePath() + ".bak"));
        try {
            OutputStream os = new FileOutputStream(file);
            for (int i = 0; i < lines.size(); i += 1) {
                os.write(getLine(view, i).getBytes());
                if (lines.size() != 1 && lines.size() - 1 != i) {

                    os.write(getLineSep(view).getBytes());
                }


            }
            log("Save line size:" + lines.size());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLineSep(ScriptView view) {
        return "\n";
    }

    @Override
    public int getLineSize(ScriptView view) {
        return lines.size();
    }

    @Override
    public boolean setLine(ScriptView view, int p, String str) {
        if (p >= lines.size()) return false;
        lines.set(p, str);
        return true;
    }

    @Override
    public boolean addLine(ScriptView view, int p, String str) {
        lines.add(p, str);
        return false;
    }

    @Override
    public boolean removeLine(ScriptView view, int p) {
        lines.remove(p);
        return false;
    }

    @Override
    public String getDescript() {
        return "文件，流，字符串读取";
    }

    @Override
    public String getName() {
        return path;
    }

    @Override
    public void setName(String newname) {
        this.path = newname;
    }

    @Override
    public boolean load() {
        if (isString) {
            lines = new ArrayList<>();
            for (String s : path.split(getLineSep(null)))
                lines.add(s);
        } else {
            data = FileUtils.readStream(is);

            lines = FileUtils.splitByLine(getLineSep(null), data);
        }
        log("load line size:" + lines.size());
        return true;
    }
}
