package com.codepath.storemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import adapters.StoreAdapter;
import fragments.FragmentClient;
import fragments.FragmentCommande;
import fragments.FragmentItems;
import models.Store;

public class ManageStoreActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView rvNameStore;
    private StoreAdapter adapter;
    TextView tvBusinessName;
    List<Store> storeData;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_store );


        //Add Toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarItem5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle( " " );



        //rvNameStore = findViewById( R.id.rvNameStore );
        tvBusinessName = findViewById( R.id.tvBusinessName );
        storeData = new ArrayList<>( );



        //storeData = Parcels.unwrap(getIntent().getParcelableExtra("StoreData"));
        Intent in = getIntent();
        String nameStore = in.getExtras().getString( "StoreData" );
        tvBusinessName.setText( nameStore );
//        for (int i = 0; i < storeData.size(); i++){
//                tvBusinessName.setText("" + storeData.get( i ).getName() );
//        }


        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById( R.id.bottom_navigation );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.my_business:
                    fragment = new FragmentItems();
                    Bundle bundle = new Bundle();
                    bundle.putString("storeName", tvBusinessName.getText().toString());
                    fragment.setArguments(bundle);
                    break;
                    case R.id.home_order:
                        fragment = new FragmentCommande();
                        break;
                    case R.id.home_client:
                        default:
                    fragment = new FragmentClient();
                    break;
                }


                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        } );

        bottomNavigationView.setSelectedItemId( R.id.my_business );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_my_store, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_logout_store:
               logoutStore();
             break;
            case R.id.action_add_item:
                addItems();
                break;
            case R.id.action_settings:
            default:
                settings();
                break;

        }
        return super.onOptionsItemSelected( item );
    }


    private void settings() {
        Toast.makeText( this, "SETTINGS", Toast.LENGTH_SHORT ).show();
    }

    private void addItems() {
        Toast.makeText( this, "Add items", Toast.LENGTH_SHORT ).show();

    }

    private void logoutStore() {
        Toast.makeText( this, "Logout successfully", Toast.LENGTH_SHORT ).show();
    }
}
