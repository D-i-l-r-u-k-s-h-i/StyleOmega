package com.example.dilrukshiperera.styleomega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dilrukshiperera.styleomega.Adapters.InqReplies_RecyclerViewAdapter;
import com.example.dilrukshiperera.styleomega.Models.InquiryReplies;
import com.example.dilrukshiperera.styleomega.Models.ProductInquiry;

import java.util.ArrayList;
import java.util.List;

public class InquiryConversationActivity extends AppCompatActivity {
    private ArrayList<String> inq_reply=new ArrayList<>();
    private ArrayList<String> inq_user=new ArrayList<>();

    InqReplies_RecyclerViewAdapter rvAdapter;

    private RecyclerView rv;

    private TextView inqTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_conversation);
        //change this in th manifest later
        getSupportActionBar().setTitle("Inquiry Replies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv=findViewById(R.id.inqRepliesRV);
        inqTV=findViewById(R.id.inquiryTV);

        String inquiry;
        String inqUser;
        String prodid;
        //retrieve StringExtras from Inquiries_RecyclerViewAdapter
        if(getIntent().hasExtra("inquiry") && getIntent().hasExtra("inquser")&&
                getIntent().hasExtra("prodname")){
            inquiry=getIntent().getStringExtra("inquiry");
            inqUser=getIntent().getStringExtra("inquser");
            prodid=getIntent().getStringExtra("prodname");
            //filtering the most out of the inquiries, if there are similar product
            // inquiries from different users
            ProductInquiry pinq=ProductInquiry.find(ProductInquiry.class,
                    "inquirydescription=? and inquser=? and productname=?",
                     inquiry,inqUser,prodid).get(0);
            long pinqID=pinq.getId();

            try {
                //get the inquiryReplies for the particular inquiry and add to arraylists
                List<InquiryReplies> inquiryReplies=InquiryReplies.find(InquiryReplies.class,
                        "pinquiryid=?", String.valueOf(pinqID));
                for(InquiryReplies inq_replies :inquiryReplies){
                    inq_reply.add(inq_replies.getReply_description());
                    inq_user.add(inq_replies.getInqreply_user());
                }
                inqTV.setText(inquiry.trim());

                rv.setVisibility(View.VISIBLE);
                rvAdapter=new InqReplies_RecyclerViewAdapter(inq_reply,inq_user);
                rv.setAdapter(rvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(this));

                rvAdapter.notifyItemInserted(inq_reply.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
