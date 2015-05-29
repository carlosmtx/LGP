// Copyright 2007-2014 Metaio GmbH. All rights reserved.
package com.lgp.lgp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.metaio.sdk.ARELActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MainActivity extends Activity
{
    //Task that will extract all the assets
    private AssetsExtracter mTask;
    public ProgressBar bar;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Enable metaio SDK debug log messages based on build configuration
        //MetaioDebug.enableLogging(BuildConfig.DEBUG);




        // extract all the assets
        mTask = new AssetsExtracter();
        mTask.execute(0);
    }

    /**
     * This task extracts all the assets to an external or internal location
     * to make them accessible to metaio SDK
     */
    private class AssetsExtracter extends AsyncTask<Integer, Integer, Boolean>
    {
        private static final String URL = "http://178.62.167.215/channel/canal1/current";
        private static final String SETTINGS_NAME = "lgp";

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected Boolean doInBackground(Integer... params)
        {
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressExtraction);
            final TextView text = (TextView) findViewById(R.id.textViewInfo);
            text.setTextColor(Color.WHITE);

            //create new folder
            File folder = new File(Environment.getExternalStorageDirectory() + "/ar_banking");

            folder.mkdir();

            boolean downloadSuccess = downloadScene(bar, text);
            if(downloadSuccess)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("Extracting...");
                    }
                });

                unpackZip(bar);

                //Delete zip after unpacking
                File file = new File(Environment.getExternalStorageDirectory() + "/ar_banking/current.zip");
                file.delete();
            }

            return true;


        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if (result)
            {
                // Start authentication screen
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);

            }
            else
            {
                // Show a toast with an error message
                Toast toast = Toast.makeText(getApplicationContext(), "Error extracting application assets!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }

            finish();
        }


        // Utility Methods
        private boolean downloadScene(ProgressBar bar, final TextView text)
        {
            SharedPreferences settings = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);

            String currentID = settings.getString("id", null);
            String id = null;

            //Get metadata
            HttpResponse metadata = getData(URL);

            try
            {
                String metadataString = EntityUtils.toString(metadata.getEntity());
                JSONObject json = new JSONObject(metadataString);
                id = (String) json.get("id");
            }
            catch(Exception e) { e.printStackTrace(); }

            if(id == null || id.equals(currentID))
                return false;

            //If data is recent, get data
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Downloading...");
                }
            });

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("as", "zip"));
            HttpResponse response = getData(URL, parameters);

            if(response == null)
                return false;

            try
            {
                InputStream is = response.getEntity().getContent();
                long total = response.getEntity().getContentLength();

                FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/ar_banking", "current.zip"));

                int read;
                long completed = 0;

                byte[] buffer = new byte[1024];

                while( (read = is.read(buffer)) > 0)
                {
                    completed += read;
                    updateBar(bar, completed, total);
                    fos.write(buffer, 0, read);
                }

                fos.close();
                is.close();
            }
            catch (Exception e) { e.printStackTrace(); }

            //After downloading new file, update current id
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("id", id);
            editor.commit();

            return true;
        }


        private HttpResponse getData(String url)
        {
            return getData(url, null);
        }

        private HttpResponse getData(String url, List<NameValuePair> parameters)
        {
            //Create http client and set parameters
            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse response = null;
            try
            {
                //Set parameters and execute request
                if(parameters != null)
                    url += "?" + URLEncodedUtils.format(parameters, "utf-8");

                URI uri = new URI(url);
                HttpUriRequest request = new HttpGet(uri);
                response = httpClient.execute(request);
            }
            catch (Exception e) { e.printStackTrace(); }

            return response;
        }

        // Unpacks a zip file
        private boolean unpackZip(ProgressBar bar)
        {
            String destPath = Environment.getExternalStorageDirectory() + "/ar_banking";
            String zipPath = Environment.getExternalStorageDirectory() + "/ar_banking/current.zip";

            byte[] buffer = new byte[1024];

            try {
                // Open the zip file
                ZipFile zipFile = new ZipFile(zipPath);
                int totalEntries = zipFile.size();
                int processedEntries = 0;
                Enumeration<?> enu = zipFile.entries();
                while (enu.hasMoreElements()) {

                    processedEntries++;
                    ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                    String name = zipEntry.getName();

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

                    updateBar(bar, processedEntries, totalEntries);
                }
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private void updateBar(ProgressBar bar, long completed, long total)
    {
        int percentage = (int) (((float) completed / total) * 100f);
        bar.setProgress(percentage);
    }
}

