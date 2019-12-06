package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class Cart extends SugarRecord<Cart> {
    //instance of User- 1 to 1
    //User user;
    String u_email;

    //String cart_id; //cartID gets created automatically as an auto incrementing key

    public Cart() {
    }

    public Cart(String u_email) {
        this.u_email = u_email;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }
}
