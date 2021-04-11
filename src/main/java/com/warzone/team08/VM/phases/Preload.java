package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidMapException;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.map_editor.services.Adapter;
import com.warzone.team08.VM.map_editor.services.EditConquestMapService;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.map_editor.services.ValidateMapService;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * <code>Preload</code> phase of the game phase. This is the initial phase of the game.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 * @version 1.0
 */
public class Preload extends MapEditor {
    /**
     * Parameterised constructor to create an instance of <code>Preload</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    public Preload(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * This method loads the map file which can be either Warzone file or Conquest map file.
     * It loads the file depending upon the file content.
     *
     * @param p_arguments Contains the filename.
     * @return Value of string acknowledging user that the file is loaded or not.
     * @throws VMException Throws if error occurs in VM Engine operation.
     * @throws IOException Throws if error occurs during IO Operation.
     */
    @Override
    public String editMap(List<String> p_arguments) throws VMException, IOException {
        String l_returnValue = "";

        if (!p_arguments.isEmpty()) {
            // Resolve file path using absolute path of user data directory.
            String l_resolvedPathToFile = PathResolverUtil.resolveFilePath(p_arguments.get(0));

            if (new File(l_resolvedPathToFile).exists()) {
                // Try to retrieve the file
                FileUtil.retrieveFile(l_resolvedPathToFile);
                // Will throw exception if the file path is not valid
                BufferedReader l_reader = new BufferedReader(new FileReader(l_resolvedPathToFile));
                String l_currentLine = l_reader.readLine();

                if (l_currentLine.startsWith("[")) {
                    if (l_currentLine.substring(l_currentLine.indexOf("[") + 1, l_currentLine.indexOf("]")).equalsIgnoreCase("continents")) {
                        EditMapService l_editMapService = new EditMapService();
                        l_returnValue = l_editMapService.execute(p_arguments);
                        d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
                    } else if (l_currentLine.substring(l_currentLine.indexOf("[") + 1, l_currentLine.indexOf("]")).equalsIgnoreCase("Map")) {
                        EditMapService l_edit = new Adapter(new EditConquestMapService());
                        //EditConquestMapService l_editConquestMapService = new EditConquestMapService();
                        l_returnValue = l_edit.execute(p_arguments);
                        d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
                    }
                } else {
                    throw new InvalidMapException("Map file is invalid.");
                }
            } else {
                EditMapService l_editMapService = new EditMapService();
                l_returnValue = l_editMapService.execute(p_arguments);
                d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
            }
        } else {
            throw new InvalidArgumentException("Please provide argument values");
        }
        return l_returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateMap(List<String> p_arguments) throws VMException {
        ValidateMapService l_validateMapService = new ValidateMapService();
        return l_validateMapService.execute(p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveMap(List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() throws VMException {
        throw new VMException("Map hasn't been loaded.");
    }
}
