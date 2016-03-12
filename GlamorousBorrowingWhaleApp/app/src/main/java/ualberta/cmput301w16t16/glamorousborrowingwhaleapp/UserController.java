package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;

/**
 * Created by Martina on 16-03-10.
 */
//Is the controller for the user. Persistent throughout the user session.
public class UserController {
    private static ArrayList<User> users = new ArrayList<User>();
    private static User user;

    //TODO will we have this ask the server for a USER?

    public UserController(User user) {
        UserController.user = user;
    }

    public static User getUser() {
        return UserController.user;
    }

    public static void setUser(User user) {
        UserController.user = user;
    }

    public static void addToUsers(User user) {
        users.add(user);
    }

    public static void removeFromUsers(User user) {
        users.remove(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        UserController.users = users;
    }
}
