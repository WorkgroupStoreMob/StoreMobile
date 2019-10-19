package com.codepath.storemobile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import adapters.ItemAdapter;
import models.ItemCart;
import models.Items;

public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    protected List<Items> listItems;
    private List<ItemCart> litemsCart;
    protected ItemAdapter adapter;
    Toolbar toolbar;
    ParseFile image = null;
    private String price ="";
    private String category ="";
    private String total ="";
    TextView tv_incrementCart;
    MenuItem menuItemCart;
    int globalPosition;
    String qte;

    private RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item );

        toolbar = (Toolbar) findViewById(R.id.toolbarItem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Items List");
        tv_incrementCart = findViewById( R.id.tv_incrementCart );

        rvItems = findViewById( R.id.rvItems);
        queryPost();

        // create data source
        listItems = new ArrayList<>( );
        litemsCart = new ArrayList<>( );
        // create adapter
        adapter = new ItemAdapter(this,  listItems );

        //set adapter on recycler view
        rvItems.setAdapter( adapter );

        //set layout manager in the recycler view
        rvItems.setLayoutManager( new LinearLayoutManager( this ) );

        adapter.SetOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                globalPosition = position;
                image = listItems.get(position).getImageItems();
                price = listItems.get(position).getPrice();
                category = listItems.get(position).getCategory();
                showAddToCartDialog();
            }

            private void showAddToCartDialog() {
                final Dialog showAddToCart = new Dialog(ItemActivity.this);
                showAddToCart.setContentView(R.layout.cart_confirm);

                ImageView iv_cart_confirm = (ImageView) showAddToCart.findViewById(R.id.iv_cart_confirm);
                TextView tv_category = (TextView) showAddToCart.findViewById(R.id.tv_category);
                Button bt_addToCard = (Button) showAddToCart.findViewById(R.id.bt_addToCard);
                ImageButton bt_close = (ImageButton) showAddToCart.findViewById(R.id.bt_close);

                final TextView tv_Price = (TextView) showAddToCart.findViewById(R.id.tv_Price);
                final TextView tv_AllPrice = (TextView) showAddToCart.findViewById(R.id.tv_AllPrice);
                final ElegantNumberButton txt_counter = (ElegantNumberButton) showAddToCart.findViewById(R.id.txt_counter);

                //set Data
                if(image != null){
                    try {
                        Glide.with( ItemActivity.this ).load( image.getFile()).into( iv_cart_confirm );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                tv_category.setText(category);
                txt_counter.setNumber("1");
                tv_Price.setText("$"+price);
                total = String.valueOf(Double.parseDouble(price) * Double.parseDouble(txt_counter.getNumber()));
                tv_AllPrice.setText("$"+total);

                for(int i = 0; i < litemsCart.size(); i++){
                    if (String.valueOf(litemsCart.get(i).getCategory()).equals(category)){
                        if (litemsCart.get(i).getQte() != null){
                            txt_counter.setNumber(""+Integer.parseInt(litemsCart.get(i).getQte()));
                            tv_AllPrice.setText("$"+String.valueOf(Double.parseDouble(price) * Double.parseDouble(litemsCart.get(i).getQte())));
                        }
                        break;
                    }
                }

                txt_counter.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        if (Integer.parseInt(txt_counter.getNumber()) >= 1){
                            qte = txt_counter.getNumber();
                            total = String.valueOf(Double.parseDouble(price) * Double.parseDouble(qte));
                            tv_AllPrice.setText("$"+total);
                        }
                    }
                });

                bt_addToCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemCart itemCart = new ItemCart(category, price, total, String.valueOf(qte), image);

                        if (litemsCart.size() == 0){
                            Toast.makeText(ItemActivity.this, " size= 0", Toast.LENGTH_SHORT).show();
                            litemsCart.add(itemCart);
                        }else{
                            for(int i = 0; i < litemsCart.size(); i++){
                                if (String.valueOf(litemsCart.get(i).getCategory()).equals(category)){
                                    itemCart = new ItemCart(category, price, total, String.valueOf(qte), image);
                                    litemsCart.remove(i);
                                    break;
                                }
                            }
                            litemsCart.add(itemCart);
                        }
                        incrementCounter();
                        showAddToCart.dismiss();
                    }
                });

                bt_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddToCart.dismiss();
                    }
                });
                showAddToCart.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                showAddToCart.show();
            }
        });

    }

    public void incrementCounter(){
        tv_incrementCart.setText(String.valueOf(litemsCart.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        menuItemCart = menu.findItem(R.id.cart_counterMenu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_counterMenu:
                Intent intent = new Intent(ItemActivity.this, CartActivity.class);
                intent.putExtra("cartList", Parcels.wrap(litemsCart));
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    protected void queryPost() {
        ParseQuery<Items> itemQuery = new ParseQuery<Items>( Items.class );
        itemQuery.whereEqualTo("store_name", getIntent().getStringExtra("nameStore"));
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
