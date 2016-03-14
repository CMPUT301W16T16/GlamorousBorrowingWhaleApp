package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by erin on 13/03/16.
 */
public class ItemListTest extends ApplicationTestCase<Application> {
    public ItemListTest() { super(Application.class); }

    // assert that an item can be added to the item List
    public void testAdd() {
        Item item = new Item();
        ItemList itemList = new ItemList();
        itemList.add(item);

        // assert that the item was added to the itemList
        assertTrue(itemList.hasItem(item));
    }

    public void testRemove() {
        Item item = new Item();
        ItemList itemList = new ItemList();
        itemList.add(item);

        // assert that the item is currently in the itemList
        assertTrue(itemList.hasItem(item));

        //assert that nothing changes when trying to remove something that is not in the item list
        Item fakeItem = new Item();
        itemList.remove(fakeItem);
        assertTrue(itemList.hasItem(item));
        assertFalse(itemList.hasItem(fakeItem));

        // remove the item
        itemList.remove(item);

        // assert that the item was removed
        assertFalse(itemList.hasItem(item));

        // assert that the itemList is now empty
        assertTrue(itemList.isEmpty());
    }

    public void testGetItemList() {
        Item item = new Item();
        ItemList itemList = new ItemList();

        // assert that nothing is returned since itemList is empty
        assertNull(itemList.getItemList());

        // add the item
        itemList.add(item);

        // assert that something is returned since itemList is not empty
        assertNotNull(itemList.getItemList());
    }

    public void testSetItemList() {
        Item item = new Item();
        ItemList itemList = new ItemList();

        ArrayList<Item> array = new ArrayList<Item>();
        array.add(item);
        itemList.setItemList(array);

        // check that the item list is equal to the array is was set as
        assertEquals(itemList.getItemList(), array);

    }
}
