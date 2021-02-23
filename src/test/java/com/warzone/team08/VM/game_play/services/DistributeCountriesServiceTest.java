package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests various operations performed during distribution of countries among players.
 *
 * @author CHARIT
 */
public class DistributeCountriesServiceTest {

    private static MapEditorEngine d_MapEditorEngine;
    private static EditMapService d_EditMapService;
    private static URL d_TestFilePath;
    private DistributeCountriesService d_distributeCountriesService;
    private static List<Player> d_PlayerList;
    private List<Country> d_countryList;
    private static GamePlayEngine d_GamePlayEngine;

    /**
     * Setting up the required objects before performing test.
     *
     * @throws Exception Exception generated during execution.
     */
    @Before
    public void before() throws Exception {
        d_countryList = d_MapEditorEngine.getCountryList();
        d_distributeCountriesService = new DistributeCountriesService();
    }

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void createPlayersList() throws Exception {
        d_PlayerList = new ArrayList<Player>();
        Player l_player1 = new Player();
        Player l_player2 = new Player();
        Player l_player3 = new Player();

        l_player1.setName("Charit");
        l_player2.setName("Rutwik");
        l_player3.setName("Brijesh");

        d_PlayerList.add(l_player1);
        d_PlayerList.add(l_player2);
        d_PlayerList.add(l_player3);
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_GamePlayEngine.setPlayerList((ArrayList<Player>) d_PlayerList);
        d_MapEditorEngine = MapEditorEngine.getInstance();
        d_EditMapService = new EditMapService();
        d_TestFilePath = DistributeCountriesServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
        d_EditMapService.handleLoadMap(d_TestFilePath.getPath());
    }

    /**
     * Tests whether the player list is empty or not.
     * Passes if list contains players objects, otherwise fails.
     */
    @Test(expected = Test.None.class)
    public void testNumberOfPlayer() throws InvalidInputException {
        d_distributeCountriesService.distributeCountries();
    }

    /**
     * Tests whether the count of countries required to be assigned to the player is correct or not.
     *
     * @throws InvalidInputException Throws if player objects list is empty.
     */
    @Test(expected = Test.None.class)
    public void testPlayerCountryCount() throws InvalidInputException {
        d_distributeCountriesService.distributeCountries();
        assertEquals(4, d_GamePlayEngine.getPlayerList().get(0).getAssignedCountryCount());
    }

    /**
     * Test whether the countries are correctly assigned or not.
     *
     * @throws InvalidInputException Throws if player objects list is empty.
     */
    @Test(expected = Test.None.class)
    public void testAssignedCountriesCount() throws InvalidInputException {
        String l_response = d_distributeCountriesService.distributeCountries();
        assertNotNull(l_response);

        int l_size = d_GamePlayEngine.getPlayerList().get(0).getAssignedCountries().size();
        assertEquals(4, l_size);
    }
}