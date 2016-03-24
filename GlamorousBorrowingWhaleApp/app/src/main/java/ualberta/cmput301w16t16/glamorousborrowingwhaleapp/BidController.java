package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.util.Log;

/**
 * Created by Martina on 16-03-21.
 * This class is a controller for the current persistent bid in the app.
 * An additional function is it may set the controller bid as null if there is
 * no bid to get from the current User/Item
 *  @author adam, andrew, erin, laura, martina.
 */

public class BidController {

    private static BidList bidList;

    public BidController(Bid bid) {
        BidController.bidList = bidList;
    }

    public static BidList getBidList() {
        return BidController.bidList;
    }

    public static void setBidList(BidList bidList) {
        BidController.bidList = bidList;
    }
}
