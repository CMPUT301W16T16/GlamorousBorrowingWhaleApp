package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;

/**
 * Created by Martina on 16-02-29.
 */
public class BidList {
    //note: added a constructor here for testing
    private ArrayList<Bid> bids = new ArrayList<Bid>();

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
