package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.Calendar;

/**
 * Created by Martina on 16-02-29.
 * This class keeps track of all the information relating to a bid and is
 * used by a few activities (IncomingBids, MyBidsActivity) to display
 * a bids information
 * @author adam, andrew, erin, laura, martina
 */

public class Bid implements Comparable<Bid> {

    private Boolean isAccepted;
    private String ownerID;
    private String renterID;
    private String itemID;
    private double bidAmount;

    public Bid() {
    }

    //This Bid must have an item passed to it (the item being bidded on)
    public Bid(Item item, double bidAmount) {
        this.itemID = item.getID();
        this.ownerID = item.getOwnerID();
        this.renterID = UserController.getUser().getID(); //current user is the renter
        this.bidAmount = bidAmount;
    }


    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getRenterID() {
        return renterID;
    }

    public void setRenterID(String renterID) {
        this.renterID = renterID;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String toString() {
        return this.getItemID() + ";" + this.bidAmount;
    }

    @Override
    public int compareTo(Bid another) {
        if (this.bidAmount < another.bidAmount) {
            return 1;
        } else if (this.bidAmount > another.bidAmount) {
            return -1;
        }
        return 0;
    }
}
