package com.warzone.team08.mapHandlers;

import java.util.List;

import com.warzone.team08.components.Continent;
import com.warzone.team08.components.Country;
/**
 * This class provides different methods for validations and handling of smooth execution of the map commands.
 * @author CHARIT
 *
 */
public class MapComponents {
	
	public boolean d_correctMap = true; 
	public String d_errorMessage;
	public List<Continent> d_continent_list;
	public List<Country> d_country_list;
	
	
	
	/**
	 * This method is used to set a boolean variable which is used to check whether map is correct or not.
	 * if correct the value is true; otherwise false. 
	 * @param p_bool boolean parameter
	 */
	public void setCorrectMap(boolean p_bool) {
		// TODO Auto-generated method stub
		d_correctMap = p_bool;
		
	}
	
	/**
	 * This method checks whether map is correct or not.
	 * @return d_correctMap boolean parameter
	 */
	public boolean checkMapCorrect() {
		// TODO Auto-generated method stub
		return d_correctMap;
	}

	/**
	 * This method is used to set error message.
	 * @param p_errorMessage message describing error.
	 */
	public void setErrorMessage(String p_errorMessage) {
		// TODO Auto-generated method stub
		d_errorMessage = p_errorMessage;
	}
	/**
	 * This method returns error message.
	 * @return d_errormessage This is an error message returned by function.
	 */
	public String getErrorMessage()
	{
		return d_errorMessage;
	}

	/**
	 * This method is used to set value of continent list.
	 * @param p_continent_list list of continents.
	 */
	public void setContinentList(List<Continent> p_continent_list) {
		// TODO Auto-generated method stub
		d_continent_list = p_continent_list;
	}

	/**
	 * This method returns the list of continents.
	 * @return d_continent_list List of continents.
	 */
	public List<Continent> getContinentList() {
		return d_continent_list;
	}

	
	public void setCountryList(List<Country> p_country_list) {
		// TODO Auto-generated method stub
		d_country_list = p_country_list;
	}

	
	public List<Country> getCountryList() {
		return d_country_list;
	}



}
