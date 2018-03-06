package com.example.ebtesam.educationexchange.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapter;
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

public class MyBooksActivity extends AppCompatActivity {

    private static final String TAG = "MyBooks";
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
private String view, view2 ,ViewBook, ViewGeneralBook;

    private ImageView profileMenu;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_material_activity);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        setTitle(getString(R.string.my_books));
        ViewBook="ViewBook";
        ViewGeneralBook="ViewGeneralBook";
        mbookname = (TextView) findViewById(R.id.nameBook);
        courseIdBook = (TextView) findViewById(R.id.courseIdBook);
        //  mDescription = (TextView) view.findViewById(R.id.description);
        //     mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        //        mPosts = (TextView) view.findViewById(R.id.tvPosts);
        //        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
        //        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        gridView =  findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
        setupFirebaseAuth();
        setupGridView();



    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");


        final ArrayList<Book> books = new ArrayList<>();


        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        final FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material));//.equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Book book=singleSnapshot.getValue(Book.class);
                if(book.getUser_id().equals(user.getUid())){
                   // Book book1=singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Book.class));


                   }}

                ListAdapter adapter = new ListAdapter(MyBooksActivity.this,R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {//
                        if(books.get(i).getType().equals("TextBooks")||books.get(i).getType()=="Lecture Notes"||!books.get(i).getType().equals("General Books")){
                            Log.d(TAG, "ViewBook");
                            view = "ViewBook";

                        }else {
                            Log.d(TAG, "ViewGeneralBook");

                            view = "ViewGeneralBook";
                        }
                        if(view.equals(ViewBook)){
                        Intent intent = new Intent(MyBooksActivity.this, ViewBookProfile.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
                        startActivity(intent);
                        }else {
                            Intent intent = new Intent(MyBooksActivity.this, ViewGeneralBookProfile.class);
                            String item =  books.get(i).getId_book();
                            intent.putExtra("id_book", item);
                            startActivity(intent);
                        }
                    }
                });

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

}
