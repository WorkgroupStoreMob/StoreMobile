package com.codepath.storemobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.ItemWelcomeAdapter;
import adapters.StoreWelcomeAdapter;
import models.Items;
import models.Store;

public class ItemWelcomeActivity extends AppCompatActivity {

    private Button btnStoreWelcomeItems;
    private ItemWelcomeAdapter adapterItemsWelcome;
    private List<Items> litems;
    RecyclerView rvItems;


    private static final String TAG = "ItemWelcomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_welcome );

        btnStoreWelcomeItems = findViewById( R.id.btn_open_store1 );
        rvItems = findViewById( R.id.rvItems );


        // create data source
        litems = new ArrayList<>( );
        // create adapter
        adapterItemsWelcome = new ItemWelcomeAdapter(this,  litems );

        //set adapter on recycler view
        rvItems.setAdapter( adapterItemsWelcome );

        //set layout manager in the recycler view
        rvItems.setLayoutManager( new LinearLayoutManager( this ) );

        queryItems();
    }

    private void queryItems() {

        ParseQuery<Items> itemsQuery = new ParseQuery<Items>( Items.class );
        itemsQuery.whereEqualTo("store_name", getIntent().getStringExtra("nameStore"));
        itemsQuery.include( Items.KEY_CATEGORY );
        itemsQuery.include( Items.KEY_PRICE );
        itemsQuery.findInBackground( new FindCallback<Items>() {
            @Override
            public void done(List<Items> items, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                litems.addAll( items );
               adapterItemsWelcome.notifyDataSetChanged();

                for (int i = 0; i < items.size(); i++){
                    Log.d(TAG, "Post: " + items.get(i).getCategory() + "Username" + items.get( i )
                            .getPrice());
                }

            }
        } );
    }
}
