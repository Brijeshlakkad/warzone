package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * This class acts as a Observer to update the data in a log file.
 * @author MILESH
 * @author RUTVIK
 */
public class LogWriter extends Observer {
    private final String d_pathToFile;
    private final File d_targetFile;

    /**
     * Constructor to initialize the file name and object.
     * @throws ResourceNotFoundException Throws if not able to find the file.
     * @param p_observable Observable object for observer
     */
    public LogWriter(Observable p_observable) throws ResourceNotFoundException {
        super(p_observable);
        String l_timestamp = String.valueOf(new Date().getTime() % 100);
        String l_FileName = l_timestamp.concat("_log_file.txt");
        d_pathToFile = PathResolverUtil.resolveFilePath(l_FileName);
        d_targetFile = FileUtil.createFileIfNotExists(d_pathToFile);
    }

    /**
     * This method implements the update method of Observer interface to write data in file.
     * @param p_observable Observable object with whom this observer in attached.
     * @throws InvalidInputException Throws if file name is not valid
     */
    @Override
    public void update(Observable p_observable) throws InvalidInputException {
        String l_message = ((LogEntryBuffer) p_observable).getMessage();
        try (Writer l_writer = new FileWriter(d_targetFile, true)) {
            l_writer.append(l_message);
        } catch (IOException p_ioException) {
            throw new InvalidInputException("Error while saving the file!");
        }
    }
}