package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class LogWriter implements Observer{
    @Override
    public void update(Observable p_observable) throws ResourceNotFoundException, InvalidInputException {
        File l_file= new File("C:\\Users\\MILESH\\Downloads\\War Zone Team08\\log.txt");
        //File l_file= FileUtil.retrieveFile(PathResolverUtil.resolveFilePath("logFile.txt"));
        String l_message=((LogEntryBuffer) p_observable).getMessage();
        try (Writer l_writer = new FileWriter(l_file,true)) {
            l_writer.write(l_message);
        } catch (IOException p_e) {
            p_e.printStackTrace();
        }
    }
}
