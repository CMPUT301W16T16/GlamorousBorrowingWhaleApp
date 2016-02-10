package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.widget.ListView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    // 01.01.01 adding equipment to your list of owned equipment
    public void testAddEquipment() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);

        assertTrue(el.has(ep));
    }

    // 01.02.01 viewing your list of owned equipment and their descriptions and statuses
    public void testViewOwnedEquipment() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);
    }

    

}