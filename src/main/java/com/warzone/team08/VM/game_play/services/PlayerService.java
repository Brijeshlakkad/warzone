package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.repositories.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class handles `gameplayer` user command to add and/or remove game player from the game.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class PlayerService {

    /**
     * Engine to store and retrieve map data.
     */
    private final GamePlayEngine d_gamePlayEngine;

    /**
     * Player repostiory.
     */
    private PlayerRepository d_playerRepository;

    public PlayerService() {
        d_gamePlayEngine = GamePlayEngine.getInstance();
    }

    /**
     * Adds the player to the list stored at Game Play engine.
     *
     * @param p_playerName Value of the player name.
     */
    public void add(String p_playerName) throws InvalidInputException {
        try {
            Player l_player = new Player();
            l_player.setName(p_playerName);
            d_gamePlayEngine.addPlayer(l_player);
        } catch (Exception e) {
            throw new InvalidInputException("Player name is not valid");
        }
    }

    /**
     * Removes the player from the list using the name.
     *
     * @param p_playerName Value of the continent name.
     */
    public void remove(String p_playerName) throws EntityNotFoundException {
        // We can check if the continent exists before filtering?
        // Filters the continent list using the continent name
        Player l_player = d_playerRepository.findByPlayerName(p_playerName);
        List<Player> l_filteredPlayerList = d_gamePlayEngine.getPlayerList().stream()
                .filter(p_l_player -> !l_player.equals(p_l_player)
                ).collect(Collectors.toList());
        d_gamePlayEngine.setPlayerList(l_filteredPlayerList);
    }
}
