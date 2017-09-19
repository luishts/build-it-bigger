package com.example;

import java.util.Random;

/**
 * Created by Luis on 18/09/2017.
 */

public class Joker {
    public static String getJoke() {
        String joke = "";
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                joke = "Q:  Mummy, why do all the other kids call me a hairy werewolf? " +
                        "A:  Now stop talking about that and brush your face!";
                break;
            case 1:
                joke = "Q:  What did one thirsty vampire say to the other as they were passing the morgue? " +
                        "A:  Let’s stop in for a cool one!";
                break;
            case 2:
                joke = "Today was a sad day. We had to pull the plug on my grandmother 'cause I" +
                        " needed the outlet for my laptop.";
                break;
        }

        return joke;
    }
}
