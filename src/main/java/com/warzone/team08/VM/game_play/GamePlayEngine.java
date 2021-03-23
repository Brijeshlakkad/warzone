package com.warzone.team08.VM.game_play;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.interfaces.Engine;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.GameLoopIllegalStateException;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.phases.Execute;
import com.warzone.team08.VM.phases.IssueOrder;
import com.warzone.team08.VM.phases.PlaySetup;
import com.warzone.team08.VM.phases.Reinforcement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int d_currentPlayerTurn = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#ISSUE_ORDER</code>
     * state.
     */
    private int d_currentPlayerForIssuePhase = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#EXECUTE_ORDER</code>
     * state.
     */
    private int d_currentPlayerForExecutionPhase = 0;

    /**
     * Thread created by <code>GamePlayEngine</code>. This thread should be responsive to interruption.
     */
    private Thread d_LoopThread;

    /**
     * Keeps track of the execution-index; it helps to decide order execution and expiration phase.
     */
    private static int d_currentExecutionIndex = 0;

    /**
     * List of the future orders which are supposed to be executed later in the future iterations.
     */
    private final List<Order> d_futurePhaseOrders = new ArrayList<>();

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
        Player l_currentPlayer = d_playerList.get(d_currentPlayerTurn);
        d_currentPlayerTurn++;
        // Round-robin fashion
        if (d_currentPlayerTurn >= d_playerList.size()) {
            d_currentPlayerTurn = 0;
        }
        return l_currentPlayer;
    }

    /**
     * Gets the index of current player.
     *
     * @return Value of index of current player.
     */
    public int getCurrentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Sets the index of current player.
     *
     * @param p_currentPlayerTurn Value of index of current player.
     */
    public void setCurrentPlayerTurn(int p_currentPlayerTurn) {
        d_currentPlayerTurn = p_currentPlayerTurn;
    }

    /**
     * Gets the previously-stored player index whose turn is to issue an order.
     *
     * @return Value of the index.
     */
    public int getCurrentPlayerForIssuePhase() {
        return d_currentPlayerForIssuePhase;
    }

    /**
     * Sets the player index whose turn is going to issue an order in the next iteration.
     *
     * @param p_currentPlayerForIssuePhase Value of the index to be set.
     */
    public void setCurrentPlayerForIssuePhase(int p_currentPlayerForIssuePhase) {
        d_currentPlayerForIssuePhase = p_currentPlayerForIssuePhase;
    }

    /**
     * Gets the previously-stored player index to get an order of the player for execution.
     *
     * @return Value of the index.
     */
    public int getCurrentPlayerForExecutionPhase() {
        return d_currentPlayerForExecutionPhase;
    }

    /**
     * Sets the player index whose order is going to be executed first in the next iteration.
     *
     * @param p_currentPlayerForExecutionPhase Value of the index to be set.
     */
    public void setCurrentPlayerForExecutionPhase(int p_currentPlayerForExecutionPhase) {
        d_currentPlayerForExecutionPhase = p_currentPlayerForExecutionPhase;
    }

    /**
     * Gets the current execution index. This index helps to keep track of orders; some of those should be executed and
     * others of those should be expired during this loop iteration.
     *
     * @return Value of the index.
     */
    public static int getCurrentExecutionIndex() {
        return d_currentExecutionIndex;
    }

    /**
     * Gets the list of future orders which should be executed during this phase.
     *
     * @return Value of the list of orders.
     */
    public List<Order> getCurrentFutureOrders() {
        return d_futurePhaseOrders.stream().filter(p_futureOrder ->
                p_futureOrder.getExecutionIndex() == d_currentExecutionIndex
        ).collect(Collectors.toList());
    }

    /**
     * Gets the list of future orders which are going to be expired after this loop iteration.
     *
     * @return Value of the list of orders.
     */
    public List<Order> getExpiredFutureOrders() {
        return d_futurePhaseOrders.stream().filter(p_futureOrder ->
                p_futureOrder.getExpiryIndex() >= d_currentExecutionIndex
        ).collect(Collectors.toList());
    }

    /**
     * Adds the order to be executed in future iteration.
     *
     * @param p_futureOrder Value of the order to be added.
     */
    public void addFutureOrder(Order p_futureOrder) {
        this.d_futurePhaseOrders.add(p_futureOrder);
    }

    /**
     * Removes the order. This method is onlt being called if the order has been expired.
     *
     * @param p_futureOrder Value of the order to be added.
     */
    public void removeFutureOrder(Order p_futureOrder) {
        this.d_futurePhaseOrders.remove(p_futureOrder);
    }

    /**
     * Increments the current-execution-index.
     */
    public static void incrementIndex() {
        d_currentExecutionIndex++;
    }

    /**
     * Starts the thread to iterate through various <code>GameLoopState</code> states. Channels the exception to
     * <code>stderr</code> method.
     */
    public void startGameLoop() {
        if (d_LoopThread != null && d_LoopThread.isAlive()) {
            d_LoopThread.interrupt();
        }
        d_LoopThread = new Thread(() -> {
            GameEngine l_gameEngine = GameEngine.getInstance();
            try {
                if (l_gameEngine.getGamePhase().getClass().equals(PlaySetup.class)) {
                    l_gameEngine.getGamePhase().nextState();
                } else {
                    throw new GameLoopIllegalStateException("Illegal state transition!");
                }
                // Responsive to thread interruption.
                while (!Thread.currentThread().isInterrupted()) {
                    if (l_gameEngine.getGamePhase().getClass().equals(Reinforcement.class)) {
                        l_gameEngine.getGamePhase().reinforce();
                    }
                    if (l_gameEngine.getGamePhase().getClass().equals(IssueOrder.class)) {
                        l_gameEngine.getGamePhase().issueOrder();
                    }
                    if (l_gameEngine.getGamePhase().getClass().equals(Execute.class)) {
                        l_gameEngine.getGamePhase().fortify();
                    }
                    l_gameEngine.getGamePhase().nextState();
                }
            } catch (VMException p_vmException) {
                VirtualMachine.getInstance().stderr(p_vmException.getMessage());
            } finally {
                // This will set CLI#UserInteractionState to WAIT
                VirtualMachine.getInstance().stdout("GAME_ENGINE_STOPPED");
            }
        });
        d_LoopThread.start();
    }

    /**
     * {@inheritDoc} Shuts the <code>GamePlayEngine</code>.
     */
    public void shutdown() {
        // Interrupt thread if it is alive.
        if (d_LoopThread != null && d_LoopThread.isAlive())
            d_LoopThread.interrupt();
    }
}