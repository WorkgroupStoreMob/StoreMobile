package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.storemobile.ItemActivity;
import com.codepath.storemobile.R;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

import models.Store;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private Context context;
    private List<Store> storeList;


    public StoreAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_store, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Store store = storeList.get(i);
        try {
            viewHolder.bind(store);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.storeContainer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ItemActivity.class );
                context.startActivity( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }


    public void clear(){
        storeList.clear();
        notifyDataSetChanged();
    }

    public void addStore(List<Store> listStore){
        listStore.addAll( listStore );
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivItemStore;
        private TextView tvItemStoreName;
        private TextView tvItemStoreDescription;

        RelativeLayout storeContainer;



        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            ivItemStore = itemView.findViewById( R.id.iv_logo_item_store );
            tvItemStoreName = itemView.findViewById( R.id.tv_name_item_store );
            tvItemStoreDescription = itemView.findViewById( R.id.tv_description_item_store );
            storeContainer = itemView.findViewById( R.id.storeContainer ) ;
        }

        public void bind(Store store) throws ParseException {


            tvItemStoreName.setText( store.getName() );
            ParseFile image = store.getImage();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( ivItemStore );
            }

            tvItemStoreDescription.setText( store.getDescription() );

        }
    }


}
