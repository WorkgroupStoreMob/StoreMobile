package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.storemobile.ItemWelcomeActivity;
import com.codepath.storemobile.LoginActivity;
import com.codepath.storemobile.R;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

import models.Items;


public class ItemWelcomeAdapter extends RecyclerView.Adapter<ItemWelcomeAdapter.ViewHolder> {

    private Context context;
    private List<Items> listItems;

    public ItemWelcomeAdapter(Context context, List<Items> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_items_welcome, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Items item = listItems.get(i);
        try {
            viewHolder.bind(item);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.ivBtnOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, LoginActivity.class );
                context.startActivity( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void clear(){
        listItems.clear();
        notifyDataSetChanged();
    }

    public void addStore(List<Items> listItems){
        listItems.addAll( listItems );
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemWelcome;
        private TextView tvItemWelcomecategory;
        private TextView tvItemsPrice;
        private TextView tvItemsDescription;
        private ImageButton ivBtnOrder;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            ivItemWelcome = itemView.findViewById( R.id.iv_items_welcome );
            tvItemWelcomecategory = itemView.findViewById( R.id.tv_items_welcome_category );
            tvItemsPrice = itemView.findViewById( R.id.tv_items_welcome_price);
            tvItemsDescription = itemView.findViewById( R.id.tv_items_welcome_description);
           ivBtnOrder = itemView.findViewById( R.id.iv_btn_order);
        }

        public void bind(Items items) throws ParseException {


            tvItemWelcomecategory.setText( items.getCategory() );
            tvItemsPrice.setText( items.getPrice() );
            ParseFile image = items.getImageItems();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( ivItemWelcome );
            }
            tvItemsDescription.setText( items.getDescriptionStore() );

        }
    }


}
