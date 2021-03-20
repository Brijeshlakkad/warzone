package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.exceptions.ReinforcementOutOfBoundException;
import com.warzone.team08.VM.game_play.GamePlayEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This service is responsible for requesting players for issuing orders.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class IssueOrderService {
    /**
     * Requests all players in round-robin fashion for the issuing order until all the players have placed all their
     * reinforcement armies on the map.
     * <p>
     * If the player issues an order with reinforcements more than enough they possess, it will request the same player
     * again for a valid order.
     */
    public void execute() {
        List<Player> finishedIssuingOrders = new ArrayList<>();
        GamePlayEngine l_gamePlayEngine = GameEngine.GAME_PLAY_ENGINE();
        l_gamePlayEngine.setCurrentPlayerTurn(l_gamePlayEngine.getCurrentPlayerForIssuePhase());

        while (finishedIssuingOrders.size() != l_gamePlayEngine.getPlayerList().size()) {
            // Find player who has reinforcements.
            Player l_currentPlayer;
            do {
                l_currentPlayer = l_gamePlayEngine.getCurrentPlayer();
            } while (finishedIssuingOrders.contains(l_currentPlayer));

            // Until player issues the valid order.
            boolean l_invalidPreviousOrder;
            boolean l_canTryAgain;
            do {
                l_canTryAgain = true;
                try {
                    // Request player to issue the order.
                    l_currentPlayer.issueOrder();
                    l_invalidPreviousOrder = false;
                } catch (ReinforcementOutOfBoundException p_e) {
                    l_invalidPreviousOrder = true;

                    // Send exception message to CLI.
                    VirtualMachine.getInstance().stderr(p_e.getMessage());

                    // If all of its reinforcements have been placed, don't ask the player again.
                    if (l_currentPlayer.getRemainingReinforcementCount() == 0) {
                        l_canTryAgain = false;
                        finishedIssuingOrders.add(l_currentPlayer);
                    }
                } catch (EntityNotFoundException | InvalidCommandException | InvalidArgumentException p_exception) {
                    l_invalidPreviousOrder = true;
                    // Show VMException error to the user.
                    VirtualMachine.getInstance().stderr(p_exception.getMessage());
                } catch (InterruptedException | ExecutionException p_e) {
                    // If interruption occurred while issuing the order.
                    l_invalidPreviousOrder = true;
                }
            } while (l_invalidPreviousOrder && l_canTryAgain);
        }

        // Store to use when starting the issue phase again.
        l_gamePlayEngine.setCurrentPlayerForIssuePhase(l_gamePlayEngine.getCurrentPlayerTurn());
    }
}
