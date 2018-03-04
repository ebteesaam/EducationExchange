package com.example.ebtesam.educationexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapter;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.ViewBook;
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
 * Created by ebtesam on 04/03/2018 AD.
 */

public class Material extends AppCompatActivity{

    private static final String TAG = "MyBooks";
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



    @Nullable
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_material_activity);
       // setTitle(getString(R.string.add_book_activity));
        //   mDisplayName = (TextView) view.findViewById(R.id.display_name);
        mbookname =  findViewById(R.id.nameBook);
        courseIdBook = (TextView)findViewById(R.id.courseIdBook);
        //  mDescription = (TextView) view.findViewById(R.id.description);
//        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
//        mPosts = (TextView) view.findViewById(R.id.tvPosts);
//        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
//        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mProgressBar = (ProgressBar)findViewById(R.id.profileProgressBar);
        gridView =  findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
        setupFirebaseAuth();
        setupGridView();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Material.this, ViewBook.class);
                Bundle bundle=new Bundle();
                Book book=new Book();
                String id=book.getId_book();
                bundle.putString("id_book", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");


        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material));
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    //Book book=singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Book.class));
                }

                ListAdapter adapter = new ListAdapter(Material.this,R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
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

