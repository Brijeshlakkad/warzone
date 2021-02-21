package com.warzone.team08.CLI.constants.layouts.commands;

import com.warzone.team08.CLI.constants.enums.specifications.ArgumentSpecification;
import com.warzone.team08.CLI.constants.enums.specifications.CommandSpecification;
import com.warzone.team08.CLI.constants.layouts.CommandLayout;
import com.warzone.team08.CLI.models.CommandArgument;
import com.warzone.team08.CLI.models.UserCommand;

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
    List<UserCommand> d_userCommands;

    public GamePlayCommandLayout() {
        d_userCommands = new ArrayList<>();

        UserCommand l_userCommand = new UserCommand();
        // Example of the command:
        // showmap
        l_userCommand = new UserCommand();
        l_userCommand.setHeadCommand("showmap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // gameplayer -add playername -remove playername
        l_userCommand = new UserCommand();
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
        // assigncountries
        l_userCommand = new UserCommand();
        l_userCommand.setHeadCommand("assigncountries");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // deploy countryID num
        l_userCommand = new UserCommand();
        l_userCommand.setHeadCommand("deploy");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);
    }

    /**
     * {@inheritDoc}
     *
     * @return Value of the list of user commands for this class.
     */
    @Override
    public List<UserCommand> getUserCommands() {
        return this.d_userCommands;
    }
}
