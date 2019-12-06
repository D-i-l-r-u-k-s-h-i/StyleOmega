package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class ProductInquiry extends SugarRecord<ProductInquiry> {

    //String inquiry_id;

    String inquiry_description;

    //create an instance of Product - 1 to many
    String productname;
    String inquser;

    public ProductInquiry() {
    }

    public ProductInquiry(String inquiry_description, String productname, String inquser) {
        this.inquiry_description = inquiry_description;
        this.productname = productname;
        this.inquser = inquser;
    }

    public String getInquiry_description() {
        return inquiry_description;
    }

    public void setInquiry_description(String inquiry_description) {
        this.inquiry_description = inquiry_description;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getInquser() {
        return inquser;
    }

    public void setInquser(String inquser) {
        this.inquser = inquser;
    }
}
