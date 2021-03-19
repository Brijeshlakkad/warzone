package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.game_play.services.ExecuteOrderService;

/**
 * Implements the method available for this phase of game.
 */
public class Fortify extends MainPlay {
    /**
     * Parameterised constructor to create an instance of <code>Fortify</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    Fortify(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reinforce() throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack() throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fortify() {
        ExecuteOrderService l_executeOrderService = new ExecuteOrderService();
        l_executeOrderService.execute();
        d_gameEngine.setGamePhase(new Reinforcement(d_gameEngine));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() {
        d_gameEngine.setGamePhase(new Reinforcement(d_gameEngine));
    }
}
