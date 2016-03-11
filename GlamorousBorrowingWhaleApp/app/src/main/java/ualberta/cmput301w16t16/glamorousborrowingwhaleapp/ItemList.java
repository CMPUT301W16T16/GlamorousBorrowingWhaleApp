package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;

/**
 * Created by Martina on 16-02-29.
 */

//ADT for ItemList control
public class ItemList {
    private ArrayList<Item> itemList = new ArrayList<>();
    //note - if setItemList is called, then this is overwritten. Problem? Good Question.

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
}
