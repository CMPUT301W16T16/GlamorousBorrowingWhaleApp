package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// controls the view where the user is able to view their profile and allows the
// user to edit their profile, access the borrowed item pages and search
// for new items to make bids on
public class ProfileViewActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView profilePhone;
    private TextView profileEmail;

    private Button buttonMyBids;
    private Button buttonMyStuff;
    private Button buttonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("USER");


        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileEmail = (TextView) findViewById(R.id.profileEmail);

        profileName.setText(user.getName());
        profilePhone.setText(user.getPhoneNumber());
        profileEmail.setText(user.getEmailAddress());

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
        buttonMyBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent bidsIntent = new Intent(v.getContext(), MyBidsActivity.class);
                startActivity(bidsIntent);
            }
        });
        buttonMyStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent stuffIntent = new Intent(v.getContext(), MyItemsActivity.class);
                startActivity(stuffIntent);
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send to search thing
            }
        });
    }

    // taken Feb-29-2016 from http://stackoverflow.com/questions/19079265/onlongclick-textview-to-edit
    // TODO fix the bug where tou can only edit the profile once before the app crashes
    public boolean longClick() {
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileViewActivity.this);
        View editProfileView = layoutInflater.inflate(R.layout.edit_profile_view, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileViewActivity.this);

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

                                phoneInput.setHint(profilePhone.getText());
                                profilePhone.setText(phoneInput.getText().toString());

                                emailInput.setHint(profileEmail.getText());
                                profileEmail.setText(emailInput.getText().toString());

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

    @Override
    protected void onPause() {
        super.onPause();
        //Do not write to storage/server here!
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO maybe add refresh content in case of offsite server update, etc. + toast to notify if done or not
        //onCreate will take care of memory release
    }


}
