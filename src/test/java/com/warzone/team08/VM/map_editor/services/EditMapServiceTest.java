package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.exceptions.AbsentTagException;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

/**
 * This Class tests the blank fields available in the map file.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 */
public class EditMapServiceTest {
    private EditMapService d_editMapService;
    private URL d_testFilePath;

    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @Before
    public void before() {
        d_editMapService = new EditMapService();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_blank_data_fields.map");
    }

    /**
     * This is a method that performs actual test. It test passes if .map file consists of any empty field.
     *
     * @throws Exception IOException
     */
    @Test(expected = AbsentTagException.class)
    public void testLoadCorruptedMap() throws Exception {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
    }
}
