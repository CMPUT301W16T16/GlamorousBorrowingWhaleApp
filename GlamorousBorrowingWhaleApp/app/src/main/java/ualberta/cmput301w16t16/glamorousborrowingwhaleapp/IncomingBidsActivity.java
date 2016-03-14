package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by andrew on 14/03/16.
 * This page shows the bids the user has recieved on all their items in a list
 * format.
 * @author adam, andrew, erin, laura, martina
*/

public class IncomingBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");

        User user = UserController.getUser();
        ItemList itemList = user.getItemsRenting();
        TextView tv = (TextView) findViewById(R.id.test_text_view);

        BidList bidsToShow = new BidList();
        if (itemList != null) {
            for (int i = 0; i < itemList.getItemList().size(); i++) {
                Item item = itemList.getItemList().get(i);
                BidList bidIterate = item.getBids();
                for (int j = 0; j < bidIterate.getBids().size(); j++) {
                    bidsToShow.addBid(bidIterate.getBids().get(j));
                }
            }
        }
        // TODO make these actually human readable
    }
}
