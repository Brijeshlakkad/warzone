package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.repositories.CountryRepository;

/**
 *This class implements the operations required to be perform when the Airlift card is used.
 *
 * @author Deep Patel
 * @version 2.0
 */
public class AirliftService{

    private String d_sourceCountryId;
    private String d_targetCountryId;
    private int d_numarmies;
    private Player d_player;

    /**
     * sets the source and target country id along with number of armies to be airlifted and player object.
     *
     * @param p_sourceCountryId source country id from which armies will be airlifted
     * @param p_targetCountryId target country id where armies will be moved.
     * @param p_numarmies number of armies for airlift
     * @param p_player current player object
     */
    public AirliftService(String p_sourceCountryId, String p_targetCountryId, int p_numarmies, Player p_player)
    {
        d_sourceCountryId = p_sourceCountryId;
        d_targetCountryId = p_targetCountryId;
        d_numarmies = p_numarmies;
        d_player = p_player;
    }

    /**
     * Returns country object from which armies will be airlifted.
     *
     * @return source country object
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries.
     */
    public Country getSourceCountry() throws EntityNotFoundException {
        return new CountryRepository().findFirstByCountryName(d_sourceCountryId);
    }

    /**
     * Returns country object where armies will be moved.
     *
     * @return target country object
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries.
     */
    public Country getTargetCountry() throws EntityNotFoundException {
        return new CountryRepository().findFirstByCountryName(d_targetCountryId);
    }

    /**
     * Verify that all the conditions has been fulfilled for the airlift command.
     *
     * @return True if command is valid else false.
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries
     * @throws InvalidCommandException Throws if player has not airlift card.
     * @throws InvalidInputException   Throws if player select opponent country for airlift or player doesn't have sufficient armies.
     */
    public boolean valid() throws EntityNotFoundException, InvalidCommandException, InvalidInputException {
        if (this.getSourceCountry().getOwnedBy() == d_player && this.getTargetCountry().getOwnedBy() == d_player){
            if (d_player.getCards().contains("airlift")) {
                if(this.getSourceCountry().getNumberOfArmies()>=d_numarmies) {
                    return true;
                } else {
                    throw new InvalidInputException("source country not have entered amount of armies for airlift");
                }
            } else {
                throw new InvalidCommandException("Airlift card is not available with the player.");
            }
        } else {
            throw new InvalidInputException("You have to select source and target country both from your owned countries");
        }
    }

    /**
     * perform the airlift operation by transferring armies.
     *
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries
     * @throws InvalidCommandException Throws if player has not airlift card.
     * @throws InvalidInputException   Throws if player select opponent country for airlift or player doesn't have sufficient armies.
     */
    public void execute() throws EntityNotFoundException, InvalidInputException, InvalidCommandException {
        int l_sourceCountryArmies = this.getSourceCountry().getNumberOfArmies();
        int l_targetCountryArmies = this.getTargetCountry().getNumberOfArmies();
        if(valid())
        {
            l_sourceCountryArmies -= d_numarmies;
            l_targetCountryArmies += d_numarmies;
            this.getSourceCountry().setNumberOfArmies(l_sourceCountryArmies);
            this.getTargetCountry().setNumberOfArmies(l_targetCountryArmies);
        }
        d_player.removeCard("airlift");
    }
}
