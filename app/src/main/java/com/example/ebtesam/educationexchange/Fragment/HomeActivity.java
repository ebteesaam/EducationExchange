package com.example.ebtesam.educationexchange.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.CustomDialogAboutUsClass;
import com.example.ebtesam.educationexchange.announcement.AnnouncementActivity;
import com.example.ebtesam.educationexchange.announcement.AnnouncementGeneralActivity;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.example.ebtesam.educationexchange.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 09/02/2018 AD.
 */

public class HomeActivity extends Fragment {

    Button addAnnouncement;

    ListView listView;
    private boolean b = true;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private boolean type=false;

    private FirebaseAuth.AuthStateListener mAuthListener;

    public HomeActivity() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_activity, container, false);

        listView = rootView.findViewById(R.id.list_announcement);
        final Button fab = rootView.findViewById(R.id.fab);
        final FloatingActionButton fab1 = rootView.findViewById(R.id.fab_1);
        final FloatingActionButton fab2 = rootView.findViewById(R.id.fab_2);
        ImageView imageView=rootView.findViewById(R.id.aboutUs);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogAboutUsClass cdd = new CustomDialogAboutUsClass(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

        View emptyView = rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        //Animations
//        final Animation show_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_show);
//        final Animation hide_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_hide);
//
//        final Animation show_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_show);
//        final Animation hide_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_hide);

        setupAnnouncementView();
        setupFirebaseAuth();
        setupUserView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b == true) {
//                    final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab1.getLayoutParams();
//                    layoutParams.rightMargin += (int) (fab1.getWidth() * 1.6);
//                    layoutParams.bottomMargin += (int) (fab1.getWidth() * 0.25);
//                    fab1.setLayoutParams(layoutParams);
//                    fab1.startAnimation(show_fab_1);
                    fab1.setVisibility(View.VISIBLE);
                    fab1.setClickable(true);


//                    final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) fab2.getLayoutParams();
//                    layoutParams2.rightMargin += (int) (fab2.getWidth() * 0.25);
//                    layoutParams2.bottomMargin += (int) (fab2.getWidth() * 1.6);
//                    fab2.setLayoutParams(layoutParams2);
//                    fab2.startAnimation(show_fab_2);
                    fab2.setVisibility(View.VISIBLE);
                    fab2.setClickable(true);
                    b = false;
                } else if (b == false) {
//                    final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab1.getLayoutParams();
//                    layoutParams.rightMargin += (int) (fab1.getWidth() * -1.6);
//                    layoutParams.bottomMargin += (int) (fab1.getWidth() * -0.25);
//                    fab1.setLayoutParams(layoutParams);
                    //fab1.startAnimation(hide_fab_1);
                    fab2.setVisibility(View.INVISIBLE);
                    fab1.setVisibility(View.INVISIBLE);

//                    final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) fab2.getLayoutParams();
//                    layoutParams2.rightMargin += (int) (fab2.getWidth() * -0.25);
//                    layoutParams2.bottomMargin += (int) (fab2.getWidth() * -1.6);
//                    fab2.setLayoutParams(layoutParams2);
//                    fab2.startAnimation(hide_fab_2);
                  //  fab2.setClickable(false);
                    b = true;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type==true) {
                Intent intent = new Intent(getActivity(), AnnouncementActivity.class);
                startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), getString(R.string.you_blocked), Toast.LENGTH_LONG).show();
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type==true) {
                Intent intent = new Intent(getActivity(), AnnouncementGeneralActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(), getString(R.string.you_blocked), Toast.LENGTH_LONG).show();
            }
            }
        });


        setupAnnouncementView();
        return rootView;


    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
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
    private void setupUserView() {


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
                        type = user.getStatus().equals("Active") || user.getStatus().equals("نشط");
                    }
                    users.add(singleSnapshot.getValue(User.class));

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void setupAnnouncementView() {


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
                    if (book.getStatus().equals("Active")||book.getStatus().equals("نشط")) {
                        books.add(singleSnapshot.getValue(Announcement.class));
                    }

                }

                ListAdapterAnnouncement adapter = new ListAdapterAnnouncement(getActivity(), R.layout.home_activity, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
