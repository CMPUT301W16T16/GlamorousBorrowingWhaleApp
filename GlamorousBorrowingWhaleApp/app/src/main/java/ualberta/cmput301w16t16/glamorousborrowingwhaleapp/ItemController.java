package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

/**
 * Created by Adam on 11/03/2016.
 */
//Similar to the User, this ItemController persists throughout the app
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
}
