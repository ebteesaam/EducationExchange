package com.example.ebtesam.educationexchange.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.ebtesam.educationexchange.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by ebtesam on 8/22/2017 AD.
 */

public class UnvirsalImageLoader {

    private static  final  int defaultImage= R.drawable.ic_profile;
    private Context mContext;

    public UnvirsalImageLoader(Context context){
        mContext=context;
    }

    /**
     * this method can be sued to set images that are static it can't be used
     * if th images are being changed in the fragment/activity or if they are being set
     * inalist or a grid
     * @param imgURL
     * @param image
     * @param append
     */

    public static void setImage(String imgURL, ImageView image, String append){

        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {


            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {


            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {


            }
        });



    }

    public ImageLoaderConfiguration getConfig(){

        DisplayImageOptions defaultOption=new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultOption)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        return configuration;

    }


}
