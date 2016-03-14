package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;


/**
 * Created by Martina on 16-03-10.
 * Holds the current + another user so it can be referenced throughout the app
 */
//Is the controller for the user. Persistent throughout the user session.
public class UserController {
    private static User user;
    private static User otherUser;

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

    public static User getOtherUser() {
        return UserController.otherUser;
    }

    public static void setOtherUser(User otherUser) {
        UserController.otherUser = otherUser;
    }
/*      otherUser.setName("Michelangelo Ferdinand");
        otherUser.setEmailAddress("ilikepizza2321@aol.com");
        otherUser.setPhoneNumber("18002672001");*/
}
