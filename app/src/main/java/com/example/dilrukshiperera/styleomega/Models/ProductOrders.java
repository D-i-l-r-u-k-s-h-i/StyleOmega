package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class ProductOrders extends SugarRecord<ProductOrders> {
    //instance of Order Class- 1 to many
    long orderrid;

    //create an instance of Product - 1 to many
    long productid;

    String quantity;

    public ProductOrders() {
    }

    public ProductOrders(long orderrid, long productid, String quantity) {
        this.orderrid = orderrid;
        this.productid = productid;
        this.quantity = quantity;
    }

    public long getOrderrid() {
        return orderrid;
    }

    public void setOrderrid(long orderrid) {
        this.orderrid = orderrid;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
