package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MyItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);

        EditText owner = (EditText) findViewById(R.id.owner);
        EditText status = (EditText) findViewById(R.id.status);

        status.setText("Status: Available");
        owner.setText("Owner: You");
    }
}
