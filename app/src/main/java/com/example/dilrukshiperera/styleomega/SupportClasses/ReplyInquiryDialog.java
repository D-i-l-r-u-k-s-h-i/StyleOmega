package com.example.dilrukshiperera.styleomega.SupportClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dilrukshiperera.styleomega.R;

public class ReplyInquiryDialog extends AppCompatDialogFragment {
    private EditText replyInqET;
    private ReplyInqDialogListener mlistner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dbuilder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.reply_inquiry_dialog,null);

        dbuilder.setView(view).setTitle("Reply to inquiry").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit
            }
        }).setPositiveButton("Post Reply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = getArguments();
                String inq="";
                String inqUser="";
                String productname="";
                if (bundle != null) {
                    inq = bundle.getString("inquiry");
                    inqUser=bundle.getString("inquirer");
                    productname=bundle.getString("prodname");
                }
                String inqReply=replyInqET.getText().toString();
                mlistner.applyInqReply(inqReply,inq,inqUser,productname);
            }
        });

        replyInqET=view.findViewById(R.id.inquiryreplyET);
        return dbuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistner=(ReplyInqDialogListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString()+" must implement ReplyInqDialogListener");
        }
    }

    public interface ReplyInqDialogListener{
        void applyInqReply(String inqReply,String inquiry,String inqUser,String prodname);
    }
}
