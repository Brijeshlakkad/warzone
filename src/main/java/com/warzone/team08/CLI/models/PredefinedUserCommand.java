package com.warzone.team08.CLI.models;

import com.warzone.team08.CLI.constants.specifications.CommandSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents the predefined structure and specification for the commands
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class PredefinedUserCommand {
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
     * Includes the command specification
     */
    private CommandSpecification d_commandSpecification;

    public PredefinedUserCommand() {
        // Initialise references
        d_commandArgumentList = new ArrayList<>();
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
                p_argumentKey.equals(PredefinedUserCommand.D_ARG_PREFIX.concat(p_p_argumentKey.getArgumentKey()))
        ).collect(Collectors.toList()).get(0);
    }

    /**
     * Checks if the provided string is the key of this command
     *
     * @param p_argKey String to be checked if it is the key of this command
     * @return Value of true if the key belongs to this command; false otherwise
     */
    public boolean isKeyOfCommand(String p_argKey) {
        if (!p_argKey.startsWith(PredefinedUserCommand.D_ARG_PREFIX))
            return false;
        return this.getArgumentKeys().stream().anyMatch((p_p_argKey) ->
                p_argKey.equals(PredefinedUserCommand.D_ARG_PREFIX.concat(p_p_argKey))
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
     * Checks the head of the command and its argument key only
     *
     * @param l_p_o UserCommand need to checked with
     * @return true if both objects are equal
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        PredefinedUserCommand l_that = (PredefinedUserCommand) l_p_o;
        return Objects.equals(d_headCommand, l_that.d_headCommand) &&
                Objects.equals(d_commandArgumentList, l_that.d_commandArgumentList);
    }
}
