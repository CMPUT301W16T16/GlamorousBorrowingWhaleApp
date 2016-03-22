package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import ualberta.cmput301w16t16.glamorousborrowingwhaleapp.unused.IncomingBids;

/**
 * Created by andrew on 14/03/16.
 * This page shows the bids the user has received on all their items in a list
 * format.
 * @author adam, andrew, erin, laura, martina
*/

public class IncomingBidsActivity extends AppCompatActivity {
    private ArrayList<Bid> bidArray;
    private ArrayAdapter<Bid> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");

        User user = UserController.getUser();
        ItemList itemList = user.getItemsRenting();
        TextView tv = (TextView) findViewById(R.id.test_text_view);
        ListView incomingBidsList = (ListView) findViewById(R.id.incomingBidsListView);

        final BidList bidsToShow = new BidList();
        if (itemList != null) {
            for (int i = 0; i < itemList.getItemList().size(); i++) {
                Item item = itemList.getItemList().get(i);
                BidList bidIterate = item.getBids();
                for (int j = 0; j < bidIterate.getBids().size(); j++) {
                    bidsToShow.addBid(bidIterate.getBids().get(j));
                }
            }
        }

        // TODO get rid of this when we don't need fake bid data any more
        User owner = new User("billy", "billy@abc.com", "123");
        Item testItem = new Item();
        testItem.setTitle("Snowshoes");
        testItem.setAvailability(true);
        testItem.setOwner(owner);
        testItem.setDescription("Nice pair of snowshoes. Worth about $20.00 to borrow");
        BidList testBidList = new BidList();
        Bid testBid = new Bid(testItem, 15.00);
        testBidList.addBid(testBid);
        testItem.setBids(testBidList);
        bidsToShow.addBid(testBid);

        Item testItem2 = new Item();
        testItem2.setTitle("Golf Clubs - don't click this one (not enough data to display)");
        testItem2.setAvailability(true);
        Bid testBid2 = new Bid(testItem2, 30.00);
        bidsToShow.addBid(testBid2);
        // these are here so at least something will show up to test with

        bidArray = bidsToShow.getBids();
        CustomIncomingBidsAdapter adapter = new CustomIncomingBidsAdapter(IncomingBidsActivity.this, bidArray);
        incomingBidsList.setAdapter(adapter);

        // allows the user the select any bid entry to start the MyItemActivity with the item the bid belongs to
        // taken Mar-21-2016 from http://stackoverflow.com/questions/20922036/android-cant-call-setonitemclicklistener-from-a-listview
        // taken Mar-21-2016 from http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
        incomingBidsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                Bid bid = bidArray.get(position);
                ItemController.setItem(bid.getItem());
                BidController.setBid(bid);
                Intent intent = new Intent(IncomingBidsActivity.this, MyItemActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    /**
     * Custom adapter that allows the ListView to display custom rows
     * @author andrew, martina
     */

    // copied from CustomAdapter class
    public class CustomIncomingBidsAdapter extends ArrayAdapter<Bid> {

        public CustomIncomingBidsAdapter(Context context, ArrayList<Bid> bids) {
            super(context, R.layout.custom_incoming_bids_row, bids);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.custom_incoming_bids_row, parent, false);
            Bid bid = getItem(position);

            // adding the item title
            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setText(bid.getItem().getTitle());

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid: $" + String.format("%.2f", bid.getBidAmount()));
            return view;
        }
    }
}

