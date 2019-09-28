package com.codepath.storemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseQuery;

import models.Store;

public class OpenStoreActivity extends AppCompatActivity {

    private Button btnLoginOpenStore;
    private Button btnCreateStore;
    private EditText etStoreName;
    private EditText etStorePassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_open_store );

        btnLoginOpenStore = findViewById( R.id.btn_login_open_store );
        btnCreateStore = findViewById(R.id.btn_sign_up_open_store);
        etStoreName = findViewById( R.id.et_name_open_store );
        etStorePassword = findViewById( R.id.et_password_open_store );

        btnLoginOpenStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storeName = etStoreName.getText().toString();
                String storePassword = etStorePassword.getText().toString();

                ParseQuery<Store> query = new ParseQuery<Store>( "Store" );

                Store store = new Store();

                if (storeName == store.getName() || storePassword == store.getKeyPassword() ){
                    Intent intent = new Intent( OpenStoreActivity.this, ManageStoreActivity.class );
                    startActivity( intent );
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder( OpenStoreActivity.this);
                    builder.setTitle( "Store Login Error" );
                    builder.setMessage( "username or password incorrect");
                    builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    } );
                    builder.show();
                }

            }
        } );

        btnCreateStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( OpenStoreActivity.this, CreateStore.class );
                startActivity( intent );
            }
        } );
    }
}
