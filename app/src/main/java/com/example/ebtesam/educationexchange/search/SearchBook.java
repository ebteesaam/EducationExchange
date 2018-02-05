package com.example.ebtesam.educationexchange.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ebtesam.educationexchange.R;

public class SearchBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book);
        setTitle(getString(R.string.action_search));

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
