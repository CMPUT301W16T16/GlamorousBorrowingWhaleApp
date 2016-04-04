package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Collections;

public class BidsForOneItemActivity extends AppCompatActivity {
    private ArrayList<Bid> bids = new ArrayList<>();
    private ArrayAdapter<Bid> adapter;

    private Item item;
    private User owner;

    Integer pos = -1;
    Integer finalPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_for_one_item);

        item = ItemController.getItem();
        owner = UserController.getUser();

        ListView allBidsView = (ListView) findViewById(R.id.allBidsView);
        bids = item.getBids().getBids();
        Collections.sort(bids);
        adapter = new CustomBidsOnItemAdapter(this, bids);
        allBidsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        allBidsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Bid selectedBid = (Bid) parent.getAdapter().getItem(position);

                Button incomingBidsAcceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
                Button incomingBidsRejectButton = (Button) view.findViewById(R.id.incomingBidsReject);
                incomingBidsAcceptButton.setVisibility(View.VISIBLE);
                incomingBidsRejectButton.setVisibility(View.VISIBLE);

                ArrayList<String> ownersItems = owner.getMyItems();
                String[] ownersItemsString = new String[ownersItems.size()];
                ownersItemsString = ownersItems.toArray(ownersItemsString);
                ItemList itemList = ItemController.getItemsByIDElasticSearch(ownersItemsString);

                for (Item item : itemList.getItemList()) {
                    pos = 0;
                    for (Bid bid : item.getBids().getBids()) {
                        if (bid.getItemID().equals(selectedBid.getItemID())) {
                            finalPos = pos;
                        }
                    }
                }
                String newID = owner.getMyItems().get(finalPos);
                String[] items = new String[1];
                items[0] = newID;
                ItemController.getItemsByIDElasticSearch(items);
                item = ItemController.getItemList().getItemList().get(0);
                item.setID(newID); // maybe redundant now?
                ItemController.setItem(item);

                incomingBidsAcceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BidController.acceptBid(selectedBid, item, owner);
                        bids = item.getBids().getBids();
                        Log.d("MARTINA", bids.toString());
                        Collections.sort(bids);
                        adapter.notifyDataSetChanged();
                    }
                });
                incomingBidsRejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BidController.rejectBid(selectedBid, item, owner);
                        bids = item.getBids().getBids();
                        Collections.sort(bids);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public class CustomBidsOnItemAdapter extends ArrayAdapter<Bid> {

        public CustomBidsOnItemAdapter(Context context, ArrayList<Bid> bids) {
            super(context, R.layout.custom_incoming_bids_row, bids);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.custom_incoming_bids_row, parent, false);
            Bid bid = getItem(position);

            TextView itemTitle = (TextView) view.findViewById(R.id.incomingBidsItemTitle);
            itemTitle.setVisibility(View.GONE);

            Button incomingBidsAcceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
            Button incomingBidsRejectButton = (Button) view.findViewById(R.id.incomingBidsReject);
            incomingBidsAcceptButton.setVisibility(View.GONE);
            incomingBidsRejectButton.setVisibility(View.GONE);

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid:\n $" + String.format("%.2f", bid.getBidAmount()) + "/Hour");
            return view;
        }
    }
}
