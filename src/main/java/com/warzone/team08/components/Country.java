package com.warzone.team08.components;

/*
 * This class is to set and get the country variables 
 * @author RUTWIK PATEL 
 * 
 */
public class Country {
	
	public int d_countrySerialNumber;
	public String d_countryName;
	public int d_parentContinentSerialNumber;
	
	
	/*
	 * This function sets the country Id.
	 * @param p_countrySerialNumber it is the country Id.
	 */
	public void setCountrySerialNumber(int p_countrySerialNumber) 
	{
		d_countrySerialNumber = p_countrySerialNumber;
	}
	
	/*
	 * This function return the country Id.
	 * @return country id.
	 */
	public int getCountrySerialNumber() 
	{
		return d_countrySerialNumber;
	}
	
	
	/*
	 * This function sets the country name.
	 * @param p_countryName, it is the country name.
	 */
	public void setCountryName(String p_countryName) 
    {
		d_countryName = p_countryName;
	}
	
	/*
	 * This function returns current country name.
	 * @return this country name.
	 */
	public String getCountryName() 
	{
		return d_countryName;
	}
	
	/*
	 * This function sets the continent Id for current country.
	 * @param p_parentContinentSerialNumber, it sets the continent Id.
	 */
	public void setParentContinentSerialNumber(int p_parentContinentSerialNumber) 
	{
		d_parentContinentSerialNumber = p_parentContinentSerialNumber;
	}
	
	
	/*
	 * This function returns continent Id for current country.
	 * @return continent Id for current Country.
	 */
	public int getParentContinentSerialNumber()
	{
		return d_parentContinentSerialNumber;
	}
}
