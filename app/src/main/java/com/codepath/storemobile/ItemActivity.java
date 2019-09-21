package com.codepath.storemobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.ItemAdapter;
import models.Items;

public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    protected List<Items> listItems;
    protected ItemAdapter adapter;

    private RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item );

        rvItems = findViewById( R.id.rvItems);
        queryPost();

        // create data source
        listItems = new ArrayList<>( );
        // create adapter
        adapter = new ItemAdapter(this,  listItems );

        //set adapter on recycler view
        rvItems.setAdapter( adapter );

        //set layout manager in the recycler view
        rvItems.setLayoutManager( new LinearLayoutManager( this ) );

    }

    protected void queryPost() {
        ParseQuery<Items> itemQuery = new ParseQuery<Items>( Items.class );
        itemQuery.include( Items.KEY_DESCRIPTION );
        //postQuery.addDescendingOrder( Post.KEY_CREATED_AT );
        itemQuery.findInBackground( new FindCallback<Items>() {
            @Override
            public void done(List<Items> items, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                listItems.addAll( items );
                adapter.notifyDataSetChanged();

                for (int i = 0; i < items.size(); i++){
                    Log.d(TAG, "Description: " + items.get(i).getDescriptionStore() + "Price" + items.get( i )
                            .getPrice());
                }

            }
        } );
    }
}
