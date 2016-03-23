package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Martina on 16-02-29.
 * This class, like Item, contains all relevant information about a User
 * (name,address etc). It is used to obtain information about a user in
 * SearchResultsActivity. This class also contains a JestId for when the users
 * information is saved to the server.
 * @author adam, andrew, erin, laura, martina
 * @see SearchResultsActivity
 */

/* User Class contains all the information about a particular user, denoted by a USERNAME.
 * What can be serialized into JSON will be stored in the object directly, what cannot will have
 * a reference stored into the object, and the content will be retrieved when needed (eg.
 * Thing Pictures from the server). UserController is the controller for interaction by the app.
 */
public class User extends AppCompatActivity implements Serializable {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private byte[] photo;
    private ItemList itemsBorrowing;
    private ItemList itemsRenting;
    protected String ID;

    public User() {

    }

    public User(String name, String emailAddress, String phoneNumber) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public void setPhoto(byte[] photoByteArray) { photo = photoByteArray; }

    public byte[] getPhoto() { return photo; }

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
        //seems like this will overwrite the existing list if there is one FYI
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

    //public String getProfilePictureRef() { //temp placeholder
        // return profilePictureRef;
    //}

    //public void setProfilePictureRef(String profilePictureRef) { //temp placeholder
        // this setter will definitely need some tweaking depending on how the bitmap/drawable/whatever
        // we end up using plays with the type(s) set.
        // this.profilePictureRef = profilePictureRef;
    //}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}