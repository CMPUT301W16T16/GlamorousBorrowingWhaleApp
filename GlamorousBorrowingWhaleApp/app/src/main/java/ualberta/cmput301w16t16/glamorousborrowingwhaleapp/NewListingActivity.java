package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This activity allows the user to enter all the necessary information about a
 * piece of equipment and save it to their repository of equipment. It also has
 * the option to cancel creating equipment.
 * @author adam, andrew, erin, laura, martina
 */

public class NewListingActivity extends AppCompatActivity {
    private EditText name;
    private EditText sport;
    private EditText size;
    private EditText description;
    private ImageView photo;
    private BidList bids;
    private User user;
    private Item item;
    private double latitude;
    private double longitude;
    private byte[] photoStream = new byte[65536];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create New Listing");
        actionBar.setHomeButtonEnabled(true);

        name = (EditText) findViewById(R.id.name);
        sport = (EditText) findViewById(R.id.sport);
        size = (EditText) findViewById(R.id.size);
        description = (EditText) findViewById(R.id.description);
        photo = (ImageView) findViewById(R.id.pictureView);

        final ImageButton saveButton;
        final ImageButton deleteButton;

        user = UserController.getUser();

        saveButton = (ImageButton) findViewById(R.id.save);
        deleteButton = (ImageButton) findViewById(R.id.delete);

        bids = new BidList();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking that something was inputted in each field
                if (name.getText().toString().isEmpty() ||
                        sport.getText().toString().isEmpty() ||
                        size.getText().toString().isEmpty() ||
                        description.getText().toString().isEmpty() ||
                        photo.toString().isEmpty()) {
                    Toast.makeText(NewListingActivity.this, "Something must be entered in every field.", Toast.LENGTH_SHORT).show();
                } else {
                    // picture management
                    if (photo == null) {
                        photo.setImageResource(R.drawable.glamorouswhale1);
                    }
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
                    item.setOwnerID(user.getID());
                    item.setRenterID("");
                    item.setPhoto(photoStream);
                    item.setOwnerID(user.getID());
                    item.setSport(sport.getText().toString());
                    item.setLatitude(latitude);
                    item.setLongitude(longitude);

                    //setting controller to this item now for fun
                    ItemController.setItem(item);

                    // check whether we have connectivity
                    if (NetworkUtil.getConnectivityStatus(NewListingActivity.this) == 1) {
                        // network is available
                        //Adding the latestItem to the current user's (Controlled by UserController) ItemList

                        ItemController.addItemElasticSearch(item);
                        user.addMyItem(item.getID());

                        // update the user to include the new item in its list
                        UserController.updateUserElasticSearch(user);
                        Toast.makeText(NewListingActivity.this, "New Thing Saved!", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        // network is not available
                        // save the item to user.offlineItems
                        user.addOfflineItem(item);

                        // turn on the receiver to watch for network changes
                        // receiver will add the item when the network is back
                        NetworkUtil.startListeningForNetwork(v.getContext());
                        Toast.makeText(NewListingActivity.this, "Item will be pushed once network is connected", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                final int result = 0;
                startActivityForResult(bringTheGallery, result);
            }

        });
    }

    //setting up the action bar icons
    //taken from http://www.androidhive.info/2013/11/android-working-with-action-bar/
    // Apr3/16
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                goToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHome() {
        Intent i = new Intent(NewListingActivity.this, MyProfileViewActivity.class);
        startActivity(i);
    }

    // this gets the returns from the photo and location Activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    photo.setImageURI(selectedImage);
                } else {
                    Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Location location = data.getParcelableExtra("location");
                    // Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    // latitude = Double.parseDouble(data.getStringExtra("latitude"));
                    // longitude = Double.parseDouble(data.getStringExtra("longitude"));
                } else {
                    Toast.makeText(this, "Could not get location", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        //this also clears on home button press. Issue for sure.
        super.onPause();
        name.setText("");
        size.setText("");
        description.setText("");
    }

    public void launchGetLocation(View view) {
        Intent intent = new Intent(view.getContext(), GetLocationActivity.class);
        final int result = 1;
        startActivityForResult(intent, result);

    }

    public void deletePhoto(View view) {
        photo.setImageResource(R.drawable.glamorouswhale1);
    }

    public void deleteLocation(View view) {
    }
}