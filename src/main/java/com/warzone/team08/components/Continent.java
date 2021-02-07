package com.warzone.team08.components;
/**
 * This class provides different methods to perform different operation on Continent.
 * @author CHARIT
 */
public class Continent {

	public String d_continentName;
	public int d_continentControlValue;
	public String d_continentColor;
	
	public void setContinentName(String p_continentName) {
		// TODO Auto-generated method stub
		d_continentName = p_continentName;
		
	}
	public String getContinentName()
	{
		return d_continentName;
	}

	public void setContinentControlValue(int p_continentControlValue) {
		// TODO Auto-generated method stub
		d_continentControlValue = p_continentControlValue;
	}
	
	public int getContinentControlValue() {
		return d_continentControlValue;
	}
	public void setContinentColor(String p_continentColor) {
		// TODO Auto-generated method stub
		d_continentColor = p_continentColor;
	}
	public String getContinentColor()
	{
		return d_continentColor;
	}

}
