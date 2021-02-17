package com.warzone.team08.VM.services;

import com.github.freva.asciitable.AsciiTable;
import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.ContinentRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/** This class is used to show the content of current map on user console
 *
 * @author MILESH
 * @version 1.0
 */
public class ShowMapService {
    MapEditorEngine d_mapEditorEngine=null;
    ContinentRepository d_continentRepository;
    Set<Continent> d_continentSet;
    Set<Country> d_countrySet;
    Map<String, Set<String>> d_continentCountryMap;

    public ShowMapService(){
        d_mapEditorEngine=MapEditorEngine.getInstance();
        d_continentSet=d_mapEditorEngine.getContinentSet();
        d_countrySet=d_mapEditorEngine.getCountrySet();
        d_continentCountryMap=d_mapEditorEngine.getContinentCountryMap();
        d_continentRepository=new ContinentRepository();
    }

    /**
     * This method is used to display all the continents with its bonusValue and countries present in that continent in Tabular structure
     *
     * @return String of all continents information
     */
    public String showContinentCountryContent() {
        String[] l_header = {"Continent Name", "Control Value", "Countries"};
        ArrayList<ArrayList<String>> l_mapContent = new ArrayList<>();
        for (Map.Entry<String, Set<String>> l_entry : d_continentCountryMap.entrySet()) {
            ArrayList<String> l_continentsList = new ArrayList<>();
            l_continentsList.add(l_entry.getKey());
            try {
                Continent l_continent = d_continentRepository.findFirstByContinentName(l_entry.getKey());
                l_continentsList.add(String.valueOf(l_continent.getContinentControlValue()));
                String l_continentCountries = String.join(",", l_entry.getValue());
                l_continentsList.add(l_continentCountries);
                l_mapContent.add(l_continentsList);

            } catch (EntityNotFoundException p_e) {
                p_e.printStackTrace();
            }
        }
        //store continent data in 2d array
        String l_continentMapMatrix[][] = new String[l_mapContent.size()][];
        for (int i = 0; i < l_mapContent.size(); i++) {
            ArrayList<String> l_singleContinentContent = l_mapContent.get(i);
            l_continentMapMatrix[i] = l_singleContinentContent.toArray(new String[l_singleContinentContent.size()]);
        }
        String l_continentMapTable = AsciiTable.getTable(l_header, l_continentMapMatrix);
        return l_continentMapTable;
    }
}
