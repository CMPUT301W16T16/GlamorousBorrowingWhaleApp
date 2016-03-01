package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Martina on 16-02-29.
 */
public class User {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    //private Drawable picture;
    private ItemList itemsBorrowing;
    private ItemList itemsRenting;

    // need to include picture
    public User(String name, String emailAddress, String phoneNumber) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    // need to add picture getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ItemList getItemsBorrowing() {
        return itemsBorrowing;
    }

    public void setItemsBorrowing(ItemList itemsBorrowing) {
        this.itemsBorrowing = itemsBorrowing;
    }

    public ItemList getItemsRenting() {
        return itemsRenting;
    }

    public void setItemsRenting(ItemList itemsRenting) {
        this.itemsRenting = itemsRenting;
    }

    public void addItemBorrowing(Item item) {
        itemsBorrowing.add(item);
    }

    public void removeItemBorrowing(Item item) {
        itemsBorrowing.remove(item);
    }

    public void addItemRenting(Item item) {
        itemsRenting.add(item);
    }

    public void removeItemRenting(Item item) {
        itemsRenting.remove(item);
    }
}