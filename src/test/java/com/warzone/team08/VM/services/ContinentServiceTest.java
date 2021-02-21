package com.warzone.team08.VM.services;

import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the add and remove operations on continent.
 * @author CHARIT
 */
public class ContinentServiceTest {
    private static ContinentService d_continentService;

    /**
     * This method runs before the test case class runs. This method initializes different objects required to perform test.
     */
    @BeforeClass
    public static void before() {
        d_continentService = new ContinentService();
    }

    /**
     * Tests whether the continent is successfully added or not.
     * @throws InvalidInputException throws if input is invalid.
     */
    @Test//(expected = InvalidInputException.class)
    public void testAddContinent() throws InvalidInputException {
        String l_str = d_continentService.add("Asia", "10");
        assertEquals("Asia continent added!", l_str);
    }

    /**
     * Tests whether the continent is successfully removed or not.
     * @throws EntityNotFoundException throws if continent if continent is not available in list.
     */
    @Test//(expected = InvalidInputException.class)
    public void testRemoveContinent() throws EntityNotFoundException {
        String l_str = d_continentService.remove("Asia");
        assertEquals("Asia continent removed!", l_str);
    }
}