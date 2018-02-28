package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.ebtesam.educationexchange.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ebtesam on 28/02/2018 AD.
 */

public class ViewBook extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    TabLayout tabLayout;
    private Context mContext = ViewBook.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_page);

        }
    }