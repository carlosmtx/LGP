package com.lgp.lgp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lgp.lgp.MenuActivity;
import com.lgp.lgp.R;


public class browser_activity extends Activity {

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        // Set login button listener
        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username
                final EditText usernameText = (EditText) findViewById(R.id.editText_user);
                final EditText passwordText = (EditText) findViewById(R.id.editText_password);

                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if(username.equals("admin") && password.equals("123")) {

                    // Launch menu intent
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    // Show toast informing that credentials are wrong
                    Toast.makeText(getApplicationContext(), "Combinacao utilizador/pin esta errada!", Toast.LENGTH_SHORT).show();

                }
            }
        });
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
