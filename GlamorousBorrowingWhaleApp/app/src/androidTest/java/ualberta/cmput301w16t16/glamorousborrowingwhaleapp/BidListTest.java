package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class BidListTest extends ApplicationTestCase<Application> {
    public BidListTest() { super(Application.class); }

    public void testAddBid() {
        Item item = new Item();
        Bid bid = new Bid(item, 25.00);
        BidList bidList = new BidList();

        // assert bidList is initially empty
        assertTrue(bidList.hasBid(bid));

        // assert one bid can be added
        bidList.addBid(bid);
        assertTrue(bidList.hasBid(bid));

        // assert multiple bids can be added
        Item item2 = new Item();
        Bid bid2 = new Bid(item2, 20.50);

        bidList.addBid(bid2);
        assertTrue(bidList.hasBid(bid2));
    }

    public void testDeleteBid() {
        Item item = new Item();
        Bid bid = new Bid(item, 5.00);

        BidList.
    }


}
