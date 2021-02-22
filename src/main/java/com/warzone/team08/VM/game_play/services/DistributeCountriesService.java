package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.floor;

/**
 * This class handles the distribution of countries among all the players.
 *
 * @author CHARIT
 */
public class DistributeCountriesService {

    public List<Player> d_playerList;
    public List<Country> d_countryList;
    public static List<Continent> D_ContinentList;
    public MapEditorEngine d_mapEditorEngine;

    /**
     * Parameterized constructor for instantiating required objects.
     */
    public DistributeCountriesService(List<Player> p_playerList) {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        D_ContinentList = d_mapEditorEngine.getContinentList();
        d_countryList = d_mapEditorEngine.getCountryList();
        // TODO GameEngine.getInstance().getPlayerList();
        //d_playerList = new ArrayList<>();
        d_playerList = p_playerList;
    }

    /**
     * Method to assign countries to different players.
     */
    public void distributeCountries() {
        int l_countryCount = d_countryList.size();
        int l_playerCount = d_playerList.size();

        int l_floorVal = (int) floor(l_countryCount / l_playerCount);
        int l_remainder = l_countryCount % l_playerCount;

        for (Player l_playerObj : d_playerList) {
            if (l_remainder > 0) {
                l_playerObj.setAssignedCountryCount(l_floorVal + l_remainder);
                l_remainder--;
            } else {
                l_playerObj.setAssignedCountryCount(l_floorVal);
            }
        }
        for (Player l_player : d_playerList) {
            int l_playerCountryCount = l_player.getAssignedCountryCount();
            List<Country> l_assignedCountryList = assignCountry(l_player, l_playerCountryCount);
            l_player.setAssignedCountries(l_assignedCountryList);
        }
    }

    /**
     * Returns the list of countries to be assigned to player.
     *
     * @param p_player             Player object.
     * @param p_playerCountryCount Number of countries can be assigned to player.
     * @return Value of list of countries.
     */
    public List<Country> assignCountry(Player p_player, int p_playerCountryCount) {
        List<Country> l_assignedCountries = new ArrayList<>();

        int l_size;
        int iterateCountryCount = 0;
        do {
            Country selectedCountry = d_countryList.get(iterateCountryCount);
            if (selectedCountry.getOwnedBy() != null) {
                selectedCountry.setOwnedBy(p_player);
                List<Country> l_groupOfCountries = findCountryNeighbors(selectedCountry, p_playerCountryCount);
                l_groupOfCountries.add(0, selectedCountry);

                l_size = l_groupOfCountries.size();
                if (l_size < p_playerCountryCount) {
                    p_playerCountryCount = p_playerCountryCount - l_size;
                    assignOwnerToCountry(p_player, l_groupOfCountries);
                    l_assignedCountries.addAll(l_groupOfCountries);
                } else {
                    List<Country> l_countryLst = l_groupOfCountries.subList(0, p_playerCountryCount);
                    assignOwnerToCountry(p_player, l_countryLst);
                    l_assignedCountries.addAll(l_countryLst);
                }
            }
            iterateCountryCount++;
            if (iterateCountryCount == d_countryList.size()) {
                break;
            }
        } while (l_assignedCountries.size() < p_playerCountryCount);
        return l_assignedCountries;
    }

    /**
     * Assigns owner to different countries.
     *
     * @param p_player      Player object
     * @param p_countryList List of countries
     */
    public void assignOwnerToCountry(Player p_player, List<Country> p_countryList) {
        for (Country l_con : p_countryList) {
            l_con.setOwnedBy(p_player);
        }
    }

    /**
     * Finds the neighboring country of the given country.
     *
     * @param p_country            Country object
     * @param p_playerCountryCount Number of countries can be assigned to player.
     * @return List of country object.
     * @throws IllegalStateException Throws if returns an empty list.
     */
    public List<Country> findCountryNeighbors(Country p_country, int p_playerCountryCount) throws IllegalStateException {
        return p_country.getNeighbourCountries().stream().filter((p_l_country) ->
                p_l_country.getOwnedBy() != null
        ).collect(Collectors.toList());
    }

}
