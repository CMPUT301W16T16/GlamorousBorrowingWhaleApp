package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Martina on 16-02-12.
 */
public class SignInActivityUITest extends ActivityInstrumentationTestCase2<SignInActivity> {
    Instrumentation instrumentation;
    SignInActivity activity = getActivity();
    EditText usernameInput;

    public SignInActivityUITest() {
        super(SignInActivity.class);
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        usernameInput = ((EditText) activity.findViewById(R.id.username));
    }


}
