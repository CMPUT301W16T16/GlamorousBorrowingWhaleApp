package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewListingActivity extends AppCompatActivity {
    private ImageButton saveButton;
    private ImageButton deleteButton;
    private String nameStr;
    private String sizeStr;
    private String descStr;
    private BidList bids;
    private User currOwner;
    private Item latestItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: setting a picture
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final EditText name = (EditText) findViewById(R.id.name);
        final EditText owner = (EditText) findViewById(R.id.owner);
        final EditText status = (EditText) findViewById(R.id.status);
        final EditText size = (EditText) findViewById(R.id.size);
        final EditText description = (EditText) findViewById(R.id.description);
        status.setText("Available");
        owner.setText("You");

        saveButton = (ImageButton) findViewById(R.id.save);
        deleteButton = (ImageButton) findViewById(R.id.delete);

        currOwner = UserController.getUser();

        bids = new BidList();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add lastest Item to ItemList (MyItems)
                nameStr = name.getText().toString();
                sizeStr = size.getText().toString();
                descStr = description.getText().toString();
                latestItem = new Item();
                latestItem.setTitle(nameStr);
                latestItem.setDescription(descStr);
                latestItem.setSize(sizeStr);
                latestItem.setAvailability(true);
                latestItem.setBids(bids);
                latestItem.setOwner(currOwner);
                Toast.makeText(NewListingActivity.this, "Item Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO delete shit
                name.setText("");
                size.setText("");
                description.setText("");
                Toast.makeText(NewListingActivity.this, "Item Cleared!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
