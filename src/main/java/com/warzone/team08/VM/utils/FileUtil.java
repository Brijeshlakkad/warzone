package com.warzone.team08.VM.utils;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This class provides common (logic) service of handling file operations.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class FileUtil {
    private static final String MAP_FILE_EXTENSION = "map";

    /**
     * Gets the value of the valid available file extension.
     *
     * @return Value of the file extension.
     */
    public static String getFileExtension() {
        return MAP_FILE_EXTENSION;
    }

    /**
     * Checks whether the given file name is valid or not.
     *
     * @param p_filePath Value of the path to file.
     * @return Value of File object for the file given with path.
     * @throws InvalidInputException     Throws if the file does not exist.
     * @throws ResourceNotFoundException Throws if file can not be created.
     */
    public static File retrieveFile(String p_filePath) throws ResourceNotFoundException, InvalidInputException {
        File l_file = new File(PathResolverUtil.resolveFilePath(p_filePath));
        String l_fileName = l_file.getName();
        try {
            l_file.createNewFile();
        } catch (Exception p_exception) {
            throw new ResourceNotFoundException("File can not be created");
        }

        int l_index = l_fileName.lastIndexOf('.');
        if (l_index > 0) {
            String l_extension = l_fileName.substring(l_index + 1);
            if (!l_extension.equalsIgnoreCase(FileUtil.getFileExtension())) {
                throw new InvalidInputException("File doesn't exist!");
            } else {
                return l_file;
            }
        } else {
            throw new InvalidInputException("File must have an extension!");
        }
    }

    /**
     * Checks if the file exists or not.
     *
     * @param p_fileObject Value of the file object.
     * @return True if the file exists; otherwise throws an exception.
     * @throws ResourceNotFoundException Throws if file not found.
     */
    private static boolean checkIfFileExists(File p_fileObject) throws ResourceNotFoundException {
        if (!p_fileObject.exists()) {
            throw new ResourceNotFoundException("File doesn't exist!");
        }
        return true;
    }

    /**
     * Copies the file from source path to the destination. This method replaces the existing file at destination path.
     *
     * @param p_source Source path to the file.
     * @param p_dest   Destination path to the file.
     */
    public static void copy(Path p_source, Path p_dest) {
        try {
            // Ignore if the file already exists.
            if (!(new File(p_dest.toUri().getPath()).exists())) {
                Files.copy(p_source, p_dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception l_ignored) {
            // Ignore the exception while copying.
        }
    }
}
