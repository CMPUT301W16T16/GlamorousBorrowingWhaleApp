package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
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
    private byte[] photo;
    protected String ID;

    //working on
    private ArrayList<String> itemsBidOn;
    //private ItemList itemsBorrowing;
    // i want to switch the ItemList over into a String[]
    private ArrayList<String> itemsBorrowed;
    //private ItemList itemsRenting;
    // i want to switch the ItemList over into a String[]
    private ArrayList<String> myItems;
    //                                                              end


//    public User() {
//        this.myItems = new ArrayList<>();
//        this.itemsBorrowed = new ArrayList<>();
//    }


    public User() { }

    public User(String username, String password, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.myItems = new ArrayList<>();
        this.itemsBorrowed = new ArrayList<>();
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