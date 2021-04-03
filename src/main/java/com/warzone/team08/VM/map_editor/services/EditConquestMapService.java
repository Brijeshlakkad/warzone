package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.constants.enums.MapModelType;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.logger.LogEntryBuffer;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.repositories.ContinentRepository;
import com.warzone.team08.VM.repositories.CountryRepository;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class act as an Adaptee.
 * It loads the conquest map into different java objects.
 *
 * @author CHARIT
 */
public class EditConquestMapService implements SingleCommand {

    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;

    private final ContinentRepository d_continentRepository;
    private final CountryRepository d_countryRepository;
    private final ContinentService d_continentService;
    private final CountryService d_countryService;
    private final CountryNeighborService d_countryNeighborService;
    private final LogEntryBuffer d_logEntryBuffer;
    private static HashMap<String,String> d_MapDetails;
    /**
     * Initializes variables required to load map into different objects.
     */
    public EditConquestMapService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentRepository = new ContinentRepository();
        d_countryRepository = new CountryRepository();
        d_continentService = new ContinentService();
        d_countryService = new CountryService();
        d_countryNeighborService = new CountryNeighborService();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    public String loadConquestMap(String p_filePath, boolean shouldCreateNew) throws ResourceNotFoundException, InvalidInputException, InvalidMapException, IOException, AbsentTagException {
        d_mapEditorEngine.initialise();
        d_mapEditorEngine.setLoadingMap(true);
        if (new File(p_filePath).exists()) {
            try {
                // Try to retrieve the file
                FileUtil.retrieveFile(p_filePath);
                // Will throw exception if the file path is not valid
                BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath));

                // The value of the current line
                String l_currentLine;
                while ((l_currentLine = l_reader.readLine()) != null) {
                    if (l_currentLine.startsWith("[")) {
                        // Parsing the [continents] portion of the map file
                        if (this.doLineHasModelData(l_currentLine, MapModelType.MAP))
                        {
                            readMapDetails(l_reader);
                        }
                        else if (this.doLineHasModelData(l_currentLine, MapModelType.CONTINENT)) {
                            readContinents(l_reader);
                        }
                        else if(this.doLineHasModelData(l_currentLine,MapModelType.TERRITORY))
                        {
                            System.out.println("territories Tag Present");
                        }
                    }
                }
                    return "File(Conquest map) successfully loaded";
            }catch (IOException e) {
                throw new ResourceNotFoundException("File not found!");
            }
        } else if (shouldCreateNew) {
            // Throws exception if file doesn't have required extension.
            FileUtil.checksIfFileHasRequiredExtension(p_filePath);

            FileUtil.createFileIfNotExists(p_filePath);
            return "New file created!";
        } else {
            throw new InvalidMapException("Please check if file exists. This may happen due to error while processing.");
        }
    }

    private void readMapDetails(BufferedReader p_reader) throws InvalidMapException, IOException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                if (!l_currentLine.isEmpty() && l_currentLine != null && !l_currentLine.equals("")) {
                    String[] maps_entry = l_currentLine.split("=");
                    d_MapDetails.put(maps_entry[0], maps_entry[1]);
                    p_reader.mark(0);
                }
            }
            p_reader.reset();
            d_mapEditorEngine.set_MapDetails(d_MapDetails);
        }
        catch(IOException e)
        {
            throw new InvalidMapException("Invalid map file");
        }
    }





    private void readContinents(BufferedReader p_reader) throws InvalidInputException, InvalidMapException, AbsentTagException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                if (l_currentLine.trim().isEmpty()) {
                    // If line is empty string.
                    continue;
                }
                List<String> l_continentComponentList = this.getModelComponents(l_currentLine);
                if (l_continentComponentList.size() >= 2) {
                    d_continentService.add(l_continentComponentList.get(0), l_continentComponentList.get(1));
                } else {
                    throw new AbsentTagException("Missing continent value!");
                }
                p_reader.mark(0);
            }
            p_reader.reset();
        } catch (IOException e) {
            throw new InvalidMapException("Error while processing!");
        }
    }






    public List<String> getModelComponents(String p_line) {
        try {
            if (!p_line.isEmpty() && p_line.contains(" ")) {
                List<String> l_continentComponentList = Arrays.asList(p_line.split("="));
                if (!l_continentComponentList.isEmpty()) {
                    l_continentComponentList = l_continentComponentList.stream().map(String::trim)
                            .collect(Collectors.toList());
                    if (!(l_continentComponentList.contains(null) || l_continentComponentList.contains(""))) {
                        return l_continentComponentList;
                    }
                }
            }
        } catch (Exception e) {
            // If error while parsing, ignore the exception and return empty array list.
        }
        return new ArrayList<>();
    }




    public String loadConquestMap(String p_filePath) throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, IOException {
        return this.loadConquestMap(p_filePath, true);
    }

    /**
     * Checks if the line is the starting point of model data.
     *
     * <pre>
     * Model data can be of the below type:
     * 1. Map
     * 2. Continents
     * 3. Territories
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

    @Override
    public String execute(List<String> p_commandValues) throws VMException, IOException {
        String l_response = "";
        if (!p_commandValues.isEmpty()) {
            String l_resolvedPathToFile = PathResolverUtil.resolveFilePath(p_commandValues.get(0));
            int l_index = l_resolvedPathToFile.lastIndexOf('\\');
            l_response = this.loadConquestMap(l_resolvedPathToFile);
            d_logEntryBuffer.dataChanged("editmap", l_resolvedPathToFile.substring(l_index + 1) + " " + l_response);
            d_mapEditorEngine.setLoadingMap(false);
            return l_response;
        } else {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
