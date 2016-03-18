package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andrew on 12/03/16.
 */
public class ProfileDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button confirmButton;
    private Item itemThatBroughtUsHere;

    public ProfileDialog(Activity a, Item item) {
        super(a);
        this.activity = a;
        this.itemThatBroughtUsHere = item; //this part should be handled by the ItemController
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_profile);
        User user;

        // this should be removed by final
        if (itemThatBroughtUsHere != null) {
            user = itemThatBroughtUsHere.getOwner();
        } else {
            user = UserController.getUser();
        }
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        if (user.getPhoto() != null) {
            byte[] tempPhoto = user.getPhoto();
            userImage.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userEmail = (TextView) findViewById(R.id.userEmail);

        userName.setText(user.getName());
        userEmail.setText(user.getEmailAddress());

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