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

public class SignInActivity extends AppCompatActivity {

    private EditText enteredUsername;
    private String username;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        enteredUsername = (EditText) findViewById(R.id.username);
        doneButton = (Button) findViewById(R.id.done_sign_up);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = enteredUsername.getText().toString();

                // prevents user from leaving the username field empty
                if (username.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "You must enter your username.", Toast.LENGTH_SHORT).show();
                } else {
                    // taken Feb-29-2016 from http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
                    // TODO need to find the user with this username, not create a new user
                    User user = new User(username, "find_old_number", "find_old_email");
                    Intent intent = new Intent(SignInActivity.this, SignInActivity.class);
                    intent.putExtra("OLD_USER", (Serializable) user);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

}
