package com.example.tma.skypeforbusiness.model;

import java.io.Serializable;

/**
 * Created by tmvien on 2/22/17.
 */
public class LinkContain implements Serializable{
    private String rel;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
