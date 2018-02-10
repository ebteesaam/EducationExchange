package com.example.ebtesam.educationexchange.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebtesam.educationexchange.R;

/**
 * Created by ebtesam on 09/02/2018 AD.
 */

public class HomeActivity  extends Fragment {

    public HomeActivity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.list, container,false);
        return rootView;


    }
}
