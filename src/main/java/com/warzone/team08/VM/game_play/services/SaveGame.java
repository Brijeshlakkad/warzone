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
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.*;

public class SaveGame {

    File fileToSave;
    Map<Integer, Continent> continentMap;
    Map<Integer, Country> countryMap;

    List<Player> playerList;
    Player currentPlayer;
    String currentPhase;
    List<Card> cards;
    List<Continent> continentList ;
    List<Country> countryList;
    Map<Integer, Set<Integer>> neighborList;
    List<String> playerName;
    List<List<Card>> playerCards;


    int getCurrentPlayerIndex;
    int getCurrentPlayerForIssuePhase;
    int getCurrentPlayerForExecutionPhase;
    static int getCurrentExecutionIndex;

    List<Order> playerFutureOrderList;
    GamePlayEngine d_gamePlayEngine;
    MapEditorEngine d_mapEditorEngine;

    public SaveGame(){
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_mapEditorEngine = MapEditorEngine.getInstance();
        continentList = new ArrayList<>();
        countryList = new ArrayList<>();
        neighborList = new HashMap<>();
        playerList = new ArrayList<>();

    }


    void saveGameContent() throws IOException {

        try(Writer l_writer = new FileWriter("abc.txt")) {
            l_writer.write("[ " + "Continent Object" + " ]\n");


            //Getting Continents, Countries and Neighbours.
            continentList.addAll(d_mapEditorEngine.getContinentList());
            String jsonContinent = JSONValue.toJSONString(continentList);
            l_writer.write(jsonContinent+"\n");

            l_writer.write("[ " + "Country Object" + " ]\n");
            countryList.addAll(d_mapEditorEngine.getCountryList());
            String jsonCountry = JSONValue.toJSONString(countryList);
            l_writer.write(jsonCountry+"\n");

            l_writer.write("[ " + "Neighbor Object" + " ]\n");
            neighborList.putAll(d_mapEditorEngine.getCountryNeighbourMap());
            String jsonNeigbor = JSONValue.toJSONString(neighborList);
            l_writer.write(jsonNeigbor+"\n");

            l_writer.write("[ " + "PLayer List" + " ]\n");
            playerList.addAll(d_gamePlayEngine.getPlayerList());
            String jsonPlayers = JSONValue.toJSONString(playerList);
            l_writer.write(jsonPlayers+"\n");

            l_writer.write("[ " + "Current Player" + " ]\n");
            currentPlayer = d_gamePlayEngine.getCurrentPlayer();
            l_writer.write(String.valueOf(currentPlayer)+"\n");

        }

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

