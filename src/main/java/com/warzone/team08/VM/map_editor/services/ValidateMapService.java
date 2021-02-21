package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.InvalidMapException;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains methods for the validation of the map and handles `validatemap` user command.
 *
 * @author Deep Patel
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
     * Initiate all the validation procedures. Checks all the validation and replies to the execute method.
     *
     * @param p_commandValues Values of command entered by user if any.
     * @return Value of the response.
     */
    @Override
    public String execute(List<String> p_commandValues) throws InvalidMapException {
        //checks map has atleast 1 continent
        if (d_mapEditorEngine.getContinentSet().size() > 0) {
            //Control value should be as per the warzone rules
            if (validationControlValue(d_mapEditorEngine.getContinentSet())) {
                //Check for the minimum number of countries required
                if (d_mapEditorEngine.getCountrySet().size() > 1) {
                    //check that every continent should have atleast 1 country
                    if (d_mapEditorEngine.getCountrySet().size() >= d_mapEditorEngine.getContinentSet().size()) {
                        //set the neighbour counter variable for the checking of graph connectivity
                        Map<Integer, Set<Integer>> l_neighbourList = d_mapEditorEngine.getCountryNeighbourMap();
                        int l_neighbourCount = l_neighbourList.size();
                        int l_compareNeighbourCount = 0;
                        for (Map.Entry<Integer, Set<Integer>> l_neighbor : l_neighbourList.entrySet()) {
                            if (l_neighbor.getValue().size() > 0) {
                                l_compareNeighbourCount++;
                            }
                        }
                        //check every country is reachable
                        if (l_compareNeighbourCount == l_neighbourCount) {
                            //set the continent counter variable for the checking of graph connectivity
                            int l_connectedContinentCounter = 0;
                            Map<String, Set<String>> p_continentCountryMap = d_mapEditorEngine.getContinentCountryMap();
                            int p_continentCountrySize = p_continentCountryMap.size();
                            for (Map.Entry<String, Set<String>> entry : p_continentCountryMap.entrySet()) {
                                Set<String> l_countryList = entry.getValue();
                                int l_countryCount = l_countryList.size();
                                if (l_countryCount > 0) {
                                    l_connectedContinentCounter++;
                                }
                            }
                            //check every continent is reachable
                            if (p_continentCountrySize == l_connectedContinentCounter) {
                                return "Map validation passed successfully";
                            } else {
                                throw new InvalidMapException("Continents must be connected");
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
            } else {
                throw new InvalidMapException("ControlValue is not as per the warzone rules");
            }
        } else {
            throw new InvalidMapException("At least one continent required!");
        }
    }

    /**
     * Checks the bonus number(Control Value) of the territories as per the warzone game rules.
     *
     * @param p_continentList Value of the total continent number.
     * @return True if the validation passes.
     */
    private boolean validationControlValue(Set<Continent> p_continentList) {
        boolean l_isInvalid = false;
        int p_continentCount = p_continentList.size();
        if (p_continentCount == 1 || p_continentCount == 2) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == 0) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else if (p_continentCount == 3 || p_continentCount == 4 || p_continentCount == 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == p_continentCount - 1) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else if (p_continentCount > 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == p_continentCount - 1
                        || l_continent.getContinentControlValue() == p_continentCount - 2) {
                    l_isInvalid = true;
                    break;
                }
            }
        } else {
            return l_isInvalid;
        }
        return l_isInvalid;
    }
}