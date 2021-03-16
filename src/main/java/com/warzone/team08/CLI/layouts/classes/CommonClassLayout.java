package com.warzone.team08.CLI.layouts.classes;

import com.warzone.team08.CLI.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * This class includes common class layouts which <code>CLI</code> can refer to on any <code>GAME_STATE</code>
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommonClassLayout implements ClassLayout {
    /**
     * Stores the one-to-one mappings between user-command and Java Class Object
     */
    private final Map<String, String> d_commandToClassMapper = new HashMap<>();

    /**
     * Default constructor to map command with file.
     */
    public CommonClassLayout() {
        d_commandToClassMapper.put("exit", "com.warzone.team08.VM.common.services.ApplicationExitService");
    }

    /**
     * Gets the mappings for <code>COMMON_CLASS</code> game state
     *
     * @return Value of the mappings
     */
    public Map<String, String> getMappings() {
        return d_commandToClassMapper;
    }
}
