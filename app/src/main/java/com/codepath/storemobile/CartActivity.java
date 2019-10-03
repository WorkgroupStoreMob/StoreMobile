package com.codepath.storemobile;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adapters.ItemCartAdapter;
import adapters.ItemWelcomeAdapter;
import models.ItemCart;
import models.Items;

public class CartActivity extends AppCompatActivity {

    Items item;
    //private ItemWelcomeAdapter adapterItemsWelcome;
    private ItemCartAdapter itemCartAdapter;
    //private List<Items> litems;
    private List<ItemCart> litems;
    RecyclerView rvItems;
    Double total = 0.0;
    TextView tv_total;
    TextView tv_subTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart);

        getSupportActionBar().setTitle("CART LIST");

        rvItems = findViewById( R.id.rvCarts );
        tv_total = findViewById(R.id.tv_total);
        tv_subTotal = findViewById(R.id.tv_subTotal);

        // create data source
        litems = new ArrayList<>( );
        litems = Parcels.unwrap(getIntent().getParcelableExtra("cartList"));
        for (int i = 0; i < litems.size(); i++){
            try {
                if (Double.parseDouble(litems.get(i).getTotal()) != 0.0){
                    total +=  Double.parseDouble(litems.get(i).getTotal());
                }
            }catch (NumberFormatException e){

            }

        }
        tv_total.setText("$"+total);
        tv_subTotal.setText("$"+total);
        // create adapter
        itemCartAdapter = new ItemCartAdapter(CartActivity.this,  litems );

        //set adapter on recycler view
        rvItems.setAdapter( itemCartAdapter );

        //set layout manager in the recycler view
        rvItems.setLayoutManager( new LinearLayoutManager( CartActivity.this, LinearLayoutManager.HORIZONTAL,
                false) );
    }
}
