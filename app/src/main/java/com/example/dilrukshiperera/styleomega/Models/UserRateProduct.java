package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class UserRateProduct extends SugarRecord<UserRateProduct> {
    //create an instance of Product - 1 to many
    long product_id;

    //instance of User- 1 to many
    String useremail;

    float user_rating; //the individual rating of a user, for a particular product

    public UserRateProduct() {
    }

    public UserRateProduct(long product_id, String useremail, float user_rating) {
        this.product_id = product_id;
        this.useremail = useremail;
        this.user_rating = user_rating;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public float getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }
}
