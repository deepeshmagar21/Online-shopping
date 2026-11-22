package com.app.android.june.allinone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.android.june.allinone.R;
import com.app.android.june.allinone.helpers.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartItemRemoveListener {
        void onItemRemove(Cart item, int position);
    }

    private List<Cart> cartList;
    private Context context;
    private OnCartItemRemoveListener removeListener;
    private DecimalFormat df = new DecimalFormat("####0.00");

    public CartAdapter(List<Cart> cartList, Context context, OnCartItemRemoveListener removeListener) {
        this.cartList = cartList;
        this.context = context;
        this.removeListener = removeListener;
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart item = cartList.get(position);

        holder.foodName.setText(item.getFoodName() != null ? item.getFoodName() : "");
        holder.shopName.setText(item.getShopName() != null ? item.getShopName() : "");
        holder.pieces.setText((item.getFoodPieces() != null ? item.getFoodPieces() : "1") + " Pieces");

        double priceVal = 0.0;
        try {
            if (item.getFoodPrice() != null) {
                priceVal = Double.parseDouble(item.getFoodPrice());
            }
        } catch (Exception ignored) {}

        holder.price.setText("₩" + df.format(priceVal));

        if (item.getFoodPhoto() != null && !item.getFoodPhoto().isEmpty()) {
            Picasso.get()
                    .load(item.getFoodPhoto())
                    .placeholder(R.drawable.food)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.food);
        }

        holder.deleteBtn.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && removeListener != null) {
                removeListener.onItemRemove(cartList.get(pos), pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, deleteBtn;
        TextView foodName, shopName, pieces, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_image);
            deleteBtn = itemView.findViewById(R.id.cart_delete);
            foodName = itemView.findViewById(R.id.cart_food_name);
            shopName = itemView.findViewById(R.id.cart_shop_name);
            pieces = itemView.findViewById(R.id.cart_pieces);
            price = itemView.findViewById(R.id.cart_price);
        }
    }
}