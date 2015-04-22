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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
    // Starts the zip download
    public void startDownload() {
        new DownloadFileAsync().execute(GET_ZIP_URL);
    }

    // Unpacks a zip file
    public boolean unpackZip(String destPath, String zipPath) {
        byte[] buffer = new byte[1024];

        Log.i("pato", zipPath);

        try {
            // Open the zip file
            ZipFile zipFile = new ZipFile(zipPath);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                Log.i("names", destPath + File.separator + name);

                // Do we need to create a directory ?
                File file = new File(destPath + File.separator + name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();

            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

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

        @Override
        protected void onPostExecute(String s) {
            if(new ManageData().unpackZip(Environment.getExternalStorageDirectory() + "/lgp", Environment.getExternalStorageDirectory() + "/lgp/current.zip"))
                Log.i("teste", "Ficheiro extraido com sucesso");
            else Log.i("teste", "Erro na extração do ficheiro");
        }
    }

}





