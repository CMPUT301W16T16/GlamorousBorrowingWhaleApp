package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
    private User owner;

    public ProfileDialog(Activity a) {
        super(a);
        this.activity = a;
        this.owner = UserController.getSecondaryUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_profile);
        setTitle("Owner:");
        User user = owner;

        //TODO: images are causing problems
        /*
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        if (user.getPhoto() != null) {
            byte[] tempPhoto = user.getPhoto();
            userImage.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }
        */

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userEmail = (TextView) findViewById(R.id.userEmail);
        TextView userPhoneNumber = (TextView) findViewById(R.id.userPhoneNumber);

        userName.setText(user.getUsername());
        userEmail.setText(user.getEmailAddress());
        userPhoneNumber.setText(user.getPhoneNumber());

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