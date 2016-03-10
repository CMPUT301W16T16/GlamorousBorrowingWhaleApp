package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Martina on 16-02-29.
 */
public class User extends AppCompatActivity implements Serializable {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    //private Drawable picture [Adam: See directly below];
    private String profilePictureRef;
    //http://developer.android.com/reference/android/graphics/Bitmap.html
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

    public String getProfilePictureRef() { //temp placeholder
        return profilePictureRef;
    }

    public void setProfilePictureRef(String profilePictureRef) { //temp placeholder
        //this setter will definitely need some tweaking depending on how the bitmap/drawable/whatever
        //we end up using plays with the type(s) set.
        this.profilePictureRef = profilePictureRef;
    }
}