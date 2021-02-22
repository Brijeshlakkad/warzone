package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.map_editor.MapEditorEngine;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

/**
 * This class handles the distribution of countries among all the players.
 * @author CHARIT
 */
public class DistributeCountriesService {

    public List<Player> d_playerList;
    public List<Country> d_countryList;
    public List<Continent> d_continentList;
    public MapEditorEngine d_mapEditorEngine;

    /**
     * Parameterized constructor for instantiating required objects.
     * @param p_playerList List of player objects
     */
    public DistributeCountriesService(List<Player> p_playerList)
    {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentList = d_mapEditorEngine.getContinentList();
        d_countryList = d_mapEditorEngine.getCountryList();
        //TODO GameEngine.getInstance().getPlayerList();
        d_playerList = p_playerList;
        //d_countryList = p_countryList;
        //d_continentList = p_continentList;
    }

    /**
     * method to assign countries to different players.
     */
    public void distributeCountries()
    {
        int l_continentCount = d_continentList.size();
        int l_countryCount = d_countryList.size();
        int l_playerCount = d_playerList.size();

        int l_floorVal = (int) floor(l_countryCount/l_playerCount);
        int l_remainder = l_countryCount % l_playerCount;

        /*for(int l_itr = 0; l_itr<l_playerCount; l_itr++)
        {

        }*/

        for(Player l_playerObj : d_playerList) {
            if (l_remainder > 0) {
                l_playerObj.setAssignedCountryCount(l_floorVal + l_remainder);
                l_remainder--;
            }
            else
            {
                l_playerObj.setAssignedCountryCount(l_floorVal);
            }
        }

        for(Player l_player : d_playerList)
        {
            int l_playerCountryCount = l_player.getAssignedCountryCount();
            List<Country> l_assignedCountryList = assignCountry(l_playerCountryCount);
            l_player.setAssignedCountries(l_assignedCountryList);
        }
    }

    /**
     * returns the list of contries to be assigned to player.
     * @param l_playerCountryCount number of countries can be assigned to player.
     * @return list of countries.
     */
    public List<Country> assignCountry(int l_playerCountryCount) {
        return null;
    }
}
