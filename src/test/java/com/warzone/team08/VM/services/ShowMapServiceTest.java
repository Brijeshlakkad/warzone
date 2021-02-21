package com.warzone.team08.VM.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.exceptions.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * This test class tests the ShowSelectedMap class functions
 * @author MILESH
 */
public class ShowMapServiceTest {
    ShowMapService d_showMapService;

    /**
     * setting up the context by loading the map file before testing the class methods.
     */
    @BeforeClass
    public static void beforeClass(){
        URL d_testFilePath;
        EditMapService l_editMapService=new EditMapService();
        d_testFilePath = ShowMapServiceTest.class.getClassLoader().getResource("map_files/testMap.map");
        //String l_resolvedPathToFile = PathResolverUtil.resolveFilePath("testMap.map");
        try {
            String l_message=l_editMapService.handleLoadMap(d_testFilePath.getPath());
        } catch (InvalidMapException | AbsentTagException | ResourceNotFoundException | InvalidInputException | EntityNotFoundException p_e) {
            p_e.printStackTrace();
        }
    }

    /**
     * This method will initialise the ShowMapService object before testing every test method.
     */
    @Before
    public void before(){
        d_showMapService=new ShowMapService();
    }
    /**
     * It tests the showContinentContent method which returns the String of continent information
     */
    @Test
    public void showContinentCountryContentTest() {
        String[] l_header = {"Continent Name", "Control Value", "Countries"};
        String[][] l_mapMatrix={
                {"Earth","10","Earth-Atlantic,Earth-SouthAmerica,Earth-SouthPole"},
                {"Venus","8","Venus-East,Venus-South,Venus-Southwest"},
                {"Mercury","6","Mercury-East,Mercury-North,Mercury-South,Mercury-West"}
        };
        String l_mapTable = FlipTable.of(l_header, l_mapMatrix);
        String l_mapData = d_showMapService.showContinentCountryContent();
        assertNotNull(l_mapData);
        assertEquals(l_mapTable,l_mapData);
    }

    @Test
    public void showNeighbourCountriesTest() {
        String[][] l_neighbourMatrix={
                {"COUNTRIES","Mercury-South","Mercury-East","Mercury-West","Mercury-North","Venus-South","Venus-East","Venus-Southwest","Earth-SouthPole","Earth-SouthAmerica","Earth-Atlantic"},
                {"Mercury-South","X","X","X","O","O","O","O","O","O","O"},
                {"Mercury-East","X","X","X","X","O","O","O","O","O","O"},
                {"Mercury-West","X","X","X","X","O","O","O","O","O","O"},
                {"Mercury-North","O","X","X","X","X","O","O","O","O","O"},
                {"Venus-South","O","O","O","X","X","X","X","O","O","O"},
                {"Venus-East","O","O","O","O","X","X","X","O","O","O"},
                {"Venus-Southwest","O","O","O","O","X","X","X","O","O","O"},
                {"Earth-SouthPole","O","O","X","O","O","O","O","X","X","O"},
                {"Earth-SouthAmerica","O","O","O","O","O","O","X","X","X","X"},
                {"Earth-Atlantic","O","O","O","O","O","O","O","O","X","X"}

        };
        String[] l_countryCountHeader=new String[l_neighbourMatrix.length];
        for(int i=0;i<l_countryCountHeader.length;i++){
            l_countryCountHeader[i]="C"+i;
        }
        String l_countryData = d_showMapService.showNeighbourCountries();
        String l_countryTable = FlipTable.of(l_countryCountHeader,l_neighbourMatrix);
        assertNotNull(l_countryData);
       assertEquals(l_countryTable,l_countryData);
    }
}