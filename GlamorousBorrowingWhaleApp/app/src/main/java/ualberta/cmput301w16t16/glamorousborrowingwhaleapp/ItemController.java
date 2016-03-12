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

    public ItemController(Item item) {
        ItemController.item = item;
    }

    public static Item getItem() {
        return ItemController.item;
    }

    public static void setItem(Item item) {
        ItemController.item = item;
    }

    public static void deleteItem(Item item) {
        //TODO implement a way to delete an item
    }

    public static void setEmpty() {
        ItemController.item = null;
        //Hi this right here is neat as it sets the item to null in a controlled manner
        //for use throughout the other classes. Hopefully they all have methods to watch
        //for null.
    }
}
