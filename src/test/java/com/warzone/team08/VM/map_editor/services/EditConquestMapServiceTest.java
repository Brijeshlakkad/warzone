package com.warzone.team08.VM.map_editor.services;

import com.fasterxml.jackson.databind.util.TypeKey;
import com.warzone.team08.Application;
import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


/**
 * This class tests the blank fields available in the map file.
 *
 * @author CHARIT
 */
public class EditConquestMapServiceTest {

    private static URL d_testCorruptedFilePath;
    private static URL d_testCorrectFilePath;
    private static MapEditorEngine d_mapEditorEngine;
    private EditConquestMapService d_editConquestMapService;

    /**
     * Sets the path to the files.
     */
    @BeforeClass
    public static void beforeClass() {
        Application l_application = new Application();
        l_application.handleApplicationStartup();
        d_testCorruptedFilePath = EditConquestMapServiceTest.class.getClassLoader().getResource("test_map_files/test_blank_field_in_conquest_map.map");
        d_testCorrectFilePath = EditConquestMapServiceTest.class.getClassLoader().getResource("map_files/conquest1.map");
    }

    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @Before
    public void before() {
        d_editConquestMapService = new EditConquestMapService();
        GameEngine.getInstance().initialise();
        d_mapEditorEngine = MapEditorEngine.getInstance();
    }

    /**
     * This is a method that performs actual test. It test passes if .map file consists of any empty field.
     *
     * @throws Exception IOException
     */
    @Test(expected = AbsentTagException.class)
    public void testLoadCorruptedMap() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(d_testCorruptedFilePath.getPath()).getPath();
        d_editConquestMapService.loadConquestMap(l_url);
    }

    /**
     * This method loads the map file and expects the none exception.
     *
     * @throws Exception IOException
     */
    @Test(expected = Test.None.class)
    public void testLoadCorrectMapFile() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(d_testCorrectFilePath.getPath()).getPath();
        d_editConquestMapService.loadConquestMap(l_url);
    }

    @Test
    public void testPrint() throws URISyntaxException, InvalidInputException, AbsentTagException, EntityNotFoundException, ResourceNotFoundException, InvalidMapException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        HashMap<String, String> h;
        String l_url = new URI(d_testCorrectFilePath.getPath()).getPath();
        String s = d_editConquestMapService.loadConquestMap(l_url);
        h = d_mapEditorEngine.getMapDetails();
        System.out.println(s);
        for (String name: h.keySet()) {
            String key = name;
            String value = h.get(name);
            System.out.println(key + " " + value);
        }

        List<Continent> l = d_mapEditorEngine.getContinentList();
        System.out.println(l);

        List<Country> lco = d_mapEditorEngine.getCountryList();
        for(Country c : lco)
        {
            System.out.println(c.getCountryName());
        }

        HashMap<String, List<String>> hs = (HashMap<String, List<String>>) d_mapEditorEngine.getContinentCountryMap();
        for (String name: hs.keySet()) {
            String key = name;
            List<String> value = hs.get(name);
            System.out.println(key + " " + value);
        }

        Map<Integer, Set<Integer>> hsm = d_mapEditorEngine.getCountryNeighbourMap();
        for (Integer name: hsm.keySet()) {
            Integer key = name;
            Set<Integer> value = hsm.get(name);
            Iterator<Integer> it = value.iterator();

            System.out.println(key);
            while (it.hasNext()) {
                System.out.print(it.next()+" ");
            }
            //value.iterator().forEachRemaining(System.out::println);

        }
      //  System.out.println(value.size());
    }
}