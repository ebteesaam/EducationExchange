package com.example.ebtesam.educationexchange.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Report;

import java.util.List;

/**
 * Created by ebtesam on 26/02/2018 AD.
 */

public class ListAdapterReport extends ArrayAdapter<Report> {

    public ListAdapterReport(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterReport(Context context, int resource, List<Report> items) {
        super(context, resource, items);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_userlistadmin, parent, false);
        }

        // Get the data item for this position
        Report user = getItem(position);

        // Lookup view for data population
//        ImageView image=convertView.findViewById(R.id.circleImageM);
//        TextView name = (TextView) convertView.findViewById(R.id.nameM);
         TextView email = (TextView) convertView.findViewById(R.id.type);

//        name.setText(user.getUsername());
        email.setText(user.getType());


//        ImageLoader imageLoader=ImageLoader.getInstance();
//
//        imageLoader.displayImage( user.getProfile_photo(),image,new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//
//
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//
//            }
//
//        });

        // Return the completed view to render on screen
        return convertView;
    }
}

