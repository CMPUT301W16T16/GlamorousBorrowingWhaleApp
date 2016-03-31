package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ArrayList<Bid> myBids;
    private ArrayAdapter<Bid> adapter;
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
        ArrayList<String> myItemsArray = user.getItemsBidOn();
        myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        ItemController.getItemsByIDElasticSearch(myItemsList);
        myItems = ItemController.getItemList().getItemList();

        // get all of the bids held by all of the items
        for (Item item: myItems) {
            ArrayList<Bid> itemBids = item.getBids().getBids();
            for (Bid bid: itemBids) {
                myBids.add(bid); //npe
            }
        }

        adapter = new CustomMyBidsAdapter(this, myBids);
        myBidsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class CustomMyBidsAdapter extends ArrayAdapter<Bid> {

        public CustomMyBidsAdapter(Context context, ArrayList<Bid> bids) {
            super(context, R.layout.custom_incoming_bids_row, bids);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.custom_incoming_bids_row, parent, false);
            Bid bid = getItem(position);

            // TODO: figure out how to reimplement this, considering Bids DO NOT HAVE ITEMS
            // adding the item title
//        TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
//        itemTitle.setText(bid.getItem().getTitle());

            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setText("temp text s.t. app doesn't crash");

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid: $" + String.format("%.2f", bid.getBidAmount()));
            return view;
        }
    }
}

