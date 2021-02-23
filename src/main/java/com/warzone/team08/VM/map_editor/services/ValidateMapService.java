package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidMapException;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.*;

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
     * Checks that continent is a connected subgraph.
     *
     * @return True if validation passes.
     */
    public boolean isContinentConnectedSubgraph() {
        if (d_mapEditorEngine.getContinentList().size() > 1) {
            boolean l_isInvalid = false;
            String l_continentName;
            List<String> l_countriesIntoContinent;
            CountryRepository l_countryRepository = new CountryRepository();
            Country l_countryName = null;
            int l_totalContinent = d_mapEditorEngine.getContinentList().size();
            int l_compareTotalContinent = 0;

            Map<String, List<String>> l_continentCountryMap = d_mapEditorEngine.getContinentCountryMap();
            for (Map.Entry<String, List<String>> entry : l_continentCountryMap.entrySet()) {
                //set countries for each Continent
                l_continentName = entry.getKey();
                l_countriesIntoContinent = entry.getValue();
                int l_otherContinentNeighbour = 0;

                //Checks that atleast 1 neighbour from other continent
                for (String l_countryNameCompare : l_countriesIntoContinent) {
                    try {
                        l_countryName = l_countryRepository.findFirstByCountryName(l_countryNameCompare);
                    } catch (EntityNotFoundException e) {
                        e.printStackTrace();
                    }
                    List<Country> l_neighbourCountries = l_countryName.getNeighbourCountries();

                    for (Country l_country : l_neighbourCountries) {
                        Continent l_continent = l_country.getContinent();
                        String ContinentName = l_continent.getContinentName();
                        if (!(ContinentName.equals(l_continentName))) {
                            l_otherContinentNeighbour++;
                            break;
                        }
                    }

                    if (l_otherContinentNeighbour > 0) {
                        l_compareTotalContinent++;
                        break;
                    }
                }
            }
            //checks that total continent value is same as test passes or not.
            if (l_compareTotalContinent == l_totalContinent) {
                l_isInvalid = true;
            }
            return l_isInvalid;
        } else {
            return true;
        }
    }


    /**
     * Checks that map is a connected graph.
     *
     * @return True if validation pass.
     */
    public boolean isMapConnectedGraph() {
        boolean l_isValid = false;
        List<Country> l_visitedCountry = new ArrayList<>();
        Stack<Country> l_stack = new Stack<>();
        List<Country> l_countryList = d_mapEditorEngine.getCountryList();
        Country l_country = l_countryList.get(0);
        l_stack.push(l_country);

        //visiting country using the DFS logic
        while (!l_stack.isEmpty()) {
            Country l_countryGet = l_stack.pop();
            l_visitedCountry.add(l_countryGet);
            List<Country> l_neighbourCountries = l_countryGet.getNeighbourCountries();
            for (Country l_pushCountry : l_neighbourCountries) {
                if (!l_stack.contains(l_pushCountry)) {
                    int l_counter = 0;
                    for (Country l_compareCountry : l_visitedCountry) {
                        if (l_pushCountry.equals(l_compareCountry)) {
                            l_counter++;
                        }
                    }
                    if (l_counter == 0) {
                        l_stack.push(l_pushCountry);
                    }
                }
            }
        }
        //Check that CountryList and VisitedCountryList are same or not
        int compareCounter = 0;
        for (Country l_compareCountry : l_countryList) {
            for (Country l_compare2 : l_visitedCountry) {
                if (l_compare2.equals(l_compareCountry)) {
                    compareCounter++;
                }
            }
        }
        if (compareCounter == l_countryList.size()) {
            l_isValid = true;
        }
        return l_isValid;
    }

    /**
     * Checks the bonus number(Control Value) of the territories as per the warzone game rules.
     *
     * @param p_continentList Value of the total continent number.
     * @return True if the validation passes.
     */
    private boolean validationControlValue(List<Continent> p_continentList) {
        boolean l_isValid = false;
        int p_continentCount = p_continentList.size();
        if (p_continentCount == 1 || p_continentCount == 2) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == 0) {
                    l_isValid = true;
                    break;
                }
            }
        } else if (p_continentCount == 3 || p_continentCount == 4 || p_continentCount == 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == p_continentCount - 1) {
                    l_isValid = true;
                    break;
                }
            }
        } else if (p_continentCount > 5) {
            for (Continent l_continent : p_continentList) {
                if (l_continent.getContinentControlValue() == p_continentCount - 1
                        || l_continent.getContinentControlValue() == p_continentCount - 2) {
                    l_isValid = true;
                    break;
                }
            }
        } else {
            return false;
        }
        return l_isValid;
    }

    /**
     * Initiate all the validation procedures. Checks all the validation and replies to the execute method.
     *
     * @param p_commandValues Values of command entered by user if any.
     * @return Value of the response.
     */
    @Override
    public String execute(List<String> p_commandValues) throws InvalidMapException {
        //Checks map has atleast 1 continent
        if (d_mapEditorEngine.getContinentList().size() > 0) {
            //Control value should be as per the warzone rules
            if (validationControlValue(d_mapEditorEngine.getContinentList())) {
                //Check for the minimum number of countries required
                if (d_mapEditorEngine.getCountryList().size() > 1) {
                    //check that every continent should have atleast 1 country
                    if (d_mapEditorEngine.getCountryList().size() >= d_mapEditorEngine.getContinentList().size()) {
                        //check every country is reachable
                        if (isContinentConnectedSubgraph()) {
                            //Check that continent is a connected subgraph
                            if (isMapConnectedGraph()) {
                                return "Map validation passed successfully";
                            } else {
                                throw new InvalidMapException("map must be a connected graph");
                            }
                        } else {
                            throw new InvalidMapException("Continent must be a connected subgraph");
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
}