package com.warzone.team08.CLI.mappers;

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
    private String d_correctUserInput;
    /**
     * Correct interpreted user command of the input text
     */
    private UserCommand d_correctUserCommand;

    @Before
    public void before() {
        this.d_correctUserInput = "editcontinent -add Canada 10 -remove Continent";
        d_correctUserCommand = new UserCommand();
        d_correctUserCommand.setHeadCommand("editcontinent");
        d_correctUserCommand.pushUserArgument("add",
                Arrays.asList("continentID", "continentvalue"));
        d_correctUserCommand.pushUserArgument("remove",
                Collections.singletonList("continentID"));
    }

    @Test
    public void testToUserCommand() {
        // Mapper class which maps text to interpreted command.
        UserCommandMapper l_userCommandMapper = new UserCommandMapper();

        // Interprets the user text
        UserCommand l_userCommand = l_userCommandMapper.toUserCommand(this.d_correctUserInput);

        assertEquals(l_userCommand, d_correctUserCommand);
    }
}
