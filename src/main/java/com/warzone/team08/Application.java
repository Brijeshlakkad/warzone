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
     * Command-line user interface; Responsible for taking input from a user.
     */
    private static CommandLineInterface d_commandLineInterface;

    /**
     * Connects interface with method APIs; An environment for the player to store the information.
     */
    private static VirtualMachine d_virtualMachine;

    public Application() {
        // Starts the runtime engine (GameEngine) for the game.
        d_virtualMachine = new VirtualMachine();

        // Creates interface for user interaction.
        // Just a local variable as the instance is not being used/shared with any other class.
        // An instance of the virtual machine can be passed to the user interface?
        d_commandLineInterface = new CommandLineInterface();
    }

    public static void main(String[] args) throws InterruptedException {
        Application l_application = new Application();

        // Sets the environment for game.
        l_application.handleApplicationStartup();

        // Starts the CLI
        l_application.handleCLIStartUp();
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
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public static GameState getGameState() {
        return d_virtualMachine.getGameState();
    }

    /**
     * Will be called when game starts.
     */
    public void handleApplicationStartup() {
        setIsRunning(true);
        try {
            restoreMapFiles();
        } catch (IOException | URISyntaxException l_ignored) {
            // ignore exceptions occurred during storing map files to user data directory.
        }
        VIRTUAL_MACHINE().setGameState(GameState.MAP_EDITOR);
    }

    /**
     * @throws InterruptedException If CLI interrupted by another thread.
     */
    public void handleCLIStartUp() throws InterruptedException {
        d_commandLineInterface.d_thread.start();
        // Wait till the game is over.
        d_commandLineInterface.d_thread.join();
    }

    /**
     * Restores the map files to user data directory location. Downloads the files to user location.
     *
     * @throws IOException        Throws if the directory can not be created. (because of permissions?)
     * @throws URISyntaxException Throws if the directory can not be found.
     */
    public void restoreMapFiles() throws IOException, URISyntaxException {
        // Download the files at user data directory.
        Path l_sourceMapFiles = Paths.get(Objects.requireNonNull(Application.class.getClassLoader().getResource("map_files")).toURI());
        Path l_userDataDirectory = PathResolverUtil.getUserDataDirectoryPath();
        Files.walk(l_sourceMapFiles)
                .forEach(source -> FileUtil.copy(source, l_userDataDirectory.resolve(l_sourceMapFiles.relativize(source))));
    }

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
     * To exist from the game
     */
    public static void exit() {
        Application.setIsRunning(false);
    }
}
