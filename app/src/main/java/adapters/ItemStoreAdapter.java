package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.storemobile.R;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

import models.Items;

public class ItemStoreAdapter  extends RecyclerView.Adapter<ItemStoreAdapter.ViewHolder> {


    private Context context;
    private List<Items> lItemsStore;

    public ItemStoreAdapter(Context context, List<Items> lItemsStore) {
        this.context = context;
        this.lItemsStore = lItemsStore;
    }

    @NonNull
    @Override
    public ItemStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.itemliststore, viewGroup, false );
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStoreAdapter.ViewHolder viewHolder, int i) {
        final Items items = lItemsStore.get(i);
        try {
            viewHolder.bind(items);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return lItemsStore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivItem_image;
        private TextView tv_Desc;
        private TextView tv_Price;
        private TextView tv_category;
        private TextView tv_buyingPrice;
        private TextView tv_qty;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            ivItem_image = itemView.findViewById( R.id.ivItem_imageStore );
            tv_Desc = itemView.findViewById( R.id.tv_DescStore );
            tv_Price = itemView.findViewById( R.id.tv_PriceStore);
            tv_category = itemView.findViewById( R.id.tv_categoryStore );
            tv_buyingPrice = itemView.findViewById( R.id.tv_buyingPrice);
            tv_qty = itemView.findViewById( R.id.tv_qty);
        }

        public void bind(Items items) throws ParseException {
            ParseFile image = items.getImageItems();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( ivItem_image );
            }
            tv_Desc.setText( items.getDescriptionStore() );
            tv_Price.setText( "Sales Px: "+"$"+items.getPrice() );
            tv_category.setText( items.getCategory() );
            tv_buyingPrice.setText("Buy Px: "+"$"+items.getBuyingPrice() );
            tv_qty.setText( "Qty:"+" "+items.getQuantity());
        }
    }
}