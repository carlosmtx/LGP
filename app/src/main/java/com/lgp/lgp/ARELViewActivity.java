// Copyright 2007-2013 Metaio GmbH. All rights reserved.
package com.lgp.lgp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.metaio.sdk.ARELActivity;
import com.metaio.sdk.MetaioDebug;

public class ARELViewActivity extends ARELActivity
{

    @Override
    protected void onStart() {
        super.onStart();

        mWebView.addJavascriptInterface(new Person() , "person");

    }

    @Override
    protected int getGUILayout()
    {
        // Attaching layout to the activity
        return R.layout.template;
    }

    private class Person {
        public int saldo = 1000;

        @JavascriptInterface
        public String getNome(){
            return "Joao";
        }

        @JavascriptInterface
        public double getSaldo(){
            return saldo;
        }

        @JavascriptInterface
        public void changeSaldo(int s)
        {
            MetaioDebug.log(Log.ERROR, "Javascript saldoRecebido: " + s);
            saldo = s;

            MetaioDebug.log(Log.ERROR, "Javascript Saldo Alterado: " + saldo);
        }

        @JavascriptInterface
        public void goToLink(String link)
        {
            Intent intent = new Intent(getApplicationContext(), browser_activity.class);
            startActivity(intent);
        }


    }

    public void onButtonClick(View v)
    {
        finish();
    }

}
