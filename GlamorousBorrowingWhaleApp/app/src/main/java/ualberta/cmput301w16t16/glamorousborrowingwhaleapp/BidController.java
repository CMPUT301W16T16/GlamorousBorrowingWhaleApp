package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

/**
 * Created by Martina on 16-03-21.
 * This class is a controller for the current persistent bid in the app.
 * An additional function is it may set the controller bid as null if there is
 * no bid to get from the current User/Item
 *  @author adam, andrew, erin, laura, martina.
 */
public class BidController {
    private static Bid bid;
    private static BidList bidList;

    public BidController(Bid bid) {
        BidController.bid = bid;
    }

    public static Bid getBid() {
        return BidController.bid;
    }

    public static void setBid(Bid bid) {
        BidController.bid = bid;
    }

    public static void deleteBid(Bid bid) {
        //TODO implement a way to delete a bid
    }

    public static void setEmpty() {
        BidController.bid = null;
        BidController.bidList = null;
        // This sets the bid to null for use throughout the other classes.
        // Hopefully they all have methods to watch for null.
    }

    public static BidList getBidList() {
        return bidList;
    }

    public static void setBidList(BidList bidList) {
        BidController.bidList = bidList;
    }
}
