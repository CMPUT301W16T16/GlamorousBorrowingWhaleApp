package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Martina on 16-03-13.
 */
public class ElasticSearchTest extends ApplicationTestCase<Application> {
    public ElasticSearchTest() {
        super(Application.class);
    }

    // assert that a user can be added to the elasticsearch
    public void testElasticAddUser() {

        // creating the user and their attributes
        Item item1 = new Item();
        item1.setTitle("baseball bat");

        Item item2 = new Item();
        item2.setTitle("skates");

        ItemList ib = new ItemList();
        ib.add(item1);

        ItemList ir = new ItemList();
        ir.add(item2);

        String username = "rosey";
        User user = new User(username, "rosey@gmail.com", "123-456-7890");
        user.setItemsBorrowing(ib);
        user.setItemsRenting(ir);

        new ElasticSearch.elasticAddUser().execute(user);

        // assert that the user is now in elastic search and can be retrieved
        User returnedUser = ElasticSearch.elasticGetUser().execute(username);
        assertEquals(user, returnedUser);
    }

    // assert that a user can be retrieved using their username
    public void testElasticGetUser() {

        // creating the first user and their attributes
        Item item1 = new Item();
        item1.setTitle("baseball bat");

        Item item2 = new Item();
        item2.setTitle("skates");

        ItemList ib = new ItemList();
        ib.add(item1);

        ItemList ir = new ItemList();
        ir.add(item2);

        String username = "rosey";
        User user = new User(username, "rosey@gmail.com", "123-456-7890");
        user.setItemsBorrowing(ib);
        user.setItemsRenting(ir);

        // adding the user to elastic search
        new ElasticSearch.elasticAddUser().execute(user);

        // <not executed correctly>
        // assert that the user is now in elastic search and can be retrieved
        User returnedUser = ElasticSearch.elasticGetUser().execute(username);
        assertEquals(user, returnedUser);

        // creating the second user and their attributes
        Item item3 = new Item();
        item3.setTitle("baseball bat");

        Item item4 = new Item();
        item4.setTitle("skates");

        ItemList ib2 = new ItemList();
        ib2.add(item3);

        ItemList ir2 = new ItemList();
        ir2.add(item4);

        String username2 = "arrow";
        User user2 = new User(username2, "arrow@gmail.com", "123-456-7890");
        user.setItemsBorrowing(ib2);
        user.setItemsRenting(ir2);

        // adding the second user
        new ElasticSearch.elasticAddUser().execute(user);

        // <these below are not executed correctly>
        // assert that a specific user can be retrieved from elastic search from multiple options
        User returnedUser2 = ElasticSearch.elasticGetUser().execute(username2);
        assertEquals(user2, returnedUser2);

        // assert that nothing is returned if the user is not in elastic search
        User returnedUser3 = ElasticSearch.elasticGetUser().execute("NoSuchUsername");
        assertNull(returnedUser3);
    }

    // assert that the correct items are returned as search results
    public void testElasticGetItems() {
        Item item1 = new Item();
        item1.setTitle("one");

        Item item2 = new Item();
        item2.setTitle("two");

        Item item3 = new Item();
        item3.setTitle("three");

        // assert that nothing is returned when elastic search is empty
        ArrayList<String> params = new ArrayList<String>();
        params.add("one");
        ItemList items = new ElasticSearch.elasticGetItems().execute(params);

        ItemList expectedResult = new ItemList();
        assertEquals(items, expectedResult);

        // adding all three items to the elastic search
        new ElasticSearch.elasticAddItem().execute(item1);
        new ElasticSearch.elasticAddItem().execute(item2);
        new ElasticSearch.elasticAddItem().execute(item3);

        // assert that the correct items are returned when searching
        params.clear();
        params.add("one");
        ItemList items1 = new ElasticSearch.elasticGetItems().execute(params);

        expectedResult.clear();
        expectedResult.add(item1);
        assertEquals(items, expectedResult);

        //assert that nothing is returned if there is nothing matching in elastic search
        params.clear();
        params.add("four");
        ItemList items2 = new ElasticSearch.elasticGetItems().execute(params);

        expectedResult.clear();
        assertEquals(items2, expectedResult);
    }

    // assert that items can be added to elastic search
    public void testElasticAddItem() {
        Item item = new Item();
        item.setTitle("one");

        new ElasticSearch.elasticAddItem().execute(item);

        // assert that the item was added to elastic search
        ArrayList<String> params = new ArrayList<String>();
        params.add("one");
        ItemList items = new ElasticSearch.elasticGetItems().execute(params);

        assertTrue(items.hasItem(item));
    }

    // assert that an item or user can successfully be deleted from elastic search
    public void testElasticDelete() {
        // assert that an item can be deleted from elastic search
        Item item = new Item();
        item.setTitle("one");

        new ElasticSearch.elasticAddItem().execute(item);

        // assert that the item was added to elastic search
        ArrayList<String> params = new ArrayList<String>();
        params.add("one");
        ItemList items = new ElasticSearch.elasticGetItems().execute(params);

        assertTrue(items.hasItem(item));

        // delete the item from elastic search
        new ElasticSearch.elasticDelete().execute(item);

        ItemList items2 = new ElasticSearch.elasticGetItems().execute(params);

        assertFalse(items2.hasItem(item));

        // assert that a user can be deleted from elastic search
        // creating the first user and their attributes
        Item item1 = new Item();
        item1.setTitle("baseball bat");

        Item item2 = new Item();
        item2.setTitle("skates");

        ItemList ib = new ItemList();
        ib.add(item1);

        ItemList ir = new ItemList();
        ir.add(item2);

        String username = "rosey";
        User user = new User(username, "rosey@gmail.com", "123-456-7890");
        user.setItemsBorrowing(ib);
        user.setItemsRenting(ir);

        // adding the user to elastic search
        new ElasticSearch.elasticAddUser().execute(user);

        // <not executed correctly>
        // assert that the user is now in elastic search and can be deleted
        User returnedUser = new ElasticSearch.elasticGetUser().execute(username);
        assertEquals(user, returnedUser);

        // deleting the user from elastic search
        new ElasticSearch.elasticDelete().execute(username);

        // assert that the user is no longer in elastic search
        User returnedUser2 = new ElasticSearch.elasticGetUser().execute(username);
        assertNull(returnedUser);

    }

    public void testElasticFind() {

    }
}
