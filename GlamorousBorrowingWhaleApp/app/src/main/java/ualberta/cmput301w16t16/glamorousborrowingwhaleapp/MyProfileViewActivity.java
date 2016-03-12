package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private Button buttonMyBids;
    private Button buttonMyStuff;
    private Button buttonSearch;

    private User user = UserController.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        Intent intent = getIntent();

        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePictureView = (ImageView) findViewById(R.id.profilePictureView);

        profileName.setText(user.getName());
        profilePhone.setText(user.getPhoneNumber());
        profileEmail.setText(user.getEmailAddress());
        profilePictureView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.glamorouswhale1)); //get bitmap reference from USER and set
        //the imageView type with the bitmap. The idea is that the imageView handles the scaling and
        //such rather than a bitmap drawable directly. Something to due with memory or something.

        buttonMyBids = (Button) findViewById(R.id.buttonMyBids);
        buttonMyStuff = (Button) findViewById(R.id.buttonMyStuff);
        buttonSearch = (Button) findViewById(R.id.buttonBorrowSearch);

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
                        //involves another intent to the photo chooser thing MAYBE
                        return false;
                    }
                }
        );

        buttonMyBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyBorrowedItemsActivity.class);
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

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchResultsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * I don't really know anything about these functions, Martina wrote them :\
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

        final EditText nameInput = (EditText) editProfileView.findViewById(R.id.editProfileName);
        final EditText phoneInput = (EditText) editProfileView.findViewById(R.id.editProfilePhone);
        final EditText emailInput = (EditText) editProfileView.findViewById(R.id.editProfileEmail);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nameInput.setHint(profileName.getText());
                                profileName.setText(nameInput.getText().toString());
                                user.setName(nameInput.getText().toString());

                                phoneInput.setHint(profilePhone.getText());
                                profilePhone.setText(phoneInput.getText().toString());
                                user.setPhoneNumber(phoneInput.getText().toString());

                                emailInput.setHint(profileEmail.getText());
                                profileEmail.setText(emailInput.getText().toString());
                                user.setEmailAddress(emailInput.getText().toString());

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
     * I don't really know anything about these functions, Martina wrote them :\
     * @author martina
     * @return
     */

    @Override
    protected void onPause() {
        super.onPause();
        //Do not write to storage/server here!
    }

    /**
     * I don't really know anything about these functions, Martina wrote them :\
     * @author martina
     * @return
     */

    @Override
    protected void onResume() {
        super.onResume();
        //TODO maybe add refresh content in case of offsite server update, etc. + toast to notify if done or not
        //onCreate will take care of memory release
    }

    /**
     * I don't really know anything about these functions, Martina wrote them :\
     * @author martina
     * @return
     */

    @Override
    protected void onStop() {
        super.onStop();
        //TODO add the write to disk/server function here probably
    }




}
