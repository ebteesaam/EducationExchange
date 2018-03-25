package com.example.ebtesam.educationexchange.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapterAnnouncement;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAnnouncementsActivity extends AppCompatActivity {

    private static final String TAG = "MyBooks";
    private static final int NUM_GRID_COLUMNS = 3;
    ListView listView;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;
    //widgets
    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mbookname, courseIdBook, mDescription;
    private ProgressBar mProgressBar;
    private GridView gridView;
    private boolean b = true;


    private String view, view2 ,ViewBook, ViewGeneralBook;

    private ImageView profileMenu;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_announcement_activity);
        setTitle(getString(R.string.view_my_announcement));

        setupFirebaseAuth();
        listView = findViewById(R.id.list_announcement);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

//        final FloatingActionButton fab = findViewById(R.id.fab);
//        final FloatingActionButton fab1 = findViewById(R.id.fab_1);
//        final FloatingActionButton fab2 = findViewById(R.id.fab_2);

        //Animations
        final Animation show_fab_1 = AnimationUtils.loadAnimation(MyAnnouncementsActivity.this, R.anim.fab1_show);
        final Animation hide_fab_1 = AnimationUtils.loadAnimation(MyAnnouncementsActivity.this, R.anim.fab1_hide);

        final Animation show_fab_2 = AnimationUtils.loadAnimation(MyAnnouncementsActivity.this, R.anim.fab2_show);
        final Animation hide_fab_2 = AnimationUtils.loadAnimation(MyAnnouncementsActivity.this, R.anim.fab2_hide);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (b == true) {
//                    final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
//                    layoutParams.rightMargin += (int) (fab1.getWidth() * 1.6);
//                    layoutParams.bottomMargin += (int) (fab1.getWidth() * 0.25);
//                    fab1.setLayoutParams(layoutParams);
//                    fab1.startAnimation(show_fab_1);
//                    fab1.setClickable(true);
//
//                    final FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
//                    layoutParams2.rightMargin += (int) (fab2.getWidth() * 0.25);
//                    layoutParams2.bottomMargin += (int) (fab2.getWidth() * 1.6);
//                    fab2.setLayoutParams(layoutParams2);
//                    fab2.startAnimation(show_fab_2);
//                    fab2.setClickable(true);
//                    b = false;
//                } else if (b == false) {
//                    final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
//                    layoutParams.rightMargin += (int) (fab1.getWidth() * -1.6);
//                    layoutParams.bottomMargin += (int) (fab1.getWidth() * -0.25);
//                    fab1.setLayoutParams(layoutParams);
//                    fab1.startAnimation(hide_fab_1);
//
//                    final FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
//                    layoutParams2.rightMargin += (int) (fab2.getWidth() * -0.25);
//                    layoutParams2.bottomMargin += (int) (fab2.getWidth() * -1.6);
//                    fab2.setLayoutParams(layoutParams2);
//                    fab2.startAnimation(hide_fab_2);
//                    fab2.setClickable(false);
//                    b = true;
//                }
//            }
//        });

//        fab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyAnnouncementsActivity.this, AnnouncementActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyAnnouncementsActivity.this, AnnouncementGeneralActivity.class);
//                startActivity(intent);
//            }
//        });



        setupAnnouncementView();

    }
    private void setupAnnouncementView() {

        final FirebaseUser user = mAuth.getCurrentUser();

        final ArrayList<Announcement> books = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_announcement));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Announcement book = singleSnapshot.getValue(Announcement.class);

                    // if (book.getType().equals("Lecture Notes") && book.getFaculty().toString().equals(getString(R.string.college_of_english))) {
                   // books.add(singleSnapshot.getValue(Announcement.class));
                if(book.getUser_id().equals(user.getUid())){
                   // Book book1=singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Announcement.class));


                }

                ListAdapterAnnouncement adapter = new ListAdapterAnnouncement(MyAnnouncementsActivity.this, R.layout.list_announcement_activity, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MyAnnouncementsActivity.this, ViewMyAnnouncement.class);
                        String item =  books.get(i).getId_announcement();
                        intent.putExtra("id_announcement", item);
                        startActivity(intent);
                    }
                });

//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(Material.this, ViewBook.class);
//                        String item = books.get(i).getId_book();
//                        intent.putExtra("id_announcement", item);
////                        books.get(i);
//                        startActivity(intent);
//                    }
//                });

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


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


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                //setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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

}
