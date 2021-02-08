package com.warzone.team08.CLI.constants.enums.states;

/**
 * This class represents the different state of the game.
 * It is also being used to validate the user command:
 * One command from one state can not be allowed to run on another state.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public enum GameState {
    /**
     * Map editor state to let user interact with map
     */
    MAP_EDITOR("map_editor"),
    /**
     * States the user has started playing the game
     */
    PLAYING("playing");

    public String d_jsonValue;

    private GameState(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Gets the string value of the enum
     *
     * @return Value of the enum
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
