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

import static org.junit.Assert.assertEquals;

/**
 * This class tests various operations performed during the execution of the bomb command.
 *
 * @author CHARIT
 */
public class BombServiceTest {
    private static Application d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private DistributeCountriesService d_distributeCountriesService;
    private List<Player> d_playerList;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Application();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = BombServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
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
    }

    /**
     * Tests the bomb operation for the player having bomb card.
     *
     * @throws EntityNotFoundException Throws if entity not found.
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    @Test(expected = Test.None.class)
    public void testBombOperationWithBombCard() throws EntityNotFoundException, InvalidInputException, InvalidCommandException {
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        //Assigning bomb card manually
        l_player1.addCard("bomb");
        BombService l_bombService = new BombService(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_player2AssignCountries.get(0).setNumberOfArmies(10);
        int l_armies = l_player2AssignCountries.get(0).getNumberOfArmies();
        l_bombService.execute();
        assertEquals(l_player2AssignCountries.get(0).getNumberOfArmies(), l_armies / 2);
    }

    /**
     * Tests the bomb operation if player doesn't have bomb card.
     *
     * @throws EntityNotFoundException Throws if entity not found.
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    @Test(expected = InvalidCommandException.class)
    public void testBombOperationWithOutBombCard() throws EntityNotFoundException, InvalidInputException, InvalidCommandException {
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        //Assigning blockade card manually.
        l_player1.addCard("blockade");
        BombService l_bombService = new BombService(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_player2AssignCountries.get(0).setNumberOfArmies(10);
        int l_armies = l_player2AssignCountries.get(0).getNumberOfArmies();
        l_bombService.execute();
        assertEquals(l_player2AssignCountries.get(0).getNumberOfArmies(), l_armies / 2);
    }

    /**
     * Tests the bomb operation when player performs bomb operation on its own country.
     *
     * @throws EntityNotFoundException Throws if entity not found.
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    @Test(expected = InvalidInputException.class)
    public void testBombOperationOnPlayerOwnedCountry() throws EntityNotFoundException, InvalidInputException, InvalidCommandException {
        Player l_player1 = d_playerList.get(0);
        List<Country> l_assignCountries = l_player1.getAssignedCountries();
        BombService l_bombService = new BombService(l_assignCountries.get(0).getCountryName(), l_player1);
        l_bombService.execute();
    }

    /**
     * Tests whether the first execution has removed the card from the list of cards available to player.
     *
     * @throws EntityNotFoundException Throws if entity not found.
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    @Test(expected = InvalidCommandException.class)
    public void testCardSuccessfullyRemoved() throws EntityNotFoundException, InvalidInputException, InvalidCommandException {
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        //Assigning bomb card manually.
        l_player1.addCard("bomb");
        BombService l_bombService = new BombService(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_bombService.execute();

        //Now during second execution player will not have a bomb card as we have assigned only one bomb card manually.
        //So it will raise InvalidCommandException.
        l_bombService.execute();
    }
}