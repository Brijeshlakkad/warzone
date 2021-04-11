package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * This file will save the state of the game.
 * @author Rutwik
 */

public class SaveGame implements SingleCommand {
    JSONObject d_currentGame;

    /**
     * Initialize JSON object.
     */
    public SaveGame(){
        d_currentGame = new JSONObject();
    }

    /**
     * toJSON method will insert the values of each state into JSON Object.
     */
    public void toJSON(){
        d_currentGame.put("map_editor", MapEditorEngine.getInstance().toJSON());
        d_currentGame.put("game_engine", GamePlayEngine.getInstance().toJSON());
    }

    /**
     * This method will write the content into JSON file.
     * @param p_fileObject File path to create if not exists and write into files.
     * @return value of response of the request
     * @throws IOException Throws if the error is in writing into the file.
     */
    public String saveGameState(File p_fileObject) throws IOException {
        this.toJSON();
        try(Writer l_writer = new FileWriter(p_fileObject)){
            l_writer.write(d_currentGame.toString()+"\n");
            return "Game State Saved Successfully.";
        } catch (IOException p_IOException) {
            throw new IOException("File not saved Successfully.");
        }
    }
    /**
     * This method will be called from CLI. Accepts the list of arguments (can be empty). The method serves the purpose
     * of the command without an argument key which may have value.
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @throws VMException Throws the base class if there is invalid input or IOException
     * @return
     */
    @Override
    public String execute(List<String> p_commandValues) throws VMException{
        this.toJSON();

        try {
            return saveGameState(FileUtil.retrieveFile(PathResolverUtil.resolveFilePath(p_commandValues.get(0))));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error in file saving.";
        }
    }
}

