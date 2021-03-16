package com.warzone.team08.CLI.layouts.commands;

import com.warzone.team08.CLI.constants.specifications.ArgumentSpecification;
import com.warzone.team08.CLI.constants.specifications.CommandSpecification;
import com.warzone.team08.CLI.layouts.CommandLayout;
import com.warzone.team08.CLI.models.CommandArgument;
import com.warzone.team08.CLI.models.PredefinedUserCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encompasses all the commands which can be entered by the user during the <code>GAME_PLAY</code> game
 * state.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class GamePlayCommandLayout implements CommandLayout {
    /**
     * The list of user commands which can be entered at the <code>GAME_PLAY</code> state of GameState
     */
    List<PredefinedUserCommand> d_userCommands;

    /**
     * Constructor sets the predefined user commands. These commands will be used to check the structure of a command
     * entered by the user.
     */
    public GamePlayCommandLayout() {
        d_userCommands = new ArrayList<>();

        PredefinedUserCommand l_userCommand;
        // Example of the command:
        // > showmap
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("showmap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > gameplayer -add playername -remove playername
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("gameplayer");
        l_userCommand.setCommandSpecification(CommandSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandArgument(
                "add",
                1,
                ArgumentSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandArgument(
                "remove",
                1,
                ArgumentSpecification.EQUAL
        ));
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > assigncountries
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("assigncountries");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        l_userCommand.setGameEngineStartCommand(true);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > deploy countryID num
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("deploy");
        l_userCommand.setGameEngineCommand(true);
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);
    }

    /**
     * {@inheritDoc}
     *
     * @return Value of the list of user commands for this class.
     */
    @Override
    public List<PredefinedUserCommand> getUserCommands() {
        return this.d_userCommands;
    }
}
