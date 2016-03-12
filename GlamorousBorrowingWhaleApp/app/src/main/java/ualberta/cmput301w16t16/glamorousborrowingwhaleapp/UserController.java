package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

/**
 * Created by Martina on 16-03-10.
 * Honestly idk what this does
 */
//Is the controller for the user. Persistent throughout the user session.
public class UserController {
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
}
