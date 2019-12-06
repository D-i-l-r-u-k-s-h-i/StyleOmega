package com.example.dilrukshiperera.styleomega;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.Adapters.Inquiries_RecyclerViewAdapter;
import com.example.dilrukshiperera.styleomega.Models.Cart;
import com.example.dilrukshiperera.styleomega.Models.InquiryReplies;
import com.example.dilrukshiperera.styleomega.Models.Orders;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.example.dilrukshiperera.styleomega.Models.ProductInquiry;
import com.example.dilrukshiperera.styleomega.Models.ProductOrders;
import com.example.dilrukshiperera.styleomega.Models.UserRateProduct;
import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;
import com.example.dilrukshiperera.styleomega.SupportClasses.ReplyInquiryDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailedItemActivity extends AppCompatActivity implements ReplyInquiryDialog.ReplyInqDialogListener{

    private ImageView item_image;
    private TextView item_nameTV;
    private TextView item_shortDescriptionTV;
    private TextView item_longDescriptionTV;
    private TextView item_ratingTV;
    private TextView item_sizesTV;
    private TextView item_priceTV;

    private RatingBar ratingsB;

    private Inquiries_RecyclerViewAdapter rvAdapter;

    private ArrayList<String> inquiry=new ArrayList<>();
    private ArrayList<String> inq_user=new ArrayList<>();

    //private ArrayList<String> prodName=new ArrayList<>();

    private EditText inquiryET;

    private Product product;
    private RecyclerView rv;

    String itemName;
    String itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        getSupportActionBar().setTitle("Style Omega");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingsB=findViewById(R.id.ratingBar);

        item_image=findViewById(R.id.itemIV);
        item_nameTV=findViewById(R.id.itemNmaeTV);
        item_shortDescriptionTV=findViewById(R.id.itemShortDescTV);
        item_longDescriptionTV=findViewById(R.id.Item_longDescriptionTV);
        item_ratingTV=findViewById(R.id.itemRatingTV);
        item_sizesTV=findViewById(R.id.availableSizesTV);
        item_priceTV=findViewById(R.id.priceTV);

        inquiryET=findViewById(R.id.inquiryET);

        if(getIntent().hasExtra("product_name") && getIntent().hasExtra("product_image")){
            itemName=getIntent().getStringExtra("product_name");
            itemImage=getIntent().getStringExtra("product_image");

            product=Product.find(Product.class,"pname=?",itemName).get(0);
            item_shortDescriptionTV.setText(product.getPshort_description());
            item_longDescriptionTV.setText(product.getPlong_description());
            item_ratingTV.setText(product.getRating().toString());
            item_sizesTV.setText(product.getAvailable_sizes());
            item_priceTV.setText(product.getP_price().toString());

            Picasso.get().load(itemImage).into(item_image);
            item_nameTV.setText(itemName);

            //using the db to retrieve the data here, and add to the arraylists
            try{
                List<ProductInquiry> productInquiries=ProductInquiry.listAll(ProductInquiry.class);

                for(ProductInquiry pi :productInquiries){
                    if((pi.getProductname()).equals(itemName)){
                        inquiry.add(pi.getInquiry_description());
                        inq_user.add(pi.getInquser());
                    }
                }
            }catch(SQLiteException e){ }
            rv=findViewById(R.id.inquiriesRV);
            //if there are no inquiries, the recycler is set to GONE.(Hidden)
            if(inquiry.isEmpty()){
                rv.setVisibility(View.GONE);
            }
            else{
                rvAdapter=new Inquiries_RecyclerViewAdapter(inquiry,inq_user,this,itemName);
                rv.setAdapter(rvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }

            //User Ratings for product
            final String userEmail=SaveSharedPreferenceInstance.getUserEmail(this);
            //listen to the changes in ratings(User inputs)
            ratingsB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    try { //filter ratings for a product by a user
                        List<UserRateProduct> u_rating=UserRateProduct.find(UserRateProduct.class,
                                "useremail=? and productid=?",userEmail,String.valueOf(product.getId()));
                        //if the user changes mind.
                        if(u_rating.size()>0){
                            u_rating.get(0).setUser_rating(rating);
                            u_rating.get(0).save();
                        }
                        else{  //Rating the product for the first time
                            UserRateProduct userRating=new UserRateProduct(product.getId(),userEmail,rating);
                            userRating.save();
                        }
                        Toast.makeText(DetailedItemActivity.this,"Thanks for the your product rating! You rated "
                                                                +rating,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //the algorithm to calculate average rating per product
            try {
                int count1=0,count2=0,count3=0,count4=0,count5=0;
                //obtain the list of user ratings and calculate the weighted average
                List<UserRateProduct> userRatings=UserRateProduct.find(UserRateProduct.class,"productid=?",
                                      String.valueOf(product.getId()));

                for(UserRateProduct prodRating:userRatings){
                    float prodUserRating=prodRating.getUser_rating();
                    if(prodUserRating==0 && prodUserRating>=1){
                        count1++;
                    }
                    else if(prodUserRating>1 && prodUserRating<=2){
                        count2++;
                    }
                    else if(prodUserRating>2 && prodUserRating<=3){
                        count3++;
                    }
                    else if(prodUserRating>3 && prodUserRating<=4){
                        count4++;
                    }
                    else if(prodUserRating>4 && prodUserRating<=5){
                        count5++;
                    }
                }

                Double avgRating= Double.valueOf((count1+(count2*2)+(count3*3)+(count4*4)+(count5*5))/(count1+count2+count3+count4+count5));

                product.setRating(avgRating);
                product.save();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //to set the ratings bar values according to the user
            try {
                UserRateProduct sessionRating=UserRateProduct.find(UserRateProduct.class,"useremail=? and productid=?",userEmail, String.valueOf(product.getId())).get(0);
                ratingsB.setRating(sessionRating.getUser_rating());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void applyInqReply(String inqReply, String inquiry,String inqUser,String prodname) {
        String session_email=SaveSharedPreferenceInstance.getUserEmail(this);
        ProductInquiry pinquiry=ProductInquiry.find(ProductInquiry.class,
          "inquirydescription=? and inquser=? and productname=?",inquiry,inqUser,prodname).get(0);
        long pinqID=pinquiry.getId();
        InquiryReplies inquiryReplies=new InquiryReplies(inqReply,pinqID,session_email);
        inquiryReplies.save();
    }

    public void post_onClick(View view){
        //get the inquiry added by the user in the EditText
        String inq_text=inquiryET.getText().toString().trim();
        if(!inq_text.equals("")){ //only if the EditText is not empty
            String session_email= SaveSharedPreferenceInstance.getUserEmail(this);
            ProductInquiry p_inquiry=new ProductInquiry(inq_text, product.getPname(),session_email);
            p_inquiry.save();

            //updating the rv right after the post.
            inquiry.add(inq_text);
            inq_user.add(session_email);
            //Setting up the adapter for the recycler view.
            rv.setVisibility(View.VISIBLE);
            rvAdapter=new Inquiries_RecyclerViewAdapter(inquiry,inq_user,this,itemName);
            rv.setAdapter(rvAdapter);
            rv.setLayoutManager(new LinearLayoutManager(this));
            //notify-add the inquiry to the rv right after user posts it.
            rvAdapter.notifyItemInserted(inquiry.size());
            inquiryET.setText("");
        }
        else{
            Toast.makeText(this,"Empty inquiries cannot be added.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addToCart:
                String session_email= SaveSharedPreferenceInstance.getUserEmail(this);
                Cart cart=Cart.find(Cart.class,"uemail=?",session_email).get(0);

                Orders order=Orders.find(Orders.class,"cartid=? and orderstatus='PENDING'", String.valueOf(cart.getId())).get(0);

                try {
                    List<ProductOrders> prodOrder=ProductOrders.find(ProductOrders.class,"productid=?", String.valueOf(product.getId()));
                    if(prodOrder.size()>0){
                        prodOrder.get(0).setQuantity(String.valueOf((Integer.valueOf(prodOrder.get(0).getQuantity())+1)));
                        prodOrder.get(0).save();
                    }
                    else{
                        ProductOrders prodOrderNew= new ProductOrders(order.getId(),product.getId(),"1");
                        prodOrderNew.save();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(DetailedItemActivity.this,"The Item "+product.getPname()
                        + " was added to the cart",Toast.LENGTH_SHORT).show();
                break;

            case R.id.shareProduct: {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                //sharing the name of the product and the url of the picture here
                String shareString=itemName;
                String shareString2=itemImage;

                //parsing the content to the app using putExtra
                intent.putExtra(Intent.EXTRA_SUBJECT,"Style Omega");
                intent.putExtra(Intent.EXTRA_TEXT,shareString + " " +shareString2);

                startActivity(Intent.createChooser(intent,"Share using"));

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
