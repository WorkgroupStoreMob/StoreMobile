package com.codepath.storemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import adapters.ItemCartAdapter;
import models.ItemCart;
import models.Items;

public class CartActivity extends AppCompatActivity {

    Items item;
    //private ItemWelcomeAdapter adapterItemsWelcome;
    private ItemCartAdapter itemCartAdapter;
    //private List<Items> litems;
    private List<ItemCart> litems;
    private Button btnProcessOrder, btnSsaveOrder;
    RecyclerView rvItems;
    Double total = 0.0;
    TextView tv_total;
    TextView tv_subTotal;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart);

        //Add Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarItem6);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo( R.drawable.logosmr);
        getSupportActionBar().setTitle("CART LIST");



        rvItems = findViewById( R.id.rvCarts );
        tv_total = findViewById(R.id.tv_total);
        tv_subTotal = findViewById(R.id.tv_subTotal);

        btnProcessOrder = findViewById( R.id.btn_process_order );
        btnSsaveOrder = findViewById( R.id.btn_save_order );


        // launch payment activity

        btnProcessOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] colors = {"Credit Card", "Mon Cash"};

                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Choose a payment type");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                               cardPayment();
                               break;
                            case 1:
                                monCash();
                                break;
                        }
                    }
                });
                builder.show();
            }
        } );

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

    private void monCash() {
        Intent intentMonCash = new Intent( CartActivity.this, MonCashActivity.class );
        startActivity( intentMonCash );

    }

    private void cardPayment() {
        Intent intent = new Intent( CartActivity.this, PaymentActivity.class );
        startActivity( intent );
    }

    public Double getTotal(){
        return total;
    }
}
