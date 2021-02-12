package com.warzone.team08.models;
/**
 * This class provides different getter-setter methods to perform different operation on Continent.
 * @author CHARIT
 */
public class Continent {

	public String d_continentName;
	public int d_continentControlValue;
	public String d_continentColor;
	public int d_continentSerialNumber;
	
	/**
	 * This method is used to get continent serial number.
	 * Serial number is the index at which the continent is located. 
	 * @return serial number
	 */
	public int getContinentSerialNumber() {
		return d_continentSerialNumber;
	}
	
	/**
	 * This method is used to set continent serial number.
	 * Serial number is the index at which the continent is located. 
	 * 
	 * @param p_continentSerialNumber serial number of the continent
	 */
	public void setContinentSerialNumber(int p_continentSerialNumber) {
		d_continentSerialNumber = p_continentSerialNumber;
	}
	
	/**
	 * This method is used to set continent name
	 * @param p_continentName Name of the continent
	 */
	public void setContinentName(String p_continentName) {
		d_continentName = p_continentName;
	}
	
	/**
	 * This method is used to get continent name.
	 * @return continent name
	 */
	public String getContinentName()
	{
		return d_continentName;
	}

	/**
	 * This method is used to set continent control value
	 * @param p_continentControlValue Control value of the continent
	 */
	public void setContinentControlValue(int p_continentControlValue) {
		d_continentControlValue = p_continentControlValue;
	}
	
	/**
	 * This method is used to get continent control value
	 * @return control value of the continent
	 */
	public int getContinentControlValue() {
		return d_continentControlValue;
	}
}
