package com.example.tma.skypeforbusiness.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tma.skypeforbusiness.R;
import com.example.tma.skypeforbusiness.SkypeforBusinessService;
import com.example.tma.skypeforbusiness.listview.MyContactAdapter;
import com.example.tma.skypeforbusiness.model.Applications.Applications;
import com.example.tma.skypeforbusiness.model.Contact;
import com.example.tma.skypeforbusiness.model.ContactPresence;
import com.example.tma.skypeforbusiness.model.Links;
import com.example.tma.skypeforbusiness.model.Me.InputMe;
import com.example.tma.skypeforbusiness.model.Me.Me;
import com.example.tma.skypeforbusiness.model.Me.Photo;
import com.example.tma.skypeforbusiness.model.MyContacts;
import com.example.tma.skypeforbusiness.model.ContactNote;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessAPI;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessClient;
import com.example.tma.skypeforbusiness.utils.InternalDataUtil;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static String TAG = MainActivity.class.getName();

    private Applications applications;

    //header view
    private TextView name_me;
    private TextView email_me;
    private ImageView image_me;

    private ListView listContact;
    private List<Contact> myContactsList;
    private MyContactAdapter contactAdapter;

    private boolean binded = false;
    private SkypeforBusinessService businessService;
    private Messenger mService = null;

    private SkypeforBusinessAPI api;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.d(TAG, "service connected");
            mService = new Messenger(service);
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            binded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        api = SkypeforBusinessClient.getServerSkype().create(SkypeforBusinessAPI.class);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //load information Me
        View headerView = navigationView.getHeaderView(0);
        name_me = (TextView) headerView.findViewById(R.id.text_name_me);
        email_me = (TextView) headerView.findViewById(R.id.text_email_me);
        image_me = (ImageView) headerView.findViewById(R.id.imageView);

        myContactsList = new ArrayList<>();

        getData();
        //load my contact
        listContact = (ListView) findViewById(R.id.listContact);
        getContacts();

        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "Onlick: " + myContactsList.get(i).getName());
                Intent intent = new Intent(MainActivity.this, MyContactActivity.class);
                intent.putExtra("myContact", myContactsList.get(i));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, SkypeforBusinessService.class);

        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(binded){
//            this.unbindService(serviceConnection);
//            binded = false;
//        }
    }

    private void getData() {
        applications = InternalDataUtil.getInstance().getApplications();
        loadInformationMe();
    }

    private void loadInformationMe() {
        //load information
        Call<Me> meCall = api.getMe(applications.get_embedded().getMe().get_links().getSelf().getHref());//(InternalDataUtil.getInstance().getServerAddress(), applications.get_embedded().getMe().get_links().getSelf().getHref(), InternalDataUtil.getInstance().getToken());
        Log.i(TAG, "Request to: " + meCall.toString());
        meCall.enqueue(new Callback<Me>() {
            @Override
            public void onResponse(Response<Me> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body() != null) {
                        Me me = response.body();
                        String name = me.getName();
                        String email = me.getEmailAddresses().get(0);

                        name_me.setText(name);
                        email_me.setText(email);
                        //load image

                        String linkPhoto = me.get_links().getPhoto().getHref();
                        Call<ResponseBody> call = api.getPhoto(linkPhoto, "HR96X96");
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    if (response.body() != null) {
                                        try {
                                            Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                                            image_me.setImageBitmap(bm);
                                            return;
                                        } catch (IOException e) {
                                            Log.e(TAG, "error get image contact");
                                        }
                                    }
                                }
                                Log.e(TAG, "error get image contact");
                                image_me.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.image_user_picture_default));
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e(TAG, "error get image contact");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "fail get me: " + t.toString());
            }
        });
    }

    private void getContacts() {
        //get list contact
        final MyContacts myContacts = applications.get_embedded().getPeople().get_links().getMyContacts();
        String urlContact = myContacts.getHref();

//        SkypeforBusinessAPI api = SkypeforBusinessClient.getClient().create(SkypeforBusinessAPI.class);
//        Call<MyContacts> myContactsCall = api.getContacts(InternalDataUtil.getInstance().getServerAddress(), urlContact, InternalDataUtil.getInstance().getToken());
        Call<MyContacts> myContactsCall = api.getContacts(urlContact);
        Log.i(TAG, "Request to: " + myContactsCall.toString());
        myContactsCall.enqueue(new Callback<MyContacts>() {
            @Override
            public void onResponse(Response<MyContacts> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body() != null) {
                        Log.i(TAG, "Contacts: " + response.body());
                        List<Contact> lists = response.body().get_embedded().getContact();
                        // presenceSubscriptions
                        String presenceSubscriptions = InternalDataUtil.getInstance().getApplications().get_embedded().getPeople().get_links().getPresenceSubscriptions().getHref();
                        List<String> listContactUri = new ArrayList<String>();
                        for (final Contact contact : lists) {
                            listContactUri.add(contact.getUri());
                            Links _link = contact.get_links();

                            String presencePath = _link.getContactPresence().getHref();
                            String notePath = _link.getContactNote().getHref();
                            final String photoPath = _link.getContactPhoto().getHref();

                            Log.d(TAG, "presencePath: " + presencePath);
                            Call<ContactPresence> presenceCall = api.contactPresence(presencePath);
                            presenceCall.enqueue(new Callback<ContactPresence>() {
                                @Override
                                public void onResponse(Response<ContactPresence> response, Retrofit retrofit) {
                                    if (response.isSuccess()) {
                                        Log.d(TAG, "presencePath: success");
                                        ContactPresence presence = response.body();
                                        contact.setContactPresence(presence);
                                        contactAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "presencePath: fail");
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Log.d(TAG, "presencePath: fail " + t.toString());
                                }
                            });
                            Log.d(TAG, "notePath: " + notePath);
                            Call<ContactNote> noteCall = api.getContactNode(notePath);
                            noteCall.enqueue(new Callback<ContactNote>() {
                                @Override
                                public void onResponse(Response<ContactNote> response, Retrofit retrofit) {
                                    if (response.isSuccess()) {
                                        Log.d(TAG, "notePath: success");
                                        ContactNote note = response.body();
                                        contact.setContactNote(note);
                                        contactAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "notePath: fail");
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                            contact.setContactPhoto(photoPath);

                            myContactsList.add(contact);
                        }

                        //load listview
                        contactAdapter = new MyContactAdapter(MainActivity.this, myContactsList);
                        listContact.setAdapter(contactAdapter);
                        contactAdapter.notifyDataSetChanged();

                        HashMap<String, Object> mHashMap = new HashMap<String, Object>();
                        mHashMap.put("duration", "10");
                        mHashMap.put("Uris", listContactUri);
                        Call<ResponseBody> call = api.presenceSubscriptions(presenceSubscriptions, mHashMap);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    Log.i(TAG, "get presenceSubscriptions: success");
                                } else {
                                    Log.i(TAG, "get presenceSubscriptions: fail with " + response.code());
                                }

                                updateStatus();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.i(TAG, "get presenceSubscriptions: fail with " + t.toString());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "fail get contact: " + t.toString());
            }
        });

    }

    private void updateStatus() {
        if (binded) {
            Message message = Message.obtain(null, SkypeforBusinessService.FETCH_STATUS_USER, 0, 0);
            message.replyTo = replyMessenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    Messenger replyMessenger = new Messenger(new HandlerReplyMsg());

    // handler for message from service
    class HandlerReplyMsg extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HashMap<String, String> map = (HashMap<String, String>) msg.obj;
            String uri = map.get("uri");
            String status = map.get("status");
            String message = map.get("message");
            Log.i(TAG, "User " + uri + " recently " + status + " with message:  " + message);
            for(int i = 0; i < myContactsList.size(); ++ i){
                Contact contact = myContactsList.get(i);
                if(contact.getUri().equals(uri)){
                    //update status
                    if(status != null) {
                        ContactPresence presence = new ContactPresence();
                        presence.setAvailability(status);
                        contact.setContactPresence(presence);
                    }

                    if(message != null) {
                        ContactNote note = new ContactNote();
                        note.setMessage(message);
                        contact.setContactNote(note);
                    }

                    myContactsList.remove(i);
                    myContactsList.add(i, contact);
                    contactAdapter.notifyDataSetChanged();
                }
            }

        }
    }
}
