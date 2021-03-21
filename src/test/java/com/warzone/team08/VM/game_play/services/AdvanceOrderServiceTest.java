package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests various operations performed during the execution of the advance command.
 *
 * @author CHARIT
 */
public class AdvanceOrderServiceTest {
    private static Application d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private DistributeCountriesService d_distributeCountriesService;
    private List<Player> d_playerList;
    private Player d_player1;
    private Player d_player2;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Application();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = AdvanceOrderServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Setting up the required objects before performing test.
     *
     * @throws AbsentTagException        Throws if any tag is missing in the map file.
     * @throws InvalidMapException       Throws if map file is invalid.
     * @throws ResourceNotFoundException Throws if the file not found.
     * @throws InvalidInputException     Throws if user input is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     */
    @Before
    public void before() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        // (Re)initialise the VM.
        VirtualMachine.getInstance().initialise();

        // Loads the map
        EditMapService l_editMapService = new EditMapService();
        assert d_TestFilePath != null;
        l_editMapService.handleLoadMap(d_TestFilePath.getPath());

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);
        d_playerList = d_GamePlayEngine.getPlayerList();
        d_distributeCountriesService = new DistributeCountriesService();
        //Distributes countries between players.
        d_distributeCountriesService.distributeCountries();

        d_player1 = d_playerList.get(0);
        d_player2 = d_playerList.get(1);
    }

    /**
     * Tests the advance operation when source and destination countries are invalid.
     * Here countries are not present in the list of available countries.
     * So it will raise EntityNotFoundException.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testInvalidCountry() throws InvalidInputException, EntityNotFoundException {
        //Randomly passing country name.
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService("INDIA", "CANADA", 10, d_player1);
        l_advanceOrderService.execute();
    }

    /**
     * Tests the advance operation when number of armies moved are invalid(negative).
     * It will raise InvalidInputException.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = InvalidInputException.class)
    public void testInvalidNoOfArmies() throws InvalidInputException, EntityNotFoundException {
        //Passing negative number of armies to move.
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService("Mercury-South", "Mercury-East", -10, d_player1);
        l_advanceOrderService.execute();
    }

    /**
     * Tests the advance operation when the source country is invalid.
     * Here source country is owned by another player, which is invalid.
     * It will raise InvalidInputException.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = InvalidInputException.class)
    public void testAdvanceOrderAsInvalidSourceCountry() throws InvalidInputException, EntityNotFoundException {

        //Passing the opponent player's country as a source country.
        //It will raise an InvalidInputException as we cannot move armies from another player's country.
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService(d_player2.getAssignedCountries().get(0).getCountryName(), d_player2.getAssignedCountries().get(1).getCountryName(), 10, d_player1);
        l_advanceOrderService.execute();
    }

    /**
     * Tests the advance operation when source and destination countries are owned by the same player.
     * So it will simply adds the number of armies. There will not be any battle.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = Test.None.class)
    public void testArmiesWhenMovedToOwnCountry() throws InvalidInputException, EntityNotFoundException {
        Country l_country1 = d_player1.getAssignedCountries().get(0);
        Country l_country2 = d_player1.getAssignedCountries().get(1);

        //Randomly assigning armies to source country and destination country.
        l_country1.setNumberOfArmies(10);
        l_country2.setNumberOfArmies(20);

        //Moving more armies than available armies.
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService(l_country1.getCountryName(), l_country2.getCountryName(), 20, d_player1);
        l_advanceOrderService.execute();
        assertEquals(30, l_country2.getNumberOfArmies());
    }

    /**
     * Tests the advance operation when the owner of source and destination countries is not same..
     * So there will be a battle.
     * This function tests the successful battle.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = Test.None.class)
    public void testSuccessfulBattle() throws InvalidInputException, EntityNotFoundException {
        Country l_country1 = d_player1.getAssignedCountries().get(4);
        Country l_country2 = d_player2.getAssignedCountries().get(0);

        //Randomly assigning armies to source country and destination country.
        l_country1.setNumberOfArmies(20);
        l_country2.setNumberOfArmies(5);

        //Manually assigning a card.
        d_player1.addCard("bomb");
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService(l_country1.getCountryName(), l_country2.getCountryName(), 10, d_player1);
        l_advanceOrderService.execute();
        assertEquals(10, l_country1.getNumberOfArmies());
        assertEquals(6, l_country2.getNumberOfArmies());
        assertEquals(d_player1, l_country2.getOwnedBy());
        assertEquals(2, d_player1.getCards().size());
    }

    /**
     * Tests the advance operation when the owner of source and destination countries is not same..
     * So there will be a battle.
     * This function tests the unsuccessful battle.
     *
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws EntityNotFoundException Throws if entity not found.
     */
    @Test(expected = Test.None.class)
    public void testUnsuccessfulBattle() throws InvalidInputException, EntityNotFoundException {
        Country l_country1 = d_player1.getAssignedCountries().get(4);
        Country l_country2 = d_player2.getAssignedCountries().get(0);

        //Randomly assigning armies to source country and destination country.
        l_country1.setNumberOfArmies(10);
        l_country2.setNumberOfArmies(5);

        //Manually assigning a card.
        d_player1.addCard("bomb");
        AdvanceOrderService l_advanceOrderService = new AdvanceOrderService(l_country1.getCountryName(), l_country2.getCountryName(), 6, d_player1);
        l_advanceOrderService.execute();
        assertEquals(6, l_country1.getNumberOfArmies());
        assertEquals(1, l_country2.getNumberOfArmies());
        assertEquals(d_player2, l_country2.getOwnedBy());
    }
}