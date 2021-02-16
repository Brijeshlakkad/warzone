package com.warzone.team08.VM.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.exceptions.InvalidMapException;

import java.util.List;
import java.util.Map;

/**
 * This class contains methods for the validation of the map and handles `validatemap` user command.
 *
 * @author Deep
 * @author Brijesh Lakkad
 */

public class ValidateMapService implements SingleCommand {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    public ValidateMapService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
    }

    /**
     * Checks the bonus number(Control Value) of the territories.
     *
     * @param p_continentList Value of the total continent number.
     * @return True if the validation passes.
     * @throws InvalidMapException Throws if the map is invalid
     */
    private boolean validationControlValue(List<Continent> p_continentList) throws InvalidMapException {
        boolean l_isInvalid = false;
        int p_continentCount = p_continentList.size();
        if (p_continentCount == 1 || p_continentCount == 2) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() != 0) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else if (p_continentCount == 3 || p_continentCount == 4 || p_continentCount == 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() != p_continentCount - 1) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else if (p_continentCount > 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() != p_continentCount - 1
                        && l_continent.getContinentControlValue() != p_continentCount - 2) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else {
            l_isInvalid = true;
        }
        if (l_isInvalid) {
            throw new InvalidMapException("Map is not valid!");
        }
        return true;
    }

    /**
     * Initiate all the validation procedures. Checks all the validation and replies to the execute method.
     *
     * @param p_commandValues Values of command entered by user if any.
     * @return Value of the response.
     */
    @Override
    public String execute(List<String> p_commandValues) throws InvalidMapException {
        if (d_mapEditorEngine.getContinentList().size() > 0) {
//            if (validationControlValue(d_mapEditorEngine.getContinentList())) {
            if (d_mapEditorEngine.getCountryList().size() > 1) {
                if (d_mapEditorEngine.getCountryList().size() >= d_mapEditorEngine.getContinentList().size()) {
                    Map<Integer, List<Integer>> l_neighbourList = d_mapEditorEngine.getCountryNeighbourMap();
                    int l_neighborCount = l_neighbourList.size();
                    int l_counter = 0;
                    for (Map.Entry<Integer, List<Integer>> l_neighbor : l_neighbourList.entrySet()) {
                        if (l_neighbor.getValue().size() > 0) {
                            l_counter++;
                        }
                    }
                    if (l_counter == l_neighborCount) {
                        int l_counter1 = 0;
                        Map<String, List<String>> p_continentCountryMap = d_mapEditorEngine.getContinentCountryMap();
                        int p_continentCountrySize = p_continentCountryMap.size();
                        for (Map.Entry<String, List<String>> entry : p_continentCountryMap.entrySet()) {
                            List<String> l_countryList = entry.getValue();
                            int l_countryCount = l_countryList.size();
                            if (l_countryCount > 0) {
                                l_counter1++;
                            }
                        }
                        if (p_continentCountrySize != l_counter1) {
                            throw new InvalidMapException("Continent must have one country!");
                        }
                    } else {
                        throw new InvalidMapException("Country must have one neighbour!");
                    }
                } else {
                    throw new InvalidMapException("Total continents must be lesser or equal to the countries!");
                }
            } else {
                throw new InvalidMapException("At least one country required!");
            }
//            }
        } else {
            throw new InvalidMapException("At least one continent required!");
        }
        return "Map validation passed successfully";
    }
}