package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Martina on 16-02-29.
 */
//This is an Item(Thing). Has attributes.
public class Item {
    protected String title;
    private String description;
    private String size;
    private Boolean availability;
    private Drawable picture;
    private BidList bids;
    private User owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public BidList getBids() {
        return bids;
    }

    public void setBids(BidList bids) {
        this.bids = bids;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    //This right here is to pass a string of our choosing to the view adapter in MyItemsActivity.
    //The adapter requires something to pass to a textview, so an override here works nicely.
    //That being said, I'm not sure of the implications of putting this here :\
    @Override
    public String toString() {
        return this.title + "; " + this.size;
    }
}
