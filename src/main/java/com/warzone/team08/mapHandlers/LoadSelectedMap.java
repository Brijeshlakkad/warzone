package com.warzone.team08.mapHandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.warzone.team08.components.Continent;
import com.warzone.team08.components.Country;

/**
 * This file loads map file in the user console.
 * 
 * @author CHARIT
 */
public class LoadSelectedMap {

	public static List<Continent> d_continent_list = new ArrayList<Continent>();
	public static List<Country> d_country_list = new ArrayList<Country>();
	String d_extension;
	String d_fileName;
	String d_line;

	public static MapComponents d_mapComponents = new MapComponents();

	/**
	 * This method reads user provided map file.
	 * It reads data from file and stores into different java objects.
	 * @param p_file_path path of the requested file.
	 * @throws IOException handles IOException.
	 */
	public void loadMap(String p_file_path) throws IOException {
		String l_msg;
		File l_file_obj = new File(p_file_path);
		l_file_obj.createNewFile();
		l_msg = fileValidation(l_file_obj);
		if (!l_msg.equalsIgnoreCase("Valid file name")) {
			d_mapComponents.setCorrectMap(false);
			d_mapComponents.setErrorMessage(l_msg);
			// System.out.println("not");
		}
		
		System.out.println("valid map");
		BufferedReader l_reader = new BufferedReader(new FileReader(p_file_path));
		String l_line;
		while ((l_line = l_reader.readLine()) != null) 
		{
			if (l_line.startsWith("[")) 
			{
				System.out.println("Hello");
				if (l_line.substring(l_line.indexOf("[") + 1, l_line.indexOf("]")).equalsIgnoreCase("continents"))
				{
					System.out.println("Continents are available");
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

				else if (l_line.substring(l_line.indexOf("[") + 1, l_line.indexOf("]")).equalsIgnoreCase("countries")) 
				{
					System.out.println("Countries are available");
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
				

				

				else
				{
					System.out.println("outside loop");
					//setMapComponent(d_mapComponents);
				}	

			}
		}
	}

	/**
	 * This method checks whether the given file name is valid or not.
	 * 
	 * @param p_file_obj
	 * @return message about validity of the file.
	 */
	public String fileValidation(File p_file_obj) {
		// TODO Auto-generated method stub

		String l_fileName = p_file_obj.getName();

		if (!p_file_obj.exists()) {
			return "File doesn't exist.";
		}

		int index = l_fileName.lastIndexOf('.');
		if (index > 0) 
		{
			String l_extension = l_fileName.substring(index + 1);
			if (!l_extension.equalsIgnoreCase("map")) 
			{
				return "Invalid file extension.";
			}
		} else {
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
		try {
			while ((l_line = p_reader.readLine()) != null && !l_line.startsWith("["))
			{
				Continent l_con = new Continent();
				String l_continentComponents[] = l_line.split(" ");
				if (!l_continentComponents[0].isEmpty() && !l_continentComponents[1].isEmpty()) {

					l_con.setContinentName(l_continentComponents[0]);
					l_con.setContinentControlValue(Integer.parseInt(l_continentComponents[1]));
					l_con.setContinentColor(l_continentComponents[2]);
					d_continent_list.add(l_con);
				} 
				p_reader.mark(0);
			}
			p_reader.reset();
		} catch (IOException e) {
			e.printStackTrace();
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
			Country l_country = new Country();
			String l_countryComponents[] = l_string.split(" ");
			if (!l_countryComponents[0].isEmpty() && !l_countryComponents[1].isEmpty() && !l_countryComponents[2].isEmpty()) 
			{
				l_country.setCountrySerialNumber(Integer.parseInt(l_countryComponents[0]));
				l_country.setCountryName(l_countryComponents[1]);
				l_country.setParentContinentSerialNumber(Integer.parseInt(l_countryComponents[2]));
				d_country_list.add(l_country);
			} 
				
			p_reader.mark(0);
		}
		p_reader.reset();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return p_mapcompo;
	}
	
	/**
	 * This method stores the Map Component object in List. 
	 * 
	 */
/*	
	public static MapComponents d_mapCompo;
	public static void setMapComponent(MapComponents p_map)
	{
		d_mapCompo = p_map;
	}
	public static MapComponents getMapComponent()
	{
		return d_mapCompo;
	}
*/

	/**
	 * This is a main method.
	 * 
	 * @param args command line argument
	 * @throws IOException handles generated IOException while operation
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		LoadSelectedMap l = new LoadSelectedMap();
		l.loadMap("C:\\Users\\Rutwik\\dsd_assignments\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map");
		
		/* 
			For Rutwik File System:-
			C:\\Users\\Rutwik\\dsd_assignments\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map
		*/
		System.out.println("Map loaded");
		//public void show()
		//{
		
			System.out.println("\nList of Continents");
			for (Continent c : d_continent_list)
			{
				System.out.println(c.getContinentName()+"   "+c.getContinentControlValue());
			}
			
			System.out.println("\nList of Countries");
			for(Country co : d_country_list)
			{
				System.out.println(co.getCountrySerialNumber()+"   "+co.getCountryName()+"   "+co.getParentContinentSerialNumber());
			}
		//}
	}

}
