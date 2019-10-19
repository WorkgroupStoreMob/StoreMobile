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

import models.Items;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Items> itemsList;
    private ItemAdapter.OnItemClickListener monOnItemClickListener;

    public ItemAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;


    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void SetOnItemClickListener(ItemAdapter.OnItemClickListener listener){
        monOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_list, viewGroup, false );
        return new ItemAdapter.ViewHolder( view, monOnItemClickListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, int i) {
        final Items items = itemsList.get(i);
        try {
            viewHolder.bind(items);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem_image;
        private TextView tv_Desc;
        private TextView tv_Price;
        private TextView tv_category;

        private ImageButton ivBtnOrder1;

        public ViewHolder(@NonNull View itemView, final ItemAdapter.OnItemClickListener listener) {
            super(itemView);
            ivItem_image = itemView.findViewById( R.id.ivItem_image );
            tv_Desc = itemView.findViewById( R.id.tv_Desc );
            tv_Price = itemView.findViewById( R.id.tv_Price );
            tv_category = itemView.findViewById( R.id.tv_category );
            ivBtnOrder1 = itemView.findViewById( R.id.iv_btn_order1 );

            ivBtnOrder1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Items items) throws ParseException {
            ParseFile image = items.getImageItems();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( ivItem_image );
            }
            tv_Desc.setText( items.getDescriptionStore() );
            tv_Price.setText( "$"+items.getPrice() );
            tv_category.setText( items.getCategory() );
        }
    }
}