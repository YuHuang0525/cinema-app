package com.cinema.bean;

import java.util.Date;

public class Movie {
    private String name;
    private String actor;
    private double score;
    private double price;
    private int duration;
    private int seats;
    private Date startTime;

    public Movie() {
    }

    public Movie(String name, String actor, double score, double price, int duration, int seats, Date startTime) {
        this.name = name;
        this.actor = actor;
        this.score = score;
        this.price = price;
        this.duration = duration;
        this.seats = seats;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", actor='" + actor + '\'' +
                ", score=" + score +
                ", price=" + price +
                ", duration=" + duration +
                ", seats=" + seats +
                ", startTime=" + startTime +
                '}';
    }
}
