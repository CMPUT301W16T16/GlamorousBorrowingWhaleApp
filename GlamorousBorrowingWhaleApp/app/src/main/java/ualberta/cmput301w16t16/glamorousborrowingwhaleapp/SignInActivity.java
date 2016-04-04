package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
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

    private static final String FILENAME = "file.sav";
    private EditText enteredUsername;
    private EditText enteredPassword;
    private String username;
    private String password;
    private User user;
    private boolean logged_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        NetworkUtil.stopListeningForNetwork(this);

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

        // allows the user to log in with their username and passwords and saves them as logged in
        enteredUsername = (EditText) findViewById(R.id.username);
        enteredPassword = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.getConnectivityStatus(view.getContext()) == 1) {
                    username = enteredUsername.getText().toString();
                    password = enteredPassword.getText().toString();
                    // TODO: We need to do an es call here and check that the username and password match

                    // prevents user from leaving the username field empty
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(SignInActivity.this, "You must enter your username and password.", Toast.LENGTH_SHORT).show();
                    } else {
                        // using elastic search to check for the user name
                        // resulting user will be set to (UserController.secondaryUser)
                        UserController.setSecondaryUser(null);
                        UserController.getUserByIDElasticSearch(username);

                        // elasticsearch sets the secondary user, so we check the secondary user's password
                        // with the provided password after checking that the user exists
                        user = UserController.getSecondaryUser();
                        if (user != null) {
                            if (UserController.checkPassword(password, user)) {
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
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
