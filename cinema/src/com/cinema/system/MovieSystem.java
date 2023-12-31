package com.cinema.system;

import com.cinema.bean.Business;
import com.cinema.bean.Customer;
import com.cinema.bean.Movie;
import com.cinema.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.CompactNumberFormat;
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
        testCustomer.setBalance(1000);
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
        Movie movie = getMovieByNameByBusiness(movieName);

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
        Movie movie = getMovieByNameByBusiness(movieName);

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
    private static Movie getMovieByNameByBusiness(String movieName) {
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
                    searchMovieInfoByName();
                    break;
                case "3":
                    writeReview();
                    break;
                case "4":
                    purchaseTicket();
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
     * customer purchase ticket method
     */
    private static void purchaseTicket() {
        System.out.println("Please enter the shop name: ");
        String shopName = SYS_SC.nextLine();

        Business business = getBusinessByShopName(shopName);

        if (business == null) {
            System.out.println("Sorry store does not exist");
        } else {
            showMovieInfo(business);

            System.out.println("\nPlease enter the movie name: ");
            String movieName = SYS_SC.nextLine();

            purchaseMovie(business, movieName);
        }

    }

    /**
     * purchase movie ticket
     * @param business
     * @param movieName
     */
    private static void purchaseMovie(Business business, String movieName) {

        // first check if business provides movie
        Movie movie = getMovieByNameByCustomer(movieName);
        if (!BUSINESS_MOVIES_MAP.get(business).contains(movie)) {
            System.out.println("Sorry, this business does not have the movie you just entered.");
            LOGGER.error("User- " + loginUser.getUserName() + " input movie is not found in the business");
        } else {
            if (movie.getSeats() == 0) {
                System.out.println("Sorry this movie has no seat anymore");
            } else {
                if (loginUser.getBalance() < movie.getPrice()) {
                    System.out.println("Sorry your balance is not enough");
                } else {
                    System.out.println("Please confirm that you want to buy 1 ticket of - " + movieName + " at " +
                            business.getShopLocation() + " @ " + movie.getStartTime() + " [Y/N]: ");
                    String confirm = SYS_SC.nextLine();
                    if (confirm.equals("y") || confirm.equals("Y")) {
                        // change the balance of both customer and business
                        loginUser.addMoney(-1.0 * movie.getPrice());
                        business.addMoney(movie.getPrice());
                        // update the number of seats for the movie
                        movie.decreaseSeat();

                        LOGGER.info("User - " + loginUser.getLoginName() + " successfully purchased 1 ticket of - " +
                                movieName + " at " + business.getShopLocation() + " @ " + movie.getStartTime());
                        System.out.println("Transaction completes, we are looking forward to seeing you!");
                    }
                }
            }
        }

    }

    /**
     * given the business object and print out all movies info of that business
     * @param business
     */
    private static void showMovieInfo(Business business) {

        System.out.println("Below are all the movies that " + business.getShopName() + " at " +
                business.getShopLocation() + " is presenting: ");

        List<Movie> ALL_Movies = BUSINESS_MOVIES_MAP.get(business);

        for (Movie movie : ALL_Movies) {
            System.out.println(movie);
        }

    }

    /**
     * get business object by shop name
     * @param shopName
     * @return Business object
     */
    private static Business getBusinessByShopName(String shopName) {

        Set<Business> ALL_Business = BUSINESS_MOVIES_MAP.keySet();

        for (Business business : ALL_Business) {
            if (business.getShopName().equals(shopName)) {
                return business;
            }
        }

        return null;
    }

    /**
     * write review score of a movie
     */
    private static void writeReview() {
        System.out.println("Please enter the movie name: ");
        String movieName = SYS_SC.nextLine();

        Movie movie = getMovieByNameByCustomer(movieName);
        if (movie == null) {
            System.out.println("Movie not found, please try again");
        } else {
            while (true) {
                try {
                    System.out.println("Please enter the score[1-10]: ");
                    String score = SYS_SC.nextLine();
                    Double scoreInDouble = Double.valueOf(score);

                    // check if score is in the correct range, i.e 1-10
                    if (scoreInDouble < 1.0 || scoreInDouble > 10.0) {
                        System.out.println("Invalid score, please try again");
                    } else {
                        movie.setScore(scoreInDouble);
                        System.out.println("Thanks for your feedback!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Score is invalid...");
                    LOGGER.error("User -" + loginUser.getLoginName() + " just entered invalid score");
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * get the movie object by movie name called by customer
     * @param movieName
     * @return movie object
     */
    private static Movie getMovieByNameByCustomer(String movieName) {

        Movie foundMovie = null;
        for (Map.Entry<Business, List<Movie>> entry : BUSINESS_MOVIES_MAP.entrySet()) {
            for (Movie movie: entry.getValue()) {
                if (movie.getName().equals(movieName)) {
                    foundMovie = movie;
                    break; // Exit the loop once the movie is found
                }
            }
            if (foundMovie != null) {
                break; // Exit the outer loop once the movie is found
            }
        }

        return foundMovie;
    }

    /**
     * search movie info by move name
     */
    private static void searchMovieInfoByName() {
        System.out.println("Please enter movie name: ");
        String movieName = SYS_SC.nextLine();

        BUSINESS_MOVIES_MAP.forEach((business, movies) -> {
            for (Movie m: movies) {
                if (m.getName().equals(movieName)) {
                    System.out.println("Movie - " + movieName + " will be presented at " + business.getShopLocation() + " by " + business.getShopName() +
                            " at " + m.getStartTime() + "\n");
                }
            }
        });


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
