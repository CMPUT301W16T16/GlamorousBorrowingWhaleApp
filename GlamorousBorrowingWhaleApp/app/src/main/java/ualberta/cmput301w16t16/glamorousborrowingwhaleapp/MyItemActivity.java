package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * This activity lists ONE of the users available items. It gives the user the
 * chance to edit any of the information associated with the item (size, sport,
 * status etc). This class also allows the user to delete an item if they want.
 * @author adam, andrew, erin, laura, martina
 */

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
