package com.lgp.lgp;

import android.os.AsyncTask;

/**
 * Created by ricardo on 05/03/2015.
 */
// class that will handle the asynchronous request
public class MakeRequest extends AsyncTask<String, Integer, Double> {

    /*
    @Override
    protected Double doInBackground(String... params) {
        // TODO Auto-generated method stub
        new GetInitialData().postData(params[0]);
        return null;
    }
    */

    @Override
    protected Double doInBackground(String... params) {

        new GetInitialData().postData(params[0]);
        return null;
    }

}
