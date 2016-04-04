package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private TextView status;
    private EditText name;
    private EditText size;
    private EditText sport;
    private EditText description;
    private TextView highestBid;
    private ImageView photo;
    private User user;
    private Boolean boolStatus;
    private String oldID;
    private String newID;
    private int result;
    private byte[] photoStream = new byte[65536];
    private ArrayAdapter<Bid> adapter;
    private ArrayList<Bid> bids = new ArrayList<>();
    private Button acceptButton;
    private Button rejectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);
        // Taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("My Item");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /**
         * Sets the user and item for this activity and grabs the EditText things for use.
         */
        item = ItemController.getItem();
        user = UserController.getUser();
        owner = (EditText) findViewById(R.id.owner);
        status = (TextView) findViewById(R.id.status);
        name = (EditText) findViewById(R.id.name);
        size = (EditText) findViewById(R.id.size);
        sport = (EditText) findViewById(R.id.sport);
        description = (EditText) findViewById(R.id.description);
        photo = (ImageView) findViewById(R.id.pictureView);

        ImageButton saveButton = (ImageButton) findViewById(R.id.save);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete);
        ImageButton myComment = (ImageButton) findViewById(R.id.myItemComment);
        Button bidsOnThisItem = (Button) findViewById(R.id.viewAllBidsButton);

        //The view is updated by asking the user object for its information.
        owner.setText(UserController.getUser().getUsername()); // shouldn't be on this page unless current user = owner
        boolStatus = item.getAvailability();
        if (boolStatus == false) {
            status.setText("Borrowed by "+item.getRenterID());
        } else {
            if (item.getBids().isEmpty()) {
                status.setText("Available");
            } else {
                status.setText("Bidded");
            }
        }
        name.setText(item.getTitle());
        description.setText(item.getDescription());
        size.setText(item.getSize());
        sport.setText(item.getSport());

        bidsOnThisItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyItemActivity.this, BidsForOneItemActivity.class);
                startActivity(intent);
            }
        });

        /*
        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        //Bids is not implemented yet.
        //Removing setBids activities for now.
        //bids = item.getBids();

        //TODO: images are causing problems
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
                if (NetworkUtil.getConnectivityStatus(v.getContext()) == 1) {
                    item = ItemController.getItem();
                    item.setTitle(name.getText().toString());
                    item.setDescription(description.getText().toString());
                    item.setSize(size.getText().toString());
                    //item.setBids(bids);
                    item.setOwnerID(user.getID());
                    //item.setPhoto(photoStream);
                    oldID = item.getID();
                    ItemController.updateItemElasticSearch(item);
                    newID = item.getID();
                    user.removeMyItem(oldID);
                    user.addMyItem(newID);
                    UserController.updateUserElasticSearch(user);
                    Toast.makeText(MyItemActivity.this, "Thing Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MyItemActivity.this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                }
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
                if (NetworkUtil.getConnectivityStatus(v.getContext()) == 1) {
                    if (!item.getBids().getBids().isEmpty()) {
                        Toast.makeText(MyItemActivity.this, "This Item Has Bids!", Toast.LENGTH_SHORT).show();
                    }
                    user.removeMyItem(item.getID());
                    new ElasticSearch.elasticDeleteItem().execute(item);
                    UserController.updateUserElasticSearch(user);
                    ItemList itemList = ItemController.getItemList();
                    itemList.remove(item);
                    ItemController.setItemList(itemList);
                    Toast.makeText(MyItemActivity.this, "Thing Deleted!", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                    //finish() because once the item is deleted, there's nothing to show
                } else {
                    Toast.makeText(v.getContext(), "You are not connected to the internet", Toast.LENGTH_SHORT).show();
                }
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

        myComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for when my comment button is pressed
                // view comments on item
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
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}
