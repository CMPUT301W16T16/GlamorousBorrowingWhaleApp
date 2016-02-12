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

    // 01.01.01 As an owner, I want to add an  item into my owned item list,
    // each denoted with a clear, suitable description.

    // assert that added item is in owner's list of items
    public void testAddItem() {
        // create equipment manager
        // create an equipment item
        // check that the equipment item does not belong to the second user
        // add the equipment item to the second user's list of equipment
        // check that the equipment item is in the user's list of equipment
    }

    // assert that the add button starts the AddItem activity and returns all the inputted
    // information when it closes
    public void testAddButton() {
        // check that when the add button is clicked, the AddItem activity is started
        // enter something into each field that AddItem requires
        // check that when the save button is clicked, the previous activity is returned to
        // check that the new item is present in the equipment manager's list with all the inputted information
    }

    // assert that a user cannot leave any fields empty
    public void testAddInput() {
        // enter AddItem activity
        // assert that the save button cannot be pressed by the user
    }

    // 01.02.01 As an owner, I want to view a list of all my things, and their descriptions and statuses.

    // assert that added items are visible in the main activity
    public void testViewList() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager
        // create another equipment item that belongs to the equipment manager
        // check that all items are visible in the main activity view
        // check that each item has description and status visible
    }

    // assert that there will be nothing in the ListView if the owner owns no equipment
    public void testEmptyViewList() {
        // create owner
        // assert that the main activity has an empty ListView
    }

    // 01.03.01 As an owner of equipment, I want to view one of my pieces of equipment, its description and status.

    // assert that the items in the main activity list view are clickable and lead to ViewOneItem activity
    public void testViewOneItem() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager
        // assert that items in ListView of main activity are clickable
        // assert that when an item in the ListView is clicked, the ViewOneItem activity is started
        // check that the item's description and status in ViewOneItem is visible and accurate
    }

    // assert that the ViewOneItem activity cannot be started if there are no items
    public void testEmptyViewOneItem() {
        // create equipment manager
        // assert that the ListView is not clickable
    }

    // 01.04.01 As an owner of equipment, I want to edit a piece of equipment in my list of owned equipment

    // assert that changing the value of an item updates the item
    public void testEditItem() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager
        // change an aspect of the items description
        // check that the description has been changed accurately
    }

    // assert that tapping on an item allows the item to be edited
    public void testTapToEditItem() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager
        // start the ViewOneItem activity with that equipment item
        // check that the ViewOneItem view has layout containing TextView fields
        // perform "tapping" of a TextView field
        // check that the TextView fields are now EditText fields
        // set new values for the EditText fields
        // assert that the save button updates the item's information and returns the EditText fields TextView fields
        // return to previous activity
        // check that values are changed in the item
        // check that new values are displayed in Main activity
    }

    // assert that if nothing is entered into the EditText field, that the item will not be altered
    public void testEditItemNoChanges() {
        // create owner
        // create an equipment item that belongs to the owner
        // start the ViewOneItemActivity with that equipment item
        // perform "tapping" of a TextView field
        // check that the save button returns the EditText fields to TextView fields
        // assert that the item values are the same as before
    }

    // 01.05.01 As an owner of equipment, I want to delete a piece of equipment from my list of owned equipment
    public void testDeleteOnlyItem() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager

        // check that the item is in the list of the equipment manager's items
        // check that the item is displayed in the main activity view
        // remove the item from the list of the equipment manager's items
        // check that the item is not in the list of the equipment manager's items
        // check that the item is not displayed in the main activity view
    }

    public void testDeleteOneItem() {
        // create equipment manager
        // create an equipment item that belongs to the equipment manager
        // create another equipment item that belongs to the equipment manager

        // check that both items are in the list of the equipment manager's items
        // check that both items are displayed in the main activity view
        // remove one item from the list of the equipment manager's items
        // check that the removed item is no longer in the list of the equipment manager's items
        // check that the other item is still in the list of the equipment manager's items
        // check that the removed item is no longer in the main activity view
        // check that the other item is still in the main activity view
    }

    public void testDeleteNull() {
        // create equipment manager
        // create a new item
        // check that there is nothing in the list of the equipment manager's items
        // check that there is nothing displayed in the main activity view
        // assert that there will be an error when trying to remove the item from the equipment manager's
        // list of items
    }

    // 02.01.01 As an owner or borrower of equipment, I want a piece of equipment to have a status of one of: available, bidded, or borrowed.
    public void testAvailableStatus() {
        // create equipment manager
        // create user
        // create an item that belongs to equipment manager
        // set the status of the item to available
        // check that the item is visible to the user
    }

    public void testBiddedStatus() {
        // create equipment manager
        // create user1
        // create user2
        // create an item that belongs to the equipment manager
        // set the status of the item to bidded for user1 and owner
        // check that the status of the item appears as "bidded" to user1
        // check that the status of the item appears as "bidded" to owner
        // check that the status of the item appears as "available" to user2
    }

    public void testBorrowedStatus() {
        // create owner
        // create user1
        // create user2
        // create an item that belongs to the equipment manager
        // set the status of the item to "borrowed" for user1 and owner
        // check that the status of the item is "borrowed" for user1
        // check that the status of the item is "borrowed" for owner
        // check that user2 is unable to find the item
    }

    // 03.01.01 As a user, I want a profile with a unique username and my contact information.
    public void testSetProfile() {
        // start signUp activity
        // set values for each of the EditText fields
        // check that the entered values are saved as a new user
    }

    public void testSetExistingProfile() {
        // create user with "abc" as username
        // start SignUp activity
        // enter "abc" as username
        // set values for each of the other EditText fields
        // assert that there is an error when user tries to press the "sign up" button
    }

    public void testSetEmptyProfile() {
        // start SignUp activity
        // assert that the "sign up" button cannot be pressed
    }

    public void testUserContactInfo() {
        // create user with username and contact information
        // create an equipment item that belongs to user
        // assert that displayed profile information in main activity is accurate
        // start ViewOneItem activity with equipment item belonging to user
        // check that profile information displayed is accurate
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
        // create first user
        // create second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment item owned by second user
        // check that bid has been received by second user
    }

    // 05.02.01 As a borrower of equipment, I want to view a list of pieces of equipment I
    // have bidded on that are pending, each piece with its description, owner username, and my bid.
    public void testViewMyBids() {
        // create first user
        // create second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment item owned by second user
        // check that the item is in the first user's list of pending bids
        // check that the display of the item is visible to the first user
    }

    // 05.03.01 As an owner of equipment, I want to be notified of a bid.
    public void testBidNotification() {
        // create first user
        // create second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // check that the second user receives a bid notification
    }

    // 05.04.01 As an owner of equipment, I want to view a list of my pieces of equipment with bids.
    public void testViewBidsOnMyEquipmentList() {
        // create first user
        // create second user
        // create third user
        // create equipment item that belongs to third user
        // create another equipment item that belongs to third user

        // make bid as first user on first item of equipment owned by third user
        // make bid as second user on second piece of equipment owned by third user
    }

    // 05.05.01 As an owner of equipment, I want to view the bids on one of my pieces of equipment.
    public void testViewBidsOnMyEquipment() {
        // create first user
        // create second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // check that second user can see the bid make by first user
    }

    // 05.06.01 As an owner of equipment, I want to accept a bid on one of my pieces of equipment,
    // setting its status to borrowed. (Any other bids are declined.)
    public void testAcceptBid() {
        // make first user
        // make second user
        // make third user
        // create equipment item that belongs to third user

        // make bid as first user on equipment owned by third user
        // make bid as second user on equipment owned by third user

        // accept bid from first user as third user

        // check that bid has been accepted for third user
        // check that bid has been accepted for first user
        // check that bid has been declined for second user
    }

    // 05.07.01 As an owner, I want to decline a bid on one of my pieces of equipment.
    public void testDeclineBid() {
        // make first user
        // make second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // decline bid from first user as second user
        // check that bid has been declined for first user
        // check that bid has been declined for second user
    }

    // 06.01.01 As a borrower of equipment, I want to view a list of pieces of equipment
    // I am borrowing, each piece with its description and owner username.
    public void testViewBorrowingIn() {
        // make first user
        // make second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // check that bid is in pending bids of first user
        // check that bid is in list of bids made by other users on second user

        // accept bid made by first user as second user
        // check that bid has been accepted for first user
        // check that bid has been accepted for second user

        // check that equipment is in list of things being borrowed by first user
        // check that equipment is in list of things being rented out by second user

        // check that list of equipment borrowed by first user is visible to first user
        // check that description and owner username of equipment borrowed by first user is visible to first user
    }

    // 06.02.01 As an owner of equipment, I want to view a list of my pieces of equipment
    // being borrowed, each piece with its description and borrower username.
    public void testViewBorrowingOut() {
        // make first user
        // make second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // check that bid is in pending bids of first user
        // check that bid is in list of bids made by other users on second user

        // accept bid made by first user as second user
        // check that bid has been accepted for first user
        // check that bid has been accepted for second user

        // check that equipment is in list of things being borrowed by first user
        // check that equipment is in list of things being rented out by second user
        // check that equipment is visible as borrowed (not available) to first user
        // check that equipment is visible as borrowed (not available) to second user

        // check that list of equipment rented out by second user is visible to second user
        // check that description and borrower username of equipment borrowed by first user is visible to second user
    }

    // 07.01.01 As an owner of equipment, I want to set a borrowed piece of equipment to be available when it is returned.
    public void testSetAvailable() {
        // make first user
        // make second user
        // create equipment item that belongs to second user

        // make bid as first user on equipment owned by second user
        // check that bid is in pending bids of first user
        // check that bid is in list of bids made by other users on second user

        // accept bid made by first user as second user
        // check that bid has been accepted for first user
        // check that bid has been accepted for second user

        // check that equipment is in list of things being borrowed by first user
        // check that equipment is in list of things being rented out by second user
        // check that equipment is visible as borrowed (not available) to first user
        // check that equipment is visible as borrowed (not available) to second user

        // set equipment owned by second user as available
        // check that equipment is visible as available to first user
        // check that equipment is visible as available to second user
    }

    // 08.01.01 As an owner of equipment, I want to define new pieces of equipment while offline,
    // and push the additions once I get connectivity.
    public void testDefineEquipmentOffline() {
        // make first user
        // make second user

        // check that second user is offline
        // create equipment item that belongs to second user

        // check that second user is online
        // check that equipment item is visible to first user
        // check that equipment item is visible to second user

    }

}