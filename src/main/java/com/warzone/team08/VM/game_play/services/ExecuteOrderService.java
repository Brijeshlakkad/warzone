package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.CardNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.exceptions.OrderOutOfBoundException;
import com.warzone.team08.VM.game_play.GamePlayEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * This service executes the player's order.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ExecuteOrderService {
    /**
     * Gets the order of the player using <code>Player#nextOrder</code> method and executes it using the type of order.
     */
    public void execute() {
        List<Player> finishedExecutingOrders = new ArrayList<>();

        VirtualMachine.getInstance().stdout("Execution of orders started!");
        GamePlayEngine l_gamePlayEngine = GameEngine.GAME_PLAY_ENGINE();
        l_gamePlayEngine.setCurrentPlayerTurn(l_gamePlayEngine.getCurrentPlayerForExecutionPhase());

        // Iterate over and execute the orders which were supposed to be executed in this phase.
        GamePlayEngine.getInstance().getCurrentFutureOrders().forEach(l_futureOrder -> {
            try {
                l_futureOrder.execute();
            } catch (InvalidOrderException | CardNotFoundException p_e) {
                VirtualMachine.getInstance().stderr(p_e.getMessage());
            }
        });

        // Expire orders which had been executed and are not valid anymore.
        GamePlayEngine.getInstance().getExpiredFutureOrders().forEach(l_futureOrder -> {
            l_futureOrder.expire();
            GamePlayEngine.getInstance().removeFutureOrder(l_futureOrder);
        });

        while (finishedExecutingOrders.size() != l_gamePlayEngine.getPlayerList().size()) {
            // Find player who has remaining orders to execute.
            Player l_currentPlayer;
            do {
                l_currentPlayer = l_gamePlayEngine.getCurrentPlayer();
            } while (finishedExecutingOrders.contains(l_currentPlayer));

            VirtualMachine.getInstance().stdout(String.format("\nExecuting %s's order", l_currentPlayer.getName()));
            try {
                // Get the next order
                Order l_currentOrder = l_currentPlayer.nextOrder();
                // If order supposed to be executed in the next phase.
                if (l_currentOrder.getExecutionIndex() == GamePlayEngine.getCurrentExecutionIndex()) {
                    l_currentOrder.execute();
                }

                VirtualMachine.getInstance().stdout(String.format("\nExecuted %s", l_currentOrder.toString()));

                // If the current player does not have any orders left.
                if (!l_currentPlayer.hasOrders()) {
                    finishedExecutingOrders.add(l_currentPlayer);
                }
            } catch (CardNotFoundException |
                    InvalidOrderException p_e) {
                VirtualMachine.getInstance().stderr(p_e.getMessage());
            } catch (OrderOutOfBoundException p_e) {
                finishedExecutingOrders.add(l_currentPlayer);
            }
        }

        // Store to use when starting the issue phase again.
        l_gamePlayEngine.setCurrentPlayerForExecutionPhase(l_gamePlayEngine.getCurrentPlayerTurn());
    }
}
