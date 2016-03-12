package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class SignInActivity extends AppCompatActivity implements Serializable {

    private EditText enteredUsername;
    private String username;
    private String profilePictureRef;

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
                //startActivityForResult(logIntent, SIGN_UP);
                startActivity(logIntent);
            }
        });

        //Intent intent = getIntent();
        enteredUsername = (EditText) findViewById(R.id.username);
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = enteredUsername.getText().toString();
                User user = new User(username, "temporary", "temporary");

                // prevents user from leaving the username field empty
                if (username.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "You must enter your username.", Toast.LENGTH_SHORT).show();
                } else {
                    UserController.setUser(user);
                    Intent intent = new Intent(view.getContext(), MyProfileViewActivity.class);
                    // TODO fetch user from username, and put the user as extra instead of username
                    //intent.putExtra("USERNAME", user);
                    startActivity(intent);
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
