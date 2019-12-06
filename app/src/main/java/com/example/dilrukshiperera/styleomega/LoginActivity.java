package com.example.dilrukshiperera.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;
import com.example.dilrukshiperera.styleomega.Models.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET,passET;
    String current_Pass;
//    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            this.emailET=findViewById(R.id.emailLoginET);
            this.passET=findViewById(R.id.passwordLoginET);

    }

    public void signIn_onClick(View view) {
        String uemail=emailET.getText().toString().trim();
        String password=passET.getText().toString().trim();
        //lists all users from the DB and verify password
        List<User> userList=User.listAll(User.class);
        String emailFromDB = null;
        for (User user:userList) {
            emailFromDB=user.getEmail();
            if(emailFromDB.equalsIgnoreCase(uemail) ){
                current_Pass=user.getPassword();
                break;
            }

        }
        //if valid, set the shared preference and pass the user email
        if(current_Pass!=null && current_Pass.equals(password)){
            SaveSharedPreferenceInstance.setUserEmail(this,emailFromDB);

            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
        else if(current_Pass==null){
            Toast.makeText(this,"User doesn't exist. Try Signing up.",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Incorrect password.",Toast.LENGTH_SHORT).show();
        }

    }

    public void signUp_onClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }
}
