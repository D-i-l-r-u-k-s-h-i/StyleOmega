package com.example.dilrukshiperera.styleomega.Models;

import com.orm.SugarRecord;

public class InquiryReplies extends SugarRecord<InquiryReplies> {

    String reply_description;

    //instance of Product_Inquiry - 1 to many
    long p_inquiryid;
    String inqreply_user;

    public InquiryReplies() {
    }

    public InquiryReplies(String reply_description, long p_inquiryid, String inqreply_user) {
        this.reply_description = reply_description;
        this.p_inquiryid = p_inquiryid;
        this.inqreply_user = inqreply_user;
    }

    public String getReply_description() {
        return reply_description;
    }

    public void setReply_description(String reply_description) {
        this.reply_description = reply_description;
    }

    public long getP_inquiryid() {
        return p_inquiryid;
    }

    public void setP_inquiryid(long p_inquiryid) {
        this.p_inquiryid = p_inquiryid;
    }

    public String getInqreply_user() {
        return inqreply_user;
    }

    public void setInqreply_user(String inqreply_user) {
        this.inqreply_user = inqreply_user;
    }
}
