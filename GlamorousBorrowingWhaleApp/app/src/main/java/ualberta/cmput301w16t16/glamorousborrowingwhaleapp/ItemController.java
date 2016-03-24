package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

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

    public ItemController(Item item) {
        ItemController.item = item;
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
}
