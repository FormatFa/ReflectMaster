package formatfa.android.f.reflectmaster.Widget;


import android.app.*;
import android.content.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

public class EditTextDialog {

    Context con;
    String title, message;

    OnEditTextListener listener;

    public EditTextDialog(Context con) {
        this.con = con;
        edit = new EditText(con);
        edit.setHint(message);

        dialog = new AlertDialog.Builder(con).setTitle(title).setView(edit).
                setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        if (listener != null) {
                            listener.onEdited(edit.getText().toString());
                        }

                    }
                }).setNegativeButton(android.R.string.cancel, null);

    }

    public void setDialog(AlertDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public AlertDialog.Builder getDialog() {
        return dialog;
    }


    public EditTextDialog setText(String s) {
        edit.setText(s);
        return this;
    }

    public EditTextDialog setListener(OnEditTextListener listener) {
        this.listener = listener;
        return this;
    }

    public OnEditTextListener getListener() {
        return listener;
    }


    EditText edit;
    private AlertDialog.Builder dialog;

    public void show() {
        dialog.show();


    }

    public EditTextDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EditTextDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

}
