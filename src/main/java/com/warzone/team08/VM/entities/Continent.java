package com.warzone.team08.VM.entities;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Continent {
    /**
     * Auto-generated ID of the continent.
     */
    private int d_continentId;
    private String d_continentName;
    private int d_continentControlValue;
    private String d_continentColor;

    /**
     * Used to keep the track of unique IDs for the continent.
     */
    public static int serialNumber = 0;

    public Continent() {
        this.setContinentControlValue(++serialNumber);
    }

    /**
     * Get the id of the continent which is the index where the continent is located.
     *
     * @return Value of the continent ID.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Set the continent id which is an index at which the continent is located.
     *
     * @param p_continentId Value of the id of continent.
     */
    public void setContinentId(int p_continentId) {
        d_continentId = p_continentId;
    }

    /**
     * Sets the value of continent name.
     *
     * @param p_continentName Name of the continent.
     */
    public void setContinentName(String p_continentName) {
        d_continentName = p_continentName;
    }

    /**
     * Gets continent name.
     *
     * @return continent name.
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Sets the continent control value.
     *
     * @param p_continentControlValue Value of the continent control.
     */
    public void setContinentControlValue(int p_continentControlValue) {
        d_continentControlValue = p_continentControlValue;
    }

    /**
     * Gets the continent control value.
     *
     * @return Value of the continent control.
     */
    public int getContinentControlValue() {
        return d_continentControlValue;
    }
}
