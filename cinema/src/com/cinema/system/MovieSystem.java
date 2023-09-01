package com.cinema.system;

import com.cinema.bean.Business;
import com.cinema.bean.Customer;
import com.cinema.bean.Movie;
import com.cinema.bean.User;

import java.time.Instant;
import java.util.*;

public class MovieSystem {

    /**
     * store all the users
     */
    public static final List<User> ALL_USERS = new ArrayList<>();

    /**
     * store all the movies
     */
    public static final Map<Business, List<Movie>> BUSINESS_MOVIES_MAP = new HashMap<>();

    public static final Scanner SYS_SC = new Scanner(System.in);

    public static User loginUser;

    static {

        /**
         * customer test data
         */

        User testCustomer = new Customer();
        testCustomer.setUserName("Yu H");
        testCustomer.setLoginName("yuh");
        testCustomer.setPassword("123");
        testCustomer.setSex('M');
        testCustomer.setPhone("123456");
        testCustomer.setBalance(0);
        ALL_USERS.add(testCustomer);

        /**
         * business test data
         */

        Business testBusiness = new Business();
        testBusiness.setUserName("admin");
        testBusiness.setLoginName("admin");
        testBusiness.setPassword("1234");
        testBusiness.setSex('M');
        testBusiness.setPhone("123456");
        testBusiness.setBalance(0);
        testBusiness.setShopName("AMC");
        testBusiness.setShopLocation("Beijing");
        ALL_USERS.add(testBusiness);

        /**
         * movie test data
         */
        Movie testMovie = new Movie();
        testMovie.setActor("Kyrie");
        testMovie.setName("Basketball dream");
        testMovie.setDuration(120);
        testMovie.setPrice(240);
        Date input = new Date();
        Instant instant = input.toInstant();
        Date testDate = Date.from(instant);
        testMovie.setStartTime(testDate);
        testMovie.setScore(5);

        List<Movie> testMovieList = new ArrayList<>();
        testMovieList.add(testMovie);

        BUSINESS_MOVIES_MAP.put(testBusiness, testMovieList);

    }

    public static void main(String[] args) {

        showMain();

    }

    /**
     * main page
     */
    private static void showMain() {

        while (true) {
            System.out.println("=========== Home Page ===========");
            System.out.println("1. Login");
            System.out.println("2. Customer Register");
            System.out.println("3. Business Register");

            System.out.println("Please choose from above: ");

            String command = SYS_SC.nextLine();

            switch (command) {
                case "1":
                    // login
                    login();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Please choose one of the operations from above");
            }
        }


    }

    /**
     * login method
     */
    private static void login() {
        while (true) {
            System.out.println("Please enter your user ID: ");
            String loginName = SYS_SC.nextLine();
            System.out.println("Please enter your password: ");
            String password = SYS_SC.nextLine();

            User u = getUserById(loginName);

            if (u != null) {
                // check if password matches
                if (u.getPassword().equals(password)) {
                    System.out.println("Login successfully!");

                    loginUser = u;
                    if (u instanceof Customer) {
                        // customers
                        showCustomerMain();

                    } else {
                        showBusinessMain();
                    }
                    return;

                } else {
                    System.out.println("Incorrect password!");
                }
            } else {
                System.out.println("Username not found!");
            }
        }

    }

    /**
     * Business main page
     */
    private static void showBusinessMain() {
        System.out.println("=========== Business Main Page ===========");
        System.out.println(loginUser.getUserName() + (loginUser.getSex() == 'M' ? " Sir" : " Mam") +
                " Welcome to the system!");
        while (true) {
            System.out.println("Please choose one of the following operations: ");
            System.out.println("1. Show business info");
            System.out.println("2. Release new movie");
            System.out.println("3. Movie out of theatre");
            System.out.println("4. Edit movie info");
            System.out.println("5. Exit");

            System.out.println("Please choose from above: ");
            String command = SYS_SC.nextLine();
            switch (command) {
                case "1":
                    showBusinessInfo();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Operation is not valid, please try again! ");
                    System.out.println("\n");
            }
        }

    }

    /**
     * show business info
     */
    private static void showBusinessInfo() {

        System.out.println("Below is your business info: ");
        Business business = (Business) loginUser;
        System.out.println("Shop name: " + business.getShopName());
        System.out.println("Shop location: " + business.getShopLocation());

        // get all the movies info from the map
        List<Movie> movies = BUSINESS_MOVIES_MAP.get(loginUser);

        // check if any movie is released yet
        if (movies == null) {
            System.out.println("currently you don't have any movies released~\n");
            return;
        }

        System.out.println("Below are the movies info: ");
        // print out all the movies information
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        System.out.println("\n");

    }

    /**
     * Customer main page
     */
    private static void showCustomerMain() {
        System.out.println("=========== Customer Main Page ===========");
        System.out.println(loginUser.getUserName() + (loginUser.getSex() == 'M' ? " Sir" : " Mam") +
                " Welcome to the system!");

        while (true) {
            System.out.println("Please choose one of the following operations: ");
            System.out.println("1. Show all movies info");
            System.out.println("2. Search movie info by movie name");
            System.out.println("3. Write review");
            System.out.println("4. Purchase ticket");
            System.out.println("5. Exit");
            System.out.println("Please choose from above: ");
            String command = SYS_SC.nextLine();
            switch (command) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Operation is not valid, please try again! ");
                    System.out.println("\n");
            }
        }


    }

    /**
     * get user by id
     * @param loginName
     * @return User object
     */
    private static User getUserById(String loginName) {

        for (User user : ALL_USERS) {
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }

        return null;
    }

}
