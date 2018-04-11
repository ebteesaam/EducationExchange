package com.example.ebtesam.educationexchange.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Report;
import com.example.ebtesam.educationexchange.models.Request;
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
 * Created by ebtesam on 29/01/2018 AD.
 */

public class RequestFragment extends Fragment {
    //firebase
    public static String reportid;
    private static String requestParent;
    ArrayList<Report> users;
    ArrayList<String> report;
    ListView listView;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private TextView empty_subtitle_text;

    public RequestFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_announcement_activity, container, false);


        setupFirebaseAuth();
        listView = rootView.findViewById(R.id.list_announcement);
        View emptyView = rootView.findViewById(R.id.empty_view);
        empty_subtitle_text=rootView.findViewById(R.id.empty_subtitle_text);
        listView.setEmptyView(emptyView);

        empty_subtitle_text.setText(getString(R.string.no_request));

        setupAnnouncementView();
        return rootView;
    }

    private void setupAnnouncementView() {

        final FirebaseUser user = mAuth.getCurrentUser();

        final ArrayList<Request> books = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child("request");
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Request book = singleSnapshot.getValue(Request.class);
//                    Log.d(TAG, "singleSnapshot.getKey()onAuthStateChanged:signed_in:" + singleSnapshot.getKey());
//                    Log.d(TAG, "singleSnapshot.getKey()onAuthStateChanged:signed_in:" + user.getUid());

                    // if (book.getType().equals("Lecture Notes") && book.getFaculty().toString().equals(getString(R.string.college_of_english))) {
                    // books.add(singleSnapshot.getValue(Announcement.class));
                    if (book.getId_user().equals(user.getUid()) && !(book.getStatus().equals("new") || book.getStatus().equals("جديد"))) {
                        // Book book1=singleSnapshot.getValue(Book.class);
                        books.add(singleSnapshot.getValue(Request.class));
                        requestParent = singleSnapshot.getKey();

                    }

                    ListAdapterRequset adapter = new ListAdapterRequset(getActivity(), R.layout.list_announcement_activity, books, requestParent);
                    // Attach the adapter to a ListView
                    listView.setAdapter(adapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(MyRequestsActivity.this, ViewMyAnnouncement.class);
//                        String item =  books.get(i).getRequest_id();
//                        intent.putExtra("id_request", item);
//                        startActivity(intent);
//                    }
//                });


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

                }
            }

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
    private void setupFirebaseAuth() {
        //  Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
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
}