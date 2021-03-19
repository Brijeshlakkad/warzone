package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.game_play.services.AssignReinforcementService;

/**
 *
 */
public class Reinforcement extends MainPlay {

    Reinforcement(GameEngine p_ge) {
        super(p_ge);
    }

    public void reinforce() throws VMException {
        AssignReinforcementService l_reinforcementService = new AssignReinforcementService();
        l_reinforcementService.execute();
        d_gameEngine.setGamePhase(new Attack(d_gameEngine));
    }

    public void attack() throws VMException {
        invalidCommand();
    }

    public void fortify() throws VMException {
        invalidCommand();
    }

    public void nextState() {
        d_gameEngine.setGamePhase(new Attack(d_gameEngine));
    }
}
