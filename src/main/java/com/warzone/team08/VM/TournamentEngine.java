package com.warzone.team08.VM;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.entities.GameResult;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.game_play.services.DistributeCountriesService;
import com.warzone.team08.VM.logger.LogEntryBuffer;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.phases.PlaySetup;

import java.util.*;

/**
 * This engine will be used when user has entered `tournament` command.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class TournamentEngine {
    /**
     * Singleton instance of the class.
     */
    private static TournamentEngine d_Instance;

    /**
     * List of <code>MapEditorEngine</code> for each map file.
     */
    private List<MapEditorEngine> d_mapEditorEngineList;

    /**
     * Map of <code>GameEngine</code> which has been completed.
     */
    private Map<Integer, List<GameEngine>> d_playedGameEngineMappings;

    /**
     * List of <code>Player</code> that representing a strategy.
     */
    private List<Player> d_players;

    /**
     * List of <code>Maps</code> that player entered in tournament.
     */
    private List<String> d_mapsList;

    /**
     * Number of times need to run the game.
     */
    private int d_numberOfGames;

    /**
     * Maximum number of turns. After maximum turn passed, draw the game.
     */
    private int d_maxNumberOfTurns;

    private LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    /**
     * Gets the single instance of the <code>TournamentEngine</code> class which was created before.
     *
     * @return Value of the instance.
     */
    public static TournamentEngine getInstance() throws NullPointerException {
        if (d_Instance == null) {
            d_Instance = new TournamentEngine();
            d_Instance.initialise();
        }
        return d_Instance;
    }

    /**
     * Initialise all the engines to reset the runtime information.
     */
    public void initialise() {
        d_mapEditorEngineList = new ArrayList<>();
        d_players = new ArrayList<>();
        d_playedGameEngineMappings = new HashMap<>();
    }

    /**
     * Signals its engines to shutdown.
     */
    public void shutdown() {
    }

    /**
     * Adds the MapEditorEngine engine to the list.
     *
     * @param p_mapEditorEngine MapEditorEngine having a single map.
     */
    public void addMapEditorEngine(MapEditorEngine p_mapEditorEngine) {
        d_mapEditorEngineList.add(p_mapEditorEngine);
    }

    /**
     * Adds a player to the list of players.
     *
     * @param p_player Player to be added.
     */
    public void addPlayer(Player p_player) {
        this.d_players.add(p_player);
    }

    /**
     * Sets the number of games to be played.
     *
     * @param p_numberOfGames Number of games to be played.
     */
    public void setNumberOfGames(int p_numberOfGames) {
        d_numberOfGames = p_numberOfGames;
    }

    /**
     * Gets the number of games to be played.
     *
     * @return p_numberOfGames Number of games to be played.
     */
    public int getNumberOfGames() {
        return d_numberOfGames;
    }

    /**
     * Gets the number of the maximum turns this tournament can have.
     *
     * @return Value of the number of maximum turns.
     */
    public int getMaxNumberOfTurns() {
        return d_maxNumberOfTurns;
    }

    /**
     * Sets the number of the maximum turns this tournament can have.
     *
     * @param p_maxNumberOfTurns Number of maximum turns.
     */
    public void setMaxNumberOfTurns(int p_maxNumberOfTurns) {
        d_maxNumberOfTurns = p_maxNumberOfTurns;
    }

    /**
     * Sets the List of Players this tournament can have.
     *
     * @param p_playersList List of Players
     */
    public void setPlayers(List<Player> p_playersList) {
        d_players = p_playersList;
    }

    /**
     * Gets the List of Maps this tournament can have.
     *
     * @return List of Maps
     */
    public List<String> getMapsList() {
        return d_mapsList;
    }

    /**
     * Sets the List of Players this tournament can have.
     *
     * @param p_mapsList List of Players
     */
    public void setMapsList(List<String> p_mapsList) {
        d_mapsList = p_mapsList;
    }

    /**
     * Clones the player for the current iteration of tournament.
     *
     * @return List of the cloned player.
     */
    public List<Player> getPlayers() {
        List<Player> l_clonedPlayers = new ArrayList<>();
        for (Player l_player : d_players) {
            l_clonedPlayers.add(new Player(l_player.getName(), l_player.getPlayerStrategyType()));
        }
        return l_clonedPlayers;
    }

    /**
     * Starts the tournament. This will create a <code>GameEngine</code> using MapEditor and GamePlay engines. It will
     * also record the <code>GameEngine</code> to the list.
     * <p>
     * If any error occurred while the game is in the loop, it will set the game result as interrupted.
     * </p>
     *
     * @throws VMException If any exception while executing the tournament.
     */
    public void onStart() throws VMException {
        for (int d_currentGameIndex = 0; d_currentGameIndex < d_numberOfGames; d_currentGameIndex++) {
            for (MapEditorEngine l_mapEditorEngine : d_mapEditorEngineList) {
                GamePlayEngine l_gamePlayEngine = new GamePlayEngine();
                GameEngine l_gameEngine = new GameEngine(l_mapEditorEngine, l_gamePlayEngine);
                // Prepare GameEngine for this tournament round.
                VirtualMachine.setGameEngine(l_gameEngine);

                l_gamePlayEngine.setPlayerList(this.getPlayers());

                DistributeCountriesService l_distributeCountriesService = new DistributeCountriesService();
                l_distributeCountriesService.execute(new ArrayList<>());

                // Tournament will start from PlaySetup phase.
                l_gameEngine.setGamePhase(new PlaySetup(l_gameEngine));

                // If the game index exists, it will add the GameEngine to the list; otherwise it will create a singleton list.
                if (d_playedGameEngineMappings.containsKey(d_currentGameIndex)) {
                    d_playedGameEngineMappings.get(d_currentGameIndex).add(l_gameEngine);
                } else {
                    d_playedGameEngineMappings.put(d_currentGameIndex, Collections.singletonList(l_gameEngine));
                }
                try {
                    l_gamePlayEngine.startGameLoop();
                    l_gamePlayEngine.getLoopThread().join();
                } catch (InterruptedException p_e) {
                    // Game result representing that game was interrupted.
                    l_gamePlayEngine.setGameResult(new GameResult());
                }
            }
        }
        this.onComplete();
    }

    /**
     * After the tournament ends, this method will be called to show the results in a tabular format.
     */
    public void onComplete() {
        // For storing tournament result in tabular format.
        String[][] l_gameResultMatrix = new String[d_mapEditorEngineList.size()][this.getNumberOfGames() + 1];
        List<String> l_playerNames = new ArrayList<>();
        StringBuilder l_builder = new StringBuilder();

        for (Player l_player : d_players) {
            l_playerNames.add(l_player.getName());
        }

        l_builder.append("\n----Result of Tournament after Execution----\n");
        l_builder.append("M: " + d_mapsList.toString() + "\n");
        l_builder.append("P: " + l_playerNames.toString() + "\n");
        l_builder.append("G: " + this.getNumberOfGames() + "\n");
        l_builder.append("D: " + this.getMaxNumberOfTurns() + "\n");

        int l_count = 1;
        for (int l_row = 0; l_row < d_mapEditorEngineList.size(); l_row++) {
            l_gameResultMatrix[l_row][0] = "Map" + l_count;
            l_count++;
        }

        int l_rowIndex = 0;
        int l_columnIndex = 0;
        for (Map.Entry<Integer, List<GameEngine>> entry : d_playedGameEngineMappings.entrySet()) {
            List<GameEngine> l_singleTurnGameEngines = entry.getValue();
            for (GameEngine l_singleGameEngine : l_singleTurnGameEngines) {
                GamePlayEngine l_gamePlayEngine = l_singleGameEngine.getGamePlayEngine();
                GameResult l_gameResult = l_gamePlayEngine.getGameResult();

                // Store the result.
                if (l_gameResult.isDeclaredDraw()) {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = "Draw";
                } else if (l_gameResult.getWinnerPlayer() != null) {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = l_gameResult.getWinnerPlayer().getName();
                } else {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = "Interrupted";
                }

                l_columnIndex++;
            }
            l_rowIndex++;
        }


        String[] l_gameHeader = new String[this.getNumberOfGames() + 1];
        l_gameHeader[0] = "Result";
        for (int i = 1; i < l_gameHeader.length; i++) {
            l_gameHeader[i] = "Game" + i;
        }
        String l_tournamentData=l_builder + FlipTable.of(l_gameHeader, l_gameResultMatrix);
        d_logEntryBuffer.dataChanged("tournament",l_tournamentData);
        VirtualMachine.getInstance().stdout(l_tournamentData);
    }
}