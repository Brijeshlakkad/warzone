package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Player;

/**
 * This class defines the behavior of benevolent player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class BenevolentStrategy extends PlayerStrategy {
    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        // TODO DEEP PATEL add order
//        this.d_player.addOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrategyType getType() {
        return StrategyType.BENEVOLENT;
    }
}
