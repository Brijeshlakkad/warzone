package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.interfaces.Card;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.ContinentService;
import com.warzone.team08.VM.map_editor.services.CountryNeighborService;
import com.warzone.team08.VM.map_editor.services.CountryService;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.repositories.CountryRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.URL;
import java.util.*;

public class SaveGame implements Serializable {

    GamePlayEngine d_gamePlayEngine;
    MapEditorEngine d_mapEditorEngine;
    ArrayList<Continent> continentList;
    public SaveGame(){
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_mapEditorEngine = MapEditorEngine.getInstance();
        continentList = new ArrayList<>();
    }


    void saveGameContent() throws IOException {
        for (Continent continent : d_mapEditorEngine.getContinentList()){
            continentList.add(continent);
        }

        try (Writer l_writer = new FileWriter("xyz.txt")){
            l_writer.write("Continents\n");
            l_writer.write(String.valueOf(continentList));
        }
        /*JSONObject jsonObject = new JSONObject();

        jsonObject.put("ID", "1");

        JSONArray continentList = new JSONArray();

        for (Continent continent : d_mapEditorEngine.getContinentList()){
            continentList.add(""+continent+"");
        }
        jsonObject.put("Continents", continentList);

        JSONArray countryList = new JSONArray();
        for (Country country : d_mapEditorEngine.getCountryList()){
            countryList.add(""+country+"");
        }
        jsonObject.put("Countries", countryList);

        JSONArray neighborList = new JSONArray();
        for (Map.Entry<Integer, Set<Integer>> entry : d_mapEditorEngine.getCountryNeighbourMap().entrySet()){
            jsonObject.put(""+entry.getKey()+"",""+entry.getValue()+"");
        }


        try {
            FileWriter file = new FileWriter("abc.txt");
            file.write(jsonObject.toJSONString()+"\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
/*
        for (Player player : d_gamePlayEngine.getPlayerList()) {
            playerName.add(player.getName());
            playerCards.add(player.getCards());
        }
*/
      /*  String jsonPlayersName = JSONValue.toJSONString(playerName);
        l_writer.write(jsonPlayersName);
        String jsonPlayersCards = JSONValue.toJSONString(playerCards);
        l_writer.write(jsonPlayersCards);
        */

        /*getCurrentPlayerIndex = d_gamePlayEngine.getCurrentPlayerTurn();
        getCurrentPlayerForIssuePhase = d_gamePlayEngine.getCurrentPlayerForIssuePhase();
        getCurrentPlayerForExecutionPhase = d_gamePlayEngine.getCurrentPlayerForExecutionPhase();

        JSONArray getValue = new JSONArray();
        getValue.add(getCurrentPlayerIndex);
        getValue.add(getCurrentPlayerForIssuePhase);
        getValue.add(getCurrentPlayerForExecutionPhase);


        playerFutureOrderList.addAll(d_gamePlayEngine.getCurrentFutureOrders());

        cards = currentPlayer.getCards();*/

    }

    public static void main(String[] args) throws IOException, AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {

        GamePlayEngine gamePlayEngine = GamePlayEngine.getInstance();

        gamePlayEngine.initialise();

        ContinentService d_ContinentService = new ContinentService();
        CountryService d_CountryService = new CountryService();
        CountryNeighborService d_CountryNeighbourService = new CountryNeighborService();

        d_ContinentService.add("Asia", "10");
        d_ContinentService.add("Australia", "15");
        d_CountryService.add("Delhi", "Asia");
        d_CountryService.add("Mumbai", "Asia");
        d_CountryService.add("Melbourne", "Australia");
        d_CountryNeighbourService.add("Delhi", "Mumbai");
        d_CountryNeighbourService.add("Mumbai", "Delhi");
        d_CountryNeighbourService.add("Melbourne", "Delhi");


        Player player1 = new Player();
        Player player2 = new Player();

        gamePlayEngine.addPlayer(player1);
        gamePlayEngine.addPlayer(player2);

        player1.setName("Rahul");
        player2.setName("Mohan");





        DistributeCountriesService distributeCountriesService = new DistributeCountriesService();
        distributeCountriesService.distributeCountries();

        AssignReinforcementService assignReinforcementService = new AssignReinforcementService();
        assignReinforcementService.execute();


        CountryRepository countryRepository = new CountryRepository();

        SaveGame saveGame = new SaveGame();
        saveGame.saveGameContent();

    }
}

