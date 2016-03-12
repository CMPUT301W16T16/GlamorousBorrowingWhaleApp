package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import io.searchbox.annotations.JestId;

import java.io.Serializable;

/**
 * Created by Martina on 16-02-29.
 */

/*User Class contains all the information about a particular user, denoted by a USERNAME.
What can be serialized into JSON will be stored in the object directly, what cannot will have
a reference stored into the object, and the content will be retrieved when needed (eg.
Thing Pictures from the server). UserController is the controller for interaction by the app.

 */
public class User extends AppCompatActivity implements Serializable {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    //private Drawable picture [Adam: See directly below];
    //private String profilePictureRef;
    //http://developer.android.com/reference/android/graphics/Bitmap.html
    //Initialize the arrays down here because Java sucks
    private ItemList itemsBorrowing;
    private ItemList itemsRenting;
<<<<<<< HEAD
    //ID is for elasticsearch server thing
    private String ID;
=======

    @JestId
    protected String ID;
>>>>>>> f05388df0ffde64fe3db166e0d169d15015968b6

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
        //return profilePictureRef;
    //}

    //public void setProfilePictureRef(String profilePictureRef) { //temp placeholder
        //this setter will definitely need some tweaking depending on how the bitmap/drawable/whatever
        //we end up using plays with the type(s) set.
        //this.profilePictureRef = profilePictureRef;
    //}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}