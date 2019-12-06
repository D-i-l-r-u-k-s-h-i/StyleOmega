package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class Orders extends SugarRecord<Orders>{
    //String order_id;

    String order_status;
    String order_total;

    //Instance of Cart - 1 to Many
    //Cart cart;
    long cartid;

    public Orders() {
    }

    public Orders(String order_status, String order_total, long cartid) {
        this.order_status = order_status;
        this.order_total = order_total;
        this.cartid = cartid;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public long getCartid() {
        return cartid;
    }

    public void setCartid(long cartid) {
        this.cartid = cartid;
    }
}
