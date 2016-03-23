package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * This class shows the user a list of their items that they have bid on and
 * whether the bid has been accepted, rejected or is still pending. If they
 * click an item in the listView they can then view that piece of equipment
 * again.
 * @author adam, andrew, laura, martina
 */

public class MyBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        setTitle("My Bids");

        User user = UserController.getUser();
        TextView tv = (TextView) findViewById(R.id.test_text_view);
        ArrayList<String> itemIDsWithRelevantBids = user.getItemsBidOn();

        // TODO: using list of ItemIDs, get all bids on those items with RenterID == UserID
        // how? not sure. would have to be asynchronous
        // for (String itemID : itemIDsWithRelevantBids) {
        //    itemID
        // }
        // BidList bidsToShow = user.getItemsBidOn();

        tv.setText("No bids.");
    }
}

