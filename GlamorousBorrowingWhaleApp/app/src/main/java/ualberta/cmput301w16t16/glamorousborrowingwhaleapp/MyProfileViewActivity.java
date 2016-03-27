package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity lists for the user their profile information which can then be
 * edited (using longClick) as well as the options to view their items
 * (MyItemsActivity), view items of theirs with bids on them (MyBidsActivity)
 * and search for things to borrow (SearchResultsActivity).
 * @author adam, andrew, erin, laura, martina
 */

public class MyProfileViewActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView profilePhone;
    private TextView profileEmail;
    private ImageView profilePictureView;

    private User user = UserController.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        setTitle("Your Profile");


        //This chunk grabs the TextViews and operates on them
        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePictureView = (ImageView) findViewById(R.id.pictureView);

        profileName.setText(user.getUsername());
        profilePhone.setText(user.getPhoneNumber());
        profileEmail.setText(user.getEmailAddress());

        if (user.getPhoto() != null) {
            byte[] tempPhoto = user.getPhoto();
            profilePictureView.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        //Initialize the buttons.
        Button buttonMyBids = (Button) findViewById(R.id.buttonMyBids);
        Button buttonMyStuff = (Button) findViewById(R.id.buttonMyStuff);
        Button buttonSearch = (Button) findViewById(R.id.buttonBorrowSearch);
        Button buttonIncomingBids = (Button) findViewById(R.id.buttonIncomingBids);
        Button buttonMyBorrowing = (Button) findViewById(R.id.buttonMyBorrowing);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);

        //Setting the longClickListeners for the text boxes. If a user wishes to edit something,
        //a long click on the box will send to longClick() which brings up a popup window!
        profileName.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //doing the same thing no matter which TextView the user long clicks
                        longClick();
                        return false;
                    }
                }
        );

        profilePhone.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //doing the same thing no matter which TextView the user long clicks
                        longClick();
                        return false;
                    }
                }
        );

        profileEmail.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //doing the same thing no matter which TextView the user long clicks
                        longClick();
                        return false;
                    }
                }
        );

        profilePictureView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longClick();
                        //TODO implement the photo chooser in the AlertDialog
                        return false;
                    }
                }
        );

        //The button clickListeners. Each sends to its corresponding activity.
        buttonMyBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyBidsActivity.class);
                startActivity(intent);
            }
        });

        buttonMyStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyItemsActivity.class);
                startActivity(intent);
                setResult(Activity.RESULT_OK, intent);
            }
        });

        //The button clickListeners. Each sends to its corresponding activity.
        buttonIncomingBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IncomingBidsActivity.class);
                startActivity(intent);
            }
        });

        buttonMyBorrowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyBorrowedItemsActivity.class);
                startActivity(intent);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchResultsActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear all of the current items in the controllers
                UserController.setUser(null);
                UserController.setSecondaryUser(null);
                ItemController.setItem(null);
                ItemController.setItemList(null);

                // set logged in as false so sign in activity will not automatically sign the user in
                UserController.setLoggedIn(v.getContext(), false);

                // go back to the sign in activity
                Intent intent = new Intent(MyProfileViewActivity.this, SignInActivity.class);

                // make sure the user cannot press the back button to go back into another activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }


    /**
     * longClick() is used in the longClickListeners for the textviews for editing profile information.
     * A layout is inflated in an alertdialog box which allows the user to edit the name, phone
     * number and email address fields.  If the CANCEL button is selected, no changes are made
     * and the dialog box is closed. If the OK button is selected with empty fields, only the fields
     * with content in them will update their corresponding attribute; the empty fields will not do
     * anything.
     * @author martina
     * @return
     */

    // taken Feb-29-2016 from http://stackoverflow.com/questions/19079265/onlongclick-textview-to-edit
    public boolean longClick() {
        //LayoutInflater has an issue with large size bitmap in xml for alertdialog, has
        //memory leak in alertdialog and outofmemory
        //http://stackoverflow.com/questions/7083441/android-alertdialog-causes-a-memory-leak
        LayoutInflater layoutInflater = LayoutInflater.from(MyProfileViewActivity.this);
        View editProfileView = layoutInflater.inflate(R.layout.edit_profile_view, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyProfileViewActivity.this);
        //PopUp window for editing profile information uses AlertDialog

        alertDialogBuilder.setView(editProfileView);

        final TextView nameView = (TextView) editProfileView.findViewById(R.id.editProfileName);
        final EditText phoneInput = (EditText) editProfileView.findViewById(R.id.editProfilePhone);
        final EditText emailInput = (EditText) editProfileView.findViewById(R.id.editProfileEmail);
        //Grab the existing attributes to fill in the "hint" field.
        nameView.setText(user.getUsername());
        phoneInput.setHint(user.getPhoneNumber());
        emailInput.setHint(user.getEmailAddress());
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Minor error checking here - if field is empty no change is made
                                //if OK is selected.
                                if (!phoneInput.getText().toString().isEmpty()) {
                                    profilePhone.setText(phoneInput.getText().toString());
                                    user.setPhoneNumber(phoneInput.getText().toString());
                                }
                                if (!emailInput.getText().toString().isEmpty()) {
                                    profileEmail.setText(emailInput.getText().toString());
                                    user.setEmailAddress(emailInput.getText().toString());
                                }
                                UserController.updateUserElasticSearch(user);
                            }
                        })
                .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


        return false;
    }

    /**
     * Android Lifecycle - probably not needed, placeholder.
     * @author adam
     * @return
     */

    @Override
    protected void onPause() {
        super.onPause();
        //Do not write to storage/server here!
    }

    /**
     * Android Lifecycle - for whatever reason the user information may be updated server-side;
     * it may be useful to update the "local" object.
     * @author adam
     * @return
     */

    @Override
    protected void onResume() {
        super.onResume();
        //TODO maybe add refresh content in case of offsite server update, etc. + toast to notify if done or not
        //onCreate will take care of memory release
    }

    /**
     * Android Lifecycle - onStop may happen whenever, so this code should be ready for it.
     * onStop may lead to a memory release (home button then start playing a game for example), the
     * app should save state when stopped, maybe a push to server if possible.  Best before a destroy
     * condition.
     * @author adam
     * @return
     */

    @Override
    protected void onStop() {
        super.onStop();
        //TODO add the write to disk/server function here probably
    }




}
