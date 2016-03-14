package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class ItemControllerTest extends ApplicationTestCase<Application> {
    public ItemControllerTest() { super(Application.class); }

    // test that the current item in UserController will be updated when the item is updated
    public void testItemController() {
        Item item = new Item();
        item.setTitle("old title");
        ItemController.setItem(item);

        // assert that the current item is accurate
        assertEquals(ItemController.getItem(), item);

        // change the item
        item.setTitle("new title");

        // assert that the ItemController changed with the updated item
        assertEquals(ItemController.getItem(), item);
    }
}
