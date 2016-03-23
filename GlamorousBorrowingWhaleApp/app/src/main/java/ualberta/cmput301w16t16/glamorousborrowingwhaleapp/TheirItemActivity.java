package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to display someone elses entry and the owner information
 * when a borrower selects an item in the list of SearchResultsActivity.
 * @author adam, andrew, erin, laura, martina
 * @see SearchResultsActivity
 */

public class TheirItemActivity extends AppCompatActivity {

    private Item item;
    private EditText owner;
    private EditText status;
    private EditText name;
    private EditText size;
    private EditText description;
    private TextView highestBid;
    private ImageView photo;
    private String userID;
    private int result;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);
        setTitle("Their Item");

        /**
         * Sets the user and item for this activity and grabs the EditText things for use.
         */
        item = ItemController.getItem();
        userID = ItemController.getItem().getOwnerID();
        owner = (EditText) findViewById(R.id.owner);
        status = (EditText) findViewById(R.id.status);
        name = (EditText) findViewById(R.id.name);
        size = (EditText) findViewById(R.id.size);
        description = (EditText) findViewById(R.id.description);
        highestBid = (TextView) findViewById(R.id.highestBid);
        photo = (ImageView) findViewById(R.id.pictureView);

        //Initialize the Buttons!
        ImageButton saveButton = (ImageButton) findViewById(R.id.save);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete);
        ImageButton acceptBidButton = (ImageButton) findViewById(R.id.acceptBid);
        ImageButton rejectBidButton = (ImageButton) findViewById(R.id.rejectBid);

        //The view is updated by asking the user object for its information.
        status.setText(Boolean.toString(item.getAvailability()));
        if (item.getOwnerID() == null) {
            owner.setText("Oops!");
        } else {
            owner.setText(item.getOwnerID());
        }
        name.setText(item.getTitle());
        description.setText(item.getDescription());
        size.setText(item.getSize());
        if (item.getBids() != null) {
            if (item.getHighestBidAmount() > 0) {
                highestBid.setText(Double.toString(item.getHighestBidAmount()));
            } else {
                highestBid.setText("No bids yet.");
            }
        } else {
            highestBid.setText("Oops!");
        }


        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        //Bids is not implemented yet.
        //Removing setBids activities for now.
        //bids = item.getBids();

        // picture management
        /*Bitmap image = ((BitmapDrawable) photo.getDrawable()).getBitmap();
        ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
        photoStream = photosNeedToBeCompressedToThis.toByteArray();*/


        /**
         * saveButton is onClick and leverages the ItemController for its item.
         * The item's attributes are then set from the EditText boxes.
         */

        //TODO needs error checking and type check etc.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TheirItemActivity.this, "This isn't your item!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * deleteButton deletes the item in the current view. (Recall the ItemController has the
         * item object).
         * Involves legit removing the item from the user's itemsRenting list. This can have a negative
         * impact as it may set the list to null (or worse) - to combat this we control it.
         * If an action will set the list to null, we leverage the ItemController and set that to
         * null, essentially "bypassing" the null list and it's many issues. Since the ItemController
         * is in a "controlled" null state, code may be implemented elsewhere that can handle this
         * simple condition.
         */

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TheirItemActivity.this, "This isn't your Item!", Toast.LENGTH_SHORT).show();
            }
        });

        // this is the gallery selection method for pictures
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bringTheGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(bringTheGallery, result);
            }

        });

        acceptBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TheirItemActivity.this, "No Bid Adding yet. Stay tuned!", Toast.LENGTH_SHORT).show();
            }
        });

        rejectBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}