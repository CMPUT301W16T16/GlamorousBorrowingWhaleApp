package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private EditText enteredPassword;
    private EditText enteredPhoneNumber;
    private EditText enteredEmailAddress;
    private int result;
    private byte[] photoStream = new byte[65536];

    private String username;
    private String password;
    private String phoneNumber;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Intent intent = getIntent();
        enteredUsername = (EditText) findViewById(R.id.username);
        enteredPassword = (EditText) findViewById(R.id.password);
        enteredPhoneNumber = (EditText) findViewById(R.id.phone);
        enteredEmailAddress = (EditText) findViewById(R.id.email);
        enteredPicture = (ImageButton) findViewById(R.id.picture);

        Button doneButton = (Button) findViewById(R.id.done_sign_up);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertInputToString();

                // prevents user from leaving any fields empty
                if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Something must be entered in every field.", Toast.LENGTH_SHORT).show();
                } else {

                    // picture management
                    Bitmap image = ((BitmapDrawable) enteredPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream photosNeedToBeCompressedToThis = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, photosNeedToBeCompressedToThis);
                    photoStream = photosNeedToBeCompressedToThis.toByteArray();

                    User latestUser = new User(username, password, emailAddress, phoneNumber);
                    latestUser.setPhoto(photoStream);
                    UserController.setUser(latestUser);

                    setResult(RESULT_OK);

                    new ElasticSearch.elasticAddUser().execute(latestUser);

                    Intent intent = new Intent(view.getContext(), MyProfileViewActivity.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(SignUpActivity.this, "New User Created!", Toast.LENGTH_SHORT).show();
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
     *
     * @author Martina
     */
    public void convertInputToString() {
        username = enteredUsername.getText().toString();
        password = enteredPassword.getText().toString();
        phoneNumber = enteredPhoneNumber.getText().toString();
        emailAddress = enteredEmailAddress.getText().toString();
    }
}
