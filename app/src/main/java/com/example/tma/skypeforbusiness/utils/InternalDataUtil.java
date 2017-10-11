package com.example.tma.skypeforbusiness.utils;

import com.example.tma.skypeforbusiness.model.Applications.Applications;

/**
 * Created by tmvien on 2/14/17.
 */

public class InternalDataUtil {
    private String serverAddress;
    private Applications applications;
    private String token;
    private String events;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Applications getApplications() {
        return applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    private static final InternalDataUtil holder = new InternalDataUtil();

    public static InternalDataUtil getInstance() {
        return holder;
    }


}
