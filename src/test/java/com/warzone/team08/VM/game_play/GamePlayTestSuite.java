package com.warzone.team08.VM.game_play;

import com.warzone.team08.VM.game_play.services.DistributeCountriesServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for <code>GAME_PLAY</code> test cases.
 *
 * @author CHARIT
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DistributeCountriesServiceTest.class,
        GamePlayEngine.class
})
public class GamePlayTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
