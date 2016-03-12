package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

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
    private Date startDate;
    private Date endDate;
    private Boolean isAccepted;
    private User owner;
    private User renter;
    private Item item;
    private double bidAmount = 0; //bid of 0 for no bids yet

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public void setBidAmount(long bidAmount) {
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
