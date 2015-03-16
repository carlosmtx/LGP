package com.lgp.lgp;


/**
 * Created by ricardo on 05/03/2015.
 */
public class ManageData {

    private final String API_URL = "http://178.62.167.215/channel/list/files";
    private final String FOLDER_URL = "/storage/emulated/0/lgp";

    public ManageData() {

    }

    // This method will make a request to API and return the files ids
    public void syncData () {

    }

    // Checks if the folder is already created(true-there is folder, false-there is no folder)
    private boolean checkFolder() {

    }

    // Returns the file's names that need to be removed from device
    private String[] filesToRemove() {

    }

    // Returns file's id that need to be removed
    private String[] filesToDownload() {

    }

    // Removes a file from device's memory
    private void removeFile(String location) {

    }

    // Saves file on default directory
    private void saveFile(String name) {

    }

    // Returns a list of names of device's files
    private String[] listDeviceFiles() {

    }

}



