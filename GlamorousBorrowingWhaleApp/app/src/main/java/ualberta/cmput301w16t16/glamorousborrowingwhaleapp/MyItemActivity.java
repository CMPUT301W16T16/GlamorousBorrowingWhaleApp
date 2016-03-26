package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * This activity shows ONE of the current user's available items. It gives the user the
 * chance to edit any of the information associated with the item (size, sport,
 * status etc). This class also allows the user to delete an item if they want. Careful! If
 * deleted, it is gooonneee forever.
 * @author adam, andrew, erin, laura, martina
 */

public class MyItemActivity extends AppCompatActivity {
    private Item item;
    private EditText owner;
    private EditText status;
    private EditText name;
    private EditText size;
    private EditText description;
    private TextView highestBid;
    private ImageView photo;
    private User user;
    private int result;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);
        // Taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("My Item");
        // END

        /**
         * Sets the user and item for this activity and grabs the EditText things for use.
         */
        item = ItemController.getItem();
        user = UserController.getUser();
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
        owner.setText(UserController.getUser().getUsername()); // shouldn't be on this page unless current user = owner
        name.setText(item.getTitle());
        description.setText(item.getDescription());
        size.setText(item.getSize());
        if (item.getHighestBidAmount() > 0) {
            highestBid.setText("$"+String.format("%.2f", item.getHighestBidAmount()));
        } else {
            highestBid.setText("No bids yet. Bummer :(");
        }
        /*
        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        //Bids is not implemented yet.
        //Removing setBids activities for now.
        //bids = item.getBids();

        // picture management
        Bitmap image = ((BitmapDrawable) photo.getDrawable()).getBitmap();
        ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
        photoStream = photosNeedToBeCompressedToThis.toByteArray();
        */
        /**
         * saveButton is onClick and leverages the ItemController for its item.
         * The item's attributes are then set from the EditText boxes.
         */

        //TODO needs error checking and type check etc.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get item from controller
                item = ItemController.getItem();
                item.setTitle(name.getText().toString());
                item.setDescription(description.getText().toString());
                item.setSize(size.getText().toString());
                item.setAvailability(true);
                //item.setBids(bids);
                item.setOwnerID(user.getID());
                //item.setPhoto(photoStream);
                //NO MEAT AND POTATOES HERE
                new ElasticSearch.elasticUpdateItem().execute(item);//I LIED IT'S HERE
                Toast.makeText(MyItemActivity.this, "Thing Saved!", Toast.LENGTH_SHORT).show();
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

        // TODO: this should really pop up an ARE YOU SURE regardless of empty BidList or no.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.getBids().getBids().isEmpty()){
                    Toast.makeText(MyItemActivity.this, "This Item Has Bids!", Toast.LENGTH_SHORT).show();
                }
                user.removeMyItem(item.getID());
                new ElasticSearch.elasticDeleteItem().execute(item);

                // TODO: remove ItemController
//                if(user.getMyItems().size() != 0) {
//                    ItemController.setItem(user.getMyItems().get(0));
//                } else{
//                    ItemController.setEmpty();
//                }

                Toast.makeText(MyItemActivity.this, "Thing Deleted!", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
                //finish() because once the item is deleted, there's nothing to show
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
                if(!item.getBids().getBids().isEmpty()) {
                    item.getBids().getHighestBid().setIsAccepted(true);
                    Toast.makeText(MyItemActivity.this, "Bid Accepted!", Toast.LENGTH_SHORT).show();
                    //TODO: insert code here to delete the other bids and notify users that their bid has been declined.
                    //TODO: insert code here to refresh the view?
                    //The rationale behind deleting all bids is if you don't want to honour the
                    //highest bid, why would you want to select a lower one? Future idea - click bid
                    //to show dates, you may be able to go off of that. Then the next highest bid wil show.
                    //Remember Bid amounts are per hour - maybe have the per hour value and the total
                    //amount calculated by the time requested.
                } else {
                    Toast.makeText(MyItemActivity.this, "No Bids!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.getBids().getBids().isEmpty()) {
                    item.getBids().getHighestBid().setIsAccepted(false);
                    Toast.makeText(MyItemActivity.this, "Bid Rejected!", Toast.LENGTH_SHORT).show();
                    //TODO: insert code here to delete all bids??
                    //TODO: insert code to refresh the view.
                } else {
                    Toast.makeText(MyItemActivity.this, "No Bids!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // also pictures
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == result) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                photo.setImageURI(selectedImage);
            } else {
                Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        item = ItemController.getItem();
    }

    protected void onStop() {
        super.onStop();
        //pretty redundant but whatev
        name.setText("");
        size.setText("");
        description.setText("");
    }
}
