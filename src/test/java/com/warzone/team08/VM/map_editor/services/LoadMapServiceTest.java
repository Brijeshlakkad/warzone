package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Test case to test the load map service.
 *
 * @author Brijesh Lakkad
 */
public class LoadMapServiceTest {
    @Before
    public void beforeTestCase() {
        Application l_application = new Application();
        l_application.handleApplicationStartup();
    }

    /**
     * Tests the load map service method to check if the map file is being loaded correctly.
     *
     * @see EditMapService#handleLoadMap
     */
    @Test(expected = Test.None.class)
    public void testLoadMapService() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        LoadMapService l_loadMapService = new LoadMapService();
        l_loadMapService.execute(Collections.singletonList("solar.map"));
    }
}
