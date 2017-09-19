package com.example.builditbigger.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private String myJoke;

    public String getJoke() {
        return myJoke;
    }

    public void setJoke(String myJoke) {
        this.myJoke = myJoke;
    }
}