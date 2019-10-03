package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.storemobile.R;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

import models.ItemCart;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ViewHolder> {
    private Context context;
    private List<ItemCart> listItemsCart;

    public ItemCartAdapter(Context context, List<ItemCart> listItemsCart){
        this.context = context;
        this.listItemsCart = listItemsCart;
    }

    @NonNull
    @Override
    public ItemCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_cart, viewGroup, false );
        return new  ItemCartAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartAdapter.ViewHolder viewHolder, int i) {
        final ItemCart itemCart = listItemsCart.get(i);
        try {
            viewHolder.bind(itemCart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listItemsCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cart;
        private TextView tv_category;
        private TextView tv_Price;
        private TextView tv_qte;
        private TextView tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cart = itemView.findViewById( R.id.iv_cart );
            tv_category = itemView.findViewById( R.id.tv_category );
            tv_Price = itemView.findViewById( R.id.tv_Price);
            tv_qte = itemView.findViewById( R.id.tv_qte);
            tv_total = itemView.findViewById( R.id.tv_total);
        }

        public void bind(ItemCart itemCart) throws ParseException {
            tv_category.setText( itemCart.getCategory() );
            tv_Price.setText("$"+itemCart.getPrice() );
            tv_total.setText("$"+itemCart.getTotal() );
            ParseFile image = itemCart.getImage();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( iv_cart );
            }
            tv_qte.setText( itemCart.getQte() );
        }
    }
}
