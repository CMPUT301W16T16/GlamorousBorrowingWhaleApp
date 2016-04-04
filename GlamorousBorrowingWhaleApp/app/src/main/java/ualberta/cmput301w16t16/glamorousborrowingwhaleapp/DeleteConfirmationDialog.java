package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by andrew on 31/03/16.
 */
public class DeleteConfirmationDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button denyButton;
    public Button confirmButton;

    public DeleteConfirmationDialog(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm_delete);

        denyButton = (Button) findViewById(R.id.confirm_button);
        denyButton.setOnClickListener(this);

        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm_button) {
            activity.finish();
        }
    }
}
