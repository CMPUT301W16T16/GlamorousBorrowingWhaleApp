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
    private ArrayList<BidItem> pairs = new ArrayList<>();
    private ArrayAdapter<BidItem> adapter;
    private BidList myBidList = new BidList();
    private BidList itemBidList = new BidList();
    private User user = UserController.getUser();
    private ListView incomingBidsList;
    private String[] myItemsList;
    private ArrayList<Item> myItems;
    private User owner;

    private Bid selectedBid;
    private Item selectedItem;
    private ArrayList<String> ownersItems;
    private String ownersItemsString[];
    private ItemList itemList;
    private String itemID;
    private String renterID;
    private User renter;
    private ArrayList<String> rentersBiddedItems;
    private String savedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");
        user.setNotification(false);

        // get all the users items that have bids
        incomingBidsList = (ListView) findViewById(R.id.incomingBidsListView);
        ArrayList<String> myItemsArray = user.getMyItems();
        myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        ItemController.getItemsByIDElasticSearch(myItemsList);
        myItems = ItemController.getItemList().getItemList();

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

        // referenced Mar-21-2016 from http://stackoverflow.com/questions/18841650/replacing-listview-row-with-another-layout-onclick
        // this doesn't work very well, although the buttons do show up when you click on one of the bids once
        // the buttons don't lead any where yet and the replaced view has the wrong text
        //TODO: the inflated layout doesn't retain the item (bid) information since the text views have different IDs
        incomingBidsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

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

                BidItem bidItem = (BidItem) parent.getAdapter().getItem(position);
                selectedItem = bidItem.item;
                selectedBid = bidItem.bid;
                owner = UserController.getUser();
                ownersItems = owner.getMyItems();
                ownersItemsString = new String[ownersItems.size()];
                ownersItemsString = ownersItems.toArray(ownersItemsString);
                itemList = ItemController.getItemsByIDElasticSearch(ownersItemsString);

                for (Item item : itemList.getItemList()) {
                    for (Bid bid : item.getBids().getBids()) {
                        if (bid.getItemID().equals(selectedBid.getItemID())) {
                            savedID = bid.getItemID();
                            ItemController.setItem(item);
                        }
                    }
                }

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

                        User owner = UserController.getUser();
                        Item item = ItemController.getItem();

                        renterID = selectedBid.getRenterID();
                        renter = UserController.getUserByIDElasticSearch(renterID);
                        renter.removeItemBidOn(savedID);
                        UserController.updateUserElasticSearch(renter);

                        BidList bids = item.getBids();
                        bids.removeBid(selectedBid);
                        item.setBids(bids);

                        itemID = item.getID();
                        owner.removeMyItem(itemID);
                        ItemController.updateItemElasticSearch(item);
                        itemID = item.getID();
                        owner.addMyItem(itemID);

                        UserController.updateUserElasticSearch(owner);
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

            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setText(item.getTitle());

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid: $" + String.format("%.2f", bid.getBidAmount()));
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

}