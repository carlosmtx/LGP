package com.lgp.lgp;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MenuActivity extends ActionBarActivity {

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressed = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(backPressed == true) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Pressione anterior mais uma vez para sair!", Toast.LENGTH_SHORT).show();
            backPressed = true;
        }

    }
}
