package com.warzone.team08.mapHandlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.warzone.team08.components.Continent;
import com.warzone.team08.components.Country;

/**
 * This class is to save the edited Map File.
 *
 * @author Rutwik
 * @version 1.0
 */

public class SaveMap {

    public List<Continent> d_continentList = new ArrayList<Continent>();
    public List<Country> d_countryList = new ArrayList<Country>();
    public TreeMap<Integer, List<Integer>> d_neighbourList = new TreeMap<Integer, List<Integer>>();


    /**
     * This method will take path of the file which user wants to save(edited file).
     *
     * @param p_filePath, it will take file path as input parameter.
     * @throws IOException, it will throw Input Output Exception which may occur during File creation or write.
     * @return, it will return a string acknowledging user that the file is save or not.
     */
    public String execute(String p_filePath) throws IOException {
        ValidateMap d_validateObj = new ValidateMap();
        String l_validationMsg = d_validateObj.execute();
        if (l_validationMsg.equals("pass")) {
            File l_fileObject = new File(p_filePath);

            String l_check = fileValidation(l_fileObject);
            boolean l_messageFromDataWrite = DataWriteIntoFile(l_check, l_fileObject);
            String l_messageToBeSend = (l_messageFromDataWrite == true) ? "File Saved." : l_check;
            return l_messageToBeSend;
        } else {
            return l_validationMsg;
        }
    }

    /**
     * This method will write all the data into the file and save it.
     *
     * @param p_checkFile,  it will state that the file is valid or not.
     * @param p_fileObject, it will take file path, to create and write into files.
     * @throws IOException, it will throw Input Output Exception which may occur during File Write.
     * @return, it will return boolean value, true if file is save else false.
     */
    public boolean DataWriteIntoFile(String p_checkFile, File p_fileObject) throws IOException {
        MapComponents l_map = MapComponents.getInstance();

        if (p_checkFile == "valid Name") {
            d_continentList = l_map.getContinentList();

            Writer l_writer = new FileWriter(p_fileObject);
            l_writer.write("[" + "Continents" + "]\n");

            for (Continent continents : d_continentList) {
                l_writer.write(continents.getContinentSerialNumber() + " " + continents.getContinentName() + " " + continents.getContinentControlValue() + "\n");
            }

            d_countryList = l_map.getCountryList();

            l_writer.write("\n[" + "Countries" + "]\n");

            for (Country country : d_countryList) {
                l_writer.write(country.d_countrySerialNumber + " " + country.getCountryName() + " " + country.getParentContinentSerialNumber() + "\n");
            }

            d_neighbourList = l_map.getCountryNeighbourMap();

            l_writer.write("\n[" + "borders" + "]\n");

            for (Map.Entry<Integer, List<Integer>> entry : d_neighbourList.entrySet()) {
                int key = entry.getKey();
                List<Integer> neighbour = entry.getValue();
                l_writer.write(key + " ");
                for (Integer a : neighbour) {
                    l_writer.write(a + " ");
                }
                l_writer.write("\n");
            }
            l_writer.close();
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method will check if the file is valid or not. It will check its extension and name.
     *
     * @param p_fileObject, the path of the file is passed into this function.
     * @return, String is return stating whether the file is valid or not.
     */
    public String fileValidation(File p_fileObject) {
        String l_fileName = p_fileObject.getName();
        int index = l_fileName.lastIndexOf('.');
        if (index > 0) {
            String l_extension = l_fileName.substring(index + 1);
            if (!l_extension.equalsIgnoreCase("map")) {
                return "Enter Valid Extension.";
            }
        } else {
            return "Add Extension";
        }
        return "valid Name";
    }

}
