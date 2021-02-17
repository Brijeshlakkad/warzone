package com.warzone.team08.CLI;

import com.warzone.team08.CLI.constants.enums.specifications.CommandSpecification;
import com.warzone.team08.CLI.models.UserCommand;
import org.junit.Test;

import java.util.Arrays;

/**
 * The class to test the interpreted user command and its specific function call with arguments if any.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandLineInterfaceTest {
    private final UserCommand d_userCommand;

    public CommandLineInterfaceTest() {
        d_userCommand = new UserCommand();
        d_userCommand.setHeadCommand("editcontinent");
        d_userCommand.setCommandSpecification(CommandSpecification.AT_LEAST_ONE);
        d_userCommand.pushUserArgument("add",
                Arrays.asList("continentID", "12"));
    }

    /**
     * Tests the interpreted user command and calls the method of the specific class
     */
    @Test(expected = Test.None.class /* no exception expected */)
    public void testTakeAction() {
        // If the method call completes without any raised exception, then the call was successful
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.d_thread.start();
        l_commandLineInterface.takeAction(d_userCommand);
    }
}
