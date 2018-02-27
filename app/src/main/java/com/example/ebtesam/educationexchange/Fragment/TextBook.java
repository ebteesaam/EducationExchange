package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Book;
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

public class TextBook extends Fragment {
    private static final String TAG = "TextBook";
    private static final int NUM_GRID_COLUMNS = 3;
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

    private ImageView profileMenu;

    private Context mContext;

    public TextBook(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.text_book_fragment, container,false);
     //   mDisplayName = (TextView) view.findViewById(R.id.display_name);
        mbookname = (TextView) view.findViewById(R.id.nameBook);
        courseIdBook = (TextView) view.findViewById(R.id.courseIdBook);
      //  mDescription = (TextView) view.findViewById(R.id.description);
//        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
//        mPosts = (TextView) view.findViewById(R.id.tvPosts);
//        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
//        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mProgressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);
        gridView =  view.findViewById(R.id.list);
        View emptyView = view.findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
        setupFirebaseAuth();
        setupGridView();
//        ListView yourListView = (ListView) view.findViewById(R.id.list);
//
//        ListAdapter customAdapter = new ListAdapter(getActivity() , R.layout.text_book_fragment);
//
//        yourListView .setAdapter(customAdapter);
        return view;


    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");
        // get data from the table by the ListAdapter

//        final ArrayList<Book> books = new ArrayList<>();
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_user_books));
//        rootRef.child(getString(R.string.dbname_user_books)).addValueEventListener(new ValueEventListener() {
//                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                                                            for (DataSnapshot messageSnapshot: snapshot.child("mensagem").getChildren()) {
//                                                                books.add(messageSnapshot.child("textoMensagem").getValue().toString());
//                                                            }
//                                                        }
//                                                     //setup our image grid
//            int gridWidth = getResources().getDisplayMetrics().widthPixels;
//            int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//                gridView.setColumnWidth(imageWidth);
//
//            ArrayList<String> imgUrls = new ArrayList<String>();
//                for(int i = 0; i < books.size(); i++){
//                imgUrls.add(books.get(i).getImage_path());
//            }
//            GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
//                    "", imgUrls);
//                gridView.setAdapter(adapter);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Log.d(TAG, "onCancelled: query cancelled.");
//        }
//    });}


//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                   // String name = ds.child("name").getValue(String.class);
//                    books.add(ds.getValue(Book.class));
//
//                //setup our image grid
//                int gridWidth = getResources().getDisplayMetrics().widthPixels;
//                int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//                gridView.setColumnWidth(imageWidth);
//
//                ArrayList<String> imgUrls = new ArrayList<String>();
//                for(int i = 0; i < books.size(); i++){
//                    imgUrls.add(books.get(i).getImage_path());
//                }
//                GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.list,
//                        "", imgUrls);
//                gridView.setAdapter(adapter);
//            }
//
//                }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//            };
//
//
//        usersdRef.addListenerForSingleValueEvent(eventListener);
//        };


        final ArrayList<Book> books = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material))
               .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    //Book book=singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Book.class));
                }
                //setup our image grid
                int gridWidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridWidth/NUM_GRID_COLUMNS;
                gridView.setColumnWidth(imageWidth);

                ArrayList<String> imgUrls = new ArrayList<String>();
                for(int i = 0; i < books.size(); i++){
                    imgUrls.add(books.get(i).getImage_path());

                }
                GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
                        "", imgUrls);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}

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

}
