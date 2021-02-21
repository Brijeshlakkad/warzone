package com.warzone.team08.CLI;

import com.warzone.team08.CLI.constants.UserCommandTest;
import com.warzone.team08.CLI.mappers.UserCommandMapperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserCommandTest.class,
        UserCommandMapperTest.class,
        CommandLineInterfaceTest.class,
})
public class CLITestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}