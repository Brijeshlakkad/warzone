package com.warzone.team08.VM.services;

import com.warzone.team08.VM.constants.enums.MapModelType;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.AbsentTagException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.InvalidMapException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This file loads map file in the user console.
 * <p>
 * This service handles `loadmap` user command.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 */
public class EditMapService implements SingleCommand {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    public EditMapService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
    }

    /**
     * This method reads user provided map file. It reads data from file and stores into different java objects.
     *
     * @param p_filePath Path of the file to be read.
     * @throws InvalidMapException       Throws if file does not have valid map data.
     * @throws AbsentTagException        Throws if there is missing tag.
     * @throws ResourceNotFoundException Throws if file not found.
     */
    public void handleLoadMap(String p_filePath) throws InvalidMapException, AbsentTagException, ResourceNotFoundException {
        try {
            // Will throw exception if the file path is not valid
            BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath));

            // The value of the current line
            String l_currentLine;
            while ((l_currentLine = l_reader.readLine()) != null) {
                if (l_currentLine.startsWith("[")) {
                    // Parsing the [continents] portion of the map file
                    if (this.doLineHasModelData(l_currentLine, MapModelType.CONTINENT)) {
                        readContinents(l_reader);
                    } else if (this.doLineHasModelData(l_currentLine, MapModelType.COUNTRY)) {
                        // Parsing the [countries] portion of the map file
                        readCountries(l_reader);
                    } else if (this.doLineHasModelData(l_currentLine, MapModelType.BORDER)) {
                        // Parsing the [borders] portion of the map file
                        readNeighbours(l_reader);
                        createContinentCountryMappings();
                    }
                }
            }
        } catch (IOException p_ioException) {
            throw new ResourceNotFoundException("File not found!");
        }
    }

    /**
     * Stores the map of a continent name as a key and list of neighboring countries as a value.
     */
    private void createContinentCountryMappings() {
        for (Continent l_continent : d_mapEditorEngine.getContinentList()) {
            ArrayList<String> l_countryList = new ArrayList<>();
            for (Country l_country : d_mapEditorEngine.getCountryList()) {
                if (l_continent.getContinentId() == l_country.getContinentId()) {
                    l_countryList.add(l_country.getCountryName());
                }
            }
            d_mapEditorEngine.addContinentCountryMap(l_continent.getContinentName(), l_countryList);
        }
    }

    /**
     * This method is used to read continent data from map file. It reads the continent name, control value, and color
     * and stores those values in Continent class object using Continent class methods. This object is later stored in
     * the list.
     *
     * @param p_reader object of BufferedReader
     * @throws AbsentTagException handles generated IOException while operation
     */
    private void readContinents(BufferedReader p_reader) throws AbsentTagException, InvalidMapException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                String[] l_continentComponents = this.getModelComponents(l_currentLine);
                if (l_continentComponents != null) {
                    if (!l_continentComponents[0].isEmpty() && !l_continentComponents[1].isEmpty()) {
                        Continent l_continent = new Continent();
                        l_continent.setContinentName(l_continentComponents[0]);
                        l_continent.setContinentControlValue(Integer.parseInt(l_continentComponents[1]));
                        d_mapEditorEngine.addContinent(l_continent);
                    } else {
                        throw new AbsentTagException("Error while processing continent tag!");
                    }
                }
                p_reader.mark(0);
            }
            p_reader.reset();
        } catch (IOException p_ioException) {
            throw new InvalidMapException("Continent property empty!");
        }
    }

    /**
     * This method is used to read country data from map file. It reads the country serial number, country name,
     * corresponding continent serial number and stores those values in Country class object using Country class
     * methods. This object is later stored in the list.
     *
     * @param p_reader object of BufferedReader
     * @throws AbsentTagException handles generated IOException while operation
     */
    private void readCountries(BufferedReader p_reader) throws AbsentTagException, InvalidMapException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                String[] l_countryComponents = this.getModelComponents(l_currentLine);
                if (l_countryComponents != null) {
                    if (!l_countryComponents[0].isEmpty() && !l_countryComponents[1].isEmpty()
                            && !l_countryComponents[2].isEmpty()) {
                        Country l_country = new Country(Integer.parseInt(l_countryComponents[0]));
                        l_country.setCountryName(l_countryComponents[1]);
                        l_country.setContinentId(Integer.parseInt(l_countryComponents[2]));
                        d_mapEditorEngine.addCountry(l_country);
                    } else {
                        throw new AbsentTagException("Error while processing countries tag!");
                    }
                }
                p_reader.mark(0);
            }
            p_reader.reset();
        } catch (IOException p_ioException) {
            throw new InvalidMapException("Country property empty!");
        }
    }

    /**
     * This method is used to read country and its neighbor data from map file. It reads the line from the file and
     * creates the list of the neighboring countries. And it adds country and list of its neighboring countries into a
     * TreeMap.
     *
     * @param p_reader object of BufferedReader
     * @throws AbsentTagException handles generated IOException while operation
     */
    private void readNeighbours(BufferedReader p_reader) throws AbsentTagException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                String[] l_borderComponents = this.getModelComponents(l_currentLine);
                if (l_borderComponents != null) {
                    List<Integer> l_neighbourNodes = new ArrayList<>();
                    for (int i = 1; i < l_borderComponents.length; i++) {
                        l_neighbourNodes.add(Integer.parseInt(l_borderComponents[i]));
                    }
                    d_mapEditorEngine.addCountryNeighbour(Integer.parseInt(l_borderComponents[0]), l_neighbourNodes);

                    for (Country l_country : d_mapEditorEngine.getCountryList()) {
                        if (l_country.getContinentId() == Integer.parseInt(l_borderComponents[0])) {
                            l_country.setNeighbourCountries(l_neighbourNodes);
                        }
                    }
                }
            }
            p_reader.mark(0);
        } catch (IOException e) {
            throw new AbsentTagException("Error while processing borders tag!");
        }
    }

    /**
     * Checks if the line is the starting point of model data.
     *
     * <pre>
     * Model data can be of the below type:
     * 1. Continents
     * 2. Countries
     * 3. Borders
     * </pre>
     *
     * <p>
     * These modal type data will be read from the input file.
     *
     * @param p_currentLine  Value of the current line to be read.
     * @param p_mapModelType Value of the model data type
     * @return True if the line represents the title of model to be read in the following lines; false otherwise.
     */
    private boolean doLineHasModelData(String p_currentLine, MapModelType p_mapModelType) {
        return p_currentLine.substring(p_currentLine.indexOf("[") + 1, p_currentLine.indexOf("]"))
                .equalsIgnoreCase(p_mapModelType.getJsonValue());
    }

    /**
     * Extracts the model components from the line.
     *
     * @param p_line Line to be interpreted.
     * @return Value of the list of components.
     */
    public String[] getModelComponents(String p_line) {
        if (!p_line.isEmpty() && p_line.contains(" ")) {
            return p_line.split("\\s");
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String execute(String p_commandValue)
            throws InvalidMapException,
            ResourceNotFoundException,
            InvalidInputException,
            AbsentTagException {
        if (!p_commandValue.isEmpty()) {
            // Resolve file path using absolute path of user data directory.
            String resolvedPathToFile = PathResolverUtil.resolveFilePath(p_commandValue);
            this.handleLoadMap(resolvedPathToFile);
            return null;
        } else {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
