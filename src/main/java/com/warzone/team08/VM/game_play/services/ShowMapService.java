package com.warzone.team08.VM.game_play.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.game_play.GamePlayEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class shows all countries and continents, armies on each country, ownership, and connectivity in a way that
 * enables efficient game play.
 *
 * @author MILESH
 * @version 1.0
 */
public class ShowMapService implements SingleCommand {
    GamePlayEngine d_gamePlayEngine;
    ArrayList<Player> d_playerList;

    public ShowMapService() {
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_playerList = new ArrayList<>();
    }

    /**
     * Shows the information of countries owned by player with the army count on each country.
     *
     * @param p_player Player for who information will be displayed.
     * @return string of player information
     */
    public String showPlayerContent(Player p_player) {
        List<Country> l_countryList = p_player.getAssignedCountries();
        LinkedList<String> l_countryNames = new LinkedList<>();
        for (Country l_country : l_countryList) {
            l_countryNames.add(l_country.getCountryName());
        }
        String[] l_header = new String[l_countryList.size() + 1];
        l_header[0] = "PLAYER NAME";
        for (int l_row = 1; l_row < l_header.length; l_row++) {
            l_header[l_row] = l_countryNames.pollFirst();
        }
        String[] l_playerMap = new String[l_header.length];
        l_playerMap[0] = p_player.getName();
        for (int l_row = 1; l_row < l_playerMap.length; l_row++) {
            l_playerMap[l_row] = "0";
        }
        return FlipTable.of(l_header, new String[][]{l_playerMap});

    }

    /**
     * Initiates all methods of ShowMapService file.
     *
     * @param p_commandValues Value of parameters entered by the user.
     * @return Value of string of continent and neighbour country information.
     * @throws EntityNotFoundException If no player is available.
     */
    @Override
    public String execute(List<String> p_commandValues) throws EntityNotFoundException {
        StringBuilder l_playerContent = new StringBuilder();
        if (!this.d_playerList.isEmpty()) {
            for (Player l_player : d_playerList) {
                l_playerContent.append(this.showPlayerContent(l_player));
            }
            return l_playerContent.toString();
        } else {
            throw new EntityNotFoundException("Please, add players to show game status!");
        }
    }
}
