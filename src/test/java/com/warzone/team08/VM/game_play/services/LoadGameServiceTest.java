package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.common.services.LoadGameService;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.repositories.CountryRepository;
import com.warzone.team08.VM.utils.FileUtil;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;


/**
 * This game tests if the file is loaded successfully.
 * @author Rutwik
 */
public class LoadGameServiceTest {
    private static Application d_application;
    private static URL d_testFilePath;
    private static URL d_testSavedFilePath;
    private static GamePlayEngine d_gamePlayEngine;

    @BeforeClass
    public static void beforeClass(){
        d_application = new Application();
        d_application.handleApplicationStartup();
        VirtualMachine.getInstance().initialise();

        d_gamePlayEngine = VirtualMachine.getGameEngine().getGamePlayEngine();
        d_testSavedFilePath = LoadGameServiceTest.class.getClassLoader().getResource("test_game_files/test_earth.warzone");
    }

    @Before
    public void before() throws InvalidInputException, EntityNotFoundException, InvalidMapException, ResourceNotFoundException, AbsentTagException, URISyntaxException {
        d_gamePlayEngine.initialise();

        EditMapService l_editMapService = new EditMapService();
        assertNotNull(d_testFilePath);

        String l_url = new URI(d_testFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);


    }

    @Test(expected = Test.None.class)
    public void testLoadFile() throws VMException {
        LoadGameService l_loadGameService = new LoadGameService();
        File l_targetFile = FileUtil.retrieveGameFile(d_testSavedFilePath.getPath());
        StringBuilder l_fileContentBuilder = new StringBuilder();
        try(BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_targetFile))) {
            String l_currentLine;
            while ((l_currentLine = l_bufferedReader.readLine()) != null) {
                l_fileContentBuilder.append(l_currentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(l_fileContentBuilder.toString());
        l_loadGameService.loadGameState(jsonObject);
    }
}
