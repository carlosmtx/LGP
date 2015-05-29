package com.lgp.lgp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.metaio.sdk.ARELActivity;
import com.metaio.sdk.MetaioDebug;
import java.io.File;


public class MenuActivity extends ActionBarActivity {

    private boolean backPressed;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressed = false;

        person = Person.getInstance();

        updateValues();




        findViewById(R.id.ar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = Environment.getExternalStorageDirectory();
                final File arelConfigFilePath = new File(dir.getAbsolutePath() + "/ar_banking/index.xml");
                MetaioDebug.log("AREL config to be passed to intent: " + arelConfigFilePath.getPath());
                Intent intent = new Intent(getApplicationContext(), ARELViewActivity.class);
                intent.putExtra(getPackageName()+ ARELActivity.INTENT_EXTRA_AREL_SCENE, arelConfigFilePath);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateValues();
    }

    public void updateValues()
    {
        TextView name = (TextView) findViewById(R.id.name_text);
        name.setText(person.getName());

        TextView saldo = (TextView) findViewById(R.id.Main_account_balance_value);
        saldo.setText(Integer.toString(person.getSaldo()));
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
