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
                User user = new User(username, password, "temporary", "temporary");

                // prevents user from leaving the username field empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "You must enter your username.", Toast.LENGTH_SHORT).show();
                } else {
                    UserController.setUser(user);
                    new ElasticSearch.elasticGetUserByName(getApplicationContext()).execute(SignInActivity.this);
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
