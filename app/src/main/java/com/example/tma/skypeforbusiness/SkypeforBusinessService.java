package com.example.tma.skypeforbusiness;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.tma.skypeforbusiness.model.Event;
import com.example.tma.skypeforbusiness.model.Events;
import com.example.tma.skypeforbusiness.model.MyContacts;
import com.example.tma.skypeforbusiness.model.ContactPresence;
import com.example.tma.skypeforbusiness.model.Sender;
import com.example.tma.skypeforbusiness.model.ContactNote;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessAPI;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessClient;
import com.example.tma.skypeforbusiness.utils.InternalDataUtil;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tmvien on 2/21/17.
 */

public class SkypeforBusinessService extends Service {
    private static final String TAG = SkypeforBusinessService.class.getSimpleName();

    public static final int FETCH_STATUS_USER = 1;
//    public static final int FETCH_NODE_USER = 2;

    private Messenger replyStatusMessage;
//    private Messenger replyNodeMessage;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "service binding");
        return mMessenger.getBinder();
    }


    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FETCH_STATUS_USER:
                    Log.d(TAG, "service Request Received: status");
                    replyStatusMessage = msg.replyTo;
                    registerStatus();
                    break;
//                case FETCH_NODE_USER:
//                    Log.d(TAG, "service Request Received: node");
//                    replyNodeMessage = msg.replyTo;
//                    registerStatus();
//                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "Service onRebind");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed");
    }

    public void registerStatus() {
        Log.i(TAG, "registerStatus");

        requestEvents(InternalDataUtil.getInstance().getEvents());
    }

    private void requestEvents(String url) {
        SkypeforBusinessAPI api = SkypeforBusinessClient.getServerSkype().create(SkypeforBusinessAPI.class);
        Call<Events> call = api.getEvents(url, "180");
        Log.i(TAG, "Request to: " + call.toString());
        call.enqueue(new Callback<Events>() {

            @Override
            public void onResponse(Response<Events> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    Events events = response.body();
                    Log.i(TAG, "get Event success: ");

                    if (events.get_links().getNext().getHref() != null && events.get_links().getNext().getHref().length() != 0) {
                        Log.i(TAG, "Request: " + events.get_links().getNext().getHref());
                        requestEvents(events.get_links().getNext().getHref());
                    } else if (events.get_links().getResync().getHref() != null && events.get_links().getResync().getHref().length() != 0) {
                        Log.i(TAG, "Request: " + events.get_links().getResync().getHref());
                        requestEvents(events.get_links().getResync().getHref());
                    } else if (events.get_links().getResume().getHref() != null && events.get_links().getResume().getHref().length() != 0) {
                        Log.i(TAG, "Request: " + events.get_links().getResume().getHref());
                        requestEvents(events.get_links().getResume().getHref());
                    } else {
                        Log.i(TAG, ".......");
                    }

                    updateStatus(events.getSender());

                } else {
                    Log.i(TAG, "get Event fail: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, "get Event fail: " + t);
            }
        });
    }

    private void updateStatus(ArrayList<Sender> senders) {
        final SkypeforBusinessAPI api = SkypeforBusinessClient.getServerSkype().create(SkypeforBusinessAPI.class);
        for (Sender sender : senders) {
            if (sender.getRel().equals("me")) {
                List<Event> events = sender.getEvents();
                //Handle update contact presence
                for (final Event event : events) {
                    if (event.getLink().getRel().equals("presence")) {
                        String presencePath = event.getLink().getHref();
                        if (presencePath.length() != 0) {
                            Log.i(TAG, "discover me presence changed");
                            Call<ContactPresence> call = api.contactPresence(presencePath);
                            call.enqueue(new Callback<ContactPresence>() {

                                @Override
                                public void onResponse(Response<ContactPresence> response, Retrofit retrofit) {
                                    if (response.isSuccess()) {
                                        ContactPresence events = response.body();
                                        Log.i(TAG, "get presence success: ");
//                                        try {
//                                            Message message = new Message();
//                                            message.obj = responseData;
//                                            replyStatusMessage.send(message);
//                                        } catch (RemoteException e) {
//                                            e.printStackTrace();
//                                        }
                                    } else {
                                        Log.i(TAG, "get presence fail: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Log.i(TAG, "get Event fail: " + t);
                                }
                            });
                        }
                    }
                }
            } else if (sender.getRel().equals("people")) {
                Log.i(TAG, "Change people");
                List<Event> events = sender.getEvents();
                //Handle update contact presence
                for (Event event : events) {
                    Log.i(TAG, "Change people: " + event.getLink().getRel());
                    if (event.getType().equals("updated")) {
                        if (event.getLink().getRel().equals("contactPresence")) {
                            final String presencePath = event.getLink().getHref();
                            String inPath = event.getIn().getHref();

                            if (presencePath.length() != 0) {
                                Log.i(TAG, "discover people presence changed");
                                Call<MyContacts> callGetContact = api.getContacts(inPath);

                                callGetContact.enqueue(new Callback<MyContacts>() {

                                    @Override
                                    public void onResponse(Response<MyContacts> response, Retrofit retrofit) {
                                        if (response.isSuccess()) {
                                            final MyContacts myContact = response.body();
                                            Log.i(TAG, "get in path success: " + myContact.getUri());
                                            Log.i(TAG, "request to: " + presencePath);
                                            Call<ContactPresence> callPresence = api.contactPresence(presencePath);
                                            callPresence.enqueue(new Callback<ContactPresence>() {
                                                @Override
                                                public void onResponse(Response<ContactPresence> response, Retrofit retrofit) {
                                                    if (response.isSuccess()) {
                                                        Log.i(TAG, "get presence success. Status: " + response.body().getAvailability());
                                                        try {
                                                            Message message = new Message();
                                                            HashMap<String, String> mData = new HashMap<String, String>();
                                                            mData.put("uri", myContact.getUri());
                                                            mData.put("status", response.body().getAvailability());
                                                            message.obj = mData;
                                                            replyStatusMessage.send(message);
                                                        } catch (RemoteException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Throwable t) {

                                                }
                                            });
                                        } else {
                                            Log.i(TAG, "get in path fail: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Log.i(TAG, "get in path fail: " + t);
                                    }
                                });

                            }
                        } else if (event.getLink().getRel().equals("presenceSubscription")) {
                            if(event.get_Embedded() != null && event.get_Embedded().get_links() != null) {
                                String presenceSubscription = event.get_Embedded().get_links().getSelf().getHref();
                                Log.i(TAG, "presenceSubscriptionPath: " + presenceSubscription);
                                HashMap<String, Object> mHashMap = new HashMap<String, Object>();
                                mHashMap.put("duration", "10");
                                Call<ResponseBody> call = api.presenceSubscriptions(presenceSubscription, mHashMap);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                                        if (response.isSuccess()) {
                                            Log.i(TAG, "get presenceSubscriptions: success");
                                        } else {
                                            Log.i(TAG, "get presenceSubscriptions: fail with " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Log.i(TAG, "get presenceSubscriptions: fail with " + t.toString());
                                    }
                                });
                            }
                        } else if(event.getLink().getRel().equals("contactNote")){
                            final String nodePath = event.getLink().getHref();
                            String inPath = event.getIn().getHref();

                            if (nodePath.length() != 0) {
                                Log.i(TAG, "discover people node changed");
                                Call<MyContacts> callGetContact = api.getContacts(inPath);

                                callGetContact.enqueue(new Callback<MyContacts>() {

                                    @Override
                                    public void onResponse(Response<MyContacts> response, Retrofit retrofit) {
                                        if (response.isSuccess()) {
                                            final MyContacts myContact = response.body();
                                            Log.i(TAG, "get in path success: " + myContact.getUri());
                                            Log.i(TAG, "request to: " + nodePath);
                                            Call<ContactNote> callPresence = api.getContactNode(nodePath);
                                            callPresence.enqueue(new Callback<ContactNote>() {
                                                @Override
                                                public void onResponse(Response<ContactNote> response, Retrofit retrofit) {
                                                    if (response.isSuccess()) {
                                                        Log.i(TAG, "get presence success. Status: " + response.body().getMessage());
                                                        try {
                                                            Message message = new Message();
                                                            HashMap<String, String> mData = new HashMap<String, String>();
                                                            mData.put("uri", myContact.getUri());
                                                            mData.put("message", response.body().getMessage());
                                                            message.obj = mData;
                                                            replyStatusMessage.send(message);
                                                        } catch (RemoteException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Throwable t) {

                                                }
                                            });
                                        } else {
                                            Log.i(TAG, "get in path fail: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Log.i(TAG, "get in path fail: " + t);
                                    }
                                });

                            }
                        }
                    }
                }
            }
        }

    }
}
