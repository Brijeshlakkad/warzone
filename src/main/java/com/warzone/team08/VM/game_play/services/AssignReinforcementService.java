package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.services.DistributeCountriesService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class AssignReinforcementService {
    public static List<DistributeCountriesService> d_DistributedCountryService;
    public List<Player> d_playerList;
    public List<Continent> d_continentList;
    public EditMapService d_editMapService;
    public MapEditorEngine d_mapEditorEngine;
    public URL d_filePath;
    public static Map<String, List<String>> d_ContinentCountryList;
    public DistributeCountriesService d_distributedCountriesService;

    public AssignReinforcementService(List<Player> p_playerList) throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        d_editMapService = new EditMapService();
        d_filePath = d_editMapService.getClass().getClassLoader().getResource("test_map_files/test_map.map");
        d_editMapService.handleLoadMap(d_filePath.getPath());
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentList = d_mapEditorEngine.getContinentList();
        d_distributedCountriesService = new DistributeCountriesService();
        // TODO GameEngine.getInstance().getPlayerList();
        d_playerList = p_playerList;
        // TODO d_DistributedCountryService.getAssignedCountryList();
    }

    public int AssignArmy(){
        d_ContinentCountryList = d_mapEditorEngine.getContinentCountryMap();
        List<String> countryList = new ArrayList<>();
        int l_continentValue = 0;
        int reinforcementArmy = 0;
        for (Player playerList : d_playerList){
            for (Continent continent : d_continentList){
                for (String entry: d_ContinentCountryList.get(continent.getContinentName())) {
                    countryList.add(entry);
                }
                //Method call: It will check whether the player occupies all countries or not.
                int l_returnContinentValue = checkPlayerOwnsContinent(playerList, countryList, continent);
                l_continentValue = l_continentValue + l_returnContinentValue;

                /*System.out.println(continent.getContinentName());
                for (String a : countryList){
                    System.out.println(a);
                }*/
            }
            //Method Call: This will add reinforcement Army to the player at each turn.
            int l_returnReinforcementArmy = addReinforcementArmy(playerList);
            reinforcementArmy = l_returnReinforcementArmy + l_continentValue;
        }
        return reinforcementArmy;
    }

    private int addReinforcementArmy(Player p_playerList) {
        int l_AssignedCountryCount = p_playerList.getAssignedCountries().size();
        int l_reinforcementArmy = Math.max(3,(int) Math.ceil(l_AssignedCountryCount/3));
        return l_reinforcementArmy;
    }

    private int checkPlayerOwnsContinent(Player p_playerList, List<String> p_countryList, Continent p_continent) {
        boolean l_checkCountry = p_playerList.getAssignedCountries().equals(p_countryList);

        if (l_checkCountry){
            return p_continent.getContinentControlValue();
        } else {
            return 0;
        }
    }


    public static void main(String[] args) throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        //Creation of player list
        List<Player> d_playerList = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        d_playerList.add(player1);
        d_playerList.add(player2);
        d_playerList.add(player3);


        AssignReinforcementService assignReinforcementService = new AssignReinforcementService(d_playerList);
        int armyRetrieved = assignReinforcementService.AssignArmy();




    }

}
