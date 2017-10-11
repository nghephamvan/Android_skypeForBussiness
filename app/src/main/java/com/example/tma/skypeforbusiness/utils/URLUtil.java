package com.example.tma.skypeforbusiness.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tmvien on 2/14/17.
 */

public class URLUtil {
    public static boolean compareUrl(String s1, String s2){
        try {
            URL url1 = new URL(s1);
            URL url2 = new URL(s2);
            if(url1.getHost().equals(url2.getHost())){
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
