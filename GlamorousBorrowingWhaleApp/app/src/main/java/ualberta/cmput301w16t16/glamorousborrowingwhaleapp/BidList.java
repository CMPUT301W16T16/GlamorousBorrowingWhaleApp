package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;

/**
 * Created by Martina on 16-02-29.
 */
public class BidList {
    private ArrayList<Bid> bids;

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }
}
