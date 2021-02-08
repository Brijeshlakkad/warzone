package com.warzone.team08.CLI.constants.layouts;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.enums.states.GameState;
import com.warzone.team08.CLI.constants.layouts.commands.MapEditorCommandLayout;
import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.models.UserCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class maps the command-layout classes to their game state.
 * The class can be used without creating any instance.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserCommandLayout {
    /**
     * The list of all classes across each GameState (A state of the game).
     */
    private static Map<GameState, CommandLayout> d_gameStateListMap = new HashMap<>();

    /**
     * The object which has its user commands for Map_Editor game state.
     */
    private static final MapEditorCommandLayout MAP_EDITOR_LAYOUT = new MapEditorCommandLayout();

    /**
     * Stores the commands according to the game state
     */
    static {
        d_gameStateListMap.put(
                GameState.MAP_EDITOR,
                UserCommandLayout.MAP_EDITOR_LAYOUT
        );
    }

    /**
     * Gets matched the user command
     * It decides the which list of predefined command using the game state
     * Then it matches the user command with the head of the command provided
     *
     * @param p_headOfCommand head of the command which needs to be matched the list of predefined commands
     * @return Value of the user command which matched with p_headOfCommand
     */
    public static UserCommand matchAndGetUserCommand(String p_headOfCommand) {
        // Gets the list of command from the layout, and then it is being streamed over to filter the list
        try {
            return d_gameStateListMap.get(Application.getGameState()).getUserCommands()
                    .stream().filter((userCommand) ->
                            userCommand.getHeadCommand().equals(p_headOfCommand)
                    ).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidArgumentException("Unrecognized command!");
        }
    }

    /**
     * Gets the instance which has the user command list for the MAP_EDITOR game state
     *
     * @return Value of instance having the list of MAP_EDITOR user commands
     */
    private static MapEditorCommandLayout getMapEditorLayout() {
        return UserCommandLayout.MAP_EDITOR_LAYOUT;
    }
}
