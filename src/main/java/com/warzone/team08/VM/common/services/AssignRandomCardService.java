package com.warzone.team08.VM.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class handles the operation to randomly assign the card to player.
 *
 * @author CHARIT
 */
public class AssignRandomCardService {

    public static List<String> d_CardList = new ArrayList<>();

    /**
     * randomly assigns card to the player.
     *
     * @return Card name
     */
    public static final String randomCard() {
        d_CardList.add("bomb");
        d_CardList.add("blockade");
        d_CardList.add("airlift");
        d_CardList.add("negotiate");
        Random rand = new Random();
        return d_CardList.get(rand.nextInt(d_CardList.size()));
    }
}
