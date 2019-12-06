package com.example.dilrukshiperera.styleomega.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dilrukshiperera.styleomega.DetailedItemActivity;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.example.dilrukshiperera.styleomega.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryItem_RecyclerViewAdapter extends RecyclerView.Adapter<CategoryItem_RecyclerViewAdapter.
        ViewHolder>{
    private ArrayList<String> names;
    private ArrayList<String> descriptiolnss;
    private ArrayList<String> prices;
    private ArrayList<String> images;

    private ArrayList<Product> productArrayList;

    private Context mContext;

    public CategoryItem_RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> descriptiolnss,
                                      ArrayList<String> images,ArrayList<String> prices, Context mContext){
        this.names = names;
        this.descriptiolnss = descriptiolnss;
        this.images = images;
        this.mContext = mContext;
        this.prices=prices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_rv,parent,
                false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //this is where retrieving of information happens from a db and show them on the view
        viewHolder.name_tv.setText(names.get(position));
        viewHolder.description_tv.setText(descriptiolnss.get(position));
        viewHolder.price_tv.setText(prices.get(position));
        //load the image using picaso here.
        Picasso.get().load(images.get(position)).into(viewHolder.productImage);
        //on click of an item..pass the image url and product name to DetailedItemActivity
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),DetailedItemActivity.class);
                intent.putExtra("product_name",names.get(position));
                intent.putExtra("product_image",images.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView name_tv;
        TextView description_tv;
        TextView price_tv;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.c_image);
            name_tv=itemView.findViewById(R.id.NameTV);
            description_tv=itemView.findViewById(R.id.descriptionTV);
            price_tv=itemView.findViewById(R.id.priceTV);

            parent_layout=itemView.findViewById(R.id.parentLayout);
        }
    }

    //for the search view
    public void setSearchOperation(List<Product> newpnameList){
        names = new ArrayList<>();
        descriptiolnss = new ArrayList<>();
        prices = new ArrayList<>();
        images = new ArrayList<>();
        for(Product prod : newpnameList){
            names.add(prod.getPname());
            descriptiolnss.add(prod.getPshort_description());
            prices.add(prod.getP_price().toString());
            images.add(prod.getP_image());
        }
        productArrayList=new ArrayList<>();
        productArrayList.addAll(newpnameList);
        notifyDataSetChanged();
    }
}
