package formatfa.reflectmaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

import view.formatfa.ftexteditor.view.ColorSetting;
//import view.formatfa.ftexteditor.view.FileDataRead;
import view.formatfa.ftexteditor.view.ScriptView;
import view.formatfa.ftexteditor.view.light.Light;
import view.formatfa.ftexteditor.view.light.LightClip;
import view.formatfa.ftexteditor.view.suggest.GeGeSuggests;
import view.formatfa.ftexteditor.view.suggest.SuggestFactory;
import view.formatfa.ftexteditor.view.suggest.Suggests;

public class CodeDialog {

    public interface CodeListener {
        public void onEdit(ScriptView view, String code);
    }

    class GLight implements Light {

        @Override
        public List<LightClip> light(String code) {
            return null;
        }
    }

    private Context context;
    private AlertDialog.Builder ab;


    private ScriptView view;

    CodeListener listener;

    //  FileDataRead read;
    public AlertDialog.Builder getDialog() {
        return ab;
    }

    public CodeDialog(Context context) {
        this.context = context;


        ab = new AlertDialog.Builder(context);
        ab.setTitle("代码编辑");
        view = new ScriptView(context);
        ColorSetting setting = new ColorSetting();
        view.setIsismpleCursor(true);
        try {
            view.setSuggests(SuggestFactory.getSuggest("gege"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.initFont();
        ab.setView(view);
        ab.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //     if(listener!=null)listener.onEdit(view,read.getString(view));
            }
        });
        ab.setNeutralButton("取消", null);
    }

    public String getString() {
//        StringBuilder sb = new StringBuilder()

        return null;
    }

    public void setCode(String code) {
//        read = new FileDataRead(code,true);
//        read.load();
//        view.setDataRead(read);


    }

    public void show() {

        ab.show();

    }
}
