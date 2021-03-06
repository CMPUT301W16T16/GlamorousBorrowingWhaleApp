package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

/**
 * Created by Martina on 16-02-29.
 * This object provides all the associated information for a piece of equipment
 * and is used to access and information about some equipment.
 * @author adam, andrew, erin, laura, martina
 */
public class Item {
    private String title;
    private String description;
    private String size;
    private Boolean availability;
    private byte[] photo;
    protected String thumbnailBase64;
    protected transient Bitmap thumbnail;
    private BidList bids;
    public RatingList ratings;
    private String sport;
    private String renterID;
    private String ownerID;
    protected String ID;
    private double latitude;
    private double longitude;



    public void Item() {
    }

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

    public void setAvailability(Boolean availability) {this.availability = availability;}

    public void setPhoto(byte[] photoByteArray) {
        photo = photoByteArray;
        thumbnailBase64 = Base64.encodeToString(photoByteArray, Base64.DEFAULT);
    }

//    public void setPhotoES(byte[] photoAsString) {
//        thumbnailBase64 = photoAsString;
//    }

    public Bitmap getPhoto() {
        thumbnailBase64 = Base64.encodeToString(photo, Base64.DEFAULT);
        if (thumbnailBase64 != null) {
            byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            thumbnail = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            return thumbnail;
        }
        return null;
    }

    public String getPhotoES() {
        return thumbnailBase64;
    }

    //public int getRating() {return rating;}
    //public void setRating(int rating) {this.rating = rating;}

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

    public String getOwnerID() { return ownerID; }

    public void setOwnerID(String ownerID) { this.ownerID = ownerID; }

    public String getRenterID() {
        return renterID;
    }

    public void setRenterID(String renterID) {
        this.renterID = renterID;
    }


    // Bid Functions
    public BidList getBids() {
        return bids;
        //Hi note that this is a BidList type!!!!!
    }

    public void setBids(BidList bids) {
        this.bids = bids;
        //Hi note that this is a BidList type!!!!!
    }

    public Bid getHighestBid() {
        return this.bids.getHighestBid();
    }

    public double getHighestBidAmount() {
        //Need to catch that null if empty
        if(bids.getBids().isEmpty()) {
            return 0;
        } else {
            return this.bids.getHighestBid().getBidAmount();
        }
    }

    public void addBid(Bid bid){
        this.bids.addBid(bid);
    }



    public String printAvailability() {
        if (availability) {
            return "Available";
        } else {
            return "Not Available";
        }
    }

    // Location Functions
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Sport Functions
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    //Rating Functions
    public RatingList getRatings(){
        return ratings;
    }

    public void setRatings(RatingList ratings){
        this.ratings = ratings;
    }

    public float calcAverageRating(RatingList ratings){
        return this.ratings.avgRating();
    }

    public void addRating(Rating rating){
        this.ratings.addRating(rating);
    }


    /**
     * Passes a string of our choosing to the view adapter in MyItemsActivity.
     * @author adam
     * @return the information of the item in a string
     */
    //This right here is to pass a string of our choosing to the view adapter in MyItemsActivity.
    //The adapter requires something to pass to a textview, so an override here works nicely.
    //That being said, I'm not sure of the implications of putting this here :\
    @Override
    public String toString() {
        return this.title + "; " + this.size;
    }
}
