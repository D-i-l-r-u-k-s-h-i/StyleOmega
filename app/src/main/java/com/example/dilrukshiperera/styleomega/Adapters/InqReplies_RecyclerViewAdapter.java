package com.example.dilrukshiperera.styleomega.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dilrukshiperera.styleomega.R;

import java.util.ArrayList;

public class InqReplies_RecyclerViewAdapter extends RecyclerView.Adapter<InqReplies_RecyclerViewAdapter
                                                                                        .ViewHolder>{
    private ArrayList<String> inq_reply;
    private ArrayList<String> inq_user;

    public InqReplies_RecyclerViewAdapter(ArrayList<String> inq_reply, ArrayList<String> inq_user) {
        this.inq_reply = inq_reply;
        this.inq_user = inq_user;
    }

    @NonNull
    @Override
    public InqReplies_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                        int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inqreplies_rv,viewGroup,
                false);
        InqReplies_RecyclerViewAdapter.ViewHolder viewHolder=new InqReplies_RecyclerViewAdapter
                .ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InqReplies_RecyclerViewAdapter.ViewHolder viewHolder,
                                                                            final int i) {
        viewHolder.inquirer_tv.setText(inq_user.get(i));
        viewHolder.inqreply_tv.setText(inq_reply.get(i));
    }

    @Override
    public int getItemCount() {
        return inq_reply.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView inqreply_tv;
        TextView inquirer_tv;

        ConstraintLayout parentLayoutt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            inqreply_tv=itemView.findViewById(R.id.InqReplyTV);
            inquirer_tv=itemView.findViewById(R.id.inquirerNameTV2);

            parentLayoutt=itemView.findViewById(R.id.inq_replyCL);
        }
    }
}
