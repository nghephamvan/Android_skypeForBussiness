package com.example.tma.skypeforbusiness.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tma.skypeforbusiness.R;
import com.example.tma.skypeforbusiness.model.Contact;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessAPI;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessClient;
import com.example.tma.skypeforbusiness.utils.Utils;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tmvien on 2/15/17.
 */
public class MyContactActivity extends Activity {
    private static String TAG = MyContactActivity.class.getSimpleName();

    private Contact contact;
    private TextView name;
    private ImageView photo, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_contact);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            contact = (Contact) bundle.getSerializable("myContact");
        }
        name = (TextView) findViewById(R.id.text_name_contact);
        photo = (ImageView) findViewById(R.id.image_my_contact);
        status = (ImageView) findViewById(R.id.image_info_status);

        if (contact != null) {
            showData();
        }

    }

    private void showData() {
        name.setText(contact.getName());
        if(contact.getContactPresence() != null) {
            status.setImageDrawable(Utils.getStatusDrawable(getApplicationContext(), contact.getContactPresence().getAvailability()));
        }
        String linkPhoto = contact.getContactPhoto();
        SkypeforBusinessAPI api = SkypeforBusinessClient.getServerSkype().create(SkypeforBusinessAPI.class);
        Call<ResponseBody> call = api.getPhoto(linkPhoto, "HR96X96");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body() != null) {

                        try {
                            Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                            photo.setImageBitmap(bm);
                            return;
                        } catch (IOException e) {
                            Log.e(TAG, "error get image contact");
                        }
                    }
                }
                Log.e(TAG, "error get image contact");
                photo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.image_user_picture_default));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "error get image contact");
            }
        });
    }
}
