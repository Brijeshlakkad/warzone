package com.warzone.team08.VM.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.utils.PathResolverUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        EditMapService l_editMapService=new EditMapService();
        String l_resolvedPathToFile = PathResolverUtil.resolveFilePath("testMap.map");
        try {
            String l_message=l_editMapService.handleLoadMap(l_resolvedPathToFile);
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
                {"Mercury","6","Mercury-East,Mercury-North,Mercury-South,Mercury-West"},
                {"Venus","8","Venus-East,Venus-South,Venus-Southwest"},
        };
        String l_mapTable = FlipTable.of(l_header, l_mapMatrix);
        String l_mapData = d_showMapService.showContinentCountryContent();
        assertNotNull(l_mapData);
        assertEquals(l_mapTable,l_mapData);
    }

    @Test
    public void showNeighbourCountriesTest() {
        String[][] l_neighbourMatrix={
                {"COUNTRIES","Earth-Atlantic","Earth-SouthAmerica","Earth-SouthPole","Mercury-East","Mercury-North","Mercury-South","Mercury-West","Venus-East","Venus-South","Venus-Southwest"},
                {"Earth-Atlantic","X","X","O","O","O","O","O","O","O","O"},
                {"Earth-SouthAmerica","X","X","X","O","O","O","O","O","O","X"},
                {"Earth-SouthPole","O","X","X","O","O","O","X","O","O","O"},
                {"Mercury-East","O","O","O","X","X","X","X","O","O","O"},
                {"Mercury-North","O","O","O","X","X","O","X","O","X","O"},
                {"Mercury-South","O","O","O","X","O","X","X","O","O","O"},
                {"Mercury-West","O","O","O","X","X","X","X","O","O","O"},
                {"Venus-East","O","O","O","O","O","O","O","X","X","X"},
                {"Venus-South","O","O","O","O","X","O","O","X","X","X"},
                {"Venus-Southwest","O","O","O","O","O","O","O","X","X","X"},




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