package com.warzone.team08.CLI.models;

import com.warzone.team08.CLI.constants.enums.specifications.CommandSpecification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the predefined structure and specification for the commands
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserCommand {
    /**
     * Prefix to be used for the argument keys.
     */
    private static final String D_ARG_PREFIX = "-";

    /**
     * Represents the user command
     */
    private String d_headCommand;

    /**
     * Represents the arguments passed with the command
     */
    private final List<CommandArgument> d_commandArgumentList;

    /**
     * Represents the argument and its values
     */
    private final Map<String, List<String>> d_userArguments;

    /**
     * Includes the command specification
     */
    private CommandSpecification d_commandSpecification;

    /**
     * Value(s) of the head of the command if any
     */
    private List<String> d_commandValues;

    public UserCommand() {
        // Initialise references
        d_commandArgumentList = new ArrayList<CommandArgument>();
        d_userArguments = new HashMap<>();
        d_commandValues = new ArrayList<>();
    }

    /**
     * Gets the head of command for this user command
     *
     * @return head of the command
     */
    public String getHeadCommand() {
        return d_headCommand;
    }

    /**
     * Sets the head of command for this user command
     *
     * @param p_headCommand head of the command
     */
    public void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }

    /**
     * Gets the list of CommandArgument
     *
     * @return the list of CommandArgument
     */
    public List<CommandArgument> getCommandArgumentList() {
        return d_commandArgumentList;
    }

    /**
     * Adds element into the list of CommandArgument
     *
     * @param p_commandArgument argument key and its value object.
     */
    public void pushCommandArgument(CommandArgument p_commandArgument) {
        d_commandArgumentList.add(p_commandArgument);
    }

    /**
     * Gets the list of argument key and its value(s)
     *
     * @return Value of the list of argument key and its value(s)
     */
    public Map<String, List<String>> getUserArguments() {
        return d_userArguments;
    }

    /**
     * Adds element to the list of user argument mappings.
     *
     * @param argKey Value of argument key.
     * @param values Value of the list of argument values.
     */
    public void pushUserArgument(String argKey, List<String> values) {
        d_userArguments.put(argKey, values);
    }


    /**
     * Gets the list of argument keys
     *
     * @return Value of the list of argument keys
     */
    public List<String> getArgumentKeys() {
        return this.d_commandArgumentList.stream().map((CommandArgument::getArgumentKey))
                .collect(Collectors.toList());
    }

    /**
     * Matches argument key with available arguments for this command.
     *
     * @param p_argumentKey Value of matched argument with the provided key.
     * @return Value of the list of available arguments for this command
     */
    public CommandArgument matchCommandArgument(String p_argumentKey) {
        // Returns only one element
        return this.d_commandArgumentList.stream().filter((p_p_argumentKey) ->
                p_argumentKey.equals(UserCommand.D_ARG_PREFIX.concat(p_p_argumentKey.getArgumentKey()))
        ).collect(Collectors.toList()).get(0);
    }

    /**
     * Checks if the provided string is the key of this command
     *
     * @param p_argKey String to be checked if it is the key of this command
     * @return Value of true if the key belongs to this command; false otherwise
     */
    public boolean isKeyOfCommand(String p_argKey) {
        if (!p_argKey.startsWith(UserCommand.D_ARG_PREFIX))
            return false;
        return this.getArgumentKeys().stream().anyMatch((p_p_argKey) ->
                p_argKey.equals(UserCommand.D_ARG_PREFIX.concat(p_p_argKey))
        );
    }

    /**
     * Sets the specification for the command.
     *
     * @param p_commandSpecification New value of the command specification.
     */
    public void setCommandSpecification(CommandSpecification p_commandSpecification) {
        this.d_commandSpecification = p_commandSpecification;
    }

    /**
     * Gets the specification for the command.
     *
     * @return Value of the command specification.
     */
    public CommandSpecification getCommandSpecification() {
        return d_commandSpecification;
    }

    /**
     * Gets the values of the head of the command if any.
     *
     * @return Values of the head of the command if any.
     */
    public List<String> getCommandValues() {
        return d_commandValues;
    }

    /**
     * Sets new values for the command.
     *
     * @param d_commandValues New value for the command.
     */
    public void setCommandValues(List<String> d_commandValues) {
        this.d_commandValues = d_commandValues;
    }

    /**
     * Checks the head of the command and its argument key only
     *
     * @param l_p_o UserCommand need to checked with
     * @return true if both objects are equal
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        UserCommand l_that = (UserCommand) l_p_o;
        return Objects.equals(d_headCommand, l_that.d_headCommand) &&
                Objects.equals(d_userArguments.keySet(), l_that.d_userArguments.keySet());
    }
}
