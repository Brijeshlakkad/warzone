package com.warzone.team08.CLI.constants.enums.specifications;

/**
 * Specification types for command to run
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public enum CommandSpecification {
    /**
     * Command can run alone; without any argument provided
     */
    CAN_RUN_ALONE,
    /**
     * Command needs at least one argument and its values to run
     */
    AT_LEAST_ONE
}
