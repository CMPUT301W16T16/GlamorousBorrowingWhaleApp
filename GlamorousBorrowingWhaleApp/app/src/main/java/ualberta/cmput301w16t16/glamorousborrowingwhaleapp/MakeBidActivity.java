package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeBidActivity extends AppCompatActivity {

    public Button bidButton;
    private User owner = UserController.getSecondaryUser();
    private Item item = ItemController.getItem();
    EditText dollarsPerHour;
    EditText numberOfHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_make_bid);

        //TODO: images are causing problems
        /*
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        if (user.getPhoto() != null) {
            byte[] tempPhoto = user.getPhoto();
            userImage.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }
        */

        dollarsPerHour = (EditText) findViewById(R.id.dollarsPerHour);
        numberOfHours = (EditText) findViewById(R.id.numberOfHours);

        bidButton = (Button) findViewById(R.id.bid_button);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: need to add number of hours to the whole thing
                //TODO: make a bid on the item, needs error checking and type check etc.

                if (NetworkUtil.getConnectivityStatus(v.getContext()) == 1) {

                    // just doing amount bid for now to get it going
                    // constraints on the EditText make it so only a decimal number can be entered
                    Double amountBid = Double.valueOf(dollarsPerHour.getText().toString());
                    Double hours = Double.valueOf(numberOfHours.getText().toString());

                    // creating the bid
                    Bid newBid = new Bid(item, amountBid);
                    newBid.setOwnerID(owner.getID());
                    newBid.setRenterID(UserController.getUser().getID());
                    newBid.setIsAccepted(false);

                    // adding the bid to the item
                    item.addBid(newBid);

                    // removing the item as it was from owner's item list
                    owner.removeMyItem(item.getID());

                    // updating the item's elastic search data to include the bid
                    // the item now has a new ID
                    ItemController.updateItemElasticSearch(item);

                    // adding the item as it now is into the owner's item list
                    owner.addMyItem(item.getID());
                    owner.setNotification(true);

                    // updating the bidder to include the item
                    UserController.getUser().addItemBidOn(item.getID());
                    UserController.updateUserElasticSearch(UserController.getUser());

                    finish();
                } else {
                    Toast.makeText(v.getContext(), "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
