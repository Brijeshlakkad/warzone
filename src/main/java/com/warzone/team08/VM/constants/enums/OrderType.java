package com.warzone.team08.VM.constants.enums;

/**
 * This enum lists all the orders which player can issue during <code>issue orders</code> phase.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public enum OrderType {
    /**
     * Order of deploying the reinforcements.
     */
    deploy("deploy");

    public String d_jsonValue;

    private OrderType(String p_jsonValue) {
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
