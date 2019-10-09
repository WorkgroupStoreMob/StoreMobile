package com.codepath.storemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import adapters.StoreAdapter;
import models.Store;

public class OpenStoreActivity extends AppCompatActivity {

    private Button btnLoginOpenStore;
    private Button btnCreateStore;
    private EditText etStoreName;
    private EditText etStorePassword;

    private StoreAdapter storeAdapter;
    private List<Store> lStore;

    private static final String TAG = "OpenStoreActivity";



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


                ParseQuery<Store> query = new ParseQuery<Store>( Store.class );
                query.findInBackground( new FindCallback<Store>() {
                    String storeName = etStoreName.getText().toString();
                    String storePassword = etStorePassword.getText().toString();

                    @Override
                    public void done(List<Store> objects, ParseException e) {

                        if (e != null) {
                            Log.e( TAG, "Error with query" );
                            e.printStackTrace();
                            return;
                        }

                        for (int i = 0; i < objects.size(); i++) {

                            if (storeName.equals(objects.get(i).getName()) || storePassword.equals(objects.get(i).getKeyPassword()) ){
                                Intent intent = new Intent( OpenStoreActivity.this, ManageStoreActivity.class );
                                startActivity( intent );
                                break;
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
                                break;
                            }

                        }

                    }
                } );

                Store store = new Store();

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
