package com.example.dilrukshiperera.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.Models.Cart;
import com.example.dilrukshiperera.styleomega.Models.Orders;
import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;
import com.example.dilrukshiperera.styleomega.Models.User;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name_et;
    private EditText email_et;
    private EditText phoneNo_et;
    private EditText password_et;
    private EditText d_address;
    private EditText confirmpass_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.name_et=findViewById(R.id.nameET);
        this.email_et=findViewById(R.id.EmailET);
        this.password_et=findViewById(R.id.passwordET);
        this.phoneNo_et=findViewById(R.id.phoneET);
        this.d_address=findViewById(R.id.deleveryAddressET);
        this.confirmpass_et=findViewById(R.id.rePasswordET);
    }

    public void signup_onClick(View view) {
        String name=name_et.getText().toString();
        String email=email_et.getText().toString();
        String password=password_et.getText().toString();
        String phone_no=phoneNo_et.getText().toString();
        String address=d_address.getText().toString();
        String repassword=confirmpass_et.getText().toString();

        if(name.isEmpty()||email.isEmpty()||password.isEmpty()||phone_no.isEmpty()||address.isEmpty()
                ||repassword.isEmpty()){
            Toast.makeText(this,"Please fill all the fields.",Toast.LENGTH_SHORT).show();
        }
        else{
            if(password.equals(repassword)){
                User new_user=new User(name,email,password,phone_no,address);
                new_user.save();
                //dedicating a cart at the creation of the user
                Cart cart=new Cart(email);
                cart.save();
                //create shared preference
                SaveSharedPreferenceInstance.setUserEmail(this,email);
                Intent intent = new Intent(RegistrationActivity.this,HomeActivity.class);
                //to clear the back stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this,"Passwords doesn't match.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void alreadySignedin_onClick(View view){
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        finish();
    }
}
