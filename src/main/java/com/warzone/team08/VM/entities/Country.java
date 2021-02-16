package com.warzone.team08.VM.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final Integer COUNTRY_ID;
    private String d_countryName;
    private Continent d_continent;
    private List<Country> d_neighbourCountries;

    /**
     * Used to keep the track of unique IDs for the continent.
     */
    public static int serialNumber = 0;

    public Country() {
        this.COUNTRY_ID = ++serialNumber;
        d_neighbourCountries = new ArrayList<>();
    }

    public Country(int p_countryId) {
        this.COUNTRY_ID = p_countryId;
        d_neighbourCountries = new ArrayList<>();
    }

    /**
     * Sets the ID for the country.
     *
     * @return Value of the country ID.
     */
    public Integer getCountryId() {
        return COUNTRY_ID;
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
     * Sets the continent for this country.
     *
     * @param p_continent Represents the value of continent.
     */
    public void setContinent(Continent p_continent) {
        d_continent = p_continent;
    }


    /**
     * Gets the continent of this country.
     *
     * @return continent of this country.
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * Sets the list of the neighboring countries.
     *
     * @param p_neighbourCountries List of neighboring countries.
     */
    public void setNeighbourCountries(List<Country> p_neighbourCountries) {
        d_neighbourCountries = p_neighbourCountries;
    }

    /**
     * Gets the list of neighbor countries.
     *
     * @return Value of neighboring countries list.
     */
    public List<Country> getNeighbourCountries() {
        return d_neighbourCountries;
    }

    /**
     * Adds the neighbor to the country.
     *
     * @param p_neighbourCountry Value of the neighbor country.
     */
    public void addNeighbourCountry(Country p_neighbourCountry) {
        d_neighbourCountries.add(p_neighbourCountry);
    }

    /**
     * Removed the neighbor from the country.
     *
     * @param p_neighbourCountry Value of the neighbor country.
     */
    public void removeNeighbourCountry(Country p_neighbourCountry) {
        d_neighbourCountries.remove(p_neighbourCountry);
    }

    /**
     * Checks if both objects are the same using both the country and continent of the object.
     *
     * @param l_p_o Value of the second element to be checked with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        Country l_l_country = (Country) l_p_o;
        return COUNTRY_ID.equals(l_l_country.COUNTRY_ID) &&
                d_continent.equals(l_l_country.d_continent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(COUNTRY_ID, d_continent);
    }
}
