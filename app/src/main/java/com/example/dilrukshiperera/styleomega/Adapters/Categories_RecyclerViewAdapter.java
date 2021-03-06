package com.example.dilrukshiperera.styleomega.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dilrukshiperera.styleomega.R;

public class Categories_RecyclerViewAdapter extends RecyclerView.Adapter<Categories_RecyclerViewAdapter.ViewHolder>{
    private String[] mImages;
    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public Categories_RecyclerViewAdapter(Context context, String[] data,String[] mImages) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mImages=mImages;
    }
    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.categories_rv, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(mData[position]);
        holder.myImageView.setImageResource(mInflater.getContext()
                .getResources()
                .getIdentifier(mImages[position], "drawable",mInflater.getContext()
                        .getPackageName()));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    } // total number of cells

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.categoryTV);
            myImageView=itemView.findViewById(R.id.mImageIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenient method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // HomeFragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
