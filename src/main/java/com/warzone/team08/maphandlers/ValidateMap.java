package com.warzone.team08.maphandlers;

import com.warzone.team08.components.Continent;

import java.util.*;

/**
 * This class is contains methods for the validation of the map.
 *
 * @author Deep
 */

public class ValidateMap {
    MapComponents d_components = MapComponents.getInstance();

    /**
     * This method is going to initiate all the validation and reply back according to that
     *
     * @return l_msg String variable
     */
    public String execute() {
        return (validation());
    }

    /**
     * This method checks all the validation and reply back to the execute method.
     *
     * @return String to the execute method.
     */
    public String validation() {
        if (d_components.getContinentList().size() > 0) {
            String l_passFail = ValidationControlValue(d_components.getContinentList().size());
            if (l_passFail.equals("pass")) {
                if (d_components.getCountryList().size() > 1) {
                    if (d_components.getCountryList().size() >= d_components.getContinentList().size()) {
                        TreeMap<Integer, List<Integer>> l_neighbourList = d_components.getCountryNeighbourMap();
                        int l_treeMapSize = l_neighbourList.size();
                        int l_counter = 0;
                        for (Map.Entry<Integer, List<Integer>> l_loop : l_neighbourList.entrySet()) {
                            if (l_loop.getValue().size() > 0) {
                                l_counter++;
                            }
                        }
                        if (l_counter == l_treeMapSize) {
                            int l_counter1 = 0;
                            LinkedHashMap<String, List<String>> p_continentCountryMap = d_components.getContinentCountryMap();
                            int p_continentCountrySize = p_continentCountryMap.size();
                            for (Map.Entry<String, List<String>> entry : p_continentCountryMap.entrySet()) {
                                List<String> l_countryList = entry.getValue();
                                int l_countryCount = l_countryList.size();
                                if (l_countryCount > 0) {
                                    l_counter1++;
                                }
                            }
                            if (p_continentCountrySize == l_counter1) {
                                return "pass";
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
                return "control value fail";
            }
        } else {
            return "Atleast 1 continent required";
        }

    }

    /**
     * This validation method check the bonus number(Control Value) of the territories.
     *
     * @param p_continentCount is the total continent number.
     * @return String "pass" or "fail" as per territory has correct bonus number or not.
     */
    public String ValidationControlValue(int p_continentCount) {
        Continent d_continentObj = new Continent();
        int d_value = d_continentObj.getContinentControlValue();
        String l_return;
        if (p_continentCount == 1 || p_continentCount == 2) {
            if (d_value == 0)
                l_return = "pass";
            else
                l_return = "fail";
        } else if (p_continentCount == 3 || p_continentCount == 4 || p_continentCount == 5) {
            if (d_value == p_continentCount - 1)
                l_return = "pass";
            else
                l_return = "fail";
        } else if (p_continentCount > 5) {
            if (d_value == p_continentCount - 1 || d_value == p_continentCount - 2)
                l_return = "pass";
            else
                l_return = "fail";
        } else {
            l_return = "fail";
        }
        return l_return;
    }
}