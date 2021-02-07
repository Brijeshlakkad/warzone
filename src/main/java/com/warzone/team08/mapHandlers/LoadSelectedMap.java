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
 * @author CHARIT
 */
public class LoadSelectedMap {

	public static List<Continent> d_continent_list = new ArrayList<Continent>();
	public static List<Country> d_country_list;
	String d_extension;
	String d_fileName;
	String d_line;
	
	MapComponents d_mapComponents = new MapComponents();
	
	
	/**
	 * This method loads user provided map file data into various components. 
	 * @param p_file_path path of the requested file.
	 * @throws IOException handles IOException.
	 */
	public void loadMap(String p_file_path) throws IOException
	{
		String l_msg;
		File l_file_obj = new File(p_file_path);
		l_file_obj.createNewFile();
		l_msg = fileValidation(l_file_obj);
		if(!l_msg.equalsIgnoreCase("Valid file name"))
		{
			d_mapComponents.setCorrectMap(false);
			d_mapComponents.setErrorMessage(l_msg);
			//System.out.println("not");
		}
		System.out.println("valid map");
		BufferedReader l_reader = new BufferedReader(new FileReader(p_file_path));
		
		String l_line;
		while((l_line = l_reader.readLine())!= null)
		{
			if(l_line.startsWith("["))
			{ 
				System.out.println("Hello");
				if(l_line.substring(l_line.indexOf("[")+1, l_line.indexOf("]")).equalsIgnoreCase("continents")) 
				{
					System.out.println("Continents are available");
					if(d_mapComponents.checkMapCorrect())
					{
						d_mapComponents=readContinents(l_reader,d_mapComponents);
						d_mapComponents.setContinentList(d_continent_list);
						System.out.println("Set continents");
					}
					else
					{
						d_mapComponents.setCorrectMap(false);
						d_mapComponents.setErrorMessage("Invalid Continent Name/Value");
						break;
					}		
				}
				
				if(l_line.substring(l_line.indexOf("[")+1, l_line.indexOf("]")).equalsIgnoreCase("countries"))
				{ 
					System.out.println("Countries are available");
					if(d_mapComponents.checkMapCorrect())
					{
						
					}
					else
					{
						d_mapComponents.setCorrectMap(false);
						d_mapComponents.setErrorMessage("Invalid Country Details");
						break;
					}
				}	
			}
		}
	}
		
	/**
	 * This method checks whether the given file name is valid or not.
	 * @param p_file_obj
	 * @return message about validity of the file.
	 */
	public String fileValidation(File p_file_obj) {
		// TODO Auto-generated method stub
		
		String l_fileName = p_file_obj.getName();
		
		if(!p_file_obj.exists())
		{
			return "File doesn't exist.";
		}
		
		int index = l_fileName.lastIndexOf('.');
		if(index > 0) 
		{
			String l_extension = l_fileName.substring(index + 1);
			if(!l_extension.equalsIgnoreCase("map"))
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
	 * It reads the continent name, control value, and color and stores those values in Continent class object.
	 * This object is later stored in Arraylist.
	 * @param reader object of BufferedReader
	 * @param mapcompo object of MapComponents class
	 * @return it returns object of MapComponents class
	 * @throws IOException handles generated IOException while operation
	 */
	public static MapComponents readContinents(BufferedReader reader,MapComponents mapcompo) throws IOException{
		String l_line;
		try {
			while ((l_line = reader.readLine()) != null && !l_line.startsWith("[")) {
		//	if (!l_line.isEmpty() && line != null && !line.equals("")&&line.contains("=")) {
					Continent l_con = new Continent();
					String l_continentComponents[] = l_line.split(" ");
					if(!l_continentComponents[0].equals("")&&!l_continentComponents[1].equals("")&&!l_continentComponents[0].isEmpty()&&!l_continentComponents[1].isEmpty()){
		
						l_con.setContinentName(l_continentComponents[0]);
						l_con.setContinentControlValue(Integer.parseInt(l_continentComponents[1]));
						l_con.setContinentColor(l_continentComponents[2]);
						d_continent_list.add(l_con);	
					}
					else{
						mapcompo.setErrorMessage("Continent property empty.");
						mapcompo.setCorrectMap(false);
						break;
					}
				//}
				reader.mark(0);
			}
			reader.reset();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return mapcompo;
	}
	
	
	/**
	 * This is a main method.
	 * @param args command line argument
	 * @throws IOException handles generated IOException while operation
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		LoadSelectedMap l = new LoadSelectedMap();
		l.loadMap("C:\\Users\\CHARIT\\eclipse-workspace\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map");
		System.out.println("Map loaded");
	}

}
