package com.lgp.lgp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.metaio.sdk.ARELActivity;
import com.metaio.sdk.MetaioDebug;
import java.io.File;


public class MenuActivity extends ActionBarActivity {

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressed = false;


        findViewById(R.id.ar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = Environment.getExternalStorageDirectory();
                final File arelConfigFilePath = new File(dir.getAbsolutePath() + "/lgp/index.xml");
                MetaioDebug.log("AREL config to be passed to intent: " + arelConfigFilePath.getPath());
                Intent intent = new Intent(getApplicationContext(), ARELViewActivity.class);
                intent.putExtra(getPackageName()+ ARELActivity.INTENT_EXTRA_AREL_SCENE, arelConfigFilePath);

                startActivity(intent);
            }
        });

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
