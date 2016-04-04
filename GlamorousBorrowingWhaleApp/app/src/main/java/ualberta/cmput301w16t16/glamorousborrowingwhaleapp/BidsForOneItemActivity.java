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
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_for_one_item);

        item = ItemController.getItem();
        user = UserController.getUser();

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

                Button acceptButton = (Button) view.findViewById(R.id.incomingBidsAccept);
                Button rejectButton = (Button) view.findViewById(R.id.incomingBidsReject);

                acceptButton.setClickable(true);
                rejectButton.setClickable(true);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MARTINA", "trying to accept the bid");
                        BidController.acceptBid(selectedBid, item, user);
                        bids = item.getBids().getBids();
                        Collections.sort(bids);
                        adapter.notifyDataSetChanged();
                    }
                });
                rejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MARTINA", "trying to reject the bid");
                        BidController.rejectBid(selectedBid, item, user);
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

            // adding the amount that was bid
            TextView amountBid = (TextView) view.findViewById(R.id.incomingBidsAmountBid);
            amountBid.setText("Amount Bid:\n $" + String.format("%.2f", bid.getBidAmount()) + "/Hour");
            return view;
        }
    }
}
