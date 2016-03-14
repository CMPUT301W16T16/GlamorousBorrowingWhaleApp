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
        Item item2 = new Item();
        Bid bid = new Bid(item, 5.00);
        Bid bid2 = new Bid(item2, 10.00);

        BidList bidList = new BidList();
        bidList.addBid(bid);
        bidList.addBid(bid2);
        // assert that the bids are in bidList before trying to delete them
        assertTrue(bidList.hasBid(bid));
        assertTrue(bidList.hasBid(bid2));

        //assert that bid2 was deleted and bid still remains
        bidList.removeBid(bid2);
        assertFalse(bidList.hasBid(bid2));
        assertTrue(bidList.hasBid(bid));

        //assert that bid was deleted and that bidList is now empty
        bidList.removeBid(bid);
        assertFalse(bidList.hasBid(bid));
        assertTrue(bidList.isEmpty());
    }

    public void testGetHighestBid() {
        Item item = new Item();
        Item item2 = new Item();
        item2.setTitle("two");
        Bid bid = new Bid(item, 5.00);
        Bid bid2 = new Bid(item2, 10.00);

        BidList bidList = new BidList();
        bidList.addBid(bid);
        bidList.addBid(bid2);

        // asserting that the highest bid is actually returned
        Bid highestBid = bidList.getHighestBid();
        assertTrue(highestBid.getBidAmount() == 10.00);

        Item item3 = new Item();
        item3.setTitle("three");
        Bid bid3 = new Bid(item3, 10.00);
        bidList.addBid(bid3);

        // checking that when two bids have the same amount, the earlier one is returned
        Bid highestBid2 = bidList.getHighestBid();
        assertTrue(highestBid2.getBidAmount() == 10.00);
        assertTrue(highestBid2.getItem().getTitle().equals("two"));
    }


}
