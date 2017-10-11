package com.example.tma.skypeforbusiness.model.Me;

import com.example.tma.skypeforbusiness.model.Links;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 2/14/17.
 */

public class Me implements Serializable {

    private String uri;
    private String name;
    private List<String> emailAddresses;
    private String title;
    private String department;
    private String officeLocation;
    private String mePhoto;
    private String mePresence;
    private Links _links;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public String getMePhoto() {
        return mePhoto;
    }

    public void setMePhoto(String mePhoto) {
        this.mePhoto = mePhoto;
    }

    public String getMePresence() {
        return mePresence;
    }

    public void setMePresence(String mePresence) {
        this.mePresence = mePresence;
    }
}
