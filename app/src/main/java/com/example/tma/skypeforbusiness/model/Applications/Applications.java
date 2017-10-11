package com.example.tma.skypeforbusiness.model.Applications;


import com.example.tma.skypeforbusiness.model.Links;

import java.io.Serializable;

/**
 * Created by tmvien on 2/14/17.
 */
public class Applications implements Serializable {
    private String culture;
    private String userAgent;
    private Links _links;
    private Embedded _embedded;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }
}
