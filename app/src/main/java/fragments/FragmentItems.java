package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;


import com.codepath.storemobile.AddItemsActivity;
import com.codepath.storemobile.R;

import java.io.File;

public class FragmentItems extends Fragment {

    public static final String TAG = "FragmentItems";

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    private Button btnAddImage;

    private TextView tvStoreName;
    String storeName;



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
    }


}
