package com.warzone.team08.VM.repositories;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Country;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class finds the Country entity from the runtime engine.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CountryRepository {
    /**
     * Finds the country using country name.
     *
     * @param p_countryName Value of the name of country.
     * @return Value of the list of matched countries.
     */
    public List<Country> findCountryWithCountryName(String p_countryName) {
        return MapEditorEngine.getInstance().getCountryList().stream().filter(p_country ->
                p_country.getCountryName().equals(p_countryName)
        ).collect(Collectors.toList());
    }

    /**
     * Finds only one country using its name.
     *
     * @param p_countryName Value of the name of country.
     * @return Value of the first matched countries.
     */
    public Country findFirstCountryWithCountryName(String p_countryName) {
        List<Country> l_countryList = this.findCountryWithCountryName(p_countryName);
        return l_countryList.size() > 0 ? l_countryList.get(0) : null;
    }
}
