package com.codepath.storemobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_store );


        //rvNameStore = findViewById( R.id.rvNameStore );
        tvBusinessName = findViewById( R.id.tvBusinessName );
        storeData = new ArrayList<>( );
        storeData = Parcels.unwrap(getIntent().getParcelableExtra("StoreData"));
        for (int i = 0; i < storeData.size(); i++){
            tvBusinessName.setText(""+storeData.get(i).getName());
        }
        

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
}
