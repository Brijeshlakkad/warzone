package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.exceptions.InvalidInputException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This service handles `editcontinent` user command to add and/or remove continent from the map.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ContinentService {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    public ContinentService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
    }

    /**
     * Adds the continent to the list stored at map editor engine.
     *
     * @param p_continentName Value of the continent name.
     * @param p_countryValue  Value of the co
     */
    public void add(String p_continentName, String p_countryValue) throws InvalidInputException {
        try {
            int l_parsedControlValue = Integer.parseInt(p_countryValue);
            Continent l_continent = new Continent();
            l_continent.setContinentName(p_continentName);
            l_continent.setContinentControlValue(l_parsedControlValue);
            d_mapEditorEngine.addContinent(l_continent);
        } catch (Exception e) {
            throw new InvalidInputException("Continent value is not valid");
        }
    }

    /**
     * Removes the continent from the list using the name.
     *
     * @param p_continentName Value of the continent name.
     */
    public void remove(String p_continentName) {
        // We can check if the continent exists before filtering?
        // Filters the continent list using the continent name
        Set<Continent> l_filteredContinentList = d_mapEditorEngine.getContinentSet().stream().filter(p_continent ->
                p_continent.getContinentName().equals(p_continentName)
        ).collect(Collectors.toSet());

        d_mapEditorEngine.setContinentSet(l_filteredContinentList);
    }
}
