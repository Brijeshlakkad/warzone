package com.warzone.team08.VM.game_play.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This test class tests the game_play's ShowMapService class functions
 *
 * @author MILESH
 */
public class ShowMapServiceTest {
    ShowMapService d_showMapService;
    DistributeCountriesService d_distributeCountriesService;
    PlayerService d_playerService;
    GamePlayEngine d_gamePlayEngine;
    List<Player> d_playerList;

    /**
     * Setting up the context by loading the map file before testing the class methods.
     */
    @BeforeClass
    public static void beforeClass() throws URISyntaxException, AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        EditMapService l_editMapService = new EditMapService();
        MapEditorEngine.getInstance().initialise();
        URL d_testFilePath = ShowMapServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);
    }

    /**
     * This method will initialise the ShowMapService object before running each test cases.
     */
    @Before
    public void before() throws InvalidInputException {
        d_showMapService = new ShowMapService();
        d_playerService = new PlayerService();
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_distributeCountriesService = new DistributeCountriesService();
        d_playerService.add("xyz");
        d_playerService.add("abc");
        d_distributeCountriesService.distributeCountries();
        d_playerList = d_gamePlayEngine.getPlayerList();
    }

    /**
     * It tests the showPlayerContent method which returns the String of player information
     */
    @Test
    public void testShowPlayerContent() {
        String[] l_header1 = {"XYZ", "Mercury-South", "Mercury-East", "Mercury-West", "Mercury-North", "Venus-South"};
        String[] l_playerContent1 = {"Army Count", "0", "0", "0", "0", "0"};

        String l_PlayerExpectedData = FlipTable.of(l_header1, new String[][]{l_playerContent1});
        String l_playerActualData = d_showMapService.showPlayerContent(d_playerList.get(0));
        assertEquals(l_PlayerExpectedData, l_playerActualData);
    }
}