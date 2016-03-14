package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.EditText;

/**
 * Created by erin on 13/03/16.
 */
public class MyBorrowedItemActivityUITest extends ActivityInstrumentationTestCase2<MyBorrowedItemActivity> {
    public MyBorrowedItemActivityUITest() { super(MyBorrowedItemActivity.class); }
    Instrumentation instrumentation;
    Activity activity = getActivity();
    EditText textInput;

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }
}
