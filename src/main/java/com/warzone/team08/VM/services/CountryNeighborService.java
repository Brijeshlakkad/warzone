package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;

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
     * Attach the neighbor to the country using the name of the countries.
     *
     * @param p_countryName         Value of the continent name.
     * @param p_neighborCountryName Value of the neighbour country to be set.
     */
    public void add(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
        Country l_country = d_countryRepository.findFirstByCountryName(p_countryName);
        Country l_neighborCountry = d_countryRepository.findFirstByCountryName(p_neighborCountryName);
        this.add(l_country, l_neighborCountry);
    }

    /**
     * Attach the neighbor to the country using the entity.
     *
     * @param p_country         Value of the country entity which will have the neighbor country.
     * @param p_neighborCountry Value of the neighbour country entity.
     */
    public void add(Country p_country, Country p_neighborCountry) {
        p_country.addNeighbourCountry(p_neighborCountry);
    }

    /**
     * Removes the neighbor for the country using the names.
     *
     * @param p_countryName         Value of the country name.
     * @param p_neighborCountryName Value of the neighbor country.
     */
    public void remove(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
        Country l_country = d_countryRepository.findFirstByCountryName(p_countryName);
        Country l_neighborCountry = d_countryRepository.findFirstByCountryName(p_neighborCountryName);

        this.remove(l_country, l_neighborCountry);
    }

    /**
     * Removes the neighbor for the country using the actual entities.
     *
     * @param p_country         Value of the country entity.
     * @param p_neighborCountry Value of the neighbor country entity.
     */
    public void remove(Country p_country, Country p_neighborCountry) {
        List<Country> l_filteredCountry = p_country.getNeighbourCountries().stream().filter(i_p_country ->
                i_p_country.equals(p_neighborCountry)
        ).collect(Collectors.toList());

        p_country.setNeighbourCountries(l_filteredCountry);
    }
}
