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
    private static PathResolverUtil d_instance;

    /**
     * Represents the path to the user data directory.
     */
    private Path d_user_data_directory_path;

    /**
     * Folder from or to save/load user files.
     */
    private String d_user_data_directory = "War Zone Team08";

    /**
     * Instance can not be created outside the class. (private)
     */
    private PathResolverUtil() {
        d_user_data_directory_path = Paths.get(System.getProperty("user.home"), "Downloads", d_user_data_directory);
    }

    /**
     * Gets the single instance of the class.
     *
     * @return Value of the instance.
     */
    public static PathResolverUtil getInstance() {
        if (d_instance == null) {
            d_instance = new PathResolverUtil();
        }
        return d_instance;
    }

    /**
     * Gets the string value of the user data directory path.
     *
     * @return Value of the path.
     */
    public static Path getUserDataDirectoryPath() {
        return PathResolverUtil.getInstance().d_user_data_directory_path;
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
}
