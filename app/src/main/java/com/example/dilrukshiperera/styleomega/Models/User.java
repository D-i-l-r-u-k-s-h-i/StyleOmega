package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class User extends SugarRecord<User>{
    String uname;
    String email;
    String password;
    String contact_no;
    String del_address;

    public User() {
    }

    public User(String uname, String email, String password, String contact_no, String del_address) {
        this.uname = uname;
        this.email = email;
        this.password = password;
        this.contact_no = contact_no;
        this.del_address = del_address;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDel_address() {
        return del_address;
    }

    public void setDel_address(String del_address) {
        this.del_address = del_address;
    }
}
