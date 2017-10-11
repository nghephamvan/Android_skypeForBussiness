package com.example.tma.skypeforbusiness.model;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by tmvien on 2/13/17.
 */

public class AccessToken {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private Integer expires_on;
    private String resource;
    private String refresh_token;
    private String scope;
    private String id_token;
    private String session_state;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpries_in() {
        return expires_in;
    }

    public void setExpries_in(Integer expries_in) {
        this.expires_in = expries_in;
    }

    public Integer getExpires_on() {
        return expires_on;
    }

    public void setExpires_on(Integer expires_on) {
        this.expires_on = expires_on;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public AccessToken(){

    }

    public AccessToken(String str) {
        String frament = "";
        try {
            URL url = new URL(str);
            frament = url.toURI().getFragment();
            String[] parameters = frament.split("&");
            for (String param : parameters) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    String key = pair[0];
                    String value = pair[1];
                    if (key.equals("access_token")) {
                        access_token = value;
                    } else if (key.equals("token_type")) {
                        token_type = value;
                    } else if (key.equals("expires_in")) {
                        expires_in = Integer.getInteger(value);
                    } else if (key.equals("session_state")) {
                        session_state = value;
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
