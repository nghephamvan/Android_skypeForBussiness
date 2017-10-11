package com.example.tma.skypeforbusiness.model;

import java.io.Serializable;

/**
 * Created by tmvien on 2/22/17.
 */

public class ContactPresence implements Serializable {
    private String availability;
    private Links _links;
    private String rel;
    private String activity;

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
