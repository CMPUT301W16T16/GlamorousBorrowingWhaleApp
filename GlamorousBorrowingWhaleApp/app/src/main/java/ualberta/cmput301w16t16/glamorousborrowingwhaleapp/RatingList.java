package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import java.util.ArrayList;

/**
 * Created by Laura on 4/3/2016.
 *  * This class contains a few functions for a list of ratings to make it easier
 * to control.
 * @author adam, andrew, erin, laura, martina
 */

public class RatingList {

    private float avgRating;

    private ArrayList<Rating> ratings = new ArrayList<>();

    public void addRating(Rating rating) {ratings.add(rating);}

    public float avgRating() {
        // TODO: write the function to calculate average
        // calculate average numerical star rating for display
        return avgRating;

    }


}

