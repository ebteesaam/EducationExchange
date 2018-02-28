package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Book;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ebtesam on 26/02/2018 AD.
 */

public class ListAdapter extends ArrayAdapter<Book> {

    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
    }

//    public ListAdapter(ValueEventListener context, List<Book> items) {
//
//        super(context, 0,items);
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }

        // Get the data item for this position
        Book book = getItem(position);

        // Lookup view for data population
         ImageView image=convertView.findViewById(R.id.relative1);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameBook);
         TextView summaryTextView = (TextView) convertView.findViewById(R.id.courseIdBook);
          TextView bookname=(TextView)convertView.findViewById(R.id.nameBook);
//
        // Populate the data into the template view using the data object
        nameTextView.setText(book.getBook_name());
        summaryTextView.setText(book.getCourse_id());

        ImageLoader imageLoader=ImageLoader.getInstance();
//
        imageLoader.displayImage( book.getImage_path(),image,new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
//                if(holder.mProgressBar !=null){
//                    holder.mProgressBar.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                if(holder.mProgressBar !=null){
//                    holder.mProgressBar.setVisibility(View.INVISIBLE);
//                }

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                if(holder.mProgressBar !=null){
//                    holder.mProgressBar.setVisibility(View.INVISIBLE);
//                }

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
//                if(holder.mProgressBar !=null){
//                    holder.mProgressBar.setVisibility(View.INVISIBLE);
//                }

            }

        });
        // Return the completed view to render on screen
        return convertView;
    }
}


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//
//        if (v == null) {
//            LayoutInflater vi;
//            vi = LayoutInflater.from(getContext());
//            v = vi.inflate(R.layout.list, null);
//        }
//
//        Book p = getItem(position);
//
//
//        if (p != null) {
//            ImageView image=convertView.findViewById(R.id.relative1);
//            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameBook);
//         TextView summaryTextView = (TextView) convertView.findViewById(R.id.courseIdBook);
//          TextView bookname=(TextView)convertView.findViewById(R.id.nameBook);
//
//
//
//                nameTextView.setText(p.getBook_name());
//
//
//            if (summaryTextView != null) {
//                summaryTextView.setText(p.getCourse_id());
//            }
//
////            if (tt3 != null) {
////                tt3.setText(p.getDescription());
////            }
////
//        ImageLoader imageLoader=ImageLoader.getInstance();
//
//        imageLoader.displayImage( p.getImage_path(),image,new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
////                if(holder.mProgressBar !=null){
////                    holder.mProgressBar.setVisibility(View.VISIBLE);
////                }
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
////                if(holder.mProgressBar !=null){
////                    holder.mProgressBar.setVisibility(View.INVISIBLE);
////                }
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                if(holder.mProgressBar !=null){
////                    holder.mProgressBar.setVisibility(View.INVISIBLE);
////                }
//
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
////                if(holder.mProgressBar !=null){
////                    holder.mProgressBar.setVisibility(View.INVISIBLE);
////                }
//
//            }
//
//        });
//
//
//
//    }
//        return v;
//}}

