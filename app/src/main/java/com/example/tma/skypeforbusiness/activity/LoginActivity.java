package com.example.tma.skypeforbusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tma.skypeforbusiness.R;
import com.example.tma.skypeforbusiness.SkypeforBusinessService;
import com.example.tma.skypeforbusiness.model.AccessToken;
import com.example.tma.skypeforbusiness.model.Applications.Applications;
import com.example.tma.skypeforbusiness.utils.InternalDataUtil;
import com.example.tma.skypeforbusiness.utils.URLUtil;
import com.example.tma.skypeforbusiness.utils.UUIDUtil;
import com.example.tma.skypeforbusiness.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getName();

    private String SCHEME = "https";

    private WebView mWebView;

    private String request;
    private String clientId;
    private String redirectURI;
    private String authenticationURLString;
    private String resource;
    private String autoDiscoverService;

    private AccessToken token;
    private boolean isAuthorization = false;
    private boolean isLogin = false;

    private ProgressDialog progressDialog;
    private boolean isShouldLoading = false;

    private String urlUser;
    private String urlApplications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDomStorageEnabled(true);

        mWebView.setWebViewClient(new SkypeWebViewClient());

        //setup basic login information
        clientId = "7d6a2d7c-6761-4297-8274-a4e3bfb2b16c";
        redirectURI = "https://example.com";
        authenticationURLString = "login.microsoftonline.com";
        autoDiscoverService = "https://webdir.online.lync.com/autodiscover/autodiscoverservice.svc/root";
        resource = "https://webdir.online.lync.com";

        azureADAuthorization(resource);
    }

    private class SkypeWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "should override url loading");
            if (url.startsWith(redirectURI)) {
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "start page on webview");
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging...");
                return;
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            isShouldLoading = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "finish page on webview");
            Log.d(TAG, url);
            if (!isShouldLoading) {
                progressDialog.dismiss();
            }
            if (url.contains(redirectURI) && !isLogin) {
                token = new AccessToken(url);
                autodiscoveryWithAuthorization();
            } else if (isLogin) {
                //begin login
                token = new AccessToken(url);
                getApplication(urlApplications);
            }
        }

        private void autodiscoveryWithAuthorization() {
            Log.i(TAG, "Autodiscovery/Re-Autodiscovery");
            requestURL().execute(autoDiscoverService);
        }

        private AsyncTask<String, Void, String> requestURL() {
            return new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... strings) {
                    HttpURLConnection urlConnection = null;
                    try {
                        URL urlRequest = new URL(strings[0]);

                        Log.i(TAG, "Request to: " + urlRequest.toString());
                        urlConnection = (HttpURLConnection) urlRequest.openConnection();
                        urlConnection.setRequestMethod("GET");

                        Log.i(TAG, "isAuthorization : " + isAuthorization);
                        if (isAuthorization) {
                            String authValue = "Bearer " + token.getAccess_token();
                            urlConnection.setRequestProperty("Authorization", authValue);
                            Log.i(TAG, "Authorization: " + authValue);

                        }
                        urlConnection.connect();

                        StringBuilder result = new StringBuilder();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        Log.i(TAG, "Result: " + result.toString());

                        String homePool = "";
                        JSONObject object = new JSONObject(result.toString());
                        JSONObject links = object.getJSONObject("_links");
                        if (isAuthorization) {
                            if (links.has("redirect")) {
                                String redirect = links.getJSONObject("redirect").getString("href");
                                Log.i(TAG, "redirect: " + redirect);
                                homePool = redirect;
                                isAuthorization = false;
                            } else if (links.has("user")) {
                                String user = links.getJSONObject("user").getString("href");
                                Log.i(TAG, "user: " + user);
                                getUser(user);
                            } else if (links.has("applications")) {
                                String application = links.getJSONObject("applications").getString("href");
                                isLogin = true;
                                return application;
                            }
                        } else {
                            homePool = links.getJSONObject("user").getString("href");
                        }
                        Log.i(TAG, "home Pool: " + homePool);
                        return homePool;
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "error: " + e);
                    } catch (IOException e) {
                        Log.e(TAG, "error: " + e);
                    } catch (JSONException e) {
                        Log.e(TAG, "error: " + e);
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String homePool) {
                    if (homePool == null || homePool.length() == 0) {
                        Log.w(TAG, "data is null");
                        return;
                    }
                    try {
                        if (isLogin) {
                            Log.d(TAG, "123.user: " + urlUser);
                            Log.d(TAG, "123.applications: " + homePool);
                            Log.d(TAG, "123.compare: " + URLUtil.compareUrl(urlUser, homePool));
                            if (URLUtil.compareUrl(urlUser, homePool)) {
                                //same host no need to authentication again
                                progressDialog.dismiss();
                                getApplication(homePool);
                                return;
                            }
                            Log.i(TAG, "authenticate with application host");

                            URL url = new URL(homePool);
                            urlApplications = homePool;
                            azureADAuthorization(url.getProtocol() + "://" + url.getHost());
                            return;
                        }

                        autoDiscoverService = homePool;
                        if (!isAuthorization) {
                            URL url = new URL(homePool);
                            Log.i(TAG, "Requesting an access token using implicit grant flow");
                            azureADAuthorization(url.getProtocol() + "://" + url.getHost());
                            isAuthorization = true;
                            return;
                        }
                        autodiscoveryWithAuthorization();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        private void getUser(String user) {
            Log.i(TAG, "get User");
            urlUser = user;
            requestURL().execute(user);
        }

        private void getApplication(final String applications) {
            new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected JSONObject doInBackground(String... strings) {
                    HttpURLConnection urlConnection = null;
                    URL urlRequest = null;
                    try {
                        urlRequest = new URL(applications);
                        Log.i(TAG, "Request to: " + urlRequest.toString());
                        urlConnection = (HttpURLConnection) urlRequest.openConnection();
                        urlConnection.setRequestMethod("POST");
                        String authValue = "Bearer " + token.getAccess_token();
                        Log.i(TAG, "Auth: " + authValue);
                        //Save authValue
                        InternalDataUtil.getInstance().setToken(token.getAccess_token());
                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put("UserAgent", "NGC1.7 Demo");
                        postDataParams.put("EndpointId", "ed06bbfd-d093-4a1a-b4bd-f9a0ab4d4789");
                        postDataParams.put("Culture", "en-US");

                        urlConnection.setRequestProperty("Authorization", authValue);
                        urlConnection.setRequestProperty("Content-Type", "application/json");
                        urlConnection.setRequestProperty("Accept", "application/json");
                        urlConnection.setRequestProperty("Content-Length", "" +
                                Integer.toString(postDataParams.toString().length()));
                        urlConnection.setRequestProperty("Content-Language", "en-US");

                        urlConnection.setUseCaches(false);
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);

                        Log.i(TAG, "json: " + getPostDataString(postDataParams));
                        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                        writer.write(postDataParams.toString());
                        writer.flush();

                        //display what returns the POST request
                        StringBuilder sb = new StringBuilder();
                        int HttpResult = urlConnection.getResponseCode();
                        if (HttpResult >= HttpURLConnection.HTTP_OK && HttpResult < HttpURLConnection.HTTP_BAD_REQUEST) {
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                            String line = null;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                        } else {
                            Log.w(TAG, "status: " + HttpResult + " error: " + urlConnection.getResponseMessage());
                            return null;
                        }
                        Log.i(TAG, "Result: " + sb.toString());
                        return new JSONObject(sb.toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(JSONObject object) {
                    if (object == null || object.length() == 0) {
                        Log.i(TAG, "get application fail");
                        Toast.makeText(LoginActivity.this, "get application error !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //save internal data
                    try {
                        URL urlAddress = new URL(applications);
                        InternalDataUtil.getInstance().setServerAddress(SCHEME + "://" + urlAddress.getHost());
                        getData(object);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void azureADAuthorization(String resource) {
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
        if (!resource.equals(this.resource)) {
            builder.appendQueryParameter("state", UUIDUtil.getUUID());
        }
        request = builder.build().toString();
        Log.e(TAG, "Request to: " + request);
        mWebView.loadUrl(request);
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private void getData(JSONObject json) {
        Applications applications = Utils.jsonToApplications(json);
        InternalDataUtil.getInstance().setEvents(applications.get_links().getEvents().getHref());
        InternalDataUtil.getInstance().setApplications(applications);
        startActivity((new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));
        finish();

    }

}
