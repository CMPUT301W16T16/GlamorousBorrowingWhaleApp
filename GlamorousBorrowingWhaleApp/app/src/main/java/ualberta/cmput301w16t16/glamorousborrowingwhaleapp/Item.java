package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

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
    private BidList bids;
    //private User owner;
    private String ownerID;
    protected String ID;

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

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public void setPhoto(byte[] photoByteArray) { photo = photoByteArray; }

    public byte[] getPhoto() { return photo; }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

    public String getOwnerID() { return ownerID; }

    public void setOwnerID(String ownerID) { this.ownerID = ownerID; }

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

//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }

    public void addBid(Bid bid){
        this.bids.addBid(bid);
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
