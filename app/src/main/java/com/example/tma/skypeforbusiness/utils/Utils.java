package com.example.tma.skypeforbusiness.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.tma.skypeforbusiness.R;
import com.example.tma.skypeforbusiness.model.Applications.Applications;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by tmvien on 2/14/17.
 */

public class Utils {
    public static Applications jsonToApplications(JSONObject json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json.toString(), Applications.class);
        } catch (JsonSyntaxException exception) {
            Log.e("LoginActivity", "Json error: " + exception);
        }
        return null;
    }

    public static Drawable getStatusDrawable(Context context, String status) {
        Drawable src = null;
        if (status.equals("Away")) {
            src = context.getResources().getDrawable(R.mipmap.skype_away);
        }
        if (status.equals("Skype_Away")) {
            src = context.getResources().getDrawable(R.mipmap.skype_away);
        }
        if (status.equals("BeRightBack")) {
            src = context.getResources().getDrawable(R.mipmap.skype_away);
        }
        if (status.equals("Busy")) {
            src = context.getResources().getDrawable(R.mipmap.skype_busy);
        }
        if (status.equals("DoNotDisturb")) {
            src = context.getResources().getDrawable(R.mipmap.skype_busy);
        }
        if (status.equals("IdleBusy")) {
            src = context.getResources().getDrawable(R.mipmap.skype_busy);
        }
        if (status.equals("IdleOnline")) {
            src = context.getResources().getDrawable(R.mipmap.skype_available);
        }
        if (status.equals("Online")) {
            src = context.getResources().getDrawable(R.mipmap.skype_available);
        }
        return src;
    }
}
