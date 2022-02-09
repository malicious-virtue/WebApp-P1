package com.revature.controllers;

import com.google.gson.Gson;
import com.revature.models.Car;
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

public class CarController {

    OurORM<Car> ms = new OurORM<>();
    Gson gson = new Gson();

    public CarController(OurORM ms) {
        this.ms = ms;
    }

    //This method should be called when we want to get a movie.
    public void getCarById(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        String input = request.getAttribute("id").toString();
        int id = 0;
        if(input.matches("[0-9]+")) {
            id = Integer.parseInt(input);
        } else {
            response.sendError(400, "ID is not a number");
            return;
        }

        Car m = ms.GetObj(Car.class,id);

        response.getWriter().append((m != null) ? gson.toJson(m): "{}");
    }

    public void getCars(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        List<Car> carList = new ArrayList<>();
        String price = request.getParameter("price");

        //if the price is null, then that Query Parameter was not provided, so we will do a normal
        //getAllMovies
        if(price == null) {
            carList = ms.GetAllObj(Car.class);
        }else {
            response.sendError(400, "Price is not a number.");
        }

        response.getWriter().append(gson.toJson(carList));

    }


    public void addCar(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, IllegalAccessException {

        //Extract data/information from the request
        BufferedReader reader = request.getReader();
        Car m = gson.fromJson(reader, Car.class);

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

    public void updateCar(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {

        Car m = gson.fromJson(request.getReader(), Car.class);
        m.setId((int) request.getAttribute("id"));
        int id = m.getId();
        m = ms.updateObj(m,id);

        response.getWriter().append((m != null) ? gson.toJson(m) : "{}");
    }

    public void deleteCar(HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException, IOException, IllegalAccessException {

        int id = (int) request.getAttribute("id");

        Car m = ms.deleteObj(Car.class, id);

//        response.getWriter().append((m != null) ? gson.toJson(m) : "{}");
        response.setStatus(204);
    }
}