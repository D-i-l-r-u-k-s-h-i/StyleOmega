package com.example.dilrukshiperera.styleomega.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dilrukshiperera.styleomega.DetailedItemActivity;
import com.example.dilrukshiperera.styleomega.InquiryConversationActivity;
import com.example.dilrukshiperera.styleomega.R;
import com.example.dilrukshiperera.styleomega.SupportClasses.ReplyInquiryDialog;


import java.util.ArrayList;

public class Inquiries_RecyclerViewAdapter extends RecyclerView.Adapter<Inquiries_RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> inquiry;
    private ArrayList<String> inq_user;

    private String productname;
    private Context mContext; //if starting an activity on click
    //getting the list of product names through the constructor as well
    public Inquiries_RecyclerViewAdapter(ArrayList<String> inquiry, ArrayList<String> inq_user,Context mContext,
                                         String productname){
        this.inquiry = inquiry;
        this.inq_user = inq_user;
        this.mContext = mContext;
        this.productname=productname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inquiries_rv,viewGroup,
                false);
        ViewHolder viewHolder=new Inquiries_RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Inquiries_RecyclerViewAdapter.ViewHolder viewHolder,final int i) {
        //set the relevant textViews at the position with inquiry and inquirer data
        viewHolder.inquirer_tv.setText(inq_user.get(i));
        viewHolder.inq_tv.setText(inquiry.get(i));
        //on click of the reply icon of the inquiry at the position
        viewHolder.replyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplyInquiryDialog replyInquiryDialog=new ReplyInquiryDialog();
                //to pass the inquiry at the position to the DialogFragment
                Bundle bundle = new Bundle();
                bundle.putString("inquiry",inquiry.get(i));
                bundle.putString("inquirer",inq_user.get(i));
                try {
                    bundle.putString("prodname",productname);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                replyInquiryDialog.setArguments(bundle);
                Activity activity=(Activity) mContext;
                replyInquiryDialog.show(((DetailedItemActivity) activity).getSupportFragmentManager(),
                        "Reply Dialog");
            }
        });
        //on click of viewReplies icon of any inquiry at the position
        viewHolder.viewinqReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent repliesIntent=new Intent(mContext, InquiryConversationActivity.class);
                //pass inquiry, user and the relevant product name along with starting of the intent to
                //set the data in the recycler view adapter in InquiryConversationActivity
                repliesIntent.putExtra("inquiry",inquiry.get(i));
                repliesIntent.putExtra("inquser",inq_user.get(i));
                try {
                    repliesIntent.putExtra("prodname",productname);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(repliesIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inquiry.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView inq_tv;
        TextView inquirer_tv;

        ImageView viewinqReplies;
        ImageView replyIcon;

        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            inq_tv=itemView.findViewById(R.id.InquiryTV);
            inquirer_tv=itemView.findViewById(R.id.inquirerNameTV);

            viewinqReplies=itemView.findViewById(R.id.viewRepliesIV);
            replyIcon=itemView.findViewById(R.id.replyIV);

            parentLayout=itemView.findViewById(R.id.inqItem);
        }
    }
}

