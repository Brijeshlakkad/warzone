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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests various operations performed during the execution of the airlift command.
 *
 * @author Deep Patel
 */
public class AirliftServiceTest {

    private static Application d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private DistributeCountriesService d_distributeCountriesService;
    private List<Player> d_playerList;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void createPlayersList(){
        d_Application = new Application();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = BombServiceTest.class.getClassLoader().getResource("map_files/solar.map");
    }

    /**
     * Setting up the required objects before performing test.
     *
     * @throws AbsentTagException        Throws if any tag is missing in the map file.
     * @throws InvalidMapException       Throws if the map is invalid.
     * @throws ResourceNotFoundException Throws if resource is not available
     * @throws InvalidInputException     Throws if user input is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @throws URISyntaxException        Throws if URI syntax problem.
     */
    @Before
    public void setup() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        VirtualMachine.getInstance().initialise();

        // Loads the map
        EditMapService l_editMapService = new EditMapService();
        assertNotNull(d_TestFilePath);
        String l_url = new URI(d_TestFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);
        d_playerList = d_GamePlayEngine.getPlayerList();
        d_distributeCountriesService = new DistributeCountriesService();
        d_distributeCountriesService.distributeCountries();
    }

    /**
     * checks that execute method working properly.
     *
     * @throws EntityNotFoundException Throws if entity not found.
     * @throws InvalidInputException   Throws if user input is invalid.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    @Test
    public void testExecute() throws EntityNotFoundException, InvalidInputException, InvalidCommandException, ResourceNotFoundException {
        Player l_player = d_playerList.get(0);
        List<Country> l_playerAssignCountries = l_player.getAssignedCountries();
        l_playerAssignCountries.get(0).setNumberOfArmies(7);
        l_playerAssignCountries.get(1).setNumberOfArmies(5);
        int l_expected = l_playerAssignCountries.get(1).getNumberOfArmies();
        l_player.addCard("airlift");

        AirliftService l_airliftService = new AirliftService(l_playerAssignCountries.get(0).getCountryName(),l_playerAssignCountries.get(1).getCountryName(),2,l_player);
        l_airliftService.execute();
        assertEquals(l_playerAssignCountries.get(1).getNumberOfArmies(),l_expected+2);
    }
}