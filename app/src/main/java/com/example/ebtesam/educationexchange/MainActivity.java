package com.example.ebtesam.educationexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ebtesam.educationexchange.Fragment.AdapterFragment;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
import com.example.ebtesam.educationexchange.login.LoginPage;
import com.example.ebtesam.educationexchange.profile.ProfilePage;
import com.example.ebtesam.educationexchange.search.SearchBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context mContext = MainActivity.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFirebaseAuth();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTextBook.class);
                startActivity(intent);
            }
        });
        ViewPager viewPager=findViewById(R.id.view_pager);

        AdapterFragment adapter=new AdapterFragment(this,getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout=findViewById(R.id.tab);

        tabLayout.setupWithViewPager(viewPager);
        //mAuth.signOut();
    }

    //............................Firebase.................................//
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LoginPage.class);
            startActivity(intent);
        }
    }
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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
    //............................end Firebase.................................//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.cd ..
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.profile:
            Intent intent=new Intent(MainActivity.this,ProfilePage.class) ;
            startActivity(intent);
                return true;
            case R.id.search:
                Intent i=new Intent(MainActivity.this, SearchBook.class) ;
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
