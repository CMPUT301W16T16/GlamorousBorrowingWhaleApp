package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.test.ApplicationTestCase;

import java.io.ByteArrayOutputStream;

/**
 * Created by erin on 13/03/16.
 */
public class ItemTest extends ApplicationTestCase<Application> {
    public ItemTest() { super(Application.class); }

    public void setPhotoTest() {

    }
    public void getPhotoTest() {

    }
    public void getHighestBidTest() {
        Item item1 = new Item();

        // assert that nothing is returned since there are no bids
        assertNull(item1.getHighestBid());

        Bid bid1 = new Bid(item1, 100.0);
        Bid bid2 = new Bid(item1, 5.00);
        item1.addBid(bid1);
        item1.addBid(bid2);

        // assert that the highest bid is returned
        assertEquals(item1.getHighestBid(), bid1);
    }
    public void getHighestBidAmountTest() {
        Item item1 = new Item();

        // assert that nothing is returned since there are no bids
        assertNull(item1.getHighestBidAmount());

        Bid bid1 = new Bid(item1, 100.0);
        Bid bid2 = new Bid(item1, 5.00);
        item1.addBid(bid1);
        item1.addBid(bid2);

        // assert that the highest bid amount is returned
        assertEquals(item1.getHighestBidAmount(), 100.0);
    }

    public void addBidTest() {
        Item item = new Item();
        Bid bid = new Bid(item, 5.00);
        BidList bidList = new BidList();
        bidList.addBid(bid);

        //assert that the item currently has no bids
        assertNull(item.getBids());

        // assert that the bid was added to the item
        item.addBid(bid);
        assertEquals(item.getBids(), bidList);
    }

    public void toStringTest() {
        Item item = new Item();
        item.setTitle("skates");
        item.setSize("women's US 8");

        // assert the the returned string is as expected
        assertEquals(item.toString(), "skates; women's US 8");

    }
}
