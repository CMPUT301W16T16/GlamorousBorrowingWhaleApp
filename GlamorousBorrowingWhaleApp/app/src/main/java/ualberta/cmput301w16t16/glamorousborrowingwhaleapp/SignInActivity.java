package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This activity is the fist one the user sees. It allows them to enter their
 * username and password (to be added) to login (which takes them to
 * MyProfileViewActivity) or a new user can click the sign up button to create
 * an account (takes them to SignUpActivity).
 * @author adam, andrew, erin, laura, martina
 * @see SignUpActivity
 * @see MyProfileViewActivity
 */

public class SignInActivity extends AppCompatActivity implements Serializable {

    private EditText enteredUsername;
    private EditText enteredPassword;
    private String username;
    private String password;
    private User user;

    private static final int SIGN_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // sign up button starts the sign up activity and the sign up view
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent logIntent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(logIntent);
                finish();
            }
        });

        enteredUsername = (EditText) findViewById(R.id.username);
        enteredPassword = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = enteredUsername.getText().toString();
                password = enteredPassword.getText().toString();
                // TODO: We need to do an es call here and check that the username and password match

                // prevents user from leaving the username field empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "You must enter your username and password.", Toast.LENGTH_SHORT).show();
                } else {
                    // using elastic search to check for the user name
                    // resulting user will be set to (UserController.secondaryUser)
                    try {
                        UserController.setSecondaryUser(null);
                        new ElasticSearch.elasticGetUserByID(getApplicationContext()).execute(username).get(1, TimeUnit.DAYS);

                        // elasticsearch sets the secondary user, so we check the secondary user's password
                        // with the provided password after checking that the user exists
                        user = UserController.getSecondaryUser();
                        if (user != null) {
                            Log.d("TEST", user.getUsername());
                            if (UserController.checkPassword(password)) {
                                Log.d("TEST", user.getUsername());
                                UserController.setUser(user);
                                Intent intent = new Intent(view.getContext(), MyProfileViewActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(view.getContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(view.getContext(), "There is no user with that username", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        Log.d("ElasticSearch", "Failed while retrieving user from ElasticSearch");
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    /*
    // the sign up activity returns the new user and starts the ViewProfileActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            // receives the information from the AddEntryActivity,
            // converts it into an entry and adds it to the list of entries
            case (SIGN_UP) : {
                if (resultCode == Activity.RESULT_OK) {
                    //User user = (User) data.getSerializableExtra("NEW_USER");
                    Intent intent = new Intent(this, MyProfileViewActivity.class);
                    //intent.putExtra("USER", (Serializable) user);
                    //TODO investigate the "redundant" Serializable studio complains about
                    Toast.makeText(getApplicationContext(), "New User Created!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;
            }
        }

    }
    */
}
