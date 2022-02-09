package com.revature.apps;

import com.revature.models.Movie;
import orm.OurORM;

import java.sql.SQLException;

public class ORMTest {

    public static void main(String[] args) throws Exception {
        OurORM<Movie> testMovie = new OurORM<>();
        Movie m = new Movie("Captain-America", 5, true, 0);
        Movie m1 = testMovie.addObj(m);
        System.out.println("Adding Movie..\n" + m1);
        System.out.println("Getting Obj..\n" + testMovie.GetObj(Movie.class,2));
        m.setPrice(m.getPrice() + 12);
        m.setPrice(12);
        System.out.println("Updated Movie.." + testMovie.updateObj(m, 1));
        System.out.println("Getting Obj..\n" + testMovie.GetObj(Movie.class,1));
        System.out.println(testMovie.GetObj(Movie.class,4));

        System.out.println("Getting all objects..\n" + testMovie.GetAllObj(Movie.class));

        //System.out.println("Deleting Obj..\n"+ testMovie.deleteObj(m,2));


    }


}
