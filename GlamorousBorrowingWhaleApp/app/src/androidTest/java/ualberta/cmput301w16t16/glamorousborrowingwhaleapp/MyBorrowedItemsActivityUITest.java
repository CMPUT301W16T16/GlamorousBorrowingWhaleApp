package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Martina on 16-02-12.
 */
public class MyBorrowedItemsActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity = getActivity();
    public MyBorrowedItemsActivityUITest() {
        super(MyBorrowedItemsActivity.class);
    }

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
