package com.codepath.storemobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnOpenStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( "Bienvenue" );
        actionBar.getTitle().toString();

        btnLogin = findViewById( R.id.btnLogin );
        btnOpenStore = findViewById( R.id.btnOpenStoreWelcome );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, LoginActivity.class );
                startActivity( i );
            }
        } );


        btnOpenStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, OpenStoreActivity.class );
                startActivity( intent );
            }
        } );

    }
}
