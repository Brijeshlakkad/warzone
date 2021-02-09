package com.warzone.team08.VM;

import com.warzone.team08.VM.engines.Runtime;

/**
 * Handles the connection with different user interfaces. Creates an environment for the player to store the
 * information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class VirtualMachine {
    /**
     * VM runtime, to store player runtime information.
     */
    private final Runtime d_runtime;

    public VirtualMachine() {
        this.d_runtime = new Runtime();
    }

    /**
     * Gets the runtime engine of the game.
     *
     * @return Value of the runtime engine.
     */
    public Runtime RUNTIME() {
        return d_runtime;
    }
}
