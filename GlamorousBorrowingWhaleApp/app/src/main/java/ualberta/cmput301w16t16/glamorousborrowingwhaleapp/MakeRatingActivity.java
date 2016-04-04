package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private RatingBar rating;
    private ImageView photo;


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
        rating = (RatingBar) findViewById(R.id.theirRating);

        // setting the views
        name.setText(item.getTitle());
        // photo.set?

        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        ImageButton saveRatingButton = (ImageButton) findViewById(R.id.saveRating);

        // TODO: make this actually update the rating objects for the item
        // Submit Rating Button (save) Submits the rating (comment and star)
        saveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for when save button is pressed

                // Use code below to save comment text
                //item.set________(description.getText().toString());

                rating.setOnRatingBarChangeListener(
                        new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                // set item rating to (String.valueOf(rating));
                            }
                        }
                );

            }
        });




    }

}
