package com.revature.servlets;

import com.revature.controllers.CarController;
import com.revature.controllers.MovieController;
import com.revature.models.Car;
import com.revature.models.Movie;
import orm.OurORM;
import util.ResourceNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;

//This Class is acting as a Delegator - to tell our code where it should be processed next.
public class RequestHelper {

    //This is us trying to use Dependency Injection to help decouple our classes from having to manage
    // Objects (dependencies) that they need
//    static MovieRepo mr = new MovieRepoDBImpl();
//    static MovieService ms = new MovieServiceImpl(mr);
    static OurORM<Movie> ms = new OurORM<>();
    static MovieController mc = new MovieController(ms);

    static OurORM<Car> cs = new OurORM<>();
    static CarController cc = new CarController(cs);


    /**
     * This method will delegate other methods on the Controller layer of our application
     * to process the request
     * @param request - the HTTP Request
     * @param response - the HTTP Response
     */
    public static void getProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        String uri = request.getRequestURI();
        System.out.println(uri);

        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            //if the uriTokens only has two elements, a blank element and the project name, then nothing to process.
            case 0:
            case 1:
            case 2: {
                response.sendError(404);
                break;
            }
            //if the uriTokens is exactly 3 then it also has the collection name, but no path parameter.
            case 3: {
                //Call our getAll<Insert Entity Here> methods.
                if(("Movies").equals(uriTokens[2])) mc.getMovies(request, response);
                else if(("Cars").equals(uriTokens[2])) cc.getCars(request, response);
                else response.sendError(400, "Collection does not exist");
                break;
            }
            case 4: {
                //Call our get<Insert Entity Here> by Id service method.
                request.setAttribute("id", uriTokens[3]);
                if(("Movies").equals(uriTokens[2])) mc.getMovieById(request, response);
                else if(("Cars").equals(uriTokens[2])) cc.getCarById(request, response);
                break;
            }
            default: {
                response.sendError(400);
                break;
            }
        }

    }

    public static void postProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, IllegalAccessException {


        String uri = request.getRequestURI();
        System.out.println(uri);

        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            //if the uriTokens only has two elements, a blank element and the project name, then nothing to process.
            case 0:
            case 1:
            case 2: {
                response.sendError(404);
                break;
            }
            //if the uriTokens is exactly 3 then it also has the collection name, but no path parameter.
            case 3: {
                //Call our getAll<Insert Entity Here> methods.
                if (("Movies").equals(uriTokens[2])) mc.addMovie(request, response);
                else if(("Cars").equals(uriTokens[2])) cc.addCar(request, response);
                else response.sendError(400, "Collection does not exist");
                break;
            }
            default: {
                response.sendError(400);
                break;
            }

        }

    }

    public static void putProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {

        String uri = request.getRequestURI();
        System.out.println(uri);

        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            //if the uriTokens only has two elements, a blank element and the project name, then nothing to process.
            case 0:
            case 1:
            case 2: {
                response.sendError(404);
                break;
            }
            //if the uriTokens is exactly 3 then it also has the collection name, but no path parameter.
            case 4: {
                int id = 0;
                String input = uriTokens[3];

                if(input.matches("[0-9]+")) {
                    id = Integer.parseInt(input);
                } else {
                    response.sendError(400, "ID is not a number");
                    return;
                }

                request.setAttribute("id", id);
                if (("Movies").equals(uriTokens[2])) mc.updateMovie(request, response);
                else if(("Cars").equals(uriTokens[2])) cc.updateCar(request, response);
                else response.sendError(400, "Collection does not exist");
                break;
            }
            default: {
                response.sendError(400);
                break;
            }

        }

    }

    public static void deleteProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ResourceNotFoundException, IllegalAccessException, ResourceNotFoundException {

        String uri = request.getRequestURI();
        System.out.println(uri);

        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            //if the uriTokens only has two elements, a blank element and the project name, then nothing to process.
            case 0:
            case 1:
            case 2: {
                response.sendError(404);
                break;
            }
            //if the uriTokens is exactly 3 then it also has the collection name, but no path parameter.
            case 4: {
                int id = 0;
                String input = uriTokens[3];

                if(input.matches("[0-9]+")) {
                    id = Integer.parseInt(input);
                } else {
                    response.sendError(400, "ID is not a number");
                    return;
                }

                request.setAttribute("id", id);
                if (("Movies").equals(uriTokens[2])) mc.deleteMovie(request, response);
                else if(("Cars").equals(uriTokens[2])) cc.deleteCar(request, response);
                else response.sendError(400, "Collection does not exist");
                break;
            }
            default: {
                response.sendError(400);
                break;
            }

        }
    }
}