package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Martina on 16-02-29.
 * This class, like Item, contains all relevant information about a User
 * (name, address etc). It is used to obtain information about a user in
 * SearchResultsActivity. A user is denoted by a username.
 * What can be serialized into JSON will be stored in the object directly, what cannot will have
 * a reference stored into the object, and the content will be retrieved when needed (eg.
 * Thing Pictures from the server). UserController is the controller for interaction by the app.
 * @author adam, andrew, erin, laura, martina
 * @see SearchResultsActivity
 */

public class User extends AppCompatActivity implements Serializable {
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private Boolean notification;
    private byte[] photo;
    protected String ID;

    // itemsBidOn = items that this user has bid on (items that belong to other people)
    // itemsBorrowed = items that this user is currently borrowing
    // myItems = items that this user owns (and that other users may have bid on)

    //working on
    private ArrayList<String> itemsBidOn;
    //private ItemList itemsBorrowing;
    // i want to switch the ItemList over into a String[]
    private ArrayList<String> itemsBorrowed;
    //private ItemList itemsRenting;
    // i want to switch the ItemList over into a String[]
    private ArrayList<String> myItems;
    //                                                              end

    // being saved until they can be pushed to elastic search
    private ArrayList<Item> offlineItems = new ArrayList<>();

    // this will be used for ratings. My idea atm is for each user to have one of these
    // array lists and when an item is set to returned (we should deal with that also)
    // that item is appended to this and next time this user logs in something pops up to
    // prompt them to rate it --erin
    //private ArrayList<Item> pendingRatings;

    public User() {
        this.itemsBidOn = new ArrayList<>();
        this.itemsBorrowed = new ArrayList<>();
        this.myItems = new ArrayList<>();
        this.offlineItems = new ArrayList<>();
    }

    public ArrayList<Item> getOfflineItems() {
        return offlineItems;
    }

    public void setOfflineItems(ArrayList<Item> offlineItems) {
        this.offlineItems = offlineItems;
    }

    public void addOfflineItem(Item item) {
        this.offlineItems.add(item);
    }

    public void removeOfflineItem(Item item) {
        this.offlineItems.remove(item);
    }

    //    public User() {
//        this.myItems = new ArrayList<>();
//        this.itemsBorrowed = new ArrayList<>();
//    }

    public User(String username, String password, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.myItems = new ArrayList<>();
        this.itemsBorrowed = new ArrayList<>();
        this.itemsBidOn = new ArrayList<>();
        this.offlineItems = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(byte[] photoByteArray) { photo = photoByteArray; }

    public byte[] getPhoto() { return photo; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    /*public ArrayList<Item> getPendingRatings() {
        return pendingRatings;
    }

    public void setPendingRatings(ArrayList<Item> pendingRatings) {
        this.pendingRatings = pendingRatings;
    }*/

    public ArrayList<String> getItemsBorrowed() {
        return itemsBorrowed;
    }

    public void setItemsBorrowed(ArrayList<String> itemsBorrowed) {
        this.itemsBorrowed = itemsBorrowed;
    }

    public void addItemBorrowed(String item) {
        itemsBorrowed.add(item);
    }

    public void removeItemBorrowed(String item) {
        itemsBorrowed.remove(item);
    }


    public ArrayList<String> getMyItems() {
        return myItems;
    }

    public void setMyItems(ArrayList<String> itemsRented) {
        this.myItems = itemsRented;
    }

    public void addMyItem(String itemID) {
        myItems.add(itemID);
    }

    public void removeMyItem(String itemID) {
        myItems.remove(itemID);
    }


    public ArrayList<String> getItemsBidOn() {
        return itemsBidOn;
    }

    public void setItemsBidOn(ArrayList<String> itemsBidOn) {
        this.itemsBidOn = itemsBidOn;
    }

    public void addItemBidOn(String item) {
        this.itemsBidOn.add(item);
    }

    public void removeItemBidOn(String item) {
        this.itemsBidOn.remove(item);
    }


}