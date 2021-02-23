package com.warzone.team08.VM.entities;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Tests if the player can issue the order correctly.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class PlayerTest {
    private static Application d_Application;
    private CommandLineInterface d_commandLineInterface;
    private EditMapService d_editMapService;
    private String d_orderInput;
    private static URL d_TestFilePath;
    private Player d_player;

    /**
     * Set the application environment.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Application();
        d_Application.handleApplicationStartup();
        d_TestFilePath = PlayerTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * @see EditMapService#handleLoadMap If any exception thrown.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        // User input text.
        d_orderInput = "deploy Mercury-South 5";
        d_commandLineInterface = new CommandLineInterface();
        d_commandLineInterface.setIn(new ByteArrayInputStream(d_orderInput.getBytes()));

        MapEditorEngine.getInstance().initialise();
        GamePlayEngine.getInstance().initialise();

        d_editMapService = new EditMapService();
        d_editMapService.handleLoadMap(d_TestFilePath.getPath());

        // Set the game state to GAME_PLAY
        Application.VIRTUAL_MACHINE().setGameStatePlaying();

        // TODO Assign countries
        List<Country> l_assignedCountries = MapEditorEngine.getInstance().getCountryList().subList(0, Math.min(4, MapEditorEngine.getInstance().getCountryList().size()));
        d_player = new Player();
        d_player.setName("Brijesh");
        d_player.setAssignedCountries(l_assignedCountries);
        d_player.setReinforcementCount(10);
        GamePlayEngine.getInstance().addPlayer(d_player);
    }

    /**
     * Tests the player issue order functionality. An order is tested against the user input and it will be stored in
     * the player's order list.
     *
     * @throws VMException If any exception while processing the issue order request.
     */
    @Test
    public void testIssueOrder() throws VMException, ExecutionException, InterruptedException {
        d_player.issueOrder();
    }
}

