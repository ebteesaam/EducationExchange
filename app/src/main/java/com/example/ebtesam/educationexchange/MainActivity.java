package com.example.ebtesam.educationexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.GeneralBook;
import com.example.ebtesam.educationexchange.Fragment.HomeActivity;
import com.example.ebtesam.educationexchange.Fragment.LectureNotes;
import com.example.ebtesam.educationexchange.Fragment.TextBook;
import com.example.ebtesam.educationexchange.Fragment.ViewPagerAdapter;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.example.ebtesam.educationexchange.admin.AnnouncementList;
import com.example.ebtesam.educationexchange.admin.ReportsActivity;
import com.example.ebtesam.educationexchange.admin.UserList;
import com.example.ebtesam.educationexchange.login.LoginPage;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.profile.ProfilePage;
import com.example.ebtesam.educationexchange.search.SearchBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static boolean type1 ;
    TabLayout tabLayout;
    private Context mContext = MainActivity.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoader();
        setupFirebaseAuth();

        ViewPager viewPager = findViewById(R.id.view_pager);
        createViewPager(viewPager);

        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        setupGridView();
        // mAuth.signOut();
        createTabIcons();
    }

    private void initImageLoader() {
        UnvirsalImageLoader universalImageLoader = new UnvirsalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(getString(R.string.text_book));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabFour);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.lecture_notes));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getString(R.string.general_book));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabTwo);


    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeActivity(), "Tab 1");
        adapter.addFrag(new TextBook(), "Tab 2");
        adapter.addFrag(new LectureNotes(), "Tab 3");
        adapter.addFrag(new GeneralBook(), "Tab 4");

        viewPager.setAdapter(adapter);
    }

    //............................Firebase.................................//
    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if (user == null) {
            Intent intent = new Intent(mContext, LoginPage.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth() {
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

    public boolean onPrepareOptionsMenu(Menu menu) {
        final FirebaseUser user = mAuth.getCurrentUser();

        MenuItem users = menu.findItem(R.id.user);
        MenuItem report = menu.findItem(R.id.report);
        MenuItem announcement = menu.findItem(R.id.announcement);



        if (type == true) {
            users.setVisible(false);
            report.setVisible(false);
            announcement.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                startActivity(intent);
                return true;
            case R.id.search:
                Intent i = new Intent(MainActivity.this, SearchBook.class);
                startActivity(i);
                return true;
            case R.id.message:
                Intent rm = new Intent(MainActivity.this, UserList.class);
                startActivity(rm);
                return true;

            case R.id.user:
                Intent in = new Intent(MainActivity.this, UserList.class);
                startActivity(in);
                return true;

            case R.id.report:
                Intent r = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(r);
                return true;

            case R.id.announcement:
                Intent a = new Intent(MainActivity.this, AnnouncementList.class);

                startActivity(a);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");


        final ArrayList<User> users = new ArrayList<>();
        final FirebaseUser user1 = mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_users));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if(user.getUser_id().equals(user1.getUid())){
                    if (user.getType().equals("Student")) {
                        type = true;
                        type1=type;
                    } else {
                        type = false;
                        type1=type;

                    }}
                    users.add(singleSnapshot.getValue(User.class));

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}