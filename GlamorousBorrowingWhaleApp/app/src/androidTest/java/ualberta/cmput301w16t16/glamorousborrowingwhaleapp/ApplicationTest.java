package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

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

        assertFalse(el.has(ep));
        el.add(ep);

        assertTrue(el.has(ep));
    }

    // 01.02.01 viewing your list of owned equipment and their descriptions and statuses
    public void testViewOwnedEquipment() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);
    }

    // 01.03.01 As an owner of equipment, I want to view one of my pieces of equipment, its description and status.
    public void testViewItemDescription() {

    }

    // 01.04.01 As an owner of equipment, I want to edit a piece of equipment in my list of owned equipment
    public void testEditItem() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);
        el.status = "Borrowed";

        assertEquals(el.status, "Borrowed");
    }

    // 01.05.01 As an owner of equipment, I want to delete a piece of equipment from my list of owned equipment
    public void testDeleteEquipment() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);

        assertTrue(el.has(ep));
        el.delete(ep);
        assertFalse(el.has(ep));
    }

    // 02.01.01 As an owner or borrower of equipment, I want a piece of equipment to have a status of one of: available, bidded, or borrowed.
    public void testItemStatus() {
        EquipmentList el = new EquipmentList();
        EquipmentPiece ep = new EquipmentPiece();
        el.add(ep);
        el.status = 0;
        assertEqual(el.status, 0);

        el.status = 1;
        assertEquals(el.status, 1);

        el.status = 2;
        assertEquals(el.status, 2);
    }

    // 03.01.01 As a user, I want a profile with a unique username and my contact information.
    public void testUserContactInfo() {
        String user_name = "JohnDoe";
        String phone_number = 17805551234;
        String email_address = "JohnDoe@gmail.com";
        User user = new User(user_name, phone_number, email_address);

        assertTrue(user.user_name == user_name && user.phone_number == phone_number && user.email_address == email_address);
    }

    // 03.02.01 As a user, I want to edit the contact information in my profile.
    public void testEditUserContactInfo() {
        String user_name = "JohnDoe";
        String phone_number = 17805551234;
        String email_address = "JohnDoe@gmail.com";
        User user = new User(user_name, phone_number, email_address);

        String new_user_name = "SallySmith";
        String new_phone_number = "1234567890";
        String new_email_address = "SallySmith@gmail.com";
        user.editUserName(new_user_name);
        user.editPhoneNumber(new_phone_number);
        user.editEmailAddress(new_email_address);

        assertTrue(user.user_name == new_user_name && user.phone_number == new_phone_number && user.email_address = new_email_address);
    }

    // 03.03.01 As a user, I want to, when a username is presented for a piece of equipment, retrieve and show its contact information.
    public void testShowContactInfo() {
        ListView listView = (ListView) findViewById(R.id.contact_information);
        //

    }

    // 04.01.01 As a borrower of equipment, I want to specify a set of keywords, and search for
    // all pieces of equipment not currently borrowed whose description contains all the keywords.
    public void testSearchEquipmentKeywords() {


    }

    // 04.02.01 As a borrower of equipment, I want search results to show each piece
    // of equipment not currently borrowed with its description, owner username, and status.
    public void testSearchUnborrowedEquipment() {

    }

    // 05.01.01 As a borrower of equipment, I want to bid for an available piece of equipment, with a monetary rate (in dollars per hour).
    public void testBid() {

    }

    // 05.02.01 As a borrower of equipment, I want to view a list of pieces of equipment I
    // have bidded on that are pending, each piece with its description, owner username, and my bid.
    public void testViewMyBids() {

    }

    // 05.03.01 As an owner of equipment, I want to be notified of a bid.
    public void testBidNotification() {

    }

    // 05.04.01 As an owner of equipment, I want to view a list of my pieces of equipment with bids.
    public void testViewBidsOnMyEquipmentList() {

    }

    // 05.05.01 As an owner of equipment, I want to view the bids on one of my pieces of equipment.
    public void testViewBidsOnMyEquipment() {

    }

    // 05.06.01 As an owner of equipment, I want to accept a bid on one of my pieces of equipment,
    // setting its status to borrowed. (Any other bids are declined.)
    public void testAcceptBid() {

    }

    // 05.07.01 As an owner, I want to decline a bid on one of my pieces of equipment.
    public void testDeclineBid() {

    }

    // 06.01.01 As a borrower of equipment, I want to view a list of pieces of equipment
    // I am borrowing, each piece with its description and owner username.
    public void testViewBorrowingIn() {

    }

    // 06.02.01 As an owner of equipment, I want to view a list of my pieces of equipment
    // being borrowed, each piece with its description and borrower username.
    public void testViewBorrowingOut() {

    }

    // 07.01.01 As an owner of equipment, I want to set a borrowed piece of equipment to be available when it is returned.
    public void testSetAvailable() {

    }

    // 08.01.01 As an owner of equipment, I want to define new pieces of equipment while offline,
    // and push the additions once I get connectivity.
    public void testDefineEquipmentOffline() {

    }

}