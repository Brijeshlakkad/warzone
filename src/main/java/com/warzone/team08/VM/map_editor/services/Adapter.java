package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.IOException;
import java.util.List;

/**
 * This class implements the behaviour of Adapter class.
 *
 * @author CHARIT
 */
public class Adapter extends EditMapService {

    private EditConquestMapService d_editConquestMapService;

    /**
     * Constructor to initialize the class object.
     *
     * @param p_editConquestMapService Object of the EditConquestMapService.java class.
     */
    public Adapter(EditConquestMapService p_editConquestMapService) {
        d_editConquestMapService = p_editConquestMapService;
    }

    /**
     * Overrides the <code>execute()</code> method of the EditMapService.java(Target class in adapter pattern).
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @return Value of string acknowledging user that the file is loaded or not.
     * @throws VMException Throws if error occurs in VM Engine operation.
     * @throws IOException Throws if error occurs during IO Operation.
     */
    @Override
    public String execute(List<String> p_commandValues) throws VMException, IOException {
        return d_editConquestMapService.execute(p_commandValues);
    }

}
