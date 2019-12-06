package com.example.dilrukshiperera.styleomega;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;
import com.example.dilrukshiperera.styleomega.Models.User;


public class EditProfileFragment extends Fragment {

    private EditText update_nameET;
    private EditText update_delAddressET;
    private EditText update_contactNoET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container,
                false);

        update_nameET=rootView.findViewById(R.id.editNameET);
        update_delAddressET=rootView.findViewById(R.id.editAddressET);
        update_contactNoET=rootView.findViewById(R.id.editContectNoET);


        View saveButton = rootView.findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save_onClick(v);
            }

        });

        return rootView;
    }

    public void save_onClick(View view){
        String session_email=SaveSharedPreferenceInstance.getUserEmail(getContext());
        //because the email is unique .get(0) is used
        User user= User.find(User.class,"email=?",session_email).get(0);
     //updating only if the EditText fields are not null, to prevent empty strings
        if(update_nameET.length()!=0){
            user.setUname(update_nameET.getText().toString().trim());
        }

        if(update_delAddressET.length()!=0){
            user.setDel_address(update_delAddressET.getText().toString().trim());
        }

        if(update_contactNoET.length()!=0){
            user.setContact_no(update_contactNoET.getText().toString().trim());
        }
        user.save();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ProfileFragment());
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
