package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by Martina on 16-03-13.
 */
public class ElasticSearchTest extends ApplicationTestCase<Application> {
    public ElasticSearchTest() {
        super(Application.class);
    }

    // assert that a user can be added to the elasticsearch
    public void testAddUser() {

        Item item1 = new Item();
        item1.setTitle("baseball bat");

        Item item2 = new Item();
        item2.setTitle("skates");

        ItemList ib = new ItemList();
        ib.add(item1);

        ItemList ir = new ItemList();
        ir.add(item2);

        User user = new User("rosey", "rosey@gmail.com", "123-456-7890");
        user.setItemsBorrowing(ib);
        user.setItemsRenting(ir);

        new ElasticSearch.elasticAddUser().execute(user);
    }
}
