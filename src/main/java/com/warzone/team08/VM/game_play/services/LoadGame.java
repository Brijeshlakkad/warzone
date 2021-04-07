package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Rutwik Patel
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class LoadGame {
    ArrayList<Continent> continents;

    public LoadGame() {
        continents = new ArrayList<>();
    }

    public void getDataFromFile() throws IOException {
        //Read data from file
        File file = new File("xyz.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals("Continents")) {
                while ((currentLine = reader.readLine()) != null && !currentLine.equals("Continents")) {

                }
            }
        }

        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("abc.txt"));
            JSONObject jsonObject = (JSONObject) obj;
            String id = (String) jsonObject.get("ID");
            System.out.println(id);
            JSONArray continents = (JSONArray) jsonObject.get("Continents");
            System.out.println("Continents:- ");
            Iterator iterator = continents.iterator();
            while (iterator.hasNext()) {
                continents.add(iterator.next());
            }

            System.out.println("\nCountries:- ");
            JSONArray countries = (JSONArray) jsonObject.get("Countries");
            Iterator countryIterator = countries.iterator();
            while (countryIterator.hasNext()) {
                Country continent = (Country) countryIterator.next();
                System.out.println(continent.getCountryId());
                System.out.println(continent.getCountryName());
            }

            System.out.println("\nNeighbors:- ");
            String Neighbor = (String) jsonObject.get("1");
            System.out.println(Neighbor);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(String[] args) throws IOException {
        this.getDataFromFile();
    }
}
