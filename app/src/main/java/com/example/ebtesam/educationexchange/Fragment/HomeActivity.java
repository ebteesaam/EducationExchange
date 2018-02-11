package com.example.ebtesam.educationexchange.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.announcement.AnnouncementActivity;

/**
 * Created by ebtesam on 09/02/2018 AD.
 */

public class HomeActivity  extends Fragment {

    Button addAnnouncement;

    public HomeActivity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_activity, container,false);
        addAnnouncement=rootView.findViewById(R.id.add_announcement);
        addAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AnnouncementActivity.class);
                startActivity(intent);

            }
        });
        return rootView;


    }
}
