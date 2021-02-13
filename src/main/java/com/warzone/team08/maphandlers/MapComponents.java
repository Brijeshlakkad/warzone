package com.warzone.team08.maphandlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import com.warzone.team08.models.Continent;
import com.warzone.team08.models.Country;
/**
 * This class provides different methods for validations and handling of smooth execution of the map commands.
 * @author CHARIT
 */
public class MapComponents {
	
	public boolean d_correctMap = true; 
	public String d_errorMessage;
	public List<Continent> d_continentList;
	public List<Country> d_countryList;
	public TreeMap<Integer,List<Integer>> d_countryNeighbourMap = new TreeMap<Integer,List<Integer>>();
	public LinkedHashMap<String,List<String>> d_continentCountryMap = new LinkedHashMap<String, List<String>>();
	private static MapComponents d_mapComponents = new MapComponents();

	/**
	 * Default Constructor
	 */
	private MapComponents(){}
	
	/**
	 * This function returns returns singleton instance of the MapComponents class
	 * @return singleton instance of the class
	 */
	public static MapComponents getInstance()
	{
		return d_mapComponents;
	}
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
	 * @param p_continentList list of continents.
	 */
	public void setContinentList(List<Continent> p_continentList) {
		// TODO Auto-generated method stub
		d_continentList = p_continentList;
	}

	/**

	 * This method returns the list of continents.
	 * @return d_continentList List of continents.
	 */
	public List<Continent> getContinentList() {
		return d_continentList;
	}

	/**
	 * This method is used to set the list of the countries.
	 * @param p_countryList List of countries.
	 */
	public void setCountryList(List<Country> p_countryList) {
		// TODO Auto-generated method stub
		d_countryList = p_countryList;
	}

	/**
	 * This method returns the list of the countries.
	 * @return list of countries.
	 */
	public List<Country> getCountryList() {
		return d_countryList;
	}

	/**
	 * This method returns the map consisting country as a key and list of its neighboring countries as a value.
	 * @return map of country and its neighbors.
	 */
	public TreeMap<Integer, List<Integer>> getCountryNeighbourMap() {
		return d_countryNeighbourMap;
	}
	
	/**
	 * This method is used to set the map consisting country as a key and list of its neighboring countries as a value.
	 * @param p_countryNeighbourMap map of country and its neighbors.
	 */
	public void setCountryNeighbourMap(TreeMap<Integer, List<Integer>> p_countryNeighbourMap) {
		d_countryNeighbourMap = p_countryNeighbourMap;
	}

	/**
	 * This method returns the map consisting continent name as a key and list of country names available in that continent as a value.
	 * @return map of continent and its member countries.
	 */
	public LinkedHashMap<String, List<String>> getContinentCountryMap() {
		return d_continentCountryMap;
	}

	/**
	 * This method is used to set the map consisting continent name as a key and list of country names available in that continent as a value.
	 * @param p_continentCountryMap map of continent and its member countries.
	 */
	public void setContinentCountryMap(LinkedHashMap<String, List<String>> p_continentCountryMap) {
		d_continentCountryMap = p_continentCountryMap;
	}

}