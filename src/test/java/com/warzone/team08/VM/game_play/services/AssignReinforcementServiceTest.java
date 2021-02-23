package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This Class will test the reinforced army as in the reinforced army is set correctly or not.
 *
 * @author Rutwik
 */
public class AssignReinforcementServiceTest {
    private static MapEditorEngine d_MapEditorEngine;
    private static EditMapService d_EditMapService;
    private static URL d_testFile;
    private static DistributeCountriesService d_DistributeCountriesService;
    private static AssignReinforcementService d_AssignReinforcementService;
    private static GamePlayEngine d_GamePlayEngine;


    /**
     * Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_MapEditorEngine = MapEditorEngine.getInstance();
        d_testFile = AssignReinforcementServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }


    /**
     * Setting up the required Objects before test run.
     */
    @Before
    public void before() throws InvalidInputException, AbsentTagException, InvalidMapException, ResourceNotFoundException, EntityNotFoundException {
        d_GamePlayEngine.initialise();
        d_MapEditorEngine.initialise();
        d_MapEditorEngine.getCountryList();
        d_AssignReinforcementService = new AssignReinforcementService();

        Player l_player1 = new Player();
        Player l_player2 = new Player();
        Player l_player3 = new Player();

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);
        d_GamePlayEngine.addPlayer(l_player3);

        d_EditMapService = new EditMapService();
        d_EditMapService.handleLoadMap(d_testFile.getPath());
        d_DistributeCountriesService = new DistributeCountriesService();
        d_DistributeCountriesService.distributeCountries();
    }

    /**
     * This test will test the assign country list. It checks whether the assign country list is empty or not.
     */
    @Test(expected = Test.None.class)
    public void testAssignCountry() {
        for (Player l_player : GamePlayEngine.getInstance().getPlayerList()) {
            assertNotNull(l_player.getAssignedCountries());
        }
    }

    /**
     * This Test will test the re-calculate reinforced army. It checks whether the army is reinforced properly or not.
     */
    @Test
    public void testingCalculatedReinforcedArmyValue() {
        d_AssignReinforcementService.AssignArmy();
        int l_reinforcementArmies = GamePlayEngine.getInstance().getPlayerList().get(0).getReinforcementCount();
        assertEquals(9, l_reinforcementArmies);

        int l_reinforcementArmies1 = GamePlayEngine.getInstance().getPlayerList().get(1).getReinforcementCount();
        assertEquals(11, l_reinforcementArmies1);

        int l_reinforcementArmies2 = GamePlayEngine.getInstance().getPlayerList().get(2).getReinforcementCount();
        assertEquals(13, l_reinforcementArmies2);
    }
}
