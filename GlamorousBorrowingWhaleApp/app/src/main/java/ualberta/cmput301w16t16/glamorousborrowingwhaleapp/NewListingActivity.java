package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class NewListingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: setting a picture
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText name = (EditText) findViewById(R.id.name);
        EditText owner = (EditText) findViewById(R.id.owner);
        EditText status = (EditText) findViewById(R.id.status);
        EditText size = (EditText) findViewById(R.id.size);
        EditText description = (EditText) findViewById(R.id.description);
        status.setText("Available");
        owner.setText("You");

        String nameStr = name.getText().toString();
        String sizeStr = size.getText().toString();
        String descStr = description.getText().toString();
        BidList bids = new BidList();
        User currOwner = UserController.getUser();
        Item latestItem = new Item();
        latestItem.setTitle(nameStr);
        latestItem.setDescription(descStr);
        latestItem.setSize(sizeStr);
        latestItem.setAvailability(true);
        latestItem.setBids(bids);
        latestItem.setOwner(currOwner);
    }
}
