package com.codepath.storemobile;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import fragments.FragmentClient;
import fragments.FragmentCommande;
import fragments.FragmentItems;

public class ManageStoreActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_store );


        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById( R.id.bottom_navigation );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.my_business:
                    fragment = new FragmentItems();
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
