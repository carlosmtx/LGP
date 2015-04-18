package com.lgp.lgp;


import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardo on 05/03/2015.
 */
public class ManageData {

    private final String GET_ZIP_URL = "http://178.62.167.215/channel/canal1/current";
    private final String SAVEDIR = Environment.getExternalStorageDirectory() + "/lgp";

    private final String CHANNEL_ID = "55022049a8ff87c7528b4568";

    public ManageData() {

    }

    // This method will make a request to API and return the files ids
    public void syncData () {

    }

    // Checks if the folder is already created(true-there is folder, false-there is no folder)
    private boolean checkFolder() {
        return false;
    }

    // Returns the file's names that need to be removed from device
    private String[] filesToRemove() {
        return null;
    }

    // Returns file's id that need to be removed
    private String[] filesToDownload() {
        return null;
    }

    // Removes a file from device's memory
    private void removeFile(String location) {
    }

    // Saves file on default directory
    private void saveFile(String name) {

    }

    // Starts the zip download
    public void startDownload() {
        new DownloadFileAsync().execute(GET_ZIP_URL);
    }

    /*
    public ArrayList<String> listServerFiles() {
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("channel", CHANNEL_ID));
        try {
            URI uri = new URI(LIST_FILES_URL + "?" + URLEncodedUtils.format(params, "utf-8"));
            HttpUriRequest request = new HttpGet(uri);
            HttpResponse response = httpClient.execute(request);
            Log.d("teste", EntityUtils.toString(response.getEntity()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Returns a list of names of device's files
    public ArrayList<File> listDeviceFiles() {
        File srcDir = new File(Environment.getExternalStorageDirectory() + "/lgp");
        if(srcDir.isDirectory()) {
            ArrayList<File> filteredFiles = new ArrayList<>();
            File[] allFiles = srcDir.listFiles();
            for(File file:allFiles) {
                if(!file.isDirectory())
                    filteredFiles.add(file);
            }
            return filteredFiles;
        }

        return null;
    }
    */

    // Downloads the zip file asynchronously
    private class DownloadFileAsync extends AsyncTask<String, String, String> {


        @Override
        // Download the zip file
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("as", "zip"));
            try {
                URI uri = new URI(params[0] + "?" + URLEncodedUtils.format(parameters, "utf-8"));
                HttpUriRequest request = new HttpGet(uri);
                HttpResponse response = httpClient.execute(request);
                InputStream is = response.getEntity().getContent();
                FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/lgp", "current.zip"));

                int read = 0;
                byte[] buffer = new byte[32768];
                while( (read = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, read);
                }
                fos.close();
                is.close();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

}





