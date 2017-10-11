package com.example.tma.skypeforbusiness.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tmvien on 2/22/17.
 */
public class Sender implements Serializable{
    private String rel;
    private String href;
    private ArrayList<Event> events;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}

