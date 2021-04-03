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

    public Adapter(EditConquestMapService p_editConquestMapService)
    {
        d_editConquestMapService = p_editConquestMapService;
    }

    @Override
    public String execute(List<String> p_commandValues) throws VMException, IOException {
        return d_editConquestMapService.execute(p_commandValues);
    }

}
