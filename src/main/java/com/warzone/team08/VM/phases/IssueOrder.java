package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.game_play.services.IssueOrderService;

/**
 * Implements the method available for this phase of game.
 */
public class Attack extends MainPlay {
    /**
     * Parameterised constructor to create an instance of <code>Attack</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    Attack(GameEngine p_gameEngine) {
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
    public void attack() {
        IssueOrderService l_issueOrderService = new IssueOrderService();
        l_issueOrderService.execute();
        d_gameEngine.setGamePhase(new Fortify(d_gameEngine));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fortify() throws VMException {
        invalidCommand();
    }

    /**
     * Call this method to go the the next state in the sequence.
     */
    public void nextState() {
        d_gameEngine.setGamePhase(new Fortify(d_gameEngine));
    }
}
