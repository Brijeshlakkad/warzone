package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;

import java.util.List;

/**
 * This class handles the distribution of countries among all the players.
 * @author CHARIT
 */
public class DistributeCountriesService {

    public List<Player> d_playerList;
    public List<Country> d_countryList;
    public List<Continent> d_continentList;
    public int d_playerCount;

    public DistributeCountriesService(List<Player> p_playerList, List<Country> p_countryList, List<Continent> p_continentList)
    {
        //TODO GameEngine.getInstance().getPlayerList();
        d_playerList = p_playerList;
        d_countryList = p_countryList;
        d_continentList = p_continentList;
    }

    public void distributeCountries()
    {
        d_playerCount = d_playerList.size();
        int l_turnValue = 0;
        for (Player p: d_playerList)
        {
            l_turnValue++;
            p.setTurnValue(l_turnValue);
        }
    }
}
