package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.util.Log;

import java.util.ArrayList;

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

    public static void rejectBid(Bid selectedBid, Item item, User owner) {
        String renterID = selectedBid.getRenterID();
        User renter = UserController.getUserByIDElasticSearch(renterID);
        renter.removeItemBidOn(item.getID());
        UserController.updateUserElasticSearch(renter);

        BidList bids = item.getBids();
        ArrayList<Bid> bidsToRemove = new ArrayList<>();
        for (Bid bid : bids.getBids()) {
            if (bid.getItemID().equals(selectedBid.getItemID())) {
                bidsToRemove.add(bid);
            }
        }
        for (Bid bid : bidsToRemove) {
            bids.removeBid(bid);
        }
        item.setBids(bids);

        owner.removeMyItem(item.getID());
        ItemController.updateItemElasticSearch(item);
        owner.addMyItem(item.getID());
        UserController.updateUserElasticSearch(owner);
    }

    public static void acceptBid(Bid selectedBid, Item item, User owner) {
        String renterID = selectedBid.getRenterID();
        User renter = UserController.getUserByIDElasticSearch(renterID);
        renter.removeItemBidOn(item.getID());

        BidList bids = item.getBids();

        for (Bid bid : bids.getBids()) {
            renterID = bid.getRenterID();
            renter = UserController.getUserByIDElasticSearch(renterID);
            renter.removeItemBidOn(item.getID());
            UserController.updateUserElasticSearch(renter);
        }

        bids = new BidList();
        item.setBids(bids);
        item.setAvailability(false);
        item.setRenterID(renterID);
        owner.removeMyItem(item.getID());
        ItemController.updateItemElasticSearch(item);
        owner.addMyItem(item.getID());
        renter.addItemBorrowed(item.getID());
        UserController.updateUserElasticSearch(renter);
        UserController.updateUserElasticSearch(owner);
    }
}
