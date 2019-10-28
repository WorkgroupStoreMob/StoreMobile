package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;


import com.codepath.storemobile.AddItemsActivity;
import com.codepath.storemobile.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapters.ItemAdapter;
import adapters.ItemStoreAdapter;
import models.ItemCart;
import models.Items;

public class FragmentItems extends Fragment {

    public static final String TAG = "FragmentItems";

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    private Button btnAddImage;
    String storeName = "";

    protected List<Items> listItems1;
    protected ItemStoreAdapter fragAdapter;

    RecyclerView rvFragmentStore;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storeName = getArguments().getString("storeName");
        return inflater.inflate( R.layout.fragment_items, container, false  );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        btnAddImage = view.findViewById( R.id.btnAddItems);


                storeName = getArguments().getString("storeName");

                btnAddImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), AddItemsActivity.class );
                intent.putExtra("nameStore", storeName);
                startActivity( intent );
            }
        } );



                // listing of items

        rvFragmentStore = view.findViewById( R.id.rvFragmentStore);


        // create data source
        listItems1 = new ArrayList<>( );

        // create adapter
        fragAdapter = new ItemStoreAdapter(getContext(),  listItems1 );

        //set adapter on recycler view
        rvFragmentStore.setAdapter( fragAdapter );

        //set layout manager in the recycler view
        rvFragmentStore.setLayoutManager( new LinearLayoutManager( getContext() ) );
        queryPost(storeName);
    }


    protected void queryPost(String storeName) {
        ParseQuery<Items> itemQuery1 = new ParseQuery<Items>( Items.class );
        itemQuery1.whereEqualTo("store_name", storeName);
        itemQuery1.include( Items.KEY_STORE_NAME );
        itemQuery1.include( Items.KEY_DESCRIPTION );
        itemQuery1.include( Items.KEY_QUANTITY );
        itemQuery1.include( Items.KEY_PRICE );
        itemQuery1.include( Items.KEY_BUYING_PRICE );
        //postQuery.addDescendingOrder( Post.KEY_CREATED_AT );
        itemQuery1.findInBackground( new FindCallback<Items>() {
            @Override
            public void done(List<Items> items, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                listItems1.addAll( items );
                fragAdapter.notifyDataSetChanged();

                for (int i = 0; i < items.size(); i++){
                    Log.d(TAG, "Description: " + items.get(i).getDescriptionStore() + "Price" + items.get( i )
                            .getPrice());
                }

            }
        } );
    }


}
