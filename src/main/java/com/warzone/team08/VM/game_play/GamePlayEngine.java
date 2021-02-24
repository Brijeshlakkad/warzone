package com.warzone.team08.VM.game_play;

import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.enums.GameLoopState;
import com.warzone.team08.VM.constants.interfaces.Engine;
import com.warzone.team08.VM.entities.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.game_play.services.AssignReinforcementService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Manages players and their orders runtime information; Responsible for executing orders in round-robin fashion.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class GamePlayEngine implements Engine {
    /**
     * Singleton instance of the class.
     */
    private static GamePlayEngine d_Instance;

    /**
     * Players of the game.
     */
    private List<Player> d_playerList;

    /**
     * Current turn of the player for issuing the order.
     */
    private int currentPlayerTurn = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#ISSUE_ORDER</code>
     * state.
     */
    private int currentPlayerForIssuePhase = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#EXECUTE_ORDER</code>
     * state.
     */
    private int currentPlayerForExecutionPhase = 0;

    /**
     * Represents the current state of the game loop.
     * <ul>
     *     <li>not_available</li>
     *     <li>assign_reinforcements</li>
     *     <li>issue_order</li>
     *     <li>execute_order</li>
     * </ul>
     *
     * @see GameLoopState for more information.
     */
    private static GameLoopState gameLoopState;

    /**
     * Thread created by <code>GamePlayEngine</code>. This thread should be responsive to interruption.
     */
    private Thread d_loopThread;

    /**
     * Gets the single instance of the <code>GamePlayEngine</code> class.
     * <p>If not created before, it creates the one.
     *
     * @return Value of the instance.
     */
    public static GamePlayEngine getInstance() {
        if (d_Instance == null) {
            d_Instance = new GamePlayEngine();
        }
        return d_Instance;
    }

    /**
     * Instance can not be created outside the class. (private)
     */
    private GamePlayEngine() {
        this.initialise();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise() {
        d_playerList = new ArrayList<>();
        setGameLoopState(GameLoopState.NOT_AVAILABLE);
    }

    /**
     * Gets the state of <code>GameLoop</code>.
     *
     * @return Value of the state.
     */
    public static GameLoopState getGameLoopState() {
        return gameLoopState;
    }

    /**
     * Sets the state of <code>GameLoop</code>.
     *
     * @param p_gameLoopState Value of the state.
     */
    public static void setGameLoopState(GameLoopState p_gameLoopState) {
        gameLoopState = p_gameLoopState;
    }

    /**
     * Adds the player to the list.
     *
     * @param p_player Player to be added.
     */
    public void addPlayer(Player p_player) {
        d_playerList.add(p_player);
    }

    /**
     * Removes the player from the list.
     *
     * @param p_player Player to be removed.
     */
    public void removePlayer(Player p_player) {
        d_playerList.remove(p_player);
    }

    /**
     * Gets the players of the game.
     *
     * @return Value of the player list.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * Get the player who's turn for issuing the order.
     *
     * @return Value of the player which will issue the order.
     */
    public Player getCurrentPlayer() {
        Player l_currentPlayer = d_playerList.get(currentPlayerTurn);
        currentPlayerTurn++;
        // Round-robin fashion
        if (currentPlayerTurn >= d_playerList.size()) {
            currentPlayerTurn = 0;
        }
        return l_currentPlayer;
    }

    /**
     * Starts the thread to iterate through various <code>GameLoopState</code> states. Channels the exception to
     * <code>stderr</code> method.
     */
    public void startGameLoop() {
        if (d_loopThread != null && d_loopThread.isAlive()) {
            d_loopThread.interrupt();
        }
        d_loopThread = new Thread(() -> {
            try {
                // Responsive to thread interruption.
                while (!Thread.currentThread().isInterrupted()) {
                    this.onAssignReinforcementPhase();
                    this.onStartIssueOrderPhase();
                    this.onStartExecuteOrderPhase();
                }
            } catch (VMException p_vmException) {
                VirtualMachine.getInstance().stderr(p_vmException.getMessage());
            } finally {
                // Set CLI#UserInteractionState to WAIT
                VirtualMachine.getInstance().stdout("GAME_ENGINE_TO_WAIT");
            }
        });
        d_loopThread.start();
    }

    /**
     * Assigns each player the correct number of reinforcement armies according to the Warzone rules.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    public void onAssignReinforcementPhase() throws GameLoopIllegalStateException {
        if (GamePlayEngine.getGameLoopState() == GameLoopState.NOT_AVAILABLE ||
                GamePlayEngine.getGameLoopState() == GameLoopState.EXECUTE_ORDER) {
            GamePlayEngine.setGameLoopState(GameLoopState.ASSIGN_REINFORCEMENTS);

            AssignReinforcementService l_reinforcementService = new AssignReinforcementService();
            l_reinforcementService.execute();
        } else {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
    }

    /**
     * Starts the <code>GameLoopState#ISSUE_ORDER</code> phase. Requests all players in round-robin fashion for the
     * issuing order until all the players have placed all their reinforcement armies on the map.
     * <p>
     * If the player issues an order with reinforcements more than enough they possess, it will request the same player
     * again for a valid order.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     * @throws EntityNotFoundException       Throws if any provided entity Id not found.
     */
    public void onStartIssueOrderPhase() throws GameLoopIllegalStateException, EntityNotFoundException {
        if (GamePlayEngine.getGameLoopState() != GameLoopState.ASSIGN_REINFORCEMENTS) {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
        GamePlayEngine.setGameLoopState(GameLoopState.ISSUE_ORDER);
        List<Player> finishedIssuingOrders = new ArrayList<>();

        this.currentPlayerTurn = this.currentPlayerForIssuePhase;

        while (finishedIssuingOrders.size() != d_playerList.size()) {
            // Find player who has reinforcements.
            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedIssuingOrders.contains(l_currentPlayer));

            // Until player issues the valid order.
            boolean l_invalidPreviousOrder;
            boolean canTryAgain;
            do {
                canTryAgain = true;
                try {
                    // Request player to issue the order.
                    l_currentPlayer.issueOrder();
                    l_invalidPreviousOrder = false;
                } catch (ReinforcementOutOfBoundException p_e) {
                    l_invalidPreviousOrder = true;

                    // Send exception message to CLI.
                    VirtualMachine.getInstance().stderr(p_e.getMessage());

                    // If all of its reinforcements have been placed, don't ask the player again.
                    if (!l_currentPlayer.isCanReinforce()) {
                        canTryAgain = false;
                        finishedIssuingOrders.add(l_currentPlayer);
                    }
                } catch (InterruptedException | ExecutionException p_e) {
                    // If interruption occurred while issuing the order.
                    l_invalidPreviousOrder = true;
                }
            } while (l_invalidPreviousOrder && canTryAgain);
        }

        // Store to use when starting the issue phase again.
        this.currentPlayerForIssuePhase = this.currentPlayerTurn;
    }

    /**
     * Starts the <code>EXECUTE_ORDER</code> game loop state.
     * <p>
     * Gets the order of the player using <code>Player#nextOrder</code> method and executes it using the type of order.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    public void onStartExecuteOrderPhase() throws GameLoopIllegalStateException {
        if (GamePlayEngine.getGameLoopState() != GameLoopState.ISSUE_ORDER) {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
        GamePlayEngine.setGameLoopState(GameLoopState.EXECUTE_ORDER);
        List<Player> finishedExecutingOrders = new ArrayList<>();

        VirtualMachine.getInstance().stdout("Execution of orders started!");

        this.currentPlayerTurn = this.currentPlayerForExecutionPhase;

        while (finishedExecutingOrders.size() != d_playerList.size()) {
            // Find player who has remaining orders to execute.
            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedExecutingOrders.contains(l_currentPlayer));
            try {
                // Get the next order
                Order l_currentOrder = l_currentPlayer.nextOrder();
                // Use VirtualMachine.stdout()
                l_currentOrder.execute();

                VirtualMachine.getInstance().stdout(String.format("\nExecuted %s", l_currentOrder.toString()));

                // If the current player does not have any orders left.
                if (!l_currentPlayer.hasOrders()) {
                    finishedExecutingOrders.add(l_currentPlayer);
                }
            } catch (OrderOutOfBoundException p_e) {
                VirtualMachine.getInstance().stderr(p_e.getMessage());
                finishedExecutingOrders.add(l_currentPlayer);
            }
        }

        // Store to use when starting the issue phase again.
        this.currentPlayerForExecutionPhase = this.currentPlayerTurn;
    }

    /**
     * {@inheritDoc} Shuts the <code>GamePlayEngine</code>.
     */
    public void shutdown() {
        // Interrupt thread if it is alive.
        if (d_loopThread != null && d_loopThread.isAlive())
            d_loopThread.interrupt();
    }
}
