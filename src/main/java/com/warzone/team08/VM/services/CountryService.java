package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.ContinentRepository;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.List;

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

    /**
     * Repository to find the country.
     */
    private final CountryRepository d_countryRepository;

    public CountryService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentRepository = new ContinentRepository();
        d_countryRepository = new CountryRepository();
    }

    /**
     * Adds the country to the list stored at map editor engine.
     *
     * @param p_countryName   Value of the country name.
     * @param p_continentName Value of the continent to which this country will be added.
     * @throws EntityNotFoundException Throws if the either country not found.
     */
    public String add(String p_countryName, String p_continentName) throws EntityNotFoundException {
        Country l_country = new Country();
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.findFirstByContinentName(p_continentName);
        if (l_continent != null) {
            // Two way mappings (one to many mappings)
            l_country.setContinent(l_continent);

            // Save country to continent
            l_continent.addCountry(l_country);

            return String.format("%s country added!", p_countryName);
        } else {
            throw new EntityNotFoundException(String.format("Continent with %s not found!", p_continentName));
        }
    }

    /**
     * Adds the country to the list stored at map editor engine.
     *
     * @param p_countryId   Value of the country id.
     * @param p_countryName Value of the country name.
     * @param p_continentId Value of the continent to which this country will be added.
     * @throws EntityNotFoundException Throws if the either country not found.
     */
    public String add(Integer p_countryId, String p_countryName, Integer p_continentId) throws EntityNotFoundException {
        Country l_country = new Country(p_countryId);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.findByContinentId(p_continentId);
        if (l_continent != null) {
            // Two way mappings (one to many mappings)
            l_country.setContinent(l_continent);

            // Save country to continent
            l_continent.addCountry(l_country);

            return String.format("%s country added!", p_countryName);
        } else {
            throw new EntityNotFoundException(String.format("Continent with %s id not found!", p_continentId));
        }
    }

    /**
     * Removes the country from the list using the name.
     *
     * @param p_countryName Value of the country name.
     * @throws EntityNotFoundException Throws if the either country not found.
     */
    public String remove(String p_countryName) throws EntityNotFoundException {
        Country l_country = d_countryRepository.findFirstByCountryName(p_countryName);
        l_country.getContinent().removeCountry(l_country);

        List<Country> l_neighborOfCountryList = d_countryRepository.findByNeighbourOfCountries(l_country);
        for (Country l_neighborOfCountry : l_neighborOfCountryList) {
            l_neighborOfCountry.removeNeighbourCountry(l_country);
        }

        return String.format("%s country added!", p_countryName);
    }
}
