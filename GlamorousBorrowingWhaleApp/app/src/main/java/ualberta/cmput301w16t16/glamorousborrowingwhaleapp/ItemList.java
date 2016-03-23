package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Martina on 16-02-29.
 * This class contains a few functions for a list of items to make it easier
 * to control. Also benefits as a sort of ADT to the app.
 * @author adam, andrew, erin, laura, martina
 */
public class ItemList implements Iterable<Item> {
    private ArrayList<Item> itemList = new ArrayList<Item>();
    //note - if setItemList is called, then this is overwritten. Problem? Good Question.
    private int currentSize = itemList.size();

    public void add(Item item) {
        itemList.add(item);
    }

    public void remove(Item item) {
        itemList.remove(item);
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public int getLength() {
        return itemList.size();
    }

    /**
     * This method allows you to iterate over item list
     */

    // (useful when converting to JSON when writing to ElasticSearch)
    // taken Mar-13-2016 from http://stackoverflow.com/questions/5849154/can-we-write-our-own-iterator-in-java
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize && itemList.get(currentIndex) != null;
            }

            @Override
            public Item next() {
                return itemList.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    // clears everything in the itemList (mostly used in testing)
    public void clear() {
        itemList.clear();
    }

    // checks to see if an item is in the itemList (mostly used in testing)
    public Boolean hasItem(Item item) {
        return itemList.contains(item);
    }

    // checks to see if the itemList is empty
    public Boolean isEmpty() {
        return itemList.isEmpty();
    }
}
