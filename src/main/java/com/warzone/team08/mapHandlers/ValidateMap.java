package com.warzone.team08.mapHandlers;

import java.util.*;

/**
 * This class is contains methods for the validation of the map.
 *
 * @author Deep
 */

public class ValidateMap {
    MapComponents d_Components = MapComponents.getInstance();

    /**
     * This method is going to initiate all the validation and reply back according to that
     *
     * @return l_msg String variable
     */
    public String execute() {
        String l_msg = validation1();
        return l_msg;
    }

    /**
     * This method checks all the validation and reply back to the execute method.
     *
     * @return String to the execute method.
     */
    public String validation1() {

        if (d_Components.getContinentList().size() > 0) {
            if (d_Components.getCountryList().size() > 1) {
                if (d_Components.getCountryList().size() >= d_Components.getContinentList().size()) {
                    TreeMap<Integer, List<Integer>> l_NeighbourList = d_Components.getCountryNeighbourMap();
                    int l_TreeMapSize = l_NeighbourList.size();
                    int l_Counter = 0;
                    for (Map.Entry<Integer, List<Integer>> l_loop : l_NeighbourList.entrySet()) {
                        if (l_loop.getValue().size() > 0) {
                            l_Counter++;
                        }
                    }
                    if (l_Counter == l_TreeMapSize) {
                        int l_Counter1 = 0;
                        LinkedHashMap<String, List<String>> l_continentCountryMap = d_Components.getContinentCountryMap();
                        int l_continentCountrySize = l_continentCountryMap.size();
                        for (Map.Entry<String, List<String>> entry : l_continentCountryMap.entrySet()) {
                            List<String> l_countryList = entry.getValue();
                            int l_countryCount = l_countryList.size();
                            if (l_countryCount > 0) {
                                l_Counter1++;
                            }
                        }
                        if (l_continentCountrySize == l_Counter1) {
                            return "Validation pass";
                        } else {
                            return "Continent must has 1 country";
                        }
                    } else {
                        return "Country must have 1 neighbour";
                    }
                } else {
                    return "Continents must be lesser or equal to the couties";
                }
            } else {
                return "Atleast 1 country required";
            }
        } else {
            return "Atleast 1 continent required";
        }

    }
}
