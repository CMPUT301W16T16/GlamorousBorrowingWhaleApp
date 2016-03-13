package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * This activity allows the user to enter all the necessary information about a
 * piece of equipment and save it to their repository of equipment. It also has
 * the option to cancel creating equipment.
 * @author adam, andrew, erin, laura, martina
 */

public class NewListingActivity extends AppCompatActivity {
    private EditText name;
    private EditText owner;
    private EditText status;
    private EditText size;
    private EditText description;
    private ImageView photo;
    private BidList bids;
    private User user;
    private Item item;
    private int result;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.name);
        owner = (EditText) findViewById(R.id.owner);
        status = (EditText) findViewById(R.id.status);
        size = (EditText) findViewById(R.id.size);
        description = (EditText) findViewById(R.id.description);
        photo = (ImageView) findViewById(R.id.pictureView);

        final ImageButton saveButton;
        final ImageButton deleteButton;

        user = UserController.getUser();

        status.setText("Available");
        owner.setText(user.getName());

        saveButton = (ImageButton) findViewById(R.id.save);
        deleteButton = (ImageButton) findViewById(R.id.delete);
        //Setting up the buttons and getting user focus.

        bids = new BidList();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // picture management
                Bitmap image = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
                photoStream = photosNeedToBeCompressedToThis.toByteArray();

                //Gathers all the variables used for EditText to apply to the Item object.
                //toString for compatibility.
                item = new Item();
                item.setTitle(name.getText().toString());
                item.setDescription(description.getText().toString());
                item.setSize(size.getText().toString());
                item.setAvailability(true);
                item.setBids(bids);
                item.setOwner(user);
                item.setPhoto(photoStream);
                //setting controller to this item now for fun
                ItemController.setItem(item);
                //Adding the latestItem to the current user's (Controlled by UserController) RentedItem
                //List. We'll have to sort out some terminology here.
                user.addItemRenting(item);//THE MEAT AND POTATOES RIGHT HERE
                // TODO ES CALL: use the following to add the item to ES
                new ElasticSearch.elasticAddItem().execute(item);

                Toast.makeText(NewListingActivity.this, "New Thing Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO delete shit
                //All this does atm is clear the edittext boxes.
                name.setText("");
                size.setText("");
                description.setText("");
                Toast.makeText(NewListingActivity.this, "View Cleared!", Toast.LENGTH_SHORT).show();
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
    protected void onPause() {
        super.onPause();
        name.setText("");
        size.setText("");
        description.setText("");
    }
}
