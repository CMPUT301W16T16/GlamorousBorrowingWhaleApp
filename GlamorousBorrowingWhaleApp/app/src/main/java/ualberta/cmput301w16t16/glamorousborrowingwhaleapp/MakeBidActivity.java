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
    private Item item = ItemController.getItem();
    private User owner;
    private User renter;
    private String ownerID;
    private String oldItemID;
    EditText dollarsPerHour;
    EditText numberOfHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_make_bid);
        renter = UserController.getUser();
        UserController.setSecondaryUser(renter);
        ownerID = item.getOwnerID();
        owner = UserController.getUserByIDElasticSearch(ownerID);
        UserController.setUser(owner);

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

                    Bid newBid = new Bid(item, amountBid);
                    newBid.setOwnerID(owner.getID());
                    newBid.setRenterID(renter.getID());
                    newBid.setIsAccepted(false);
                    item.addBid(newBid);
                    owner.removeMyItem(item.getID());
                    owner.setNotification(true);
                    oldItemID = item.getID();
                    ItemController.updateItemElasticSearch(item);
                    owner.addMyItem(item.getID());
                    renter.removeItemBidOn(oldItemID);
                    renter.addItemBidOn(item.getID());
                    //newBid.setItemID(item.getID());
                    UserController.updateUserElasticSearch(owner);
                    UserController.updateUserElasticSearch(renter);

                    finish();
                } else {
                    Toast.makeText(v.getContext(), "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
