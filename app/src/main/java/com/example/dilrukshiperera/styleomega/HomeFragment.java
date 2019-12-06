package com.example.dilrukshiperera.styleomega;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dilrukshiperera.styleomega.Adapters.Categories_RecyclerViewAdapter;

public class HomeFragment extends Fragment implements Categories_RecyclerViewAdapter.ItemClickListener{

    Categories_RecyclerViewAdapter adapter;
    String[] data = {"Mens", "Ladies","Kids","Accessories"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);
        //pictures at the categories recycler view.
        String[] images={"category_men","category_women","category_kids","category_accessories"};

        RecyclerView recyclerView = rootView.findViewById(R.id.categoriesRV);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new Categories_RecyclerViewAdapter(getActivity(), data,images);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(view.getContext(),CategoryItemsActivity.class);
        //pass the category as a putExtra to CategoryItemsActivity
        intent.putExtra("category",data[position]);
        startActivity(intent);
    }
}
