package com.example.tma.skypeforbusiness.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tmvien on 2/17/17.
 */
public class Contact implements Serializable {

    private String company;
    private String department;
    private ArrayList<String> emailAddresses;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String name;
    private String uri;
    private String workPhoneNumber;
    private String contactPhoto;
    private ContactPresence contactPresence;
    private ContactNote contactNote;
    private Links _links;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(ArrayList<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(String contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public ContactPresence getContactPresence() {
        return contactPresence;
    }

    public void setContactPresence(ContactPresence contactPresence) {
        this.contactPresence = contactPresence;
    }

    public ContactNote getContactNote() {
        return contactNote;
    }

    public void setContactNote(ContactNote contactNote) {
        this.contactNote = contactNote;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
