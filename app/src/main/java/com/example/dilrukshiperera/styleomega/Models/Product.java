package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class Product extends SugarRecord<Product> {
    //String product_id;

    String pname;
    String pshort_description;
    String plong_description;
    Double p_price;
    Double rating;
    String category;
    String available_sizes;
    boolean availability_status;
    int prod_quantity;
    String p_image;

    public Product() {
    }

    public Product(String pname, String pshort_description, String plong_description, Double p_price, Double rating, String category, String available_sizes, boolean availability_status, int prod_quantity, String p_image) {
        this.pname = pname;
        this.pshort_description = pshort_description;
        this.plong_description = plong_description;
        this.p_price = p_price;
        this.rating = rating;
        this.category = category;
        this.available_sizes = available_sizes;
        this.availability_status = availability_status;
        this.prod_quantity = prod_quantity;
        this.p_image = p_image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPshort_description() {
        return pshort_description;
    }

    public void setPshort_description(String pshort_description) {
        this.pshort_description = pshort_description;
    }

    public String getPlong_description() {
        return plong_description;
    }

    public void setPlong_description(String plong_description) {
        this.plong_description = plong_description;
    }

    public Double getP_price() {
        return p_price;
    }

    public void setP_price(Double p_price) {
        this.p_price = p_price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailable_sizes() {
        return available_sizes;
    }

    public void setAvailable_sizes(String available_sizes) {
        this.available_sizes = available_sizes;
    }

    public boolean isAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(boolean availability_status) {
        this.availability_status = availability_status;
    }

    public int getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(int prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }
}
