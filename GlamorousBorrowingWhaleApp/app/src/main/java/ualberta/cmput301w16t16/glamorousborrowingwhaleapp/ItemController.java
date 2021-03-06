package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.net.Network;
import android.util.Log;
import android.widget.ListView;

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
    private static ItemList itemList = new ItemList();

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
    // there is a problem with elasticAddItemUsingID at the moment so it's deleting but not
    // adding back in
    public static void updateItemElasticSearch(Item item) {
        ItemController.deleteItemElasticSearch(item);
        ItemController.addItemElasticSearch(item);
    }

    public static void deleteItemElasticSearch(Item item) {
        try {
            new ElasticSearch.elasticDeleteItem().execute(item).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void getItemsElasticSearch(ListView itemsListView, String query,
                                             CustomSearchResultsAdapter adapter, Context context) {
        try {
            new ElasticSearch.elasticGetItems(query, adapter,
                    context).execute(itemsListView).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static ItemList getItemsByIDElasticSearch(String[] myItemsList) {
        try {
            new ElasticSearch.elasticGetItemsByID().execute(myItemsList).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public static void addItemElasticSearch(Item item) {
        try {
            new ElasticSearch.elasticAddItem().execute(item).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
