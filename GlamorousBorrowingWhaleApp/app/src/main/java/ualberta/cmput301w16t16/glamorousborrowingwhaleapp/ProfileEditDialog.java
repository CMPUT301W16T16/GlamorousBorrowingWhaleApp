// elephant graveyard of do not touch plz


//package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import static android.support.v4.app.ActivityCompat.startActivityForResult;
//
///**
// * Created by andrew on 12/03/16.
// */
//
//public class ProfileEditDialog extends Dialog implements android.view.View.OnClickListener {
//
//    public Activity activity;
//    private TextView userName;
//    private TextView userPhone;
//    private TextView userEmail;
//    private ImageView userImage;
//    private int result;
//    private User user;
//
//    public ProfileEditDialog(Activity activity, User user) {
//        super(activity);
//        this.activity = activity;
//        this.user = user;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.edit_profile_view);
//
//        userImage = (ImageView) findViewById(R.id.profilePictureView);
//        // more sophisticated checking would be wise, i'm pretty sure with the whale default, this if cannot fail and thus is stupid
//        if (user.getPhoto() != null) {
//            byte[] tempPhoto = user.getPhoto();
//            userImage.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
//        }
//
//
//        userName = (TextView) findViewById(R.id.editProfileName);
//        userPhone = (TextView) findViewById(R.id.editProfilePhone);
//        userEmail = (TextView) findViewById(R.id.editProfileEmail);
//
//        userName.setText(user.getName());
//        userPhone.setText(user.getPhoneNumber());
//        userEmail.setText(user.getEmailAddress());
//
//        Button saveButton = (Button) findViewById(R.id.save_button);
//        saveButton.setOnClickListener(this);
//
//        // this is the gallery selection method for pictures
//        userImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent bringTheGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(bringTheGallery, result);
//            }
//
//        });
//
//    }
//
//    // also pictures
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == result) {
//            if (resultCode == RESULT_OK) {
//                Uri selectedImage = data.getData();
//                userImage.setImageURI(selectedImage);
//            } else {
//                Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.save_button) {
//
//            if (!userName.getText().toString().isEmpty()) {
//                user.setName(userName.getText().toString());
//            }
//            if (!userPhone.getText().toString().isEmpty()) {
//                user.setPhoneNumber(userPhone.getText().toString());
//            }
//            if (!userEmail.getText().toString().isEmpty()) {
//                user.setEmailAddress(userEmail.getText().toString());
//            }
//            activity.finish();
//        }
//    }
//}