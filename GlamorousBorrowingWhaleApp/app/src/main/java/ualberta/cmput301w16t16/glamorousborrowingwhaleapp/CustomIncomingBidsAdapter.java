package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom adapter that allows the ListView to display custom rows
 * @author andrew, martina
 */

public class CustomIncomingBidsAdapter extends ArrayAdapter<Bid> {

    public CustomIncomingBidsAdapter(Context context, ArrayList<Bid> bids) {
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