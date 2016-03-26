package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.sql.Time;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Adam on 16-03-11.
 * This class is a controller for the current persistent item in the app.
 * An additional function is it may set the controller item as null if there is
 * no item to get from the current User.
 * @author adam, andrew, erin, laura, martina
 */
public class ItemController {

    private static Item item;
    private static ItemList itemList;

    public ItemController() { }

    public ItemController(Item item, ItemList itemList) {
        ItemController.item = item;
        ItemController.itemList = itemList;
    }

    public static Item getItem() {
        return ItemController.item;
    }

    public static void setItem(Item item) {
        ItemController.item = item;
    }

    public static ItemList getItemList() {
        return itemList;
    }

    public static void setItemList(ItemList itemList) {
        ItemController.itemList = itemList;
    }

    // hand this method an item and it will update the item's data on Elastic Search
    public static void updateItemElasticSearch(Item item) {
        try {
            new ElasticSearch.elasticDeleteItem().execute(item).get(1, TimeUnit.DAYS);
            new ElasticSearch.elasticAddItemUsingID().execute(item).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
