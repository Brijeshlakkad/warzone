package com.warzone.team08;

import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.CLI.constants.enums.states.GameState;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The main class of the War Zone Team08
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class Application {
    /**
     * False if user is interacting, meaning user is playing the game; true otherwise
     */
    private static volatile boolean d_isRunning = true;

    /**
     * Keeps track of the game state
     */
    private static GameState d_gameState = GameState.MAP_EDITOR;

    /**
     * Connects interface with method APIs; An environment for the player to store the information.
     */
    private static VirtualMachine d_virtualMachine;

    /**
     * Gets false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @return Value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static boolean isRunning() {
        return d_isRunning;
    }

    /**
     * Sets new false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @param d_isRunning New value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static void setIsRunning(boolean d_isRunning) {
        d_isRunning = d_isRunning;
    }

    /**
     * Sets new Keeps track of the game state.
     *
     * @param p_gameState New value of Keeps track of the game state.
     */
    public static void setGameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public static GameState getGameState() {
        return d_gameState;
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        setIsRunning(true);
        // Sets the environment for game.
        onStartUp();

        // Starts the runtime engine (GameEngine) for the game.
        d_virtualMachine = new VirtualMachine();

        // Creates interface for user interaction.
        // Just a local variable as the instance is not being used/shared with any other class.
        // An instance of the virtual machine can be passed to the user interface?
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.d_thread.start();

        // Wait till the game is over.
        l_commandLineInterface.d_thread.join();
    }

    /**
     * Gets the instance of virtual machine.
     *
     * @return Value of virtual machine.
     */
    public static VirtualMachine VIRTUAL_MACHINE() {
        return d_virtualMachine;
    }

    /**
     * Will be called when game starts.
     */
    public static void onStartUp() {
        try {
            restoreMapFiles();
        } catch (IOException | URISyntaxException l_ignored) {

        }
    }

    /**
     * Restores the map files to user data directory location. Downloads the files to user location.
     *
     * @throws IOException        Throws if the directory can not be created. (because of permissions?)
     * @throws URISyntaxException Throws if the directory can not be found.
     */
    public static void restoreMapFiles() throws IOException, URISyntaxException {
        // Download the files at user data directory.
        Path l_sourceMapFiles = Paths.get(Objects.requireNonNull(Application.class.getClassLoader().getResource("map_files")).toURI());
        Path l_userDataDirectory = PathResolverUtil.getUserDataDirectoryPath();
        Files.walk(l_sourceMapFiles)
                .forEach(source -> FileUtil.copy(source, l_userDataDirectory.resolve(l_sourceMapFiles.relativize(source))));
    }

    /**
     * To exist from the game
     */
    public static void exit() {
        Application.setIsRunning(false);
    }
}
