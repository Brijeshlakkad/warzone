package com.warzone.team08.VM.common.services;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.interfaces.Card;
import com.warzone.team08.VM.entities.cards.*;

import java.util.List;
import java.util.Random;

/**
 * This class handles the operation to randomly assign the card to player.
 *
 * @author CHARIT
 */
public class AssignRandomCardService {

    /**
     * List of CardType objects indicating the card type.
     */
    public static List<CardType> d_CardList = CardType.usableCardList();

    /**
     * randomly assigns card to the player.
     *
     * @return Card name
     */
    public static Card randomCard() {
        Random rand = new Random();
        CardType l_cardType = d_CardList.get(rand.nextInt(d_CardList.size()));
        if (l_cardType == CardType.AIRLIFT) {
            return new AirliftCard();
        }
        if (l_cardType == CardType.BOMB) {
            return new BombCard();
        }
        if (l_cardType == CardType.BLOCKADE) {
            return new BlockadeCard();
        }
        if (l_cardType == CardType.DIPLOMACY) {
            return new DiplomacyCard();
        }
        return new EmptyCard();
    }
}
