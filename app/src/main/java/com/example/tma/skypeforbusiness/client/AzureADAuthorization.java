package com.example.tma.skypeforbusiness.client;

import android.net.Uri;

import com.example.tma.skypeforbusiness.utils.UUIDUtil;

/**
 * Created by tmvien on 2/14/17.
 */
public class AzureADAuthorization {

    private String request;
    private String clientId;
    private String redirectURI;
    private String authenticationURLString;
    private String resource;
    private String autoDiscoverService;

    private static AzureADAuthorization ourInstance = new AzureADAuthorization();

    public static AzureADAuthorization getInstance() {
        return ourInstance;
    }

    private AzureADAuthorization() {
        //setup basic login information
        clientId = "7d6a2d7c-6761-4297-8274-a4e3bfb2b16c";
        redirectURI = "https://example.com";
        authenticationURLString = "login.microsoftonline.com";
        resource = "https://webdir.online.lync.com";
        autoDiscoverService = "https://webdir.online.lync.com/autodiscover/autodiscoverservice.svc/root";
    }

    public String azureADAuthorization(String resource, boolean isAuthorization){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(authenticationURLString)
                .appendPath("common")
                .appendPath("oauth2")
                .appendPath("authorize")
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("redirect_uri", redirectURI)
                .appendQueryParameter("response_type", "token")
                .appendQueryParameter("resource", resource);
        if(!isAuthorization) {
            builder.appendQueryParameter("state", UUIDUtil.getUUID());
        }
        builder.build();
        return null;
    }
}
