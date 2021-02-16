package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.ContinentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This service handles `editcountry` user command to add/or remove country from the map.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CountryService {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    /**
     * Repository to find the continent from the list.
     */
    private final ContinentRepository d_continentRepository;

    public CountryService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentRepository = new ContinentRepository();
    }

    /**
     * Adds the country to the list stored at map editor engine.
     *
     * @param p_countryName   Value of the country name.
     * @param p_continentName Value of the continent to which this country will be added.
     */
    public void add(String p_countryName, String p_continentName) throws EntityNotFoundException {
        Country l_country = new Country();
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.findFirstContinentWithContinentName(p_continentName);
        if (l_continent != null) {
            l_country.setContinentId(l_continent.getContinentId());
            d_mapEditorEngine.addCountry(l_country);
        } else {
            throw new EntityNotFoundException(String.format("Continent with %s not found!", p_continentName));
        }
    }

    /**
     * Removes the country from the list using the name.
     *
     * @param p_countryName Value of the country name.
     */
    public void remove(String p_countryName) {
        // Filters the country list using the name.
        List<Country> l_filteredCountryList = d_mapEditorEngine.getCountryList().stream().filter(p_country ->
                p_country.getCountryName().equals(p_countryName)
        ).collect(Collectors.toList());

        d_mapEditorEngine.setCountryList(l_filteredCountryList);
    }
}
