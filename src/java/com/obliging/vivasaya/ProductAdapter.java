package com.obliging.vivasaya;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    List<ProductsCard> productsCards;


    public ProductAdapter(Context c,List<ProductsCard>p){
        context =c;
        productsCards=p;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.products_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
            holder.product.setText(productsCards.get(position).getProduct());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),PurchaseActivity.class);
                    String text = holder.product.getText().toString();
                    intent.putExtra("product",text);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {return productsCards.size(); }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product_title);
            CardView cardView = itemView.findViewById(R.id.card_product);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            cardView.setBackgroundColor(color);
        }
    }
}
