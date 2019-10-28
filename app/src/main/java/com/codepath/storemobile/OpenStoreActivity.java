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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


import org.parceler.Parcels;


import java.util.List;

import models.Store;

public class OpenStoreActivity extends AppCompatActivity {

    private Button btnLoginOpenStore;
    private Button btnCreateStore;
    private EditText etStoreName;
    private EditText etStorePassword;

    TextView tvBusinessName;
    ImageView ivLogoBusiness;

    ProgressBar pbLoading;
    boolean login = false;


    private static final String TAG = "OpenStoreActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_open_store );

        btnLoginOpenStore = findViewById( R.id.btn_login_open_store );
        btnCreateStore = findViewById(R.id.btn_sign_up_open_store);
        etStoreName = findViewById( R.id.et_name_open_store );
        etStorePassword = findViewById( R.id.et_password_open_store );

        tvBusinessName = findViewById( R.id.tvBusinessName );
        ivLogoBusiness = findViewById( R.id.ivLogoBusiness );

        pbLoading = findViewById( R.id.pbLoading );



        btnLoginOpenStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);
                ParseQuery<Store> query = new ParseQuery<Store>( Store.class );
                query.include( Store.KEY_NAME );
                query.include( Store.KEY_IMAGE );
                query.findInBackground( new FindCallback<Store>() {
                    String storeName = etStoreName.getText().toString();
                    String storePassword = etStorePassword.getText().toString();

                    @Override
                    public void done(List<Store> objects, ParseException e) {

                        if (e != null) {
                            Log.e( TAG, "Error with query Open Store: "+e.getMessage() );
                            e.printStackTrace();
                            pbLoading.setVisibility(View.GONE);
                            return;
                        }

                        for (int i = 0; i < objects.size(); i++) {
                            if (storeName.equals(objects.get(i).getName()) && storePassword.equals(objects.get(i).getKeyPassword()) ){
                                login = true;
                                Intent intent = new Intent( OpenStoreActivity.this, ManageStoreActivity.class );
                                //intent.putExtra("StoreData", Parcels.wrap(objects));
                                intent.putExtra("StoreData", storeName);
                                startActivity( intent );
                                pbLoading.setVisibility(View.GONE);
                                break;
                            }

//                            if ((i + 1 == objects.size()) && storeName.equals(objects.get(i).getName()) || storePassword.equals(objects.get(i).getKeyPassword())){
//                                AlertDialog.Builder builder = new AlertDialog.Builder( OpenStoreActivity.this);
//                                pbLoading.setVisibility(View.GONE);
//                                builder.setTitle( "Store Login Error" );
//                                builder.setMessage( "username or password incorrect");
//                                builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                        pbLoading.setVisibility(View.GONE);
//                                    }
//                                } );
//                                builder.show();
//                                pbLoading.setVisibility(View.GONE);
//                                break;
//                            }

                        }
                        if (login == false){
                            AlertDialog.Builder builder = new AlertDialog.Builder( OpenStoreActivity.this);
                            pbLoading.setVisibility(View.GONE);
                            builder.setTitle( "Store Login Error" );
                            builder.setMessage( "username or password incorrect");
                            builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    pbLoading.setVisibility(View.GONE);
                                }
                            } );
                            builder.show();
                            pbLoading.setVisibility(View.GONE);
                        }
                        login = false;

                    }

                } );
              // tvBusinessName.setText( storeName );

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
