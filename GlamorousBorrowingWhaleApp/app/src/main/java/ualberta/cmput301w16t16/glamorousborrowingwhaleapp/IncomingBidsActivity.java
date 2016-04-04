package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    // I'm sorry theres like a thousand variables i'll try to clean this up later - erin
    Button acceptButton;
    Button rejectButton;
    private ArrayList<BidItem> pairs = new ArrayList<>();
    private ArrayAdapter<BidItem> adapter;
    private ArrayList<String> ownersItems;
    private Bid selectedBid;
    private BidList myBidList = new BidList();
    private BidList itemBidList = new BidList();
    private ListView incomingBidsList;
    private String[] myItemsList;
    private String[] items;
    private String[] ownersItemsString;
    private String renterID;
    private String oldID;
    private String newID;
    private ArrayList<Item> myItems;
    private User owner = UserController.getUser();
    private User renter;
    private ItemList itemList;
    private Integer pos = -1;
    private Integer finalPos;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");
        owner.setNotification(false);

        acceptButton = (Button) findViewById(R.id.incomingBidsAccept);
        rejectButton = (Button) findViewById(R.id.incomingBidsReject);

        SetBids();

        incomingBidsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Button acceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
                Button rejectButton = (Button) view.findViewById(R.id.incomingBidsReject);

                BidItem bidItem = (BidItem) parent.getAdapter().getItem(position);
                selectedBid = bidItem.bid;
                owner = UserController.getUser();
                item = bidItem.item;

                view.clearFocus();
                acceptButton.setClickable(true);
                rejectButton.setClickable(true);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BidController.acceptBid(selectedBid, item, owner);
                        SetBids();
                    }
                });
                rejectButton.setOnClickListener(new View.OnClickListener() {
                    //TODO: get a refresh or something working so this bid disappears when u reject it
                    @Override
                    public void onClick(View v) {
                        BidController.rejectBid(selectedBid, item, owner);
                        SetBids();
                    }
                });
            }
        });
    }

    public class CustomIncomingBidsAdapter extends ArrayAdapter<BidItem> {

        public CustomIncomingBidsAdapter(Context context, ArrayList<BidItem> bids) {
            super(context, R.layout.custom_incoming_bids_row, bids);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.custom_incoming_bids_row, parent, false);
            BidItem pair = getItem(position);
            Bid bid = pair.bid;
            Item item = pair.item;

            Button acceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
            Button rejectButton = (Button) view.findViewById(R.id.incomingBidsReject);

            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setText(item.getTitle());

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid:\n $" + String.format("%.2f", bid.getBidAmount()) + "/Hour");
            return view;
        }
    }

    public class BidItem {
        public final Bid bid;
        public final Item item;

        public BidItem(Bid bid, Item item) {
            this.bid = bid;
            this.item = item;
        }
    }

    public void SetBids() {
        incomingBidsList = (ListView) findViewById(R.id.incomingBidsListView);
        ArrayList<String> myItemsArray = owner.getMyItems();
        myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        ItemController.getItemsByIDElasticSearch(myItemsList);
        myItems = ItemController.getItemList().getItemList();
        pairs.clear();
        for (Item item : myItems) {
            itemBidList = item.getBids();
            for (Bid bid : itemBidList.getBids()) {
                myBidList.addBid(bid);
                BidItem pair = new BidItem(bid, item);
                pairs.add(pair);
            }
        }

        adapter = new CustomIncomingBidsAdapter(this, pairs);
        incomingBidsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (myBidList.isEmpty()) {
            Toast.makeText(IncomingBidsActivity.this, "You don't have any incoming bids.", Toast.LENGTH_SHORT).show();
        }
    }
}
