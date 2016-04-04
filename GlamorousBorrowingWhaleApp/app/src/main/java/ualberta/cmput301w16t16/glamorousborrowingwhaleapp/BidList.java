package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Martina on 16-02-29.
 * This class contains a few functions for a list of bids to make it easier
 * to control (?)
 * @author adam, andrew, erin, laura, martina
 */

public class BidList {

    private ArrayList<Bid> bids = new ArrayList<>();

    public BidList() { }

    public BidList(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    private Bid highestBid;

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    /**
     * This function returns the highest bid which is displayed in an item page
     * so the user can decide which bid they want to accept
     * @author adam, andrew, erin, laura, martina
     * @return the highest bid in an items bid list
     */
    public Bid getHighestBid() {
        //TODO implement a check if the bidlist has been touched - if not, don't bother doing this loop
        //If this bidlist is not empty, do this loop to find the highest bid.
        //NOTE possible performance hog for a lot of bids. Perhaps an autocheck as a bid comes in
        //check against the current highest bid?
        if(!this.bids.isEmpty()) {
            int bidsSize = this.bids.size();
            //set highestBid as first bid in Bids
            highestBid = this.bids.get(0);
            if(bidsSize > 1){
                for(int i = 0; i < bidsSize-1; i++) {
                    if(highestBid.getBidAmount() < this.bids.get(i+1).getBidAmount()) {
                        highestBid = this.bids.get(i+1);
                    }
                }
            }
        }
        return highestBid;
    }

    // checks to see if the current bid is in the bidlist (mainly for testing)
    public Boolean hasBid(Bid bid) {
        return this.bids.contains(bid);
    }

    public Boolean isEmpty() {
        return bids.isEmpty();
    }
}
