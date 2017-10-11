package com.example.tma.skypeforbusiness.model;

import java.io.Serializable;

/**
 * Created by tmvien on 2/23/17.
 */
public class ContactNote implements Serializable {
    private String message;
    private String type;
    private String rel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
