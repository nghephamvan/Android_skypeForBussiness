package com.example.tma.skypeforbusiness.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tmvien on 2/22/17.
 */

public class Events implements Serializable {
    private Links _links;
    private ArrayList<Sender> sender;

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public ArrayList<Sender> getSender() {
        return sender;
    }

    public void setSender(ArrayList<Sender> sender) {
        this.sender = sender;
    }
}