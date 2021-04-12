package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.TournamentEngine;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.map_editor.services.LoadMapService;
import com.warzone.team08.VM.map_editor.services.ValidateMapService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <code>Preload</code> phase of the game phase. This is the initial phase of the game.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class Preload extends MapEditor {
    /**
     * Parameterised constructor to create an instance of <code>Preload</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    public Preload(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String prepareTournament(List<Map<String, List<String>>> p_arguments) throws VMException {
        TournamentEngine l_tournamentEngine = VirtualMachine.TOURNAMENT_ENGINE();
        for (Map<String, List<String>> l_argument : p_arguments) {
            if (l_argument.containsKey("M")) {
                List<String> l_listOfMapFiles = l_argument.get("M");
                l_tournamentEngine.setMapsList(l_listOfMapFiles);
                for (String l_mapFilePath : l_listOfMapFiles) {
                    LoadMapService l_loadMapService = new LoadMapService();

                    // Loading the map data will first remove the old at EditMapService
                    l_loadMapService.execute(Collections.singletonList(l_mapFilePath));

                    // Add MapEditorEngine to the list at TournamentEngine
                    l_tournamentEngine.addMapEditorEngine(VirtualMachine.getGameEngine().getMapEditorEngine());
                }
                VirtualMachine.getGameEngine().initialise();
            } else if (l_argument.containsKey("P")) {
                List<String> l_playerStrategies = l_argument.get("P");
                for (String l_playerStrategy : l_playerStrategies) {
                    try {
                        StrategyType l_strategyType = StrategyType.valueOf(l_playerStrategy.toUpperCase());
                        if (l_strategyType == StrategyType.HUMAN) {
                            throw new InvalidArgumentException("Strategy cannot be `human`!");
                        }
                        l_tournamentEngine.addPlayer(new Player(l_playerStrategy, l_strategyType));
                    } catch (IllegalArgumentException p_e) {
                        throw new InvalidArgumentException("Strategy type is invalid!");
                    }
                }
            } else if (l_argument.containsKey("G")) {
                try {
                    int l_numberOfGamesValue = Integer.parseInt(l_argument.get("G").get(0));
                    l_tournamentEngine.setNumberOfGames(l_numberOfGamesValue);
                } catch (IndexOutOfBoundsException p_e) {
                    throw new InvalidArgumentException("Number of games not specified!");
                } catch (NumberFormatException p_exception) {
                    throw new InvalidArgumentException("Number of games is in invalid format!");
                }
            } else if (l_argument.containsKey("D")) {
                try {
                    int l_maxNumberOfTurns = Integer.parseInt(l_argument.get("D").get(0));
                    l_tournamentEngine.setMaxNumberOfTurns(l_maxNumberOfTurns);
                } catch (IndexOutOfBoundsException p_e) {
                    throw new InvalidArgumentException("Number of maximum turns not specified!");
                } catch (NumberFormatException p_exception) {
                    throw new InvalidArgumentException("Number of maximum turns is in invalid format!");
                }
            }
        }
        // If no error occurred during preparing the tournament, start it.
        this.d_gameEngine.setGamePhase(new Reinforcement(d_gameEngine));
        l_tournamentEngine.onStart();
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editMap(List<String> p_arguments) throws VMException {
        EditMapService l_editMapService = new EditMapService();
        String l_returnValue = l_editMapService.execute(p_arguments);
        d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
        return l_returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateMap(List<String> p_arguments) throws VMException {
        ValidateMapService l_validateMapService = new ValidateMapService();
        return l_validateMapService.execute(p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveMap(List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() throws VMException {
        throw new VMException("Map hasn't been loaded.");
    }
}
