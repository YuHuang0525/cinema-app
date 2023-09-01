package com.cinema.system;

import com.cinema.bean.Business;
import com.cinema.bean.Movie;
import com.cinema.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieSystem {

    /**
     * store all the users
     */
    public static final List<User> ALL_USERS = new ArrayList<>();

    /**
     * store all the movies
     */
    public static final Map<Business, List<Movie>> BUSINESS_MOVIES_MAP = new HashMap<>();


    public static void main(String[] args) {

    }

}
