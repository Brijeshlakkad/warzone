package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;

/**
 * The interface representing the strategy of the player.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public interface PlayerStrategy {
    /**
     * Executes the strategy implemented by the concrete class.
     */
    void execute();

    /**
     * Gets the type of strategy.
     *
     * @return Type of the strategy.
     */
    StrategyType getType();
}
