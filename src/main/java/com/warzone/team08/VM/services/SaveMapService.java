package com.warzone.team08.VM.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.InvalidMapException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.utils.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * This class is to save the edited Map File.
 * <p>
 * The service handles `savemap` user command.
 *
 * @author Rutwik
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class SaveMapService implements SingleCommand {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    public SaveMapService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
    }

    /**
     * Reads from map editor engine and saves the data to file.
     *
     * @param p_fileObject File path to create if not exists and write into files.
     * @throws InvalidInputException Throws if the file write operation was not successful.
     */
    private String saveToFile(File p_fileObject) throws InvalidInputException {
        try (Writer l_writer = new FileWriter(p_fileObject)) {
            l_writer.write("[" + "Continents" + "]\n");

            for (Continent continents : d_mapEditorEngine.getContinentList()) {
                l_writer.write(continents.getContinentId() + " " + continents.getContinentName() + " " + continents.getContinentControlValue() + "\n");
            }

            l_writer.write("\n[" + "Countries" + "]\n");

            for (Country country : d_mapEditorEngine.getCountryList()) {
                l_writer.write(country.getCountryId() + " " + country.getCountryName() + " " + country.getContinentId() + "\n");
            }

            l_writer.write("\n[" + "borders" + "]\n");

            for (Map.Entry<Integer, List<Integer>> entry : d_mapEditorEngine.getCountryNeighbourMap().entrySet()) {
                int key = entry.getKey();
                List<Integer> neighbour = entry.getValue();
                l_writer.write(key + " ");
                for (Integer a : neighbour) {
                    l_writer.write(a + " ");
                }
                l_writer.write("\n");
            }
            return "File saved successfully";
        } catch (IOException p_ioException) {
            throw new InvalidInputException("Error while saving the file!");
        }
    }

    /**
     * Takes the path of the file which user wants to save(edited file).
     *
     * @param p_commandValues Value of parameters entered by the user.
     * @return Value of string acknowledging user that the file is saved or not.
     * @throws InvalidInputException Throws if the file write operation was not successful.
     * @throws InvalidMapException   Throws if the map was not valid.
     */
    @Override
    public String execute(List<String> p_commandValues) throws InvalidInputException,
            InvalidMapException,
            ResourceNotFoundException {
        // Validates the map before saving the file.
        ValidateMapService d_validateObj = new ValidateMapService();
        d_validateObj.execute(null);

        // Validates the file, gets the file object, and writes the data into it.
        return saveToFile(FileUtil.retrieveFile(p_commandValues.get(0)));
    }
}
