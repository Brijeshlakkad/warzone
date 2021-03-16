package com.warzone.team08.VM.entities;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.CLI.constants.states.GameState;
import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.VirtualMachine;
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
    private static URL d_TestFilePath;

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
     * Loads different objects and performs necessary operations required to execute testcase.
     *
     * @throws AbsentTagException        Throws if any tag is missing in map file.
     * @throws InvalidMapException       Throws if map is invalid.
     * @throws ResourceNotFoundException Throws if map file not found.
     * @throws InvalidInputException     Throws if input command is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @see EditMapService#handleLoadMap If any exception thrown.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        // CLI to read and interpret the user input
        d_commandLineInterface = new CommandLineInterface();

        // (Re)initialise the VM.
        VirtualMachine.getInstance().initialise();

        // EditMap service to load the map
        d_editMapService = new EditMapService();
        d_editMapService.handleLoadMap(d_TestFilePath.getPath());

        // Set the game state to GAME_PLAY
        GameEngine.getInstance().setGameState(GameState.GAME_PLAY);

        List<Country> l_assignedCountries = MapEditorEngine.getInstance().getCountryList().subList(0, Math.min(4, MapEditorEngine.getInstance().getCountryList().size()));
        Player l_player1 = new Player();
        l_player1.setName("User_1");
        l_player1.setAssignedCountries(l_assignedCountries);
        l_player1.setReinforcementCount(10);

        Player l_player2 = new Player();
        l_player2.setName("User_2");
        l_player2.setAssignedCountries(l_assignedCountries);
        l_player2.setReinforcementCount(10);

        GamePlayEngine.getInstance().addPlayer(l_player1);
        GamePlayEngine.getInstance().addPlayer(l_player2);
    }

    /**
     * Tests the player issue order functionality. An order is tested against the user input and it will be stored in
     * the player's order list.
     *
     * @throws VMException          Throws if any exception while processing the issue order request.
     * @throws ExecutionException   Throws if error occurs in execution.
     * @throws InterruptedException Throws if interruption occurs during normal execution.
     */
    @Test
    public void testIssueOrder() throws VMException, ExecutionException, InterruptedException {
        // User input text.
        String l_orderInput = "deploy Mercury-South 5";

        d_commandLineInterface.setIn(new ByteArrayInputStream(l_orderInput.getBytes()));
        GamePlayEngine.getInstance().getPlayerList().get(0).issueOrder();
    }

    /**
     * Tests the player issue order functionality when the player enters more reinforcements to deploy than possessing.
     *
     * @throws VMException If any exception while processing the issue order request.
     * @throws ExecutionException   Throws if error occurs in execution.
     * @throws InterruptedException Throws if interruption occurs during normal execution.
     */
    @Test(expected = ReinforcementOutOfBoundException.class)
    public void testOutOfReinforcementIssueOrder() throws VMException, ExecutionException, InterruptedException {
        // User input text.
        String d_outOfResourcesOrderInput = "deploy Mercury-South 14";

        d_commandLineInterface.setIn(new ByteArrayInputStream(d_outOfResourcesOrderInput.getBytes()));

        // Below will throw <code>ReinforcementOutOfBoundException</code> exception.
        GamePlayEngine.getInstance().getPlayerList().get(0).issueOrder();
    }
}

