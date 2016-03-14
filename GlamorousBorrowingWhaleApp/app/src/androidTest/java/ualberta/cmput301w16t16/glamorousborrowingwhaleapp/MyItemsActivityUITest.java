package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Martina on 16-02-12.
 */
public class MyItemsActivityUITest extends ActivityInstrumentationTestCase2 {
        Instrumentation instrumentation;
        Activity activity;
        EditText textInput;
        public MyItemsActivityUITest() {
            super(SignInActivity.class);
        }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        textInput = ((EditText) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.description));
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }

    // fills in the text field and clicks the save button
    public void makeItem(String text) {
        assertNotNull(activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.save));
        textInput.setText(text);
        ((Button) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.save)).performClick();
    }

    public void setFirstBidsTest() {
        // This is just a temporary function
    }

}
