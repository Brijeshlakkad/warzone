package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * This class tests ValidateMapService class for different types of validation requires for Map.
 */
public class ValidateMapServiceTest {

    private ValidateMapService d_validateMapService;
    private EditMapService d_editMapService;
    private URL d_testFilePath;
    private String d_expectedValue;

    /**
     * This method initialize the EditMapService object for fetching file data in Validation.
     */
    @Before
    public void beforeTest(){
        d_editMapService = new EditMapService();
        d_validateMapService = new ValidateMapService();
        d_expectedValue = "Map validation passed successfully";
    }

    /**
     * map validation - map is a connected graph.
     * @throws Exception in case getPath not able to find path.(NullPointerException)
     */
    @Test(expected = InvalidMapException.class)
    public void testMapIsConnectedGraph() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map_connectedGraph.map");
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
        d_validateMapService.execute(null);
    }

    /**
     * continent validation - continent is a connected subgraph.
     * @throws Exception in case getPath not able to find path.(NullPointerException)
     */
    @Test(expected = InvalidMapException.class)
    public void testContinentConnectedSubgraph() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_continent_subgraph.map");
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
        d_validateMapService.execute(null);
    }

    /**
     * It checks the ValidateMapService's execute method.
     * @throws Exception in case getPath not able to find path.(NullPointerException)
     */
    @Test
    public void testValidateMapService() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("map_files/solar.map");
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
        String l_actualValue = d_validateMapService.execute(null);
        assertEquals(d_expectedValue, l_actualValue);
    }
}