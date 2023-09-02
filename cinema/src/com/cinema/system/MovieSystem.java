package com.cinema.system;

import com.cinema.bean.Business;
import com.cinema.bean.Customer;
import com.cinema.bean.Movie;
import com.cinema.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
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
//    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static final Logger LOGGER = LoggerFactory.getLogger("MovieSystem.class");

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
                        LOGGER.info("Customer - " + u.getUserName() + " login successful");
                        showCustomerMain();

                    } else {
                        LOGGER.info("Business - "  + u.getUserName() + " login successful");
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
                    releaseNewMovie();
                    break;
                case "3":
                    removeMovie();
                    break;
                case "4":
                    editMovie();
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
     * edit the movie info
     */
    private static void editMovie() {
        System.out.println("Please enter the movie name that you want to edit: ");
        String movieName = SYS_SC.nextLine();
        Movie movie = getMovieByName(movieName);

        if (movie == null) {
            System.out.println("The movie name you entered does not exist, please try again");
        } else {
            while (true) {
                System.out.println("Please choose the attribute that you want to edit: ");
                System.out.println("1. Movie name");
                System.out.println("2. Movie price");
                System.out.println("3. Movie actors");
                System.out.println("4. Exit");
                String command = SYS_SC.nextLine();
                switch (command) {
                    case "1":
                        changeMovieName(movie);
                        break;
                    case "2":
                        changeMoviePrice(movie);
                        break;
                    case "3":
                        changeMovieActor(movie);
                        break;
                    case "4":
                        return;
                    default:
                        System.out.println("Invalid operation, please try again");
                        break;
                }
            }

        }

    }

    /**
     * change the movie's actor
     * @param movie
     */
    private static void changeMovieActor(Movie movie) {
        System.out.println("Please enter the new movie actors: ");
        String oldMovieActor = movie.getActor();
        String newMovieActor = SYS_SC.nextLine();
        movie.setActor(newMovieActor);
        System.out.println("Movie - " + movie.getName() + "'s actors has been changed to - " + newMovieActor + ", from " + oldMovieActor);
    }

    /**
     * change the movie's price
     * @param movie
     */
    private static void changeMoviePrice(Movie movie) {
        System.out.println("Please enter the new movie price: ");
        Double oldMoviePrice = movie.getPrice();
        String newMoviePrice = SYS_SC.nextLine();
        movie.setPrice(Double.valueOf(newMoviePrice));
        System.out.println("Movie - " + movie.getName() + "'s price has been changed to - " + newMoviePrice + ", from " + oldMoviePrice);
    }

    /**
     * change the movie's name
     * @param movie
     */
    private static void changeMovieName(Movie movie) {
        System.out.println("Please enter the new movie name: ");
        String oldMovieName = movie.getName();
        String newMovieName = SYS_SC.nextLine();
        movie.setName(newMovieName);
        System.out.println("Movie - " + oldMovieName + " has been changed to - " + newMovieName);
    }

    /**
     * remove movie out of theatre
     */
    private static void removeMovie() {
        System.out.println("Please enter the movie name that you want to remove: ");
        String movieName = SYS_SC.nextLine();
        Movie movie = getMovieByName(movieName);

        if (movie == null) {
            System.out.println("The movie name you entered does not exist, please try again");
        } else {
            System.out.println("Are you sure, you want to remove the movie from theatre? [Y/N]");
            String confirm = SYS_SC.nextLine();
            if (confirm.equals("Y") || confirm.equals("y")) {
                BUSINESS_MOVIES_MAP.get(loginUser).remove(movie);
                System.out.println("Movie " + movie.getName() + " has been successfully removed from theatre");
            }
        }

    }

    /**
     * get the movie objects from the business' map by the movie's name
     * @param movieName
     * @return movie object
     */
    private static Movie getMovieByName(String movieName) {
        List<Movie> allMovies = BUSINESS_MOVIES_MAP.get(loginUser);

        // loop through the map to find the corresponding movie
        for (Movie movie : allMovies) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }

        return null;

    }

    /**
     * method for business to release new movie
     */
    private static void releaseNewMovie() {

        // ask for the movie information

        /**
         *         this.name = name;
         *         this.actor = actor;
         *         this.score = score;
         *         this.price = price;
         *         this.duration = duration;
         *         this.seats = seats;
         *         this.startTime = startTime;
         */
        System.out.println("Please enter the movie's name: ");
        String name = SYS_SC.nextLine();
        System.out.println("Please enter actors: ");
        String actors = SYS_SC.nextLine();
        System.out.println("Please enter price: ");
        String price = SYS_SC.nextLine();
        System.out.println("Please enter number of seats: ");
        String seats = SYS_SC.nextLine();

        // build Movie object
        Movie newMovie = new Movie();
        newMovie.setName(name);
        newMovie.setActor(actors);
        newMovie.setPrice(Double.valueOf(price));
        newMovie.setSeats(Integer.valueOf(seats));

        // get the movie list of the business
        List<Movie> movies = BUSINESS_MOVIES_MAP.get((Business) loginUser);

        // add movie to the movie lists
        movies.add(newMovie);

        System.out.println("New movie - " + name + " has been successfully released!");
        System.out.println("\n");
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
        if (movies.size() == 0) {
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
                    showAllMovies();
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
     * show all movies info
     */
    private static void showAllMovies() {
        System.out.println("Below are all of the movies info: ");
        BUSINESS_MOVIES_MAP.forEach(((business, movies) -> {
            System.out.println(business.getShopName() + " at " + business.getShopLocation() + ":");
            for (Movie movie: movies) {
                System.out.println(movie);
            }
        }));
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
