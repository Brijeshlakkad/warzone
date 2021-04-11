package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.phases.PlaySetup;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


import static org.junit.Assert.assertNotNull;

/**
 * This file will tests whether the file is created and the items in it are stored or not.
 * @author Rutwik
 */
public class SaveGameTest {
    private static Application d_application;
    private static URL d_testFilePath;
    private static MapEditorEngine d_mapEditorEngine;
    private static GamePlayEngine d_gamePlayEngine;
    private DistributeCountriesService d_distributeCountriesService;
    private AssignReinforcementService d_assignReinforcementService;
    private SaveGame d_saveGame;
    private List<Player> d_playerList;
    private Player d_player1;
    private Player d_player2;
    private String testFile = "testing_save_file.txt";

    /**
     * Create temporary folder for test case.
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();


    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_application = new Application();
        d_application.handleApplicationStartup();
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_testFilePath = SaveGameTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Setting up the required objects before performing test.
     *
     * @throws AbsentTagException         Throws if any tag is missing in the json file.
     * @throws InvalidMapException        Throws if map file is invalid.
     * @throws ResourceNotFoundException  Throws if the file not found.
     * @throws InvalidInputException      Throws if user input is invalid.
     * @throws EntityNotFoundException    Throws if entity not found.
     * @throws URISyntaxException         Throws if URI syntax problem.
     */
    @Before
    public void before() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        VirtualMachine.getInstance().initialise();

        EditMapService l_editMapService = new EditMapService();
        assertNotNull(d_testFilePath);
        System.out.println(d_testFilePath.getPath());
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_gamePlayEngine.addPlayer(l_player1);
        d_gamePlayEngine.addPlayer(l_player2);

        d_playerList = d_gamePlayEngine.getPlayerList();

        d_distributeCountriesService = new DistributeCountriesService();
        //Distributes countries between players.
        d_distributeCountriesService.distributeCountries();

        d_assignReinforcementService = new AssignReinforcementService();
        d_assignReinforcementService.execute();

        d_saveGame = new SaveGame();

    }

    /**
     * test that the content is saved into file or not.
     * @throws IOException Error while opening the file.
     */
    @Test(expected = Test.None.class)
    public void testSaveFile() throws IOException {
        //final File testFileObject = tempFolder.newFile(testFile);
        File file = new File(testFile);
        String response = d_saveGame.saveGameState(file);

        assert file.exists();
        assertNotNull(response);
    }
}