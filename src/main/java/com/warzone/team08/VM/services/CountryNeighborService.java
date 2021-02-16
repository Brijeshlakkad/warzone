package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.repositories.CountryRepository;

/**
 * This service handles `editneighbor` user command to set/remove neighbors of the different countries on the map.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CountryNeighborService {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;
    private final CountryRepository d_countryRepository;

    public CountryNeighborService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_countryRepository = new CountryRepository();
    }

    /**
     * Sets the neighbors for the country.
     *
     * @param p_countryName         Value of the continent name.
     * @param p_neighborCountryName Value of the neighbour country to be set.
     */
    public void add(String p_countryName, String p_neighborCountryName) throws InvalidInputException {
        Country l_country = d_countryRepository.findFirstCountryWithCountryName(p_countryName);
        Country l_neighborCountry = d_countryRepository.findFirstCountryWithCountryName(p_neighborCountryName);

//        d_mapEditorEngine.addCountryNeighbour(l_country.getCountryId(), l_neighborCountry.getCountryId());
//
//        if (l_country.getContinentId() == Integer.parseInt(l_borderComponents[0])) {
//            l_country.setNeighbourCountries(l_neighbourNodes);
//        }
    }

    /**
     * Removes the neighbor for the country.
     *
     * @param p_countryName         Value of the country name.
     * @param p_neighborCountryName Value of the neighbor country.
     */
    public void remove(String p_countryName, String p_neighborCountryName) {

    }
}
