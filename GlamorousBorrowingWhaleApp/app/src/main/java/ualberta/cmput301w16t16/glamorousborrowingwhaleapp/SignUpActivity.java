package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity implements Serializable {

    //private Drawable enteredPicture;
    private EditText enteredUsername;
    private EditText enteredPhoneNumber;
    private EditText enteredEmailAddress;
    private Button doneButton;

    private String username;
    private String phoneNumber;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        enteredUsername = (EditText) findViewById(R.id.username);
        enteredPhoneNumber = (EditText) findViewById(R.id.phone);
        enteredEmailAddress = (EditText) findViewById(R.id.email);

        doneButton = (Button) findViewById(R.id.done_sign_up);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertInputToString();

                // prevents user from leaving any fields empty
                if (username.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Something must be entered in every field.", Toast.LENGTH_SHORT).show();
                } else {
                    // need to include picture
                    // taken Feb-29-2016 from http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
                    User user = new User(username, emailAddress, phoneNumber);
                    Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
                    intent.putExtra("NEW_USER", user);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public void convertInputToString() {
        username = enteredUsername.getText().toString();
        phoneNumber = enteredPhoneNumber.getText().toString();
        emailAddress = enteredEmailAddress.getText().toString();
    }






}
