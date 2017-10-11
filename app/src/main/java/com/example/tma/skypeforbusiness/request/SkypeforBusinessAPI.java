package com.example.tma.skypeforbusiness.request;

import com.example.tma.skypeforbusiness.model.ContactNote;
import com.example.tma.skypeforbusiness.model.Events;
import com.example.tma.skypeforbusiness.model.Me.InputMe;
import com.example.tma.skypeforbusiness.model.Me.Me;
import com.example.tma.skypeforbusiness.model.MyContacts;
import com.example.tma.skypeforbusiness.model.ContactPresence;
import com.squareup.okhttp.ResponseBody;

import java.util.HashMap;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by tmvien on 2/14/17.
 */

public interface SkypeforBusinessAPI {
    @GET
    public Call<MyContacts> getContacts(@Url String url);

    @GET
    public Call<Me> getMe(@Url String url);

//    @GET("/SkypeDemo/jsonApi/getContacts")
//    public Call<MyContacts> getContacts(@Query("baseURL") String baseURL, @Query("path") String path, @Query("accessToken") String accessToken);


    @GET("/SkypeDemo/jsonApi/getMe")
    public Call<InputMe> getMe(@Query("baseURL") String baseUrl, @Query("path") String path, @Query("accessToken") String accessToken);

    @GET
    public Call<ResponseBody> getPhoto(@Url String url, @Query("size") String size);

    @GET
    public Call<Events> getEvents(@Url String url, @Query("timeout") String timeout);

    @POST
    public Call<ResponseBody> presenceSubscriptions(@Url String url, @Body HashMap<String, Object> hashMap);

    @GET
    public Call<ContactPresence> contactPresence(@Url String url);

    @GET
    public Call<ContactNote> getContactNode(@Url String url);
}
