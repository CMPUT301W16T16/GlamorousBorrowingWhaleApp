package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
        ListView incomingBidsList = (ListView) findViewById(R.id.incomingBidsListView);

        new ElasticSearch.elasticGetIncomingBids(getApplicationContext()).execute(incomingBidsList);


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

