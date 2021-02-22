package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This file tests whether the map file is save successfully or not.
 *
 * @author Rutwik
 */
public class SaveMapServiceTest {
    private static ContinentService d_continentService;
    private static CountryService d_countryService;
    private static CountryNeighborService d_countryNeighbourService;
    private static SaveMapService d_saveMapService;
    private String testFile = "testing_save_file.map";

    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @BeforeClass
    public static void before(){
        d_continentService = new ContinentService();
        d_countryService = new CountryService();
        d_countryNeighbourService = new CountryNeighborService();
        d_saveMapService = new SaveMapService();
    }

    /**
     * Re-initializes the continent list before test case run.
     */
    @Before
    public void beforeTestCase() {
        MapEditorEngine.getInstance().initialise();
    }

    /**
     * This test will add content to Continent List, Country List and Neighbour List.
     *
     * @throws InvalidInputException Any invalid input other than the reqired parameters will throw this error.
     * @throws EntityNotFoundException Any Continent that is not found in the Continent List but added in the Country List will throw this error.
     */
    @Before
    public void addContentToTheMapFile() throws InvalidInputException, EntityNotFoundException {
        d_continentService.add("Asia", "10");
        d_continentService.add("Australia", "15");
        d_countryService.add("Delhi", "Asia");
        d_countryService.add("Mumbai", "Asia");
        d_countryService.add("Melbourne", "Australia");
        d_countryNeighbourService.add("Delhi","Mumbai");
        d_countryNeighbourService.add("Mumbai","Delhi");
        d_countryNeighbourService.add("Melbourne","Delhi");
    }

    /**
     * This test will save the content added in Continent, Country and Neighbour List into the .map file.
     * @throws ResourceNotFoundException If the Target File where content is to be saved is not found then this exception will be raised.
     */
    @Test(expected = Test.None.class)
    public void testSaveFile() throws ResourceNotFoundException, InvalidInputException {
        String response = d_saveMapService.saveToFile(FileUtil.retrieveFile(testFile));

        File createdFile = new File(PathResolverUtil.resolveFilePath(testFile));
        assert createdFile.exists();
        assertNotNull(response);
    }

}
