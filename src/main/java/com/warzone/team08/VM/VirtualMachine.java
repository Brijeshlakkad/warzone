package com.warzone.team08.VM;

import com.warzone.team08.VM.engines.MapEditorEngine;

/**
 * Handles the connection with different user interfaces. Creates an environment for the player to store the
 * information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class VirtualMachine {

    public VirtualMachine() {

    }

    /**
     * Gets VM runtime map-editor engine to store map runtime information.
     *
     * @return Value of the map editor engine.
     */
    public MapEditorEngine MAP_EDITOR_ENGINE() {
        return MapEditorEngine.getInstance();
    }
}
