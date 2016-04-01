package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * This class is used to display someone elses entry and the owner information
 * when a borrower selects an item in the list of SearchResultsActivity.
 * @author adam, andrew, erin, laura, martina
 * @see SearchResultsActivity
 */

public class TheirItemActivity extends AppCompatActivity {

    private Item item;
    private User ownerUser;

    private TextView name;
    private TextView owner;
    private TextView status;
    private TextView size;
    private TextView description;
    private TextView sport;
    private ImageView photo;
    private Button makeBid;
    private Button itemOwner;

    private int result;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_their_item);
        setTitle("Their Item");

        // getting item and the owner
        item = ItemController.getItem();
        ownerUser = UserController.getUser();

        // getting the TextViews
        name = (TextView) findViewById(R.id.theirItemName);
        owner = (TextView) findViewById(R.id.theirItemOwner);
        status = (TextView) findViewById(R.id.theirItemStatus);
        size = (TextView) findViewById(R.id.theirItemSize);
        sport = (TextView) findViewById(R.id.theirItemSport);
        description = (TextView) findViewById(R.id.theirItemDescription);
        photo = (ImageView) findViewById(R.id.pictureView);
        makeBid = (Button) findViewById(R.id.theirItemMakeBid);
        itemOwner = (Button) findViewById(R.id.theirItemOwner);

        // setting the TextViews
        name.setText(item.getTitle());
        owner.setText(item.getOwnerID());
        status.setText(item.printAvailability());
        size.setText(item.getSize());
        sport.setText(item.getSport());
        description.setText(item.getDescription());

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


        makeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TheirItemActivity.this, MakeBidActivity.class);
                startActivity(intent);
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

        itemOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TheirItemActivity.this, TheirProfileViewActivity.class);
                startActivity(intent);

            }
        });

    }
}