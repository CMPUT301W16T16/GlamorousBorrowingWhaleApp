package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

/**
 * Created by Martina on 16-03-10.
 */
public class UserController {
    private static User user;

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
