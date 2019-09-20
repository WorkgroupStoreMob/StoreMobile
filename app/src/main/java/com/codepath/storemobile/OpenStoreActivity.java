package com.codepath.storemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenStoreActivity extends AppCompatActivity {

    private Button btnLoginOpenStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_open_store );

        btnLoginOpenStore = findViewById( R.id.btn_login_open_store );

        btnLoginOpenStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( OpenStoreActivity.this, ManageStoreActivity.class );
                startActivity( intent );
            }
        } );
    }
}
