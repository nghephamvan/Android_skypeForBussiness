package com.example.tma.skypeforbusiness.client;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tmvien on 2/14/17.
 */

public class RequestAsyncTask extends AsyncTask<String, Void, String> {
    private static String TAG = RequestAsyncTask.class.getName();

    public interface RequestAsyncResponse {
        void processFinish(String output);
    }

    public RequestAsyncResponse delegate = null;

    public RequestAsyncTask(RequestAsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        try {
            URL urlRequest = new URL(strings[0]);

            Log.i(TAG, "Request to: " + urlRequest.toString());
            urlConnection = (HttpURLConnection) urlRequest.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            StringBuilder result = new StringBuilder();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.i(TAG, "Result: " + result.toString());
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "error: " + e);
        } catch (IOException e) {
            Log.e(TAG, "error: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
