package com.warzone.team08.maphandlers;

/**
 * This File is used to run as a complete file. It is just a testing file. For running Load and save file together.
 * @author Rutwik Patel
 * 
 */
import java.io.IOException;

import com.warzone.team08.exceptions.InvalidMapException;


public class checkFile {
	public static void main(String[] args) throws InvalidMapException
	{
		LoadMap load = new LoadMap();
		try {
			load.loadMap("C:\\Users\\CHARIT\\eclipse-workspace\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load.show();
		
	/*	SaveMap savemap = new SaveMap();
		try {
			String a = savemap.execute("C:\\Users\\Rutwik\\dsd_assignments\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\testing_file.map");
			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}