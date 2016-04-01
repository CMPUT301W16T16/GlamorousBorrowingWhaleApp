package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TheirProfileViewActivity extends AppCompatActivity {
    TextView theirName;
    TextView theirEmail;
    TextView theirPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_their_profile_view);

        theirName = (TextView) findViewById(R.id.theirName);
        theirEmail = (TextView) findViewById(R.id.theirEmail);
        theirPhoneNumber = (TextView) findViewById(R.id.theirPhoneNumber);

        User owner = UserController.getSecondaryUser();

        theirName.setText(owner.getID());
        theirEmail.setText(owner.getEmailAddress());
        theirPhoneNumber.setText(owner.getPhoneNumber());
    }
}
