package com.warzone.team08.VM.game_play;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.VM.constants.enums.GameLoopState;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class GameEngineTest {
    private static Application d_Application;
    private CommandLineInterface d_commandLineInterface;
    private EditMapService d_editMapService;
    private String d_orderInput;
    private static URL d_TestFilePath;

    /**
     * Set the application environment.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Application();
        d_Application.handleApplicationStartup();
        d_TestFilePath = GameEngineTest.class.getClassLoader().getResource("test_map_files/test_map.map");
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
        Player l_player = new Player();
        l_player.setName("Brijesh");
        l_player.setAssignedCountries(l_assignedCountries);
        l_player.setReinforcementCount(10);
        GamePlayEngine.getInstance().addPlayer(l_player);
    }

    /**
     * Tests the player issue order functionality. An order is tested against the user input and it will be stored in
     * the player's order list.
     *
     * @throws VMException If any exception while processing the issue order request.
     */
    @Test
    public void testOnStartIssueOrderPhase() throws VMException {
        GamePlayEngine.setGameLoopState(GameLoopState.ASSIGN_REINFORCEMENTS);
        GamePlayEngine.getInstance().onStartIssueOrderPhase();
    }
}
