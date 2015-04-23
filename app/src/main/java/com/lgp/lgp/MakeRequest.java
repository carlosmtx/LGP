package com.lgp.lgp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ricardo on 05/03/2015.
 */
// class that will handle the asynchronous request
public class MakeRequest extends AsyncTask<Void, Void, Void> {

    private String URL;
    private MainActivity activity;

    public MakeRequest(String downloadURL, MainActivity activity) {
        this.URL = downloadURL;
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        new ManageData(activity).startDownload();
        return null;
    }

}
