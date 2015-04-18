package com.lgp.lgp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ricardo on 05/03/2015.
 */
// class that will handle the asynchronous request
public class MakeRequest extends AsyncTask<String, Integer, Double> {

    @Override
    protected Double doInBackground(String... params) {

       new ManageData().startDownload();
       return null;
    }

}
