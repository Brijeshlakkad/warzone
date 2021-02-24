package com.warzone.team08.VM.map_editor;

import com.warzone.team08.VM.constants.interfaces.Engine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;

import java.util.*;

/**
 * Manages player runtime data, such as: The continents, countries, and the neighbors.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 * @version 1.0
 */
public class MapEditorEngine implements Engine {
    /**
     * Singleton instance of the class.
     */
    private static MapEditorEngine d_instance;

    private List<Continent> d_continentList;

    /**
     * Gets the single instance of the class.
     *
     * @return Value of the instance.
     */
    public static MapEditorEngine getInstance() {
        if (d_instance == null) {
            d_instance = new MapEditorEngine();
        }
        return d_instance;
    }

    /**
     * Instance can not be created outside the class. (private)
     */
    private MapEditorEngine() {
        this.initialise();
    }

    /**
     * {@inheritDoc}
     */
    public void initialise() {
        d_continentList = new ArrayList<>();
        // resets serial information to start from zero again for the next iteration of loading the map.
        Continent.resetSerialNumber();
        Country.resetSerialNumber();
    }

    /**
     * Gets the list of continents.
     *
     * @return d_continentList List of continents.
     */
    public List<Continent> getContinentList() {
        return d_continentList;
    }

    /**
     * Sets the value of continent list.
     *
     * @param p_continentList list of continents.
     */
    public void setContinentList(List<Continent> p_continentList) {
        d_continentList = p_continentList;
    }

    /**
     * Gets the list of the countries.
     *
     * @return list of countries.
     */
    public ArrayList<Country> getCountryList() {
        ArrayList<Country> l_countries = new ArrayList<>();
        for (Continent l_continent : d_continentList) {
            for (Country l_country : l_continent.getCountryList()) {
                if (!l_countries.contains(l_country)) {
                    l_countries.add(l_country);
                }
            }
        }
        return l_countries;
    }

    /**
     * This method returns the map consisting country as a key and list of its neighboring countries as a value.
     *
     * @return Value of the map of country and its neighbors.
     */
    public Map<Integer, Set<Integer>> getCountryNeighbourMap() {
        Map<Integer, Set<Integer>> l_continentCountryMap = new HashMap<>();
        ArrayList<Country> l_countries = this.getCountryList();
        for (Country l_country : l_countries) {
            Set<Integer> l_neighborCountryIdList = new HashSet<>();
            for (Country l_neighborCountry : l_country.getNeighbourCountries()) {
                l_neighborCountryIdList.add(l_neighborCountry.getCountryId());
            }
            l_continentCountryMap.put(l_country.getCountryId(), l_neighborCountryIdList);
        }
        return l_continentCountryMap;
    }


    /**
     * This method returns the map consisting continent name as a key and list of country names available in that
     * continent as a value.
     *
     * @return map of continent and its member countries.
     */
    public Map<String, List<String>> getContinentCountryMap() throws EntityNotFoundException {
        Map<String, List<String>> l_continentCountryMap = new HashMap<>();
        for (Continent l_continent : d_continentList) {
            if(!l_continent.getCountryList().isEmpty()){
                for (Country l_country : l_continent.getCountryList()) {
                    String continentName = l_continent.getContinentName();
                    List<String> l_countryNames;
                    if (l_continentCountryMap.containsKey(continentName)) {
                        l_countryNames = l_continentCountryMap.get(continentName);
                    } else {
                        l_countryNames = new ArrayList<>();
                    }
                    l_countryNames.add(l_country.getCountryName());
                    l_continentCountryMap.put(continentName, l_countryNames);
                }
            }
            else{
                throw new EntityNotFoundException("add minimum 1 country in a continent");
            }
        }
        return l_continentCountryMap;
    }

    /**
     * Adds the element to the list of continents.
     *
     * @param p_continent Value of the element.
     */
    public void addContinent(Continent p_continent) {
        d_continentList.add(p_continent);
    }

    /**
     * {@inheritDoc} Shuts the <code>MapEditorEngine</code>.
     */
    public void shutdown() {
        // No threads created by MapEditorEngine.
    }
}
