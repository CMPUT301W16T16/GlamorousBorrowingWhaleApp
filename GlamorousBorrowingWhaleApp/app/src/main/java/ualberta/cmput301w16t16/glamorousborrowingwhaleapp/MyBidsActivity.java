package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


/**
 * This class shows the user a list of their items that they have bid on and
 * whether the bid has been accepted, rejected or is still pending. If they
 * click an item in the listView they can then view that piece of equipment
 * again.
 * @author adam, andrew, laura, martina
 */

public class MyBidsActivity extends AppCompatActivity {
    private ArrayList<BidItem> pairs = new ArrayList<>();
    private ArrayAdapter<BidItem> adapter;
    private User user = UserController.getUser();
    private ListView myBidsView;
    private String[] myItemsList;
    private ArrayList<Item> myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        setTitle("My Bids");

        // get all of the users items that they have bid on
        myBidsView = (ListView) findViewById(R.id.myBidsListView);
        getPairs();

        adapter = new CustomMyBidsAdapter(this, pairs);
        myBidsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        myBidsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /*
            The ViewAdapter saves the position of what is clicked in the dynamic list.
            Set that Item as the current Item and send the user to the MyItemActivity with the current Item
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BidItem bidItem = (BidItem) parent.getAdapter().getItem(position);
                ItemController.setItem(bidItem.item);
                Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    public class CustomMyBidsAdapter extends ArrayAdapter<BidItem> {

        public CustomMyBidsAdapter(Context context, ArrayList<BidItem> bids) {
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
            acceptButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);

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

    @Override
    protected void onResume() {
        super.onResume();
        getPairs();
        adapter.notifyDataSetChanged();
    }

    public void getPairs() {
        ArrayList<String> myItemsArray = user.getItemsBidOn();
        myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        ItemController.getItemsByIDElasticSearch(myItemsList);
        myItems = ItemController.getItemList().getItemList();

        pairs.clear();
        // get all of the bids held by all of the items
        for (Item item: myItems) {
            ArrayList<Bid> itemBids = item.getBids().getBids();
            for (Bid bid: itemBids) {
                // only add items that this user is actually bidding on
                if (bid.getRenterID().equals(UserController.getUser().getID())) {
                    BidItem pair = new BidItem(bid, item);
                    pairs.add(pair);
                    //myBids.add(bid); //npe
                }
            }
        }

        if (pairs.isEmpty()) {
            Toast.makeText(MyBidsActivity.this, "You haven't bid on any items.", Toast.LENGTH_SHORT).show();
        }
    }


}

