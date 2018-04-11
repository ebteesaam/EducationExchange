package com.example.ebtesam.educationexchange.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.ReportedMaterial;
import com.example.ebtesam.educationexchange.Fragment.ReportedMaterialNew;
import com.example.ebtesam.educationexchange.Fragment.ViewPagerAdapter;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.login.LoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReportsActivity extends AppCompatActivity {

    public static String reportid;
    TabLayout tabLayout;
    private Context mContext = ReportsActivity.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //initImageLoader();
        setupFirebaseAuth();

        ViewPager viewPager = findViewById(R.id.view_pager);
        createViewPager(viewPager);

        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        //setupGridView();
        // mAuth.signOut();
        createTabIcons();

    }

    private void createTabIcons() {


        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(getString(R.string.all_report));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabFour);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.new_report));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabThree);


    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ReportedMaterial(), "Tab 1");
        adapter.addFrag(new ReportedMaterialNew(), "Tab 2");


        viewPager.setAdapter(adapter);
    }

    //............................Firebase.................................//
    private void checkCurrentUser(FirebaseUser user) {
        //Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if (user == null) {
            Intent intent = new Intent(mContext, LoginPage.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth() {
        //Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                   // Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
//............................end Firebase.................................//
}