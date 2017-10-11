package com.example.tma.skypeforbusiness.model;

import com.example.tma.skypeforbusiness.model.Applications.Embedded;

import java.io.Serializable;

/**
 * Created by tmvien on 2/22/17.
 */
public class Event implements Serializable {
    private LinkContain link;
    private String type;
    private LinkContain in;
    private Embedded _Embedded;

    public LinkContain getLink() {
        return link;
    }

    public void setLink(LinkContain link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkContain getIn() {
        return in;
    }

    public void setIn(LinkContain in) {
        this.in = in;
    }

    public Embedded get_Embedded() {
        return _Embedded;
    }

    public void set_Embedded(Embedded _Embedded) {
        this._Embedded = _Embedded;
    }
}
