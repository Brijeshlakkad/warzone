package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * This service loads the information stored inside <code>Engines</code> attached to VM (directly or indirectly) from a
 * game file.
 *
 * @author Brijesh Lakkad
 * @author Rutwik
 * @version 1.0
 */
public class LoadGameService implements SingleCommand {
    /**
     * Loads the game engine and its sub-engines from the provided path to JSON file.
     *
     * @param p_targetFile File representing the target.
     * @return Value of response of the request.
     * @throws VMException If any error occurred while loading the engines.
     */
    public String loadGameState(File p_targetFile) throws VMException {
        // Read JSON data from file
        JSONObject jsonObj = new JSONObject(p_targetFile);
        throw new VMException("File couldn't be loaded!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(List<String> p_commandValues) throws VMException {
        return this.loadGameState(FileUtil.retrieveGameFile(
                PathResolverUtil.resolveFilePath(
                        p_commandValues.get(0).concat(".").concat(FileUtil.getGameExtension())
                )));
    }
}
