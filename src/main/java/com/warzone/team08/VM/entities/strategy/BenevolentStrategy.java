package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;

/**
 * This class defines the behavior of benevolent player.
 */
public class BenevolentStrategy implements PlayerStrategy {

    public void execute()
    {

    }
    public StrategyType getType()
    {
        return StrategyType.BENEVOLENT;
    }
}
