package com.warzone.team08.CLI.models;

import com.warzone.team08.CLI.constants.enums.specifications.ArgumentSpecification;

import java.util.Objects;

/**
 * Predefined structure for the command arguments and its value(s) It also provides the specification to: 1. Validate
 * the number of values provided by the user 2. Validate each value with Regex
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandArgument {
    private String d_argumentKey;
    private int d_numOfValues;
    private ArgumentSpecification d_specification;

    public CommandArgument(String p_argumentKey, int p_numOfValues, ArgumentSpecification p_specification) {
        d_argumentKey = p_argumentKey;
        d_numOfValues = p_numOfValues;
        d_specification = p_specification;
    }

    public String getArgumentKey() {
        return d_argumentKey;
    }

    public void setArgumentKey(String p_argumentKey) {
        d_argumentKey = p_argumentKey;
    }

    public int getNumOfValues() {
        return d_numOfValues;
    }

    public void setNumOfValues(int p_numOfValues) {
        d_numOfValues = p_numOfValues;
    }

    public ArgumentSpecification getSpecification() {
        return d_specification;
    }

    public void setSpecification(ArgumentSpecification p_specification) {
        d_specification = p_specification;
    }

    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        CommandArgument l_that = (CommandArgument) l_p_o;
        return d_numOfValues == l_that.d_numOfValues &&
                Objects.equals(d_argumentKey, l_that.d_argumentKey) &&
                d_specification == l_that.d_specification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_argumentKey, d_numOfValues, d_specification);
    }
}
