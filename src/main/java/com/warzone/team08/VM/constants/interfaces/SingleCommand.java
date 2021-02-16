package com.warzone.team08.VM.constants.interfaces;

import com.warzone.team08.VM.exceptions.VMException;

/**
 * Java interface which need to be implemented when command does not have any argument keys.
 * <p>
 * This type of command have zero or more values.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public interface SingleCommand {
    /**
     * This method will be called from CLI. Accepts the list of arguments (can be empty). The method serves the purpose
     * of the command without an argument key which may have value.
     *
     * @param p_commandValue Represents the values passed while running the command.
     * @return Value of the string to be shown to the user.
     */
    String execute(String p_commandValue) throws VMException;
}
