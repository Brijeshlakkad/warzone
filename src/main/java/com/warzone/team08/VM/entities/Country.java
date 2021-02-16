package com.warzone.team08.VM.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is to set and get the country variables
 *
 * @author RUTWIK PATEL
 * @author Brijesh Lakkad
 */
public class Country {
    /**
     * Auto-generated ID of the country.
     */
    private int d_countryId;
    private String d_countryName;
    private int d_continentId;
    private List<Integer> d_neighbourCountries;

    /**
     * Used to keep the track of unique IDs for the continent.
     */
    public static int serialNumber = 0;

    public Country() {
        this.d_continentId = ++serialNumber;
        d_neighbourCountries = new ArrayList<>();
    }

    public Country(int p_countryId) {
        this.d_continentId = p_countryId;
        d_neighbourCountries = new ArrayList<>();
    }

    /**
     * Sets the ID for the country.
     *
     * @return Value of the country ID.
     */
    public int getCountryId() {
        return d_continentId;
    }


    /**
     * Sets the country name.
     *
     * @param p_countryName Value of the country name.
     */
    public void setCountryName(String p_countryName) {
        d_countryName = p_countryName;
    }

    /**
     * Gets this country name.
     *
     * @return this name of the country.
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * Sets the continent ID for this country.
     *
     * @param p_continentId Represents the value of continent ID.
     */
    public void setContinentId(int p_continentId) {
        d_continentId = p_continentId;
    }


    /**
     * Gets the continent ID for this country.
     *
     * @return continent ID for this country.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Sets the list of the neighboring countries.
     *
     * @param p_neighbourCountries List of neighboring countries.
     */
    public void setNeighbourCountries(List<Integer> p_neighbourCountries) {
        d_neighbourCountries = p_neighbourCountries;
    }

    /**
     * Gets the list of neighbor countries.
     *
     * @return Value of neighboring countries list.
     */
    public List<Integer> getNeighbourCountries() {
        return d_neighbourCountries;
    }

    /**
     * Adds the neighbor to the country.
     *
     * @param p_neighbourCountryId Id of the neighbor country.
     */
    public void addNeighbourCountry(Integer p_neighbourCountryId) {
        d_neighbourCountries.add(p_neighbourCountryId);
    }
}
