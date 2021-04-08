package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Rutwik
 */

public class SaveGame implements SingleCommand {
    JSONObject d_currentGame;

    public SaveGame(){
        d_currentGame = new JSONObject();
    }

    public void toJSON(){
        d_currentGame.put("map_editor", MapEditorEngine.getInstance().toJSON);
    }

    /**
     * This method will be called from CLI. Accepts the list of arguments (can be empty). The method serves the purpose
     * of the command without an argument key which may have value.
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @throws VMException Throws the base class if there is invalid input or IOException
     */
    @Override
    public void execute(List<String> p_commandValues) throws VMException {
        this.toJSON();
    }
}

