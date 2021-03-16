package com.warzone.team08.VM.constants.enums;

/**
 * Keeps track of the game loop.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public enum GameLoopState {
    /**
     * When user has not entered the <code>assigncountries</code>.
     */
    NOT_AVAILABLE("not_available"),
    /**
     * State when the game has to assign reinforcements.
     */
    ASSIGN_REINFORCEMENTS("assign_reinforcements"),
    /**
     * Player can issue an order in this state.
     */
    ISSUE_ORDER("issue_order"),
    /**
     * Executes the order from each player in round-robin fashion.
     */
    EXECUTE_ORDER("execute_order");

    /**
     * Variable to set enum value.
     */
    public String d_jsonValue;

    /**
     * Sets the string value of the enum.
     *
     * @param p_jsonValue Value of the enum.
     */
    private GameLoopState(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Gets the string value of the enum.
     *
     * @return Value of the enum.
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
