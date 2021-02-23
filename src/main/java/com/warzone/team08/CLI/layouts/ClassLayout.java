package com.warzone.team08.CLI.layouts;

import java.util.Map;

/**
 * Interface to define the method(s) which should be implemented for various class-layout classes.
 * The class-layout are the classes that define the classes for the specific command during a specific game state.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public interface ClassLayout {
    /**
     * Gets the mappings of the command to its Java classes
     * Inheriting class must have the list of predefined classes for the particular command.
     *
     * @return the list of commands
     */
    public Map<String, String> getMappings();
}
