package com.example.dilrukshiperera.styleomega;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.Adapters.CartItems_RecyclerViewAdapter;
import com.example.dilrukshiperera.styleomega.Models.Cart;
import com.example.dilrukshiperera.styleomega.Models.Orders;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.example.dilrukshiperera.styleomega.Models.ProductOrders;
import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    private SwipeRefreshLayout swipeLayout;

    private CartItems_RecyclerViewAdapter rvAdapter;

    private TextView noOfItemsTV;
    private TextView order_total;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> imageUrls=new ArrayList<>();
    private ArrayList<String> mDescriptions=new ArrayList<>();
    private ArrayList<String> prices=new ArrayList<>();

    List <ProductOrders> porder;
    Double orderTotal=0.0;
    long orderID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        noOfItemsTV=rootView.findViewById(R.id.noOfItemsTV);
        order_total=rootView.findViewById(R.id.totalPriceTV);

        swipeLayout=rootView.findViewById(R.id.swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //start page again..
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new CartFragment()).addToBackStack(null).commit();
                //getFragmentManager().popBackStack();

                //getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false );
                    }
                },4000);
            }
        });

        String session_email= SaveSharedPreferenceInstance.getUserEmail(getContext());
        Cart cart=Cart.find(Cart.class,"uemail=?",session_email).get(0);
        long cartID=cart.getId();
        //getting the pending order of the session user
        Orders order= null;
        try {
            order = Orders.find(Orders.class,"cartid=? and orderstatus='PENDING'",
                                                                    String.valueOf(cartID)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //create a new order if the order is null
        if(order==null){
            order=new Orders("PENDING","0",cart.getId());
            order.save();
        }
        orderID=order.getId();
        porder=ProductOrders.find(ProductOrders.class,"orderrid=?", String.valueOf(orderID));
        //retrieve from database and add to the arraylists to set the custom   recycler view
        for (ProductOrders po:porder) {
            Product product=Product.findById(Product.class,po.getProductid());
            mNames.add(product.getPname());
            mDescriptions.add(product.getPshort_description());
            prices.add(product.getP_price().toString());
            imageUrls.add(product.getP_image());

            orderTotal+=Double.valueOf(product.getP_price())*Integer.valueOf(po.getQuantity());
        }
        //since there can be only one pending order-get(0)
        order.setOrder_total(order_total.toString());
        order.save();

        //After retrieval and setting in the arraylists
        RecyclerView rv=rootView.findViewById(R.id.cartRV);
        rvAdapter=new CartItems_RecyclerViewAdapter(mNames,mDescriptions,prices,imageUrls,getContext());
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        order_total.setText(orderTotal.toString());
        //setting the no. of items
        if(!(porder ==null) && porder.size()==1){
            noOfItemsTV.setText("1 item");
        }
        else if(!(porder ==null) && porder.size()>1){
            noOfItemsTV.setText(""+porder.size()+" items");
        }
        else{
            noOfItemsTV.setText("No items");
        }

        View buyButton = rootView.findViewById(R.id.buyBtn);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_onClick(v);
            }
        });

        return rootView;
    }
    //leads the user to the checkout activity, only if the cart is not empty.
    public void buy_onClick(View view){
        if(orderTotal!=0){
            Intent intent=new Intent(getActivity(),CheckoutActivity.class);
            intent.putExtra("ordertotal",orderTotal);
            intent.putExtra("orderID",orderID);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(),"No items in the cart!",Toast.LENGTH_SHORT).show();
        }
    }
    //to be able to show toast message from the non-activity class(CartItems_Recycler view Adapter)
    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }
}
