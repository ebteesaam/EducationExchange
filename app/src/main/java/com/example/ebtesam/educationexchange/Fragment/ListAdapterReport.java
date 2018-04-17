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

public class ListAdapterReport extends ArrayAdapter<Book> {

    public ListAdapterReport(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterReport(Context context, int resource, List<Book> items) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_report, parent, false);
        }

        // Get the data item for this position
        Book book = getItem(position);
      //  Log.d(ListAdapterReport.this.toString(), reportid);

        // Lookup view for data population
         ImageView image=convertView.findViewById(R.id.relative1);
            TextView name = convertView.findViewById(R.id.nameBook);
         TextView courseID = convertView.findViewById(R.id.courseIdBook);
          TextView bookprice= convertView.findViewById(R.id.bookPrice);
          TextView courseName= convertView.findViewById(R.id.courseIdBookName);
//
        TextView status= convertView.findViewById(R.id.state);
status.setText(book.getAvailability());
        // Populate the data into the template view using the data object
        name.setText(book.getBook_name());
        if(book.getCourse_id()!=null){
        courseID.setText(book.getCourse_id());
        }else {
            courseID.setVisibility(View.GONE);
            courseName.setVisibility(View.GONE);
        }
        bookprice.setText(book.getPrice());

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

