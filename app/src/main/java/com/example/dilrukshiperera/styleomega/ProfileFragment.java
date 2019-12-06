package com.example.dilrukshiperera.styleomega;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;
import com.example.dilrukshiperera.styleomega.Models.User;

import java.util.List;


public class ProfileFragment extends Fragment {

    private TextView customerNameTV;
    private TextView deleveryAddressTV;
    private TextView contactNumberTV;
    private TextView loginEmailTV;

    private TextView resetpassTV;
    private TextView deleteAccTV;
    private Button changePassBtn;

    private EditText currentPasswordET;
    private EditText newPasswordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        customerNameTV=rootView.findViewById(R.id.custNameTV);
        deleveryAddressTV=rootView.findViewById(R.id.delAddressTV);
        contactNumberTV=rootView.findViewById(R.id.contactNoTV);
        loginEmailTV=rootView.findViewById(R.id.emailforLoginTV);

        deleteAccTV=rootView.findViewById(R.id.deleteAccountTV);
        resetpassTV=rootView.findViewById(R.id.resetPassTV);
        changePassBtn=rootView.findViewById(R.id.changePasswordBtn);

        currentPasswordET=rootView.findViewById(R.id.currentPassET);
        newPasswordET=rootView.findViewById(R.id.newPasswordET);

        View editButton = rootView.findViewById(R.id.editBtn);
        View changePasswordBtn=rootView.findViewById(R.id.changePasswordBtn);

        String session_email=SaveSharedPreferenceInstance.getUserEmail(getContext());

        List<User> userList=User.listAll(User.class);

        for (User user:userList) {
            String user_email=user.getEmail();
            if(user_email.equalsIgnoreCase(session_email)){
                customerNameTV.setText(user.getUname());
                deleveryAddressTV.setText(user.getDel_address());
                contactNumberTV.setText(user.getContact_no());
                loginEmailTV.setText(session_email);
            }
        }

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edit_onClick(v);
            }

        });

        resetpassTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetPassword_onClick(v);
            }

        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePassword_onClick(v);
            }

        });

        deleteAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAcc_onClick(v);
            }
        });

        return rootView;
    }

    public void resetPassword_onClick(View view){
        currentPasswordET.setVisibility(View.VISIBLE);
        newPasswordET.setVisibility(View.VISIBLE);
        changePassBtn.setVisibility(View.VISIBLE);
    }

    public void edit_onClick(View view){ //lead the user to EditProfileFragment()
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new EditProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changePassword_onClick(View view){
        String current_userPass=currentPasswordET.getText().toString().trim();
        //get the emil from shared preference
        String session_email= SaveSharedPreferenceInstance.getUserEmail(getContext());
        //filter the user object with the session_email
        User user= User.find(User.class,"email=?",session_email).get(0);
        //compare the entered current password with the users actual password.
        if(current_userPass.equals(user.getPassword())){
            //save new password if the verification is correct
            user.setPassword(newPasswordET.getText().toString().trim());
            user.save();
            //set the widgets to gone state, which would not require layout space.
            currentPasswordET.setVisibility(View.GONE);
            newPasswordET.setVisibility(View.GONE);
            changePassBtn.setVisibility(View.GONE);

            Toast.makeText(getActivity(),"Password got Changed.",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(),"Incorrect Current Password.",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAcc_onClick(View view){
        //Dialog box popup
        AlertDialog.Builder abuilder=new AlertDialog.Builder(getActivity());
        abuilder.setMessage("Are you sure you want to DELETE your StyleOmega Account?").setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete from the db
                String session_email= SaveSharedPreferenceInstance.getUserEmail(getContext());
                User user= User.find(User.class,"email=?",session_email).get(0);
                //if the alert dialog value is yes
                user.delete();
                Toast.makeText(getContext(),"The account got deleted.",Toast.LENGTH_SHORT).show();
                //clear shared preference
                SaveSharedPreferenceInstance.clearUser(getContext());
                startActivity(new Intent(getActivity(),LoginActivity.class));
                //check whether this gets added to the backStack
            }
        }).setNegativeButton("No",null);

        AlertDialog confirmAlert=abuilder.create();
        confirmAlert.show();
    }
}

