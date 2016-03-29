package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by andrew on 14/03/16.
 * This page shows the bids the user has received on all their items in a list
 * format.
 * @author adam, andrew, erin, laura, martina
*/

public class IncomingBidsActivity extends AppCompatActivity {
    private BidList bidList = new BidList();
    private ArrayAdapter<Bid> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");

        User user = UserController.getUser();
        ListView incomingBidsList = (ListView) findViewById(R.id.incomingBidsListView);

        //////////////////////////////////////////////////////////////////////////////
        /*
        Item item = new Item();
        item.setAvailability(true);
        item.setOwnerID(UserController.getUser().getID());
        item.setTitle("snowshoes");
        item.setDescription("nice pair of snowshows");
        item.setPhoto(null);
        item.setID("snowshoes");
        item.setSize("medium");
        Bid bid = new Bid(item, 5);
        BidList bidList = new BidList();
        bidList.addBid(bid);
        item.setBids(bidList);
        user.addMyItem(item.getID());
        */
        //////////////////////////////////////////////////////////////////////////////

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            ItemController.getIncomingBidsElasticSearch();
            if (BidController.getBidList() != null) {
                bidList = BidController.getBidList();
            } else {
                Toast.makeText(IncomingBidsActivity.this, "You don't have any incoming bids!", Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomIncomingBidsAdapter(IncomingBidsActivity.this, bidList.getBids());
            incomingBidsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }

        // TODO: figure out how to reimplement this, considering Bids do not have Item

//        // allows the user the select any bid entry to start the MyItemActivity with the item the bid belongs to
//        // taken Mar-21-2016 from http://stackoverflow.com/questions/20922036/android-cant-call-setonitemclicklistener-from-a-listview
//        // taken Mar-21-2016 from http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
//        incomingBidsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Bid bid = bidArray.get(position);
//                ItemController.setItem(bid.getItem());
//                BidController.setBid(bid);
//                Intent intent = new Intent(IncomingBidsActivity.this, MyItemActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

        // referenced Mar-21-2016 from http://stackoverflow.com/questions/18841650/replacing-listview-row-with-another-layout-onclick
        // this doesn't work very well, although the buttons do show up when you click on one of the bids once
        // the buttons don't lead any where yet and the replaced view has the wrong text
        //TODO: the inflated layout doesn't retain the item (bid) information since the text views have different IDs
        incomingBidsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // removing the old views
                TextView incomingBidsItemTitle = (TextView) findViewById(R.id.incomingBidsItemTitle);
                TextView incomingBidsAmountBid = (TextView) findViewById(R.id.incomingBidsAmountBid);
                incomingBidsItemTitle.setVisibility(View.GONE);
                incomingBidsAmountBid.setVisibility(View.GONE);

                RelativeLayout rl_inflate = (RelativeLayout) view.findViewById(R.id.rl_inflate);
                View child = getLayoutInflater().inflate(R.layout.incoming_bids_inflate, null);
                rl_inflate.addView(child);

                ImageButton checkmarkButton = (ImageButton) child.findViewById(R.id.incomingBidsCheckmark);
                ImageButton xmarkButton = (ImageButton) child.findViewById(R.id.incomingBidsXmark);
                TextView incomingBidsItemTitleInflate = (TextView) findViewById(R.id.incomingBidsItemTitleInflate);
                TextView incomingBidsAmountBidInflate = (TextView) findViewById(R.id.incomingBidsAmountBidInflate);
                incomingBidsAmountBidInflate.setText(incomingBidsAmountBid.getText());
                incomingBidsItemTitleInflate.setText(incomingBidsItemTitle.getText());

                checkmarkButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO: accept this bid
                    }
                });

                xmarkButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO: reject this bid
                    }
                });
            }
        });
    }


}

