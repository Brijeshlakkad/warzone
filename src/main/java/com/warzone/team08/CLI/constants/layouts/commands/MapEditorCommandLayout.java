package com.warzone.team08.CLI.constants.layouts.commands;

import com.warzone.team08.CLI.constants.enums.specifications.ArgumentSpecification;
import com.warzone.team08.CLI.constants.enums.specifications.CommandSpecification;
import com.warzone.team08.CLI.constants.layouts.CommandLayout;
import com.warzone.team08.CLI.models.CommandArgument;
import com.warzone.team08.CLI.models.PredefinedUserCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encompasses all the commands which can be entered by the user during the <code>MAP_EDITOR</code> game
 * state.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class MapEditorCommandLayout implements CommandLayout {
    /**
     * The list of user commands which can be entered at the <code>MAP_EDITOR</code> state of GameState
     */
    List<PredefinedUserCommand> d_userCommands;

    public MapEditorCommandLayout() {
        d_userCommands = new ArrayList<>();

        // Example of the below command:
        // editcontinent -add continentID continentvalue -remove continentID
        PredefinedUserCommand l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("editcontinent");
        l_userCommand.setCommandSpecification(CommandSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandArgument(
                "add",
                2,
                ArgumentSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandArgument(
                "remove",
                1,
                ArgumentSpecification.EQUAL
        ));
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // editcountry -add countryID continentID -remove countryID
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("editcountry");
        l_userCommand.setCommandSpecification(CommandSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandArgument(
                "add",
                2,
                ArgumentSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandArgument(
                "remove",
                1,
                ArgumentSpecification.EQUAL
        ));
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("editneighbor");
        l_userCommand.setCommandSpecification(CommandSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandArgument(
                "add",
                2,
                ArgumentSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandArgument(
                "remove",
                2,
                ArgumentSpecification.EQUAL
        ));
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // showmap
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("showmap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // savemap filename
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("savemap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // editmap filename
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("editmap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // validatemap
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("validatemap");
        l_userCommand.setCommandSpecification(CommandSpecification.CAN_RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // loadmap filename
        l_userCommand = new PredefinedUserCommand();
        l_userCommand.setHeadCommand("loadmap");
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
