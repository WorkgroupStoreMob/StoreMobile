package com.codepath.storemobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.StoreAdapter;
import models.Store;

public class StoreActivity extends AppCompatActivity {

    private static final String TAG = "StoreActivity";

    protected List<Store> listStore;
    protected StoreAdapter adapter;

    private RecyclerView rvStore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_store );

        //Add Toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarItem3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo( R.drawable.logosmr);
        getSupportActionBar().setTitle("  List of Store");



        rvStore = findViewById( R.id.rvStore);

        // create data source
        listStore = new ArrayList<>( );
        // create adapter
        adapter = new StoreAdapter(this,  listStore );

        //set adapter on recycler view
        rvStore.setAdapter( adapter );

        //set layout manager in the recycler view
        rvStore.setLayoutManager( new LinearLayoutManager( this ) );

        queryPost();

    }

    protected void queryPost() {
        ParseQuery<Store> storeQuery = new ParseQuery<Store>( Store.class );
        storeQuery.include( Store.KEY_NAME );
        //postQuery.addDescendingOrder( Post.KEY_CREATED_AT );
        storeQuery.findInBackground( new FindCallback<Store>() {
            @Override
            public void done(List<Store> store, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query in StoreActivity");
                    e.printStackTrace();
                    return;
                }
                listStore.addAll( store );
                adapter.notifyDataSetChanged();

                for (int i = 0; i < store.size(); i++){
                    Log.d(TAG, "Post: " + store.get(i).getDescription() + "Username" + store.get( i )
                            .getName());
                }

            }
        } );
    }
}
