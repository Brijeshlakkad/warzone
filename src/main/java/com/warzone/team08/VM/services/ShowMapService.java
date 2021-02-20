package com.warzone.team08.VM.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.ContinentRepository;
import com.warzone.team08.VM.repositories.CountryRepository;
import java.util.*;

/** This class is used to show the content of current map on user console
 *
 * @author MILESH
 * @version 1.0
 */
public class ShowMapService implements SingleCommand {
    MapEditorEngine d_mapEditorEngine;
    ContinentRepository d_continentRepository;
    CountryRepository d_countryRepository;
    Set<Continent> d_continentSet;
    Set<Country> d_countrySet;
    Map<String, Set<String>> d_continentCountryMap;

    public ShowMapService(){
        d_mapEditorEngine=MapEditorEngine.getInstance();
        d_continentSet=d_mapEditorEngine.getContinentSet();
        d_countrySet=d_mapEditorEngine.getCountrySet();
        d_continentCountryMap=d_mapEditorEngine.getContinentCountryMap();
        d_continentRepository=new ContinentRepository();
        d_countryRepository=new CountryRepository();
    }

    /**
     * This method is used to display all the continents with its bonusValue and countries present in that continent in Tabular structure
     *
     * @return String of all continents information
     */
    public String showContinentCountryContent() {

        //table header
        String[] l_header = {"Continent Name", "Control Value", "Countries"};
        List<List<String>> l_mapContent = new ArrayList<>();

        for (Map.Entry<String, Set<String>> l_entry : d_continentCountryMap.entrySet()) {
            ArrayList<String> l_continentsList = new ArrayList<>();
            l_continentsList.add(l_entry.getKey());
            try {
                //fetching continent object using its name
                Continent l_continent = d_continentRepository.findFirstByContinentName(l_entry.getKey());
                l_continentsList.add(String.valueOf(l_continent.getContinentControlValue()));

                //for sorting the countries of continent
                List<String> l_sortedCountryList=new ArrayList<>(l_entry.getValue());
                Collections.sort(l_sortedCountryList);
                String l_continentCountries = String.join(",",l_sortedCountryList);
                l_continentsList.add(l_continentCountries);
                l_mapContent.add(l_continentsList);

            } catch (EntityNotFoundException p_e) {
                p_e.printStackTrace();
            }
        }

        //for sorting the continent table wrt to continent name
        Collections.sort(l_mapContent, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        });
        //store continent data in 2d array
        String[][] l_continentMapMatrix = new String[l_mapContent.size()][];
        for (int i = 0; i < l_mapContent.size(); i++) {
            List<String> l_singleContinentContent = l_mapContent.get(i);
            l_continentMapMatrix[i] = l_singleContinentContent.toArray(new String[l_singleContinentContent.size()]);
        }

        return FlipTable.of(l_header, l_continentMapMatrix);
    }

    /**
     * This method is used to display the adjacency of all countries
     *
     * @return String of country's neighbour information
     */
    public String showNeighbourCountries(){
        LinkedList<String> l_countryNames = new LinkedList<>();
        String[][] l_neighbourCountryMatrix = new String[d_countrySet.size() + 1][d_countrySet.size() + 1];

        //for adding all country names
        for (Country l_country : d_countrySet) {
            l_countryNames.add(l_country.getCountryName());
        }
        l_neighbourCountryMatrix[0][0]="COUNTRIES";
        Collections.sort(l_countryNames);

        //for storing country names in first row and column of matrix
        for (int l_row = 1; l_row < l_neighbourCountryMatrix.length; l_row++) {
            String l_name=l_countryNames.pollFirst();
            l_neighbourCountryMatrix[l_row][0] = l_name;
            l_neighbourCountryMatrix[0][l_row] = l_name;
        }

        //for storing neighbours of each country
        for (int l_row = 1; l_row < l_neighbourCountryMatrix.length; l_row++) {
            Country l_countryRow;
            try {
                l_countryRow = d_countryRepository.findFirstByCountryName(l_neighbourCountryMatrix[l_row][0]);
                List<Country> l_countryNeighbourList = l_countryRow.getNeighbourCountries();
                for (int l_col = 1; l_col < l_neighbourCountryMatrix.length; l_col++) {
                    Country l_countryColumn = d_countryRepository.findFirstByCountryName(l_neighbourCountryMatrix[0][l_col]);
                    if (l_countryRow.equals(l_countryColumn) || l_countryNeighbourList.contains(l_countryColumn)) {
                        l_neighbourCountryMatrix[l_row][l_col] = "X";
                    }
                    else {
                        l_neighbourCountryMatrix[l_row][l_col] = "O";
                    }
                }
            } catch (EntityNotFoundException p_e) {
                p_e.printStackTrace();
            }
        }

        String[] l_countryCountHeader=new String[l_neighbourCountryMatrix.length];
        for(int i=0;i<l_countryCountHeader.length;i++){
            l_countryCountHeader[i]="C"+i;
        }

        return FlipTable.of(l_countryCountHeader,l_neighbourCountryMatrix);
    }

    /**
     * Initiates all methods of ShowMapService file.
     *
     * @param p_commandValues Value of parameters entered by the user.
     * @return Value of string of continent and neighbour country information.
     */
    @Override
    public String execute(List<String> p_commandValues){
        return this.showContinentCountryContent() + "\n" + this.showNeighbourCountries();
    }
}
