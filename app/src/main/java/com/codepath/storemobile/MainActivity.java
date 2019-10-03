package com.codepath.storemobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.StoreAdapter;
import adapters.StoreWelcomeAdapter;
import models.Store;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnOpenStore;

    StoreWelcomeAdapter adapterWelcome;
    List<Store> lstore;

    RecyclerView rvStoreWelcome;

    private static final String TAG = "MainActivity";

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


        rvStoreWelcome = findViewById( R.id.rvStore_welcome);



        // create data source
        lstore = new ArrayList<>( );
        // create adapter
        adapterWelcome = new StoreWelcomeAdapter(this,  lstore );

        //set adapter on recycler view
        rvStoreWelcome.setAdapter( adapterWelcome );

        //set layout manager in the recycler view
        rvStoreWelcome.setLayoutManager( new LinearLayoutManager( this ) );

        queryStore();

    }

    protected void queryStore() {
        ParseQuery<Store> storeQuery = new ParseQuery<Store>( Store.class );
        storeQuery.include( Store.KEY_NAME );
        //postQuery.addDescendingOrder( Post.KEY_CREATED_AT );
        storeQuery.findInBackground( new FindCallback<Store>() {
            @Override
            public void done(List<Store> store, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                lstore.addAll( store );
                adapterWelcome.notifyDataSetChanged();

                for (int i = 0; i < store.size(); i++){
                    Log.d(TAG, "Post: " + store.get(i).getDescription() + "Username" + store.get( i )
                            .getName());
                }

            }
        } );
    }

    }

