package com.warzone.team08.VM.repositories;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class finds the Continent entity from the runtime engine.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ContinentRepository {
    /**
     * Finds the continent using continent name.
     *
     * @param p_continentName Value of the name of continent.
     * @return Value of the list of matched continents.
     */
    public List<Continent> findContinentWithContinentName(String p_continentName) {
        return MapEditorEngine.getInstance().getContinentList().stream().filter(p_continent ->
                p_continent.getContinentName().equals(p_continentName)
        ).collect(Collectors.toList());
    }

    /**
     * Finds only one continent using its name.
     *
     * @param p_continentName Value of the name of continent.
     * @return Value of the first matched continents.
     */
    public Continent findFirstContinentWithContinentName(String p_continentName) {
        List<Continent> l_continentList = this.findContinentWithContinentName(p_continentName);
        return l_continentList.size() > 0 ? l_continentList.get(0) : null;
    }
}
