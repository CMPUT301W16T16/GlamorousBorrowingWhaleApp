package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Martina on 16-02-29.
 * This class keeps track of all the information relating to a bid and is
 * used by a few activities (IncomingBidsActivity, MyBidsactivity) to display
 * a bids information
 * @author adam, andrew, erin, laura, martina
 */
public class Bid {
    // expiry of a bid?
    //switched Date to Calendar type for ease of use.
    private Calendar startDate;
    private Calendar endDate;
    private Boolean isAccepted;
    private User owner;
    private User renter;
    private Item item;
    private double bidAmount;

    //This Bid must have an item passed to it (the item being bidded on)
    public Bid(Item item, double bidAmount) {
        //Constructing a new Bid
        //no ItemController maybe?
        this.item = item;
        this.owner = item.getOwner();
        this.renter = UserController.getUser();//current user is the renter
        this.bidAmount = bidAmount;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
