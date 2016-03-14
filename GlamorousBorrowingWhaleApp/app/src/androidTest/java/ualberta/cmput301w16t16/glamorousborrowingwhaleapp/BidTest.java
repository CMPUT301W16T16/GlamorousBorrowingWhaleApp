package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class BidTest extends ApplicationTestCase<Application> {
    public BidTest() { super(Application.class); }

    // testing that the bid is updated when the item is changed
    public void testChangeItem() {
        Item item = new Item();
        item.setTitle("initial title");
        Bid bid = new Bid(item, 5.00);

        // assert that the title is set to the initial title
        assertTrue(bid.getItem().getTitle().equals("initial title"));

        // changing the item
        item.setTitle("final title");

        // assert that the title is updated in the bid
        assertTrue(bid.getItem().getTitle().equals("final title"));
    }

}
