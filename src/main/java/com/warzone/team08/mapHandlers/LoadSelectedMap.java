package com.warzone.team08.mapHandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.warzone.team08.components.Continent;
import com.warzone.team08.components.Country;
import com.warzone.team08.exceptions.AbsentTagException;

/**
 * This file loads map file in the user console.
 * 
 * @author CHARIT
 */
public class LoadSelectedMap {

	public static List<Continent> d_continent_list;
	public static List<Country> d_country_list;
	public static TreeMap<Integer, List<Integer>> d_country_neighbour_map;
	public static LinkedHashMap<String, List<String>> d_continent_country_map;
	MapComponents d_mapComponents = MapComponents.getInstance();

	/**
	 * This method reads user provided map file.
	 * It reads data from file and stores into different java objects.
	 * @param p_file_path path of the requested file.
	 * @throws IOException handles IOException.
	 */
	public MapComponents loadMap(String p_file_path) throws IOException {
		
		String l_msg;
		boolean d_isContinentPresent = false;// to check [continents] tag is available or not in map file.
		boolean d_isCountryPresent = false;// to check [countries] is available or not in map file.
		boolean d_isBorderPresent = false; // to check [borders] is available or not in map file. 

		File l_file_obj = new File(p_file_path);
		l_file_obj.createNewFile();
		l_msg = fileValidation(l_file_obj);
		
		//Checking whether file is correct or not.
		if (!l_msg.equalsIgnoreCase("Valid file name")) {
			d_mapComponents.setCorrectMap(false);
			d_mapComponents.setErrorMessage(l_msg);
			return d_mapComponents;
		}
		
		System.out.println("valid map");
		BufferedReader l_reader = new BufferedReader(new FileReader(p_file_path));
		
		d_continent_list = new ArrayList<Continent>();
		d_country_list = new ArrayList<Country>();
		d_country_neighbour_map = new TreeMap<Integer,List<Integer>>();
		d_continent_country_map = new LinkedHashMap<String, List<String>>();
		
		String l_line;
		while ((l_line = l_reader.readLine()) != null) 
		{
			if (l_line.startsWith("[")) 
			{
				// Parsing the [continents] portion of the map file
				if (l_line.substring(l_line.indexOf("[") + 1, l_line.indexOf("]")).equalsIgnoreCase("continents"))
				{
					System.out.println("Continents are available");
					d_isContinentPresent = true;
					if (d_mapComponents.checkMapCorrect())
					{
						d_mapComponents = readContinents(l_reader, d_mapComponents);
						d_mapComponents.setContinentList(d_continent_list);
						
						System.out.println("Set continents");
					} 
					else {
						d_mapComponents.setCorrectMap(false);
						d_mapComponents.setErrorMessage("Invalid Continent Name/Value");
						break;
					}
				}
				
				// Parsing the [countries] portion of the map file
				else if (l_line.substring(l_line.indexOf("[") + 1, l_line.indexOf("]")).equalsIgnoreCase("countries")) 
				{
					System.out.println("Countries are available");
					d_isCountryPresent = true;
					if (d_mapComponents.checkMapCorrect()) 
					{
						d_mapComponents = readCountries(l_reader, d_mapComponents);
						d_mapComponents.setCountryList(d_country_list);
						System.out.println("Set Countries");
					}
					else 
					{
						d_mapComponents.setCorrectMap(false);
						d_mapComponents.setErrorMessage("Invalid Country Details");
						break;
					}
				}
				
				// Parsing the [borders] portion of the map file
				else if (l_line.substring(l_line.indexOf("[") + 1, l_line.indexOf("]")).equalsIgnoreCase("borders")) 
				{
					System.out.println("Borders are available");
					d_isBorderPresent = true;
					if (d_mapComponents.checkMapCorrect()) 
					{
						d_mapComponents = readNeighbours(l_reader, d_mapComponents);
						d_mapComponents.setCountryNeighbourMap(d_country_neighbour_map);
						createContinentCountryMap(d_continent_list, d_country_list);
						d_mapComponents.setContinentCountryMap(d_continent_country_map);
						System.out.println("Country neighbour map set");
						
					}
					else 
					{
						d_mapComponents.setCorrectMap(false);
						d_mapComponents.setErrorMessage("Invalid Neighbour Details");
						break;
					}
				}
				else
				{
					System.out.println("outside loop");
				}	
			}
		}
		return d_mapComponents;
	}
	
	/**
	 * This method creates the map which stores continent name as a key and list of neighboring countries as a value.
	 * @param p_continent_list  List of continents.
	 * @param p_country_list  List of Countries.
	 */
	public void createContinentCountryMap(List<Continent> p_continent_list, List<Country> p_country_list)
	{
		for(Continent l_continent : p_continent_list)
		{
			ArrayList<String> l_countryList = new ArrayList<String>();
			for(Country l_country : p_country_list)
			{
				if(l_continent.getContinentSerialNumber() == l_country.getParentContinentSerialNumber())
				{
					l_countryList.add(l_country.getCountryName());
				}
			}
			d_continent_country_map.put(l_continent.getContinentName(), l_countryList);
		}
	}
	
	/**
	 * This method checks whether the given file name is valid or not.
	 * 
	 * @param p_file_obj
	 * @return message about validity of the file.
	 */
	public String fileValidation(File p_file_obj) {
		
		String l_fileName = p_file_obj.getName();

		if (!p_file_obj.exists()) {
			return "File doesn't exist.";
		}
		
		int l_index = l_fileName.lastIndexOf('.');
		if (l_index > 0) 
		{
			String l_extension = l_fileName.substring(l_index + 1);
			if (!l_extension.equalsIgnoreCase("map")) 
			{
				return "Invalid file extension.";
			}
		} 
		else 
		{
			return "File must have an extension.";
		}
		return "Valid file name";
	}

	/**
	 * This method is used to read continent data from map file. 
	 * It reads the continent name, control value, and color and stores those values in Continent class object using Continent class methods.
	 * This object is later stored in the list.
	 * 
	 * @param p_reader   object of BufferedReader
	 * @param p_mapcompo object of MapComponents class
	 * @return p_mapcompo it returns object of MapComponents class
	 * @throws IOException handles generated IOException while operation
	 */
	public static MapComponents readContinents(BufferedReader p_reader, MapComponents p_mapcompo) throws IOException {
		String l_line;
		int l_continentCounter = 1;
		try {
			while ((l_line = p_reader.readLine()) != null && !l_line.startsWith("["))
			{
				if (!l_line.isEmpty() && l_line != null && !l_line.equals("") && l_line.contains(" ")) {	
					Continent l_con = new Continent();
					String l_continentComponents[] = l_line.split(" ");
					if (!l_continentComponents[0].isEmpty() && !l_continentComponents[1].isEmpty()) {
	
						l_con.setContinentSerialNumber(l_continentCounter);
						l_continentCounter++;
						l_con.setContinentName(l_continentComponents[0]);
						l_con.setContinentControlValue(Integer.parseInt(l_continentComponents[1]));
						d_continent_list.add(l_con);
					}
					else{
						p_mapcompo.setErrorMessage("Continent property empty.");
						p_mapcompo.setCorrectMap(false);
						break;
					}
				}
				p_reader.mark(0);
			}
			p_reader.reset();
		} catch (IOException e) {
			throw new AbsentTagException("Error while processing continents tag.");
		}
		return p_mapcompo;
	}
	
	/**
	 * This method is used to read country data from map file. 
	 * It reads the country serial number, country name, corresponding continent serial number 
	 * and stores those values in Country class object using Country class methods. 
	 * This object is later stored in the list.
	 * 
	 * @param p_reader   object of BufferedReader
	 * @param p_mapcompo object of MapComponents class
	 * @return p_mapcompo it returns object of MapComponents class
	 * @throws IOException handles generated IOException while operation
	 */
	public static MapComponents readCountries(BufferedReader p_reader, MapComponents p_mapcompo) throws IOException 
	{
		String l_string;
		try {
		while ((l_string = p_reader.readLine()) != null && !l_string.startsWith("[")) 
		{
			if (!l_string.isEmpty() && l_string != null && !l_string.equals("") && l_string.contains(" ")) {
				Country l_country = new Country();
				String l_countryComponents[] = l_string.split(" ");
				if (!l_countryComponents[0].isEmpty() && !l_countryComponents[1].isEmpty() && !l_countryComponents[2].isEmpty()) 
				{
					l_country.setCountrySerialNumber(Integer.parseInt(l_countryComponents[0]));
					l_country.setCountryName(l_countryComponents[1]);
					l_country.setParentContinentSerialNumber(Integer.parseInt(l_countryComponents[2]));
					d_country_list.add(l_country);
				}
				else{
					p_mapcompo.setErrorMessage("Continent property empty.");
					p_mapcompo.setCorrectMap(false);
					break;
				}
			}
			p_reader.mark(0);
		}
		p_reader.reset();
		}
		catch(IOException e)
		{
			throw new AbsentTagException("Error while processing countries tag.");
		}
		return p_mapcompo;
	}
	

	/**
	 * This method is used to read country and its neighbor data from map file.
	 * It reads the line from the file and creates the list of the neighboring countries.
	 * And it adds country and list of its neighboring countries into a TreeMap.
	 * 
	 * @param p_reader   object of BufferedReader
	 * @param p_mapcompo object of MapComponents class
	 * @return p_mapcompo it returns object of MapComponents class
	 * @throws IOException handles generated IOException while operation
	 */
	public static MapComponents readNeighbours(BufferedReader p_reader, MapComponents p_mapcompo) throws IOException {
		String l_line;
		int l_countryNumber;
		try {
				while ((l_line = p_reader.readLine()) != null && !l_line.startsWith("["))
				{				
					List<Integer> l_neighbourNodes = new ArrayList<Integer>();
					String l_borderComponents[] = l_line.split(" ");					
					l_countryNumber = Integer.parseInt(l_borderComponents[0]);
					for(int i = 1; i<l_borderComponents.length;i++)
					{
						l_neighbourNodes.add(Integer.parseInt(l_borderComponents[i]));
					}
					d_country_neighbour_map.put(l_countryNumber, l_neighbourNodes);
							
					for(Country c : d_country_list)
					{
						if(c.getCountrySerialNumber() == Integer.parseInt(l_borderComponents[0]))
						{
							c.setNeighbourCountries(l_neighbourNodes);
						}
					}
				}							
				p_reader.mark(0);
		}
		catch(IOException e)
		{
			throw new AbsentTagException("Error while processing borders tag.");
		}
		return p_mapcompo;
	}

		/**
		 * This method prints data stored in different java objects.
		 */
		public void show()
		{
			System.out.println("\nList of Continents");
			for (Continent c : d_continent_list)
			{
				System.out.println(c.getContinentSerialNumber()+"   "+c.getContinentName()+"   "+c.getContinentControlValue());
			}
			
			System.out.println("\nList of Countries");
			for(Country co : d_country_list)
			{
				System.out.println(co.getCountrySerialNumber()+"   "+co.getCountryName()+"   "+co.getParentContinentSerialNumber());
			}
			
			System.out.println("\nList of neighbours");
			 for(Map.Entry<Integer, List<Integer>> entry:d_country_neighbour_map.entrySet())
			 {    
			        int key=entry.getKey();  
			        List<Integer> b=entry.getValue();  
			        System.out.print(key+" ");
			        for(Integer i : b)
			        {
			        	System.out.print(i+"  ");
			        }
			        System.out.println();
			 }    
			 
			 
			 System.out.println("\nList of continent countries");
			 for(Map.Entry<String, List<String>> entry:d_continent_country_map.entrySet())
			 {    
			        String key=entry.getKey();  
			        List<String> b=entry.getValue();  
			        System.out.print(key+" ");
			        for(String i : b)
			        {
			        	System.out.print(i+"  ");
			        }
			        System.out.println();
			 }    
		}		
}
