package view.formatfa.ftexteditor.view.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import view.formatfa.ftexteditor.view.DataRead;
import view.formatfa.ftexteditor.view.DataSave;
import view.formatfa.ftexteditor.view.ScriptView;

public class FileSave implements DataSave {

    private OutputStream os;

    private File file;

    public FileSave(File file) {
        this.file = file;
    }

    @Override
    public void save(ScriptView view) throws FileNotFoundException {

        os = new FileOutputStream(file);


        DataRead read = view.getDataRead();

        file.renameTo(new File(file.getAbsolutePath() + ".bak"));
        try {
            OutputStream os = new FileOutputStream(file);
            for (int i = 0; i < read.getLineSize(view); i += 1) {
                os.write(read.getLine(view, i).getBytes());
                if (read.getLineSize(view) != 1) {

                    os.write(read.getLineSep(view).getBytes());
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
