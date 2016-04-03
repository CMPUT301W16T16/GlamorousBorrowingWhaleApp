package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Laura on 4/3/2016
 * This class is for creating and leaving a rating/comments for an item a user has borrowed.
 * @author adam, andrew, erin, laura, martina
 */
public class MakeRatingActivity extends AppCompatActivity{

    private Item item;
    private User ownerUser;

    private TextView name;
    private EditText comment;
    // rating
    private ImageView photo;

    private Button discard;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rating);
        //setTitle("Leave your Review");

        // getting item and the owner
        item = ItemController.getItem();
        ownerUser = UserController.getUser();

        // getting the views
        name = (TextView) findViewById(R.id.theirItemName);
        photo = (ImageView) findViewById(R.id.pictureView);

        // setting the views
        name.setText(item.getTitle());
        // photo.set?

        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }


        // Submit Rating Button
        // Discard Button

        // Add Comment
        // Add Rating



    }

}
