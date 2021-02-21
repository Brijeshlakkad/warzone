package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.enums.states.GameState;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * This Class tests the blank fields available in the map file.
 *
 * @author Brijesh Lakkad
 */
public class LoadMapServiceTest {
    private Application d_application;
    private URL d_testFilePath;
    private EditMapService d_editMapService;

    @Before
    public void beforeTestCase() {
        d_application = new Application();
        d_application.handleApplicationStartup();

        // Re-initialise map editor engine.
        MapEditorEngine.getInstance().initialise();

        d_editMapService = new EditMapService();
        d_testFilePath = getClass().getClassLoader().getResource("map_files/solar.map");
    }

    /**
     * Tests the load map service method to check if the map file is being loaded correctly.
     *
     * @see EditMapService#handleLoadMap
     */
    @Test(expected = Test.None.class)
    public void testLoadMapService() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        d_editMapService.handleLoadMap(d_testFilePath.getPath());
        Application.VIRTUAL_MACHINE().setGameStatePlaying();
        assertEquals(Application.getGameState(), GameState.GAME_PLAY);
    }
}
