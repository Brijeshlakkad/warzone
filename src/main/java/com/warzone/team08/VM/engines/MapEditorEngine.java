package com.warzone.team08.VM.engines;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;

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
    private List<Country> d_countryList;
    private Map<Integer, List<Integer>> d_countryNeighbourMap;
    private Map<String, List<String>> d_continentCountryMap;

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
        d_continentList = new ArrayList<>();
        d_countryList = new ArrayList<>();
        d_countryNeighbourMap = new TreeMap<>();
        d_continentCountryMap = new LinkedHashMap<>();
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
    public List<Country> getCountryList() {
        return d_countryList;
    }

    /**
     * Sets the list of the countries.
     *
     * @param p_countryList List of countries.
     */
    public void setCountryList(List<Country> p_countryList) {
        d_countryList = p_countryList;
    }

    /**
     * This method returns the map consisting country as a key and list of its neighboring countries as a value.
     *
     * @return Value of the map of country and its neighbors.
     */
    public Map<Integer, List<Integer>> getCountryNeighbourMap() {
        return d_countryNeighbourMap;
    }

    /**
     * This method is used to set the map consisting country as a key and list of its neighboring countries as a value.
     *
     * @param p_countryNeighbourMap map of country and its neighbors.
     */
    public void setCountryNeighbourMap(TreeMap<Integer, List<Integer>> p_countryNeighbourMap) {
        d_countryNeighbourMap = p_countryNeighbourMap;
    }

    /**
     * This method returns the map consisting continent name as a key and list of country names available in that
     * continent as a value.
     *
     * @return map of continent and its member countries.
     */
    public Map<String, List<String>> getContinentCountryMap() {
        return d_continentCountryMap;
    }

    /**
     * This method is used to set the map consisting continent name as a key and list of country names available in that
     * continent as a value.
     *
     * @param p_continentCountryMap map of continent and its member countries.
     */
    public void setContinentCountryMap(LinkedHashMap<String, List<String>> p_continentCountryMap) {
        d_continentCountryMap = p_continentCountryMap;
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
     * Adds the element to the list of country.
     *
     * @param p_country Value of the element.
     */
    public void addCountry(Country p_country) {
        d_countryList.add(p_country);
    }

    /**
     * Adds the element to the mappings of country-neighbor.
     *
     * @param p_countryId      Value of id of the country.
     * @param p_neighbourNodes List of the neighbors of country.
     */
    public void addCountryNeighbour(Integer p_countryId, List<Integer> p_neighbourNodes) {
        if (!d_countryNeighbourMap.containsKey(p_countryId)) {
            List<Integer> l_neighborIds = d_countryNeighbourMap.get(p_countryId);
            if (l_neighborIds != null && l_neighborIds.size() > 0) {
                l_neighborIds.addAll(p_neighbourNodes);
                d_countryNeighbourMap.put(p_countryId, l_neighborIds);
            } else {
                d_countryNeighbourMap.put(p_countryId, p_neighbourNodes);
            }
        } else {
            d_countryNeighbourMap.put(p_countryId, p_neighbourNodes);
        }
    }

    /**
     * Adds the element to the mappings of continent-country.
     *
     * @param p_continent   Value of the continent.
     * @param p_countryList Value of the country list to be set with continent.
     */
    public void addContinentCountryMap(String p_continent, List<String> p_countryList) {
        if (!d_continentCountryMap.containsKey(p_continent)) {
            d_continentCountryMap.put(p_continent, p_countryList);
        }
    }
}
