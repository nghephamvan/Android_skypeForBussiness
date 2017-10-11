package com.example.tma.skypeforbusiness.model.Applications;

import com.example.tma.skypeforbusiness.model.Links;
import com.example.tma.skypeforbusiness.model.MyContacts;
import com.example.tma.skypeforbusiness.model.MyGroups;

import java.io.Serializable;

/**
 * Created by tmvien on 2/14/17.
 */
public class SkypePeople implements Serializable {

    private Links _links;

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
