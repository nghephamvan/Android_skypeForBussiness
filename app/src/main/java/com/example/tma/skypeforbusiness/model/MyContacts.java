package com.example.tma.skypeforbusiness.model;

import com.example.tma.skypeforbusiness.model.Applications.Embedded;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 2/14/17.
 */
public class MyContacts implements Serializable {
    private static final long serialVersionUID = 1L;

    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    private String uri;
    private String name;
    private String type;
    private List<String> emailAddresses;
    private String workPhoneNumber;
    private String mobilePhoneNumber;
    private Links _links;
    private String href;
    private String company;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // Client server TMA
    private int errorCode;
    private List<Contact> contact;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<Contact> getContacts() {
        return contact;
    }

    public void setContacts(List<Contact> contact) {
        this.contact = contact;
    }
}
