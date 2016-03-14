package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class UseControllerTest extends ApplicationTestCase<Application> {
    public UseControllerTest() { super(Application.class); }

    // assert that a user can be saved and retrieved
    public void testRetrieveUser() {
        User user = new User("joey", "joey@gmail.com", "123-456-7890");

        // assert that there is no current user
        assertNull(UserController.getUser());

        // set user as the current user
        UserController.setUser(user);

        // assert that the user is the current user and can be retrieved
        User returnedUser = UserController.getUser();

        assertEquals(user, returnedUser);
        assertEquals(returnedUser.getName(), "joey");
    }
}
