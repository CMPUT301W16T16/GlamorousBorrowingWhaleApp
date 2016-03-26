package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Martina on 16-03-25.
 */
public class MakeBidPopup extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button bidButton;
    private User owner;
    private Item item;

    public MakeBidPopup(Activity a, User owner) {
        super(a);
        this.activity = a;
        this.owner = UserController.getSecondaryUser();
        this.item = ItemController.getItem();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_profile);
        User user = owner;

        //TODO: images are causing problems
        /*
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        if (user.getPhoto() != null) {
            byte[] tempPhoto = user.getPhoto();
            userImage.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }
        */

        EditText dollarsPerHour = (EditText) findViewById(R.id.dollarsPerHour);
        EditText userEmail = (EditText) findViewById(R.id.numberOfHours);

        bidButton = (Button) findViewById(R.id.bid_button);
        bidButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bid_button) {
            //TODO: make a bid on the item, needs error checking and type check etc.
            activity.finish();
        }
    }
}
