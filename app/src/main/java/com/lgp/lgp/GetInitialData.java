package com.lgp.lgp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardo on 05/03/2015.
 */
public class GetInitialData {

       public GetInitialData() {

       }

    /*
       public void postData(String value) {
            Log.d("test", "coisaaaa");

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.6/lgp/index.php");

            try {
                    List nameValuePairs = new ArrayList();
                    nameValuePairs.add(new BasicNameValuePair("data", value));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httppost);
                    Log.d("test", EntityUtils.toString(response.getEntity()));

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
       }
       */

    public void postData (String value) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection;

        try {
            URL url = new URL(value);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/teste.xml");

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } else Log.d("test", "Connection problem");


        } catch (Exception e) {
            Log.d("test", "exception");
        }
    }

}



