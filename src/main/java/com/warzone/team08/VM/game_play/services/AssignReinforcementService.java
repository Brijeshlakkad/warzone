package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.services.DistributeCountriesService;

import java.util.ArrayList;
import java.util.List;

public class AssignReinforcementService {
    public static List<DistributeCountriesService> d_DistributedCountryService;
    public List<Player> d_playerList;
    public List<Country> d_CountryList;
    public List<Continent> d_continentList;
    public MapEditorEngine d_mapEditorEngine;

    public AssignReinforcementService(List<Player> p_playerList){
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentList = d_mapEditorEngine.getContinentList();
        d_CountryList = d_mapEditorEngine.getCountryList();
        // TODO GameEngine.getInstance().getPlayerList();
        d_playerList = p_playerList;
        // TODO d_DistributedCountryService.getAssignedCountryList();
    }

    public void AssignArmy(){
    }

}
