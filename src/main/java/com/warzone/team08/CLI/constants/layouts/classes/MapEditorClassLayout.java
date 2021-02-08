package com.warzone.team08.CLI.constants.layouts.classes;

import com.warzone.team08.CLI.constants.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * This class includes all the classes for the command of MAP_EDITOR game state that can be entered by the user.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class MapEditorClassLayout implements ClassLayout {
    /**
     * Stores the one-to-one mappings between user-command and Java Class Object
     */
    private final Map<String, String> d_commandToClassMapper = new HashMap<String, String>();

    public MapEditorClassLayout() {
        d_commandToClassMapper.put("editcontinent", "com.warzone.team08.VirtualMachine.map.Continent");
        d_commandToClassMapper.put("editcountry", "com.warzone.team08.VirtualMachine.map.Country");
    }

    /**
     * Gets the mappings of MapEditor
     *
     * @return Value of the mappings
     */
    public Map<String, String> getMappings() {
        return d_commandToClassMapper;
    }
}
