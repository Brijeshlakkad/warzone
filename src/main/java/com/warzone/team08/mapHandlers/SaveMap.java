package com.warzone.team08.mapHandlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import java.util.List;
import com.warzone.team08.components.Continent;
import com.warzone.team08.components.Country;

/*
 * This class is to save the edited Map File.
 * @author RUTWIK PATEL 
 * 
 */

public class SaveMap {
	
	public static List<Continent> d_continentList = new ArrayList<Continent>(); 
	public static List<Country> d_countryList = new ArrayList<Country>();
	
	
	public void SaveSelectedMap(String p_filePath) throws IOException
	{
		File l_fileObject = new File(p_filePath);
		String l_check = fileExist(l_fileObject);
		
		MapComponents l_map = MapComponents.getInstance();
		
		if(l_check == "valid Name")
		{
			
			d_continentList = l_map.getContinentList();
			   
			Writer l_writer = new FileWriter("C:\\Users\\Rutwik\\dsd_assignments\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\testing_file.map");
			l_writer.write("["+"Continents"+"]\n");
			
			for(Continent continents : d_continentList)
			{
				l_writer.write( continents.getContinentSerialNumber()+ " "+continents.getContinentName()+" "+continents.getContinentControlValue()+"\n");
				//System.out.print(name.getContinentName() + " ");
				//System.out.println(name.d_continentControlValue);
			}
			
			
			d_countryList = l_map.getCountryList();
			
			l_writer.write("\n["+"Countries"+"]\n");
			
			for(Country country : d_countryList)
			{
				l_writer.write(country.d_countrySerialNumber+ " "+country.getCountryName()+" "+country.getParentContinentSerialNumber()+"\n");
				//System.out.print(country.getCountrySerialNumber() + " ");
				//System.out.print(country.getCountryName()+" ");
				//System.out.println(country.getParentContinentSerialNumber());
			}
			
			l_writer.close();
		}
	}
	
	public String fileExist(File p_fileObject)
	{
		String l_fileName = p_fileObject.getName();
		if(!p_fileObject.exists())
		{
			return "Enter valid filename.";
		}
		
		int index = l_fileName.lastIndexOf('.');
		if (index > 0) 
		{
			String l_extension = l_fileName.substring(index + 1);
			//String[] l_nameComponent = l_fileName.split(".");
			if(!l_extension.equalsIgnoreCase("map"))
			{
				return "Enter Valid Extension.";
			}
		}
		else
		{
			return "Add Extension";
		}
		return "valid Name";
	}
	
}
