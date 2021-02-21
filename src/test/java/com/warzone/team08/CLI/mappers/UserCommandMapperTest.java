package com.warzone.team08.CLI.mappers;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.models.UserCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the interpreted value of the user input text
 */
public class UserCommandMapperTest {
    /**
     * User input text
     */
    private String d_commandWithArgument;
    private String d_commandWithValue;

    /**
     * Correct interpreted user command of the input text
     */
    private UserCommand d_correctCommandWithArgument;
    private UserCommand d_correctCommandWithValue;

    @Before
    public void before() {
        d_commandWithArgument = "editcontinent -add Canada 10 -remove Continent";
        d_correctCommandWithArgument = new UserCommand();
        d_correctCommandWithArgument.setHeadCommand("editcontinent");
        d_correctCommandWithArgument.pushUserArgument("add",
                Arrays.asList("continentID", "continentvalue"));
        d_correctCommandWithArgument.pushUserArgument("remove",
                Collections.singletonList("continentID"));

        d_commandWithValue = "savemap filename";
        d_correctCommandWithValue = new UserCommand();
        d_correctCommandWithValue.setHeadCommand("savemap");
        d_correctCommandWithValue.setCommandValues(Collections.singletonList("filename"));

        // Sets the game state to MAP_EDITOR
        Application l_application = new Application();
        l_application.handleApplicationStartup();
    }

    @Test
    public void testUserInput() {
        // Mapper class which maps text to interpreted command.
        UserCommandMapper l_userCommandMapper = new UserCommandMapper();

        // Interprets the user text
        UserCommand l_commandWithArgument = l_userCommandMapper.toUserCommand(this.d_commandWithArgument);
        UserCommand l_commandWithValue = l_userCommandMapper.toUserCommand(this.d_commandWithValue);

        assertEquals(l_commandWithArgument, d_correctCommandWithArgument);
        assertEquals(l_commandWithValue, d_correctCommandWithValue);
    }
}
