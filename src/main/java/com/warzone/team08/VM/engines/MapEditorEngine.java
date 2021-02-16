package com.warzone.team08.VM.engines;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private Set<Continent> d_continentSet;

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
     * @inheritDoc
     */
    public void initialise() {
        d_continentSet = new HashSet<>();
    }

    /**
     * Gets the list of continents.
     *
     * @return d_continentList List of continents.
     */
    public Set<Continent> getContinentSet() {
        return d_continentSet;
    }

    /**
     * Sets the value of continent list.
     *
     * @param p_continentSet list of continents.
     */
    public void setContinentSet(Set<Continent> p_continentSet) {
        d_continentSet = p_continentSet;
    }

    /**
     * Gets the list of the countries.
     *
     * @return list of countries.
     */
    public Set<Country> getCountrySet() {
        Set<Country> l_countries = new HashSet<>();
        for (Continent l_continent : d_continentSet) {
            l_countries.addAll(l_continent.getCountrySet());
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
        Set<Country> l_countries = this.getCountrySet();
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
    public Map<String, Set<String>> getContinentCountryMap() {
        Map<String, Set<String>> l_continentCountryMap = new HashMap<>();
        for (Continent l_continent : d_continentSet) {
            for (Country l_country : l_continent.getCountrySet()) {
                String continentName = l_continent.getContinentName();
                Set<String> l_countryNames;
                if (l_continentCountryMap.containsKey(continentName)) {
                    l_countryNames = l_continentCountryMap.get(continentName);
                } else {
                    l_countryNames = new HashSet<>();
                }
                l_countryNames.add(l_country.getCountryName());
                l_continentCountryMap.put(continentName, l_countryNames);
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
        d_continentSet.add(p_continent);
    }
}
