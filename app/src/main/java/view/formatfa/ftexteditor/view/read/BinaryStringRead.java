package view.formatfa.ftexteditor.view.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import view.formatfa.ftexteditor.view.DataRead;
import view.formatfa.ftexteditor.view.ScriptView;

public class BinaryStringRead implements DataRead {


    private String path;

    private List<String> lines;

    public BinaryStringRead(String path) {
        this.path = path;
    }

    public BinaryStringRead() {
    }


    private BinaryReader reader;

    @Override
    public String getLine(ScriptView view, int position) {
        if (position >= lines.size()) return null;
        view.setCanNewLine(false);
        return lines.get(position);
    }

    @Override
    public String getString(ScriptView view) {
        return null;
    }

    @Override
    public void save(ScriptView view) {

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

        lines.set(p, str);
        return true;
    }

    @Override
    public boolean addLine(ScriptView view, int p, String str) {
        return false;
    }

    @Override
    public boolean removeLine(ScriptView view, int p) {
        return false;
    }

    @Override
    public String getDescript() {
        return "二进制文件字符读取";
    }

    @Override
    public String getName() {
        return "字符读取";
    }

    @Override
    public void setName(String newname) {
        this.path = newname;
    }

    @Override
    public boolean load() {


        try {
            reader = new BinaryReader(new FileInputStream(path));
            reader.read();
            lines = reader.getStrings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
