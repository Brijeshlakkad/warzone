package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class SaveGame implements SingleCommand {
    JSONObject d_currentGame;

    public SaveGame() {
        d_currentGame = new JSONObject();
    }

    public void toJSON() {
        d_currentGame.put("map_editor", MapEditorEngine.getInstance().toJSON());
    }

    public String execute(List<String> args) {
        this.toJSON();

        // TODO Save to file here using d_currentGame
        throw new UnsupportedOperationException();
    }
}

