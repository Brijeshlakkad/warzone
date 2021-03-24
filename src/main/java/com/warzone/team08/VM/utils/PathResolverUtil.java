package com.warzone.team08.VM.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class resolves the path using the user data directory path.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class PathResolverUtil {
    /**
     * Singleton instance of the class.
     */
    private static PathResolverUtil d_Instance;

    /**
     * Represents the path to the user data directory.
     */
    private final Path USER_DATA_DIRECTORY_PATH;

    /**
     * Folder from or to save/load user files.
     */
    private final String USER_DATA_DIRECTORY = "War Zone Team08";

    /**
     * Directory where logs will be saved.
     */
    private final String USER_LOG_DIRECTORY = "logs";

    /**
     * Instance can not be created outside the class. (private)
     */
    private PathResolverUtil() {
        USER_DATA_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Downloads", USER_DATA_DIRECTORY);
    }

    /**
     * Gets the single instance of the class.
     *
     * @return Value of the instance.
     */
    public static PathResolverUtil getInstance() {
        if (d_Instance == null) {
            d_Instance = new PathResolverUtil();
        }
        return d_Instance;
    }

    /**
     * Gets the string value of the user data directory path.
     *
     * @return Value of the path.
     */
    public static Path getUserDataDirectoryPath() {
        return PathResolverUtil.getInstance().USER_DATA_DIRECTORY_PATH;
    }

    /**
     * Gets the string value of the log directory.
     *
     * @return Value of the path.
     */
    public static String getLogDirectoryName() {
        return PathResolverUtil.getInstance().USER_LOG_DIRECTORY;
    }

    /**
     * Uses the user data directory path to resolve absolute the path to the file.
     *
     * @param p_filePath Name of the file.
     * @return Value of the absolute path to the file.
     */
    public static String resolveFilePath(String p_filePath) {
        return Paths.get(getUserDataDirectoryPath().toString(), p_filePath).toString();
    }

    /**
     * Uses the user data directory and log folder paths to resolve absolute the path to the file.
     *
     * @param p_filePath Name of the file.
     * @return Value of the absolute path to the file.
     */
    public static String resolveLogPath(String p_filePath) {
        return Paths.get(getUserDataDirectoryPath().toString(), getLogDirectoryName(), p_filePath).toString();
    }
}
