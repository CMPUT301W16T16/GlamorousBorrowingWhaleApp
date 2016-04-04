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
/*
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
                /*owner = UserController.getUser();
                itemList = ItemController.getItemsByIDElasticSearch(ownersItemsString);

                for (Item item : itemList.getItemList()) {
                    pos = 0;
                    for (Bid bid : item.getBids().getBids()) {
                        if (bid.getItemID().equals(selectedBid.getItemID())) {
                            finalPos = pos;
                        }
                    }
                }
                newID = owner.getMyItems().get(finalPos);
                items = new String[1];
                items[0] = newID;
                ItemController.getItemsByIDElasticSearch(items);
                item = ItemController.getItemList().getItemList().get(0);
                oldID = item.getID();
                ItemController.setItem(item);

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
}*/

public class IncomingBidsActivity extends AppCompatActivity {
    // I'm sorry theres like a thousand variables i'll try to clean this up later - erin
    private ArrayList<BidItem> pairs = new ArrayList<>();
    private ArrayAdapter<BidItem> adapter;
    private ArrayList<String> ownersItems = new ArrayList<>();
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
    private ArrayList<Item> myItems = new ArrayList<>();
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

        SetBids();

        // referenced Mar-21-2016 from http://stackoverflow.com/questions/18841650/replacing-listview-row-with-another-layout-onclick
        // this doesn't work very well, although the buttons do show up when you click on one of the bids once
        // the buttons don't lead any where yet and the replaced view has the wrong text
        //TODO: the inflated layout doesn't retain the item (bid) information since the text views have different IDs
        incomingBidsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Button incomingBidsAcceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
                Button incomingBidsRejectButton = (Button) view.findViewById(R.id.incomingBidsReject);
                incomingBidsAcceptButton.setVisibility(View.VISIBLE);
                incomingBidsRejectButton.setVisibility(View.VISIBLE);

                BidItem bidItem = (BidItem) parent.getAdapter().getItem(position);
                selectedBid = bidItem.bid;
                owner = UserController.getUser();
                ownersItems = owner.getMyItems();
                ownersItemsString = new String[ownersItems.size()];
                ownersItemsString = ownersItems.toArray(ownersItemsString);
                itemList = ItemController.getItemsByIDElasticSearch(ownersItemsString);

                for (Item item : itemList.getItemList()) {
                    pos = 0;
                    for (Bid bid : item.getBids().getBids()) {
                        if (bid.getItemID().equals(selectedBid.getItemID())) {
                            finalPos = pos;
                        }
                    }
                }
                newID = owner.getMyItems().get(finalPos);
                items = new String[1];
                items[0] = newID;
                ItemController.getItemsByIDElasticSearch(items);
                item = ItemController.getItemList().getItemList().get(0);
                oldID = item.getID();
                item.setID(newID); // maybe redundant now?
                ItemController.setItem(item);

                incomingBidsAcceptButton.setOnClickListener(new View.OnClickListener() {
                    //TODO: get a refresh or something working so this bid disappears when u reject it
                    @Override
                    public void onClick(View v) {
                        AcceptBid();
                        SetBids();
                    }
                });
                incomingBidsRejectButton.setOnClickListener(new View.OnClickListener() {
                    //TODO: get a refresh or something working so this bid disappears when u reject it
                    @Override
                    public void onClick(View v) {
                        RejectBid();
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

            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setText(item.getTitle());

            Button incomingBidsAcceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
            Button incomingBidsRejectButton = (Button) view.findViewById(R.id.incomingBidsReject);
            incomingBidsAcceptButton.setVisibility(View.GONE);
            incomingBidsRejectButton.setVisibility(View.GONE);

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

    public void RejectBid() {
        renterID = selectedBid.getRenterID();
        renter = UserController.getUserByIDElasticSearch(renterID);
        renter.removeItemBidOn(newID);
        UserController.updateUserElasticSearch(renter);

        BidList bids = item.getBids();
        ArrayList<Bid> bidsToRemove = new ArrayList<>();
        if (!bids.getBids().isEmpty()) {
            for (Bid bid : bids.getBids()) {
                if (bid.getItemID().equals(selectedBid.getItemID())) {
                    bidsToRemove.add(bid);
                }
            }
        }
        for (Bid bid : bidsToRemove) {
            bids.removeBid(bid);
        }
        item.setBids(bids);

        String renterID = selectedBid.getRenterID();
        User renter = UserController.getUserByIDElasticSearch(renterID);

        owner.removeMyItem(item.getID());
        renter.removeItemBidOn(item.getID());

        ItemController.updateItemElasticSearch(item);

        owner.addMyItem(item.getID());

        boolean renterHasBids = false;
        for (Bid bid : item.getBids().getBids()) {
            if (bid.getRenterID().equals(renterID)) {
                renterHasBids = true;
            }
        }
        if (renterHasBids) {
            renter.addItemBidOn(item.getID());
        }

        UserController.updateUserElasticSearch(owner);
        UserController.updateUserElasticSearch(renter);
    }

    public void AcceptBid() {
        renterID = selectedBid.getRenterID();
        renter = UserController.getUserByIDElasticSearch(renterID);
        renter.removeItemBidOn(newID);

        BidList bids = item.getBids();

        for (Bid bid : bids.getBids()) {
            renterID = bid.getRenterID();
            renter = UserController.getUserByIDElasticSearch(renterID);
            renter.removeItemBidOn(newID);
            renter.removeItemBidOn(oldID);
            UserController.updateUserElasticSearch(renter);
        }

        bids = new BidList();
        item.setBids(bids);
        item.setAvailability(false);
        item.setRenterID(renterID);
        owner.removeMyItem(newID);
        ItemController.updateItemElasticSearch(item);
        newID = item.getID();
        owner.addMyItem(newID);
        renter.addItemBorrowed(newID);
        UserController.updateUserElasticSearch(renter);
        UserController.updateUserElasticSearch(owner);
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
