package com.warzone.team08.VM.entities;

import com.warzone.team08.VM.constants.interfaces.JSONable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is to set and get the country variables
 *
 * @author RUTWIK PATEL
 * @author Brijesh Lakkad
 */
public class Country implements JSONable {
    /**
     * Auto-generated ID of the country.
     */
    private final Integer COUNTRY_ID;
    private String d_countryName;
    private Continent d_continent;
    private List<Country> d_neighbourCountries;
    private Player d_ownedBy;
    private int d_numberOfArmies;

    /**
     * Used to keep the track of unique IDs for the continent.
     */
    public static int d_SerialNumber = 0;

    /**
     * Assigns country id to the country and creates the neighbour countries list.
     */
    public Country() {
        this.COUNTRY_ID = ++d_SerialNumber;
        d_neighbourCountries = new ArrayList<>();
    }

    /**
     * Assigns country id to the country and creates the neighbour countries list.
     *
     * @param p_countryId Country id.
     */
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
     * Resets the serial number to zero. Used when the map engine is being reset.
     */
    public static void resetSerialNumber() {
        d_SerialNumber = 0;
    }

    /**
     * Getter method to determine country owner.
     *
     * @return country owner object.
     */
    public Player getOwnedBy() {
        return d_ownedBy;
    }

    /**
     * Setter method for country owner.
     *
     * @param p_ownedBy Country owner object.
     */
    public void setOwnedBy(Player p_ownedBy) {
        d_ownedBy = p_ownedBy;
    }

    /**
     * Gets the number of armies that are placed on this country by the player <code>getOwnedBy</code>
     *
     * @return Value of the count of armies.
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Sets the number of armies for this country placed by the player.
     *
     * @param p_numberOfArmies Values of the count of armies.
     */
    public void setNumberOfArmies(int p_numberOfArmies) {
        d_numberOfArmies = p_numberOfArmies;
    }

    /**
     * Checks if both objects are the same using both the country and continent of the object.
     *
     * @param p_l_o Value of the second element to be checked with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object p_l_o) {
        if (this == p_l_o) return true;
        if (p_l_o == null || getClass() != p_l_o.getClass()) return false;
        Country l_l_country = (Country) p_l_o;
        return COUNTRY_ID.equals(l_l_country.COUNTRY_ID) &&
                d_continent.equals(l_l_country.d_continent);
    }

    /**
     * Return the hash value of the country.
     *
     * @return Hash value of the country.
     */
    @Override
    public int hashCode() {
        return Objects.hash(COUNTRY_ID, d_continent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject toJSON() {
        JSONObject l_countryJSON = new JSONObject();
        l_countryJSON.put("id", COUNTRY_ID);
        l_countryJSON.put("mame", d_countryName);
        JSONArray d_neighborCountryJSONList = new JSONArray();
        for (Country l_country : getNeighbourCountries()) {
            d_neighborCountryJSONList.put(l_country.toJSON());
        }
        l_countryJSON.put("owner", d_ownedBy.toJSON());
        l_countryJSON.put("numberOfArmies", d_numberOfArmies);
        l_countryJSON.put("neighbourCountries", d_neighborCountryJSONList);
        return l_countryJSON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fromJSON(JSONObject p_jsonObject) {
        // TODO Rutwik Patel Assign data members using the p_jsonObject
    }
}
