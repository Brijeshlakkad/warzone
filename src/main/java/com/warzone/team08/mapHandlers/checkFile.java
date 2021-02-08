package com.warzone.team08.mapHandlers;

/*
 * This File is used to run as a complete file. It is just a testing file. For running Load and save file together.
 * @author Rutwik Patel
 * 
 */
import java.io.IOException;



public class checkFile {
	public static void main(String[] args)
	{
		LoadSelectedMap load = new LoadSelectedMap();
		try {
			load.loadMap("C:\\Users\\CHARIT\\eclipse-workspace\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load.show();
		
		/*SaveMap savemap = new SaveMap();
		try {
			savemap.SaveSelectedMap("C:\\Users\\Rutwik\\dsd_assignments\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\solar.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}

}
