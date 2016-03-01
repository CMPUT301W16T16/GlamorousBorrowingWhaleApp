package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int SIGN_UP = 1;
    private static final int SIGN_IN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent logIntent = new Intent(view.getContext(), SignUpActivity.class);
                startActivityForResult(logIntent, SIGN_UP);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent logIntent = new Intent(view.getContext(), SignInActivity.class);
                startActivityForResult(logIntent, SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            // receives the information from the AddEntryActivity,
            // converts it into an entry and adds it to the list of entries
            case (SIGN_UP) : {
                if (resultCode == Activity.RESULT_OK) {
                    User user = (User) data.getSerializableExtra("NEW_USER");

                }
                break;
            }
            case (SIGN_IN) : {
                // TODO change this to loading the specified user based on the given username
                if (resultCode == Activity.RESULT_OK) {
                    User user = (User) data.getSerializableExtra("OLD_USER");
                }
                break;
            }
        }
    }


}
