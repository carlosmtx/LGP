// Copyright 2007-2013 Metaio GmbH. All rights reserved.
package com.lgp.lgp;

import android.view.View;

import com.metaio.sdk.ARELActivity;

public class ARELViewActivity extends ARELActivity
{

    @Override
    protected int getGUILayout()
    {
        //new MakeRequest().execute("http://192.168.1.6/lgp/teste.xml");
        // Attaching layout to the activity
        return R.layout.template;
    }

    public void onButtonClick(View v)
    {
        finish();
    }

}
