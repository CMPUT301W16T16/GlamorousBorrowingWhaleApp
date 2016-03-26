package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

/**
 * Created by Martina on 16-03-10.
 * Holds the current + another user so it can be referenced throughout the app
 * @author martina
 */

//Is the controller for the user. Persistent throughout the user session.
public class UserController {
    private static User user;
    // I created this to be able to use UserController with other users and pass them around like
    // we use ItemController. This may not be the best place for it though
    private static User secondaryUser;

    public UserController(User user) {
        UserController.user = user;
    }

    public static User getUser() {
        return UserController.user;
    }

    public static void setUser(User user) {
        UserController.user = user;
    }

    public static User getSecondaryUser() {
        return secondaryUser;
    }

    public static void setSecondaryUser(User secondaryUser) {
        UserController.secondaryUser = secondaryUser;
    }

    // checks given password against secondary user's password since secondary user
    // is returned from elastic search
    public static boolean checkPassword(String password) {
        return secondaryUser.getPassword().equals(password);
    }
}
