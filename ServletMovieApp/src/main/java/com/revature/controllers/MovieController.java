package com.revature.controllers;

import com.google.gson.Gson;
import com.revature.models.Movie;
import orm.OurORM;
import util.ResourceNotFoundException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieController {

    //MovieService ms;
    OurORM<Movie> ms = new OurORM<>();
    Gson gson = new Gson();

    public MovieController(OurORM ms) {
        this.ms = ms;
    }

    //This method should be called when we want to get a movie.
    public void getMovieById(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        String input = request.getAttribute("id").toString();
        int id = 0;
        if(input.matches("[0-9]+")) {
            id = Integer.parseInt(input);
        } else {
            response.sendError(400, "ID is not a number");
            return;
        }

        Movie m = ms.GetObj(Movie.class,id);

        response.getWriter().append((m != null) ? gson.toJson(m): "{}");
    }

    public void getMovies(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        List<Movie> movieList = new ArrayList<>();
        String price = request.getParameter("price");

        //if the price is null, then that Query Parameter was not provided, so we will do a normal
        //getAllMovies
        if(price == null) {
            movieList = ms.GetAllObj(Movie.class);
        }else {
            response.sendError(400, "Price is not a number.");
        }

        response.getWriter().append(gson.toJson(movieList));

    }


    public void addMovie(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, IllegalAccessException {

        //Extract data/information from the request
        BufferedReader reader = request.getReader();
        Movie m = gson.fromJson(reader, Movie.class);

        //Call some service(s) to process the data/information
        m = ms.addObj(m);

        //Generate a response from that processed data.
        if(m != null) {
            response.setStatus(201);
            response.getWriter().append(gson.toJson(m));
        } else {
            response.getWriter().append("{}");
        }


    }

    public void updateMovie(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {

        Movie m = gson.fromJson(request.getReader(), Movie.class);
        m.setId((int) request.getAttribute("id"));
        int id = m.getId();
        m = ms.updateObj(m,id);

        response.getWriter().append((m != null) ? gson.toJson(m) : "{}");
    }

    public void deleteMovie(HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException, IOException, IllegalAccessException {

        int id = (int) request.getAttribute("id");

       Movie m = ms.deleteObj(Movie.class, id);

//        response.getWriter().append((m != null) ? gson.toJson(m) : "{}");
        response.setStatus(204);
    }
}