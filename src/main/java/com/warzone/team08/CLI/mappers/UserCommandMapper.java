package com.warzone.team08.CLI.mappers;

import com.warzone.team08.CLI.constants.enums.specifications.ArgumentSpecification;
import com.warzone.team08.CLI.constants.enums.specifications.CommandSpecification;
import com.warzone.team08.CLI.constants.layouts.UserCommandLayout;
import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.models.CommandArgument;
import com.warzone.team08.CLI.models.UserCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converts string entity of user text to UserCommand object that can be used to call the different APIs
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserCommandMapper {
    /**
     * Parses user input text and returns the UserCommand object.
     * <pre>
     * The UserCommand object is equivalent to the user text which is being used to call the APIs related to the command instruction
     * </pre>
     *
     * @param p_userInput Text entered by the user
     */
    public UserCommand toUserCommand(String p_userInput) {
        // Cracks a command line string
        List<String> l_commands = Arrays.asList(p_userInput.split("\\s"));

        if (l_commands.size() > 0) {
            // The command entered by the user
            String l_headOfCommand = l_commands.get(0);

            // Fetched predefined structure of the user command using the head
            // Use this to save interpreted instructions given by the user.
            UserCommand l_predefinedUserCommand =
                    UserCommandLayout.matchAndGetUserCommand(l_headOfCommand);
            l_predefinedUserCommand.setHeadCommand(l_headOfCommand);

            // Represents the body of the command line
            List<String> l_argumentBody = l_commands.subList(1, l_commands.size());

            // Throws an exception if the command needs its value to run and not provided by the user
            if (validateIfCommandDoesNeedValue(l_predefinedUserCommand, l_argumentBody.size())) {
                // This UserCommand instance will be returned from here.
                l_predefinedUserCommand.setCommandValues(l_argumentBody);
                return l_predefinedUserCommand;
            } else if (validateIfCommandCanRunAlone(l_predefinedUserCommand, l_argumentBody.size())) {
                // Throws an exception if the command can run alone and the user has provided with some random text
            } else {
                // Throws an exception if the command does not have any argument
                // Need to be checked again after extracting the argument and validating it.
                validateIfCommandDoesNeedArgument(l_predefinedUserCommand, l_argumentBody.size());
            }

            // If the command can accept arguments and user have provided the arguments
            if (l_predefinedUserCommand.getCommandArgumentList().size() > 0 &&
                    l_argumentBody.size() > 0) {
                // Stores the position of the argument keys in the argument body
                List<Integer> l_positionOfArgKeyList = new ArrayList<>();

                // Index, to keep track of the iteration over the l_argumentBody
                int l_index = 0;
                for (String l_command : l_argumentBody) {
                    if (l_predefinedUserCommand.isKeyOfCommand(l_command)) {
                        l_positionOfArgKeyList.add(l_index);
                    }
                    l_index++;
                }

                // Throws an exception if the input does not have any argument
                validateIfCommandDoesNeedArgument(l_predefinedUserCommand, l_positionOfArgKeyList.size());

                // If user has provided the argument(s)
                for (int l_currentPosIndex = 0; l_currentPosIndex < l_positionOfArgKeyList.size(); l_currentPosIndex++) {
                    // The index of the argument key and the next argument key in the found-position list
                    int l_nextPosIndex = l_currentPosIndex + 1;

                    // If there is next key, find the index of that key
                    int l_indexOfNextKey;
                    if (l_nextPosIndex == l_positionOfArgKeyList.size()) {
                        l_indexOfNextKey = l_argumentBody.size();
                    } else {
                        l_indexOfNextKey = l_positionOfArgKeyList.get(l_nextPosIndex);
                    }

                    // Get argument details for the current argument key
                    String l_currentArgKey = l_argumentBody.get(l_positionOfArgKeyList.get(l_currentPosIndex));
                    CommandArgument l_commandArgument = l_predefinedUserCommand.matchCommandArgument(l_currentArgKey);
                    int l_indexOfCurrentKey = l_positionOfArgKeyList.get(l_currentPosIndex);

                    // Get the number of values provided by the user for the current argument
                    List<String> l_values = l_argumentBody.subList(l_indexOfCurrentKey + 1, l_indexOfNextKey);

                    // Checks if the user has entered the correct number of values for the argument
                    if (l_commandArgument.getSpecification() == ArgumentSpecification.EQUAL &&
                            l_commandArgument.getNumOfValues() == l_values.size()) {
                        l_predefinedUserCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    } else if (l_commandArgument.getSpecification() == ArgumentSpecification.MAX &&
                            l_commandArgument.getNumOfValues() >= l_values.size()) {
                        l_predefinedUserCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    } else if (l_commandArgument.getSpecification() == ArgumentSpecification.MIN &&
                            l_commandArgument.getNumOfValues() <= l_values.size()) {
                        l_predefinedUserCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    }

                    // Throw if the user has not provided the correct number of values
                    if (l_commandArgument.getSpecification() != ArgumentSpecification.MAX &&
                            l_commandArgument.getNumOfValues() >= l_values.size()
                    ) {
                        throw new InvalidArgumentException("Required argument values not provided!");
                    } else {
                        throw new InvalidArgumentException("Unrecognised argument values");
                    }
                }
            }
            return l_predefinedUserCommand;
        } else {
            // If user has entered only spaces
            throw new InvalidCommandException("Invalid user input!");
        }
    }

    /**
     * Checks if the command doesn't need any argument to run
     *
     * @param p_userCommand Value of the command which has the specification
     * @param numOfKeys     Value of the number of keys entered by the user
     * @return True if the command can run alone; false otherwise
     */
    private boolean validateIfCommandCanRunAlone(UserCommand p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandSpecification.CAN_RUN_ALONE) {
            // Means the user has entered the not-needful text after the command
            if (numOfKeys > 0)
                throw new InvalidArgumentException("Unrecognized argument!");
            return true;
        }
        return false;
    }

    /**
     * Checks if the command needs value to proceed
     *
     * @param p_userCommand Value of the command which has the specification.
     * @param numOfKeys     Value of the number of text entered by the user after the command.
     * @return True if the command can run alone; false otherwise.
     */
    private boolean validateIfCommandDoesNeedValue(UserCommand p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandSpecification.CAN_RUN_ALONE_WITH_VALUE) {
            // Means the user has not provided the value required with the command
            if (numOfKeys == 0)
                throw new InvalidArgumentException("Value not provided");
            return true;
        }
        return false;
    }

    /**
     * Checks if the argument body is empty and the command needs an argument to run
     *
     * @param p_userCommand Value of the command which has the specification
     * @param numOfKeys     Value of the number of keys entered by the user
     * @return True if the entered command has at least one argument; false otherwise
     */
    private boolean validateIfCommandDoesNeedArgument(UserCommand p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandSpecification.AT_LEAST_ONE) {
            // Means the user has not provided any required argument keys
            if (numOfKeys == 0)
                throw new InvalidArgumentException("Command requires at least one argument to run!");
            return true;
        }
        return false;
    }
}
