package com.example.tma.skypeforbusiness.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tma.skypeforbusiness.R;
import com.example.tma.skypeforbusiness.model.Contact;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessAPI;
import com.example.tma.skypeforbusiness.request.SkypeforBusinessClient;
import com.example.tma.skypeforbusiness.utils.ImageDownloaderUtil;
import com.example.tma.skypeforbusiness.utils.InternalDataUtil;
import com.example.tma.skypeforbusiness.utils.Utils;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tmvien on 2/14/17.
 */

public class MyContactAdapter extends BaseAdapter {
    private static String TAG = MyContactAdapter.class.getName();

    private Context context;
    private List<Contact> contactList;
    private static LayoutInflater inflater = null;
    public ViewHolder holder;

    public MyContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
        inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_contact_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text_name_my_contact);
            holder.photo = (ImageView) convertView.findViewById(R.id.image_my_contact);
            holder.status = (ImageView) convertView.findViewById(R.id.image_status);
            holder.note = (TextView) convertView.findViewById(R.id.text_note);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact myContact = contactList.get(position);
        holder.name.setText(myContact.getName());
        if(myContact.getContactNote() != null) {
            holder.note.setText(myContact.getContactNote().getMessage());
        }
        String linkPhoto = InternalDataUtil.getInstance().getServerAddress() + myContact.getContactPhoto();
        Log.i(TAG, "link photo: " + linkPhoto);
        if (holder.photo != null) {
            new ImageDownloaderUtil(holder.photo).execute(linkPhoto);
        }
        if (holder.status != null) {
            if (myContact.getContactPresence() != null && myContact.getContactPresence().getAvailability() != null) {
                String status = myContact.getContactPresence().getAvailability();
                if(status != null && status.length() > 0){
                    Drawable src = Utils.getStatusDrawable(context, status);
                    holder.status.setImageDrawable(src);
                }
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView photo;
        ImageView status;
        TextView note;
    }
}
