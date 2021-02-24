package com.warzone.team08.CLI.layouts.classes;

import com.warzone.team08.CLI.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * This class includes all the classes for the command of <code>GAME_PLAY</code> game state that can be entered by the
 * user.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class GamePlayClassLayout implements ClassLayout {
    /**
     * Stores the one-to-one mappings between user-command and Java Class Object
     */
    private final Map<String, String> d_commandToClassMapper = new HashMap<>();

    public GamePlayClassLayout() {
        d_commandToClassMapper.put("gameplayer", "com.warzone.team08.VM.game_play.services.PlayerService");
        d_commandToClassMapper.put("assigncountries", "com.warzone.team08.VM.game_play.services.DistributeCountriesService");
        d_commandToClassMapper.put("showmap", "com.warzone.team08.VM.game_play.services.ShowMapService");
    }

    /**
     * Gets the mappings for <code>GAME_PLAY</code> game state
     *
     * @return Value of the mappings
     */
    public Map<String, String> getMappings() {
        return d_commandToClassMapper;
    }
}
