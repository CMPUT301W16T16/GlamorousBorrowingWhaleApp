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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageView photo;
    private User user;
    private BidList bids;
    private int result;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);

        //This chunk sets the user and item for this activity
        //and grabs the EditText things for use.
        item = ItemController.getItem();
        user = UserController.getUser();
        owner = (EditText) findViewById(R.id.owner);
        status = (EditText) findViewById(R.id.status);
        name = (EditText) findViewById(R.id.name);
        size = (EditText) findViewById(R.id.size);
        description = (EditText) findViewById(R.id.description);
        photo = (ImageView) findViewById(R.id.pictureView);

        //Initialize the Buttons!
        ImageButton saveButton = (ImageButton) findViewById(R.id.save);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete);

        //The view is updated by asking the user object for its information.
        status.setText(Boolean.toString(item.getAvailability()));
        owner.setText(item.getOwner().getName());
        name.setText(item.getTitle());
        description.setText(item.getDescription());
        size.setText(item.getSize());

        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            photo.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }
        //Bids is not implemented yet.
        bids = item.getBids();

        // picture management
        Bitmap image = ((BitmapDrawable) photo.getDrawable()).getBitmap();
        ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
        photoStream = photosNeedToBeCompressedToThis.toByteArray();

        //saveButton is onClick and leverages the ItemController for its item.
        //The item's attributes are then set from the EditText boxes.
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
                item.setBids(bids);
                item.setOwner(user);
                item.setPhoto(photoStream);
                //NO MEAT AND POTATOES HERE
                Toast.makeText(MyItemActivity.this, "Thing Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        //deleteButton deletes the item in the current view. (Recall the ItemController has the
        //item object).
        //Involves legit removing the item from the user's itemsRenting list. This can have a negative
        //impact as it may set the list to null (or worse) - to combat this we control it.
        //If an action will set the list to null, we leverage the ItemController and set that to
        //null, essentially "bypassing" the null list and it's many issues. Since the ItemController
        //is in a "controlled" null state, code may be implemented elsewhere that can handle this
        //simple condition.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.removeItemRenting(item);
                Toast.makeText(MyItemActivity.this, Integer.toString(user.getItemsRenting().getItemList().size()), Toast.LENGTH_SHORT).show();
                //below is pretty flaky, needs error check for no items in list
                if(user.getItemsRenting().getItemList().size() != 0) {
                    ItemController.setItem(user.getItemsRenting().getItemList().get(0));
                } else{
                    ItemController.setEmpty();
                }
                //recommend implementing some sort of undo mechanism
                Toast.makeText(MyItemActivity.this, "Thing Deleted!", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
                //finish() because once the item is deleted, the view has nothing to go on
                //and really why would the user want to stare at a blank page they can just push
                //the plus button on the myitems page.
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
