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

/**
 * This activity allows the user to enter all the necessary information about a
 * piece of equipment and save it to their repository of equipment. It also has
 * the option to cancel creating equipment.
 * @author adam, andrew, erin, laura, martina
 */

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
        //Create the variables for EditText type so that the actual Item object may be updated below.
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText owner = (EditText) findViewById(R.id.owner);
        final EditText status = (EditText) findViewById(R.id.status);
        final EditText size = (EditText) findViewById(R.id.size);
        final EditText description = (EditText) findViewById(R.id.description);
        status.setText("Available");
        owner.setText("You");

        saveButton = (ImageButton) findViewById(R.id.save);
        deleteButton = (ImageButton) findViewById(R.id.delete);
        //Setting up the buttons and getting user focus.
        currOwner = UserController.getUser();

        bids = new BidList();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gathers all the variables used for EditText to apply to the Item object.
                //toString for compatibility.
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
                //Adding the latestItem to the current user's (Controlled by UserController) RentedItem
                //List. We'll have to sort out some terminology here.
                currOwner.addItemRenting(latestItem);//THE MEAT AND POTATOES RIGHT HERE
                Toast.makeText(NewListingActivity.this, "Item Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO delete shit
                //All this does atm is clear the edittext boxes.
                name.setText("");
                size.setText("");
                description.setText("");
                Toast.makeText(NewListingActivity.this, "View Cleared!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
