package com.example.dilrukshiperera.styleomega;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.dilrukshiperera.styleomega.Adapters.CategoryItem_RecyclerViewAdapter;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity {

    private CategoryItem_RecyclerViewAdapter rvAdapter;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> imageUrls=new ArrayList<>();
    private ArrayList<String> mDescriptions=new ArrayList<>();
    private ArrayList<String> prices=new ArrayList<>();

    List<Product> productList;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        //retrieve the data passed on Category click using getStringExtra("")
        if(getIntent().hasExtra("category")){
            category=getIntent().getStringExtra("category");
            //set the title of the action bar to the respective category
            getSupportActionBar().setTitle(category);
            //enabling the up button for back navigation
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //read the products.json file using Gson
            Gson gson = new Gson();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getAssets()
                                    .open("sample_products.json")));
                //object data that is read form the json file is stored in an Product[] array
                Product[] products = gson.fromJson(br, Product[].class);

                //retrieve data from database to check whether the db is empty
                try {
                    productList=Product.listAll(Product.class);

                }catch (SQLiteException ex){
                }
                //get each product's data to variables
                for(Product p:products){

                    String pname=p.getPname();
                    String pshort_description=p.getPshort_description();
                    String plong_description=p.getPlong_description();
                    Double p_price=p.getP_price();
                    Double rating=p.getRating();
                    String p_category=p.getCategory();
                    String available_sizes=p.getAvailable_sizes();
                    boolean availability_status=p.isAvailability_status();
                    int prod_quantity=p.getProd_quantity();
                    String p_image=p.getP_image();

                    //filters according to the category and add to the arraylists
                    if(p_category.equals(category)){
                        mNames.add(pname);
                        mDescriptions.add(pshort_description);
                        prices.add(p_price.toString());
                        imageUrls.add(p_image);
                    }
                    //save the data read from the json file, if the database is empty
                    if(productList.isEmpty()){
                        Product productt=new Product(pname,pshort_description,plong_description,p_price,
                                rating,p_category,available_sizes,availability_status,prod_quantity,p_image);
                        productt.save();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //After retrieval and setting in the arraylists set up the adapter.
        RecyclerView rv=findViewById(R.id.categoryItemsRV);
        rvAdapter=new CategoryItem_RecyclerViewAdapter(mNames,mDescriptions,imageUrls,prices,this);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflator=getMenuInflater();
        mInflator.inflate(R.menu.search_menu,menu);
        
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                List<Product> prodSearchList=new ArrayList<>();
                for(Product prod:productList){  //check whether this gives Zero
                    String prod_name=prod.getPname().toLowerCase();
                    if((prod.getCategory()).equals(category) && prod_name.contains(newText)){
                        prodSearchList.add(prod);
                    }
                }
                rvAdapter.setSearchOperation(prodSearchList);
                return true;
            }
        });
        return true;
    }
}
