package com.example.tma.skypeforbusiness.model.Applications;

import com.example.tma.skypeforbusiness.model.Contact;
import com.example.tma.skypeforbusiness.model.Links;
import com.example.tma.skypeforbusiness.model.Me.Me;
import com.example.tma.skypeforbusiness.model.MyContacts;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 2/14/17.
 */
public class Embedded implements Serializable {
    private Me me;
    private SkypePeople people;
    private OnlineMeetings onlineMeetings;
    private Communication communication;

    private String id;
    private Links _links;

    private List<Contact> contact;

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    public Me getMe() {
        return me;
    }

    public void setMe(Me me) {
        this.me = me;
    }

    public SkypePeople getPeople() {
        return people;
    }

    public void setPeople(SkypePeople people) {
        this.people = people;
    }

    public OnlineMeetings getOnlineMeetings() {
        return onlineMeetings;
    }

    public void setOnlineMeetings(OnlineMeetings onlineMeetings) {
        this.onlineMeetings = onlineMeetings;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
