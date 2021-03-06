package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        /*
        // check if there are still any items that need to be pushed to ElasticSearch
        /*if (!user.getOfflineItems().isEmpty()) {
            if (NetworkUtil.getConnectivityStatus(this) == 1) {
                // we have items to push and we have a connection
                UserController.pushOfflineItems();
            } else {
                // we have items to push but no connectivity
                // (the receiver will push the items for us once we have connectivity)
                NetworkUtil.startListeningForNetwork(this);
            }
        } else {
            // we don't have any items to push, continue normally
            // make sure we're not listening for network changes
            NetworkUtil.stopListeningForNetwork(this);
        }*/

        setContentView(R.layout.activity_profile_view);
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Your Profile");*/
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Profile");
        actionBar.setHomeButtonEnabled(true);

        //This chunk grabs the TextViews and operates on them
        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePictureView = (ImageView) findViewById(R.id.pictureView);

        profileName.setText(user.getUsername());
        profilePhone.setText(user.getPhoneNumber());
        profileEmail.setText(user.getEmailAddress());

        if (user.getPhoto() != null) {
            profilePictureView.setImageBitmap(user.getPhoto());
        }

        Button buttonMyBids = (Button) findViewById(R.id.buttonMyBids);
        Button buttonMyStuff = (Button) findViewById(R.id.buttonMyStuff);
        Button buttonSearch = (Button) findViewById(R.id.buttonBorrowSearch);
        Button buttonIncomingBids = (Button) findViewById(R.id.buttonIncomingBids);

        if (user.getNotification()) {
            buttonIncomingBids.setTextColor(Color.parseColor("#ffffff"));
            buttonIncomingBids.setBackgroundResource(R.drawable.rounded_corners_dark);
        } else {
            buttonIncomingBids.setTextColor(Color.parseColor("#2e4154"));
            buttonIncomingBids.setBackgroundResource(R.drawable.rounded_corners_dark);
            buttonIncomingBids.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        Button buttonMyBorrowing = (Button) findViewById(R.id.buttonMyBorrowing);
        Button buttonMyBorrowedItems = (Button) findViewById(R.id.buttonMyItemsBorrowed);
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
                user.setNotification(false);
                UserController.updateUserElasticSearch(user);
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

        buttonMyBorrowedItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewBorrowingOutActivity.class);
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
                logout();
                finish();
            }
        });
    }

    public void logout() {
        // clear all of the current items in the controllers
        UserController.setUser(null);
        UserController.setSecondaryUser(null);
        ItemController.setItem(null);
        ItemController.setItemList(null);

        // set logged in as false so sign in activity will not automatically sign the user in
        //UserController.setLoggedIn(v.getContext(), false);

        // go back to the sign in activity
        Intent intent = new Intent(MyProfileViewActivity.this, SignInActivity.class);

        // make sure the user cannot press the back button to go back into another activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    /**
     * longClick() is used in the longClickListeners for the textviews for editing profile information.
     * A layout is inflated in an alertdialog box which allows the user to edit the name, phone
     * number and email address fields.  If the CANCEL button is selected, no changes are made
     * and the dialog box is closed. If the OK button is selected with empty fields, only the fields
     * with content in them will update their corresponding attribute; the empty fields will not do
     * anything.
     *
     * @return
     * @author martina
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
    //setting up the action bar icons
    //taken from http://www.androidhive.info/2013/11/android-working-with-action-bar/
    // Apr3/16
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu_nosearch_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.help_menu:
                //taken from http://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application
                try{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/CMPUT301W16T16/GlamorousBorrowingWhaleApp/wiki"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(MyProfileViewActivity.this,
                            "No browser to navigate to the help page!", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.logout_menu:
                logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Android Lifecycle - probably not needed, placeholder.
     *
     * @return
     * @author adam
     */
    @Override
    protected void onPause() {
        super.onPause();
        //Do not write to storage/server here!
    }

    /**
     * Android Lifecycle - for whatever reason the user information may be updated server-side;
     * it may be useful to update the "local" object.
     *
     * @return
     * @author adam
     */
    @Override
    protected void onResume() {
        super.onResume();
        //onCreate will take care of memory release
        Button buttonIncomingBids = (Button) findViewById(R.id.buttonIncomingBids);
        if (user.getNotification()) {
            buttonIncomingBids.setTextColor(Color.parseColor("#ffffff"));
            buttonIncomingBids.setBackgroundResource(R.drawable.rounded_corners_dark);
        } else {
            buttonIncomingBids.setTextColor(Color.parseColor("#2e4154"));
            buttonIncomingBids.setBackgroundResource(R.drawable.rounded_corners_dark);
            buttonIncomingBids.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    /**
     * Android Lifecycle - onStop may happen whenever, so this code should be ready for it.
     * onStop may lead to a memory release (home button then start playing a game for example), the
     * app should save state when stopped, maybe a push to server if possible.  Best before a destroy
     * condition.
     *
     * @return
     * @author adam
     */
    @Override
    protected void onStop() {
        super.onStop();
    }
}