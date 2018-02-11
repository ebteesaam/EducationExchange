package com.example.ebtesam.educationexchange.announcement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ebtesam.educationexchange.R;

public class AnnouncementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement);
        setTitle(getString(R.string.add_announcement));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
