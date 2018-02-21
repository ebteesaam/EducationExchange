package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.SqaureImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 8/23/2017 AD.
 */

public class GridAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResoures;
    private String mAppend;

    private ArrayList<String> imgURLs;
    public GridAdapter(Context context, int layoutResoures, String append, ArrayList<String> imgURLs){
       super(context ,layoutResoures ,imgURLs);
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext= context;

        this.layoutResoures=layoutResoures;
       mAppend=append;
       this.imgURLs=imgURLs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //view holder build pattern (similar to recycleview)
        final ViewHolder holder;
        if(convertView ==null){
            convertView=mInflater.inflate(R.layout.list, parent,false);
            holder =new ViewHolder();
            holder.mProgressBar=(ProgressBar)convertView.findViewById(R.id.profileProgressBar);
            holder.image=(SqaureImageView) convertView.findViewById(R.id.relative1);
           // holder.nameTextView = (TextView) view.findViewById(R.id.nameBook);
//        TextView summaryTextView = (TextView) view.findViewById(R.id.courseIdBook);
//   TextView bookname=(TextView)convertView.findViewById(R.id.nameBook);
//            UserSettings userSettings=new UserSettings();
//            Book book =userSettings.getBook();
//
//            bookname.setText(book.getBook_name());
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        String imgURL=getItem(position);

        ImageLoader imageLoader=ImageLoader.getInstance();

        imageLoader.displayImage(mAppend + imgURL, holder.image,new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        });


        return convertView;
    }

    private static class ViewHolder{
        SqaureImageView image;
        ProgressBar mProgressBar;

    }
}
