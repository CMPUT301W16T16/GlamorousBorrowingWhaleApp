package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This activity allows the user to create a new account with a name, phone
 * number email and username that they can use to access their account again in
 * the future. Once they click the done button, their information is saved and
 * they're taken to MyProfileViewActivity
 * @author adam, andrew, erin, laura, martina
 * @see MyProfileViewActivity
 */

//TODO review redundant code studio is complaining about
public class SignUpActivity extends AppCompatActivity implements Serializable {

    private ImageButton enteredPicture;
   private EditText enteredUsername;
    private EditText enteredPhoneNumber;
    private EditText enteredEmailAddress;
    private Button doneButton;
    //private Bitmap profilePicture;
    private int result;
    private byte[] photoStream = new byte[65536];

    private String username;
    private String phoneNumber;
    private String emailAddress;

    private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Intent intent = getIntent();
        enteredUsername = (EditText) findViewById(R.id.username);
        enteredPhoneNumber = (EditText) findViewById(R.id.phone);
        enteredEmailAddress = (EditText) findViewById(R.id.email);
        enteredPicture = (ImageButton) findViewById(R.id.picture);
        //profilePicture = BitmapFactory.decodeResource(getResources(), R.drawable.glamorouswhale1);//temp placeholder

        doneButton = (Button) findViewById(R.id.done_sign_up);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertInputToString();

                // prevents user from leaving any fields empty
                if (username.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Something must be entered in every field.", Toast.LENGTH_SHORT).show();
                } else {

                    ///////////////////////////////////////////////////////////////////////////////// A CONVERSATION ABOUT BITMAP METHODS
                    // need to include picture
                    // [FromAdamMar9 - I suggest using an adapter here to pick photo from gallery,
                    // then pass to here and update USER with the new image, which the view control
                    // will refresh to display the image.]
                    // taken Feb-29-2016 from http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
                    //
                    // pictures work, via a method largely derived from this guy: https://www.youtube.com/playlist?list=PLe60o7ed8E-Q7tqKNPnWFdUoeniqH_-A9
                    // with this compress to bytestream method, rendering the picture anywhere requires a few lines of work, as seen in MyProfileViewActivity
                    // buuuuuuuut, you can upload those bytestreams over elastic search, or so the internet tells me - andrew, mar 11
                    // if this method is made satisfactory, the placeholder image "profilePicture" above can be removed. for now, the two relevant lines are commented out.

                    Bitmap image = ((BitmapDrawable) enteredPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
                    photoStream = photosNeedToBeCompressedToThis.toByteArray();
                    // end picture management

                    User latestUser = new User(username, emailAddress, phoneNumber);
                    latestUser.setItemsBorrowing(null);
                    latestUser.setItemsRenting(null);
                    latestUser.setPhoto(photoStream);
                    users.add(latestUser);
                    UserController.setUser(latestUser);

                    users.add(latestUser);

                    //AsyncTask<User, Void, Void> execute = new ElasticSearch.AddUserTask();
                    //execute.execute(latestUser);

                    Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
                    //intent.putExtra("NEW_USER", latestUser);
                    setResult(Activity.RESULT_OK, intent);
                    //Toast.makeText(SignUpActivity.this, "New User Created!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // this is the gallery selection method for pictures
        enteredPicture.setOnClickListener(new View.OnClickListener() {
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
                enteredPicture.setImageURI(selectedImage);
            } else {
                Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Converts the input given in the activity into strings that can then be
     * added to the User class.
     * @author martina I think?
     */
    public void convertInputToString() {
        username = enteredUsername.getText().toString();
        phoneNumber = enteredPhoneNumber.getText().toString();
        emailAddress = enteredEmailAddress.getText().toString();
    }
}
