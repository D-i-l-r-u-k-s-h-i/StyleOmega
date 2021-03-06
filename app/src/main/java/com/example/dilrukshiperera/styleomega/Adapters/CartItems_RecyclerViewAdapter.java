package com.example.dilrukshiperera.styleomega.Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.CartFragment;
import com.example.dilrukshiperera.styleomega.DetailedItemActivity;
import com.example.dilrukshiperera.styleomega.HomeActivity;
import com.example.dilrukshiperera.styleomega.HomeFragment;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.example.dilrukshiperera.styleomega.Models.ProductOrders;
import com.example.dilrukshiperera.styleomega.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.provider.SyncStateContract.Helpers.update;

public class CartItems_RecyclerViewAdapter extends RecyclerView.Adapter<CartItems_RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> names;
    private ArrayList<String> descriptiolnss;
    private ArrayList<String> prices;
    private ArrayList<String> images;

    private Context mContext;

    public CartItems_RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> descriptiolnss,
                                         ArrayList<String> prices, ArrayList<String> images, Context mContext) {
        this.names = names;
        this.descriptiolnss = descriptiolnss;
        this.prices = prices;
        this.images = images;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView name_tv;
        TextView description_tv;
        TextView price_tv;
        ConstraintLayout parent_layout;
        ImageView removeItem_iv;
        NumberPicker quantityNP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.c_image2);
            name_tv=itemView.findViewById(R.id.nameTV2);
            description_tv=itemView.findViewById(R.id.descriptionTV2);
            price_tv=itemView.findViewById(R.id.priceTV2);
            removeItem_iv=itemView.findViewById(R.id.removeItemIV);
            quantityNP=itemView.findViewById(R.id.numberPicker);

            parent_layout=itemView.findViewById(R.id.parentLayoutCart);

        }
    }

    @NonNull
    @Override
    public CartItems_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_rv,parent,false);
        CartItems_RecyclerViewAdapter.ViewHolder viewHolder=new CartItems_RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItems_RecyclerViewAdapter.ViewHolder viewHolder,
                                 final int position) {
        viewHolder.name_tv.setText(names.get(position));
        viewHolder.description_tv.setText(descriptiolnss.get(position));
        viewHolder.price_tv.setText(prices.get(position));
        //load the image using picaso here.
        Picasso.get().load(images.get(position)).into(viewHolder.productImage);
        //on click of an item, the detailed view of the product is displayed.
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),DetailedItemActivity.class);
                intent.putExtra("product_name",names.get(position));
                intent.putExtra("product_image",images.get(position));
                mContext.startActivity(intent);
            }
        });
        //get the Product object where the name is the product name at the position in the arraylist
        Product product=Product.find(Product.class,"pname=?",names.get(position)).get(0);
        long prodid=product.getId();
        //obtaining the ProductOrder using the productID.
        final ProductOrders pOrder=ProductOrders.find(ProductOrders.class,"productid=?",
                String.valueOf(prodid)).get(0);
        //on click of the remove icon in the recyclerview item,
        viewHolder.removeItem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //product order will be detleted from the database
                pOrder.delete();
                //Also remove the data on arraylist regarding the product order, so that it would be
                //added as a recycler view item.
                try {
                    names.remove(position);
                    descriptiolnss.remove(position);
                    prices.remove(position);
                    images.remove(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //notify that an item got changed.
                notifyItemRemoved(position);
                new CartFragment().showToast("Swipe down to Refresh.",mContext);
            }
        });

        viewHolder.quantityNP.setMinValue(1);
        //later set the max value to the quantity of products available
        viewHolder.quantityNP.setMaxValue(product.getProd_quantity());
        //viewHolder.quantityNP.setWrapSelectorWheel(false); //to make it non recurring
        viewHolder.quantityNP.setValue(Integer.parseInt(pOrder.getQuantity()));

        viewHolder.quantityNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //save the new quantity picked by the user, in the database.
                pOrder.setQuantity(String.valueOf(newVal));
                pOrder.save();
                //The page should be refreshed.
                new CartFragment().showToast("Swipe down to Refresh.",mContext);
            }
        });
    }
    @Override
    public int getItemCount() {
        return names.size();
    }
}
