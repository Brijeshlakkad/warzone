package com.warzone.team08.VM.constants.interfaces;

import org.json.JSONObject;

/**
 * This interface provides the methods to be return <code>JSONObject</code> or to assign the data member using the
 * <code>JSONObject</code>.
 *
 * @author Rutwik Patel
 * @version 1.0
 */
public interface JSONable {
    /**
     * Creates <code>JSONObject</code> using the runtime information stored in data members of this class.
     *
     * @return Created <code>JSONObject</code>.
     */
    JSONObject toJSON();

    /**
     * Assigns the data members of the concrete class using the values inside <code>JSONObject</code>.
     *
     * @param p_jsonObject <code>JSONObject</code> holding the runtime information.
     */
    void fromJSON(JSONObject p_jsonObject);
}
