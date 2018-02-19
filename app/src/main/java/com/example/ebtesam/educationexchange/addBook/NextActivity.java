package com.example.ebtesam.educationexchange.addBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NextActivity extends AppCompatActivity {
    private static final String TAG = "NextActivity";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;

    //widgets
    //private EditText mCaption;

    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        mFirebaseMethods = new FirebaseMethod(NextActivity.this);
        //mCaption = (EditText) findViewById(R.id.caption) ;

        setupFirebaseAuth();

//        ImageView backArrow = (ImageView) findViewById(R.id.ivBackArrow);
//        backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: closing the activity");
//                finish();
//            }
//        });


//        TextView share = (TextView) findViewById(R.id.tvShare);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to the final share screen.");
//                //upload the image to firebase
//                Toast.makeText(NextActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
//                String caption = mCaption.getText().toString();
//                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, imgUrl);
//            }
//        });

        setImage();
    }

    private void someMethod(){
        /*
            Step 1)
            Create a data model for Photos
            Step 2)
            Add properties to the Photo Objects (caption, date, imageUrl, photo_id, tags, user_id)
            Step 3)
            Count the number of photos that the user already has.
            Step 4)
            a) Upload the photo to Firebase Storage
            b) insert into 'photos' node
            c) insert into 'user_photos' node
         */

    }


    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
    private void setImage(){
        Intent intent = getIntent();
        ImageView image = (ImageView) findViewById(R.id.imageShare);
        imgUrl = intent.getStringExtra(getString(R.string.selected_image));
        UnvirsalImageLoader.setImage(imgUrl, image, null, mAppend);
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
        Log.d(TAG, "onDataChange: image count: " + imageCount);

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

                imageCount = mFirebaseMethods.getBooksCount(dataSnapshot);
                Log.d(TAG, "onDataChange: image count: " + imageCount);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                Log.d(TAG, "onClick: navigating to the final share screen.");
                Toast.makeText(NextActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                String caption = "nothing".toString();
                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_book), caption, imageCount, imgUrl);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
   }
}

//    //firebase
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference myRef;
//    private FirebaseMethod mFirebaseMethods;
//
//    //vars
//    private String mAppend = "file:/";
//    private int imageCount = 0;
//    private String imgUrl;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setTitle(R.string.photo);
//        setContentView(R.layout.activity_next);
//        mFirebaseMethods = new FirebaseMethod(NextActivity.this);
//
//        setupFirebaseAuth();
//
//        setImage();
//    }
//
//    private void someMethod(){
//        /*
//            Step 1)
//            Create a data model for Photos
//
//            Step 2)
//            Add properties to the Photo Objects (caption, date, imageUrl, photo_id, tags, user_id)
//
//            Step 3)
//            Count the number of photos that the user already has.
//
//            Step 4)
//            a) Upload the photo to Firebase Storage
//
//            b) insert into 'photos' node
//
//            c) insert into 'user_photos' node
//         */
//
//
//    }
//    /**
//     * gets the image url from the incoming intent and displays the chosen image
//     */
//    private void setImage(){
//        Intent intent = getIntent();
//        ImageView image = (ImageView) findViewById(R.id.imageShare);
//        //UnvirsalImageLoader.setImage(intent.getStringExtra(getString(R.string.selected_image)), image, null, mAppend);
//        imgUrl = intent.getStringExtra(getString(R.string.selected_image));
//         UnvirsalImageLoader.setImage(imgUrl, image, null, mAppend);
//    }
//
//     /*
//    ------------------------------------ Firebase ---------------------------------------------
//     */
//
//    /**
//     * Setup the firebase auth object
//     */
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//
//        Log.d(TAG, "onDataChange: image count: " + imageCount);
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                imageCount = mFirebaseMethods.getBooksCount(dataSnapshot);
//                Log.d(TAG, "onDataChange: image count: " + imageCount);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu options from the res/menu/menu_editor.xml file.
//        // This adds menu items to the app bar.
//        getMenuInflater().inflate(R.menu.save, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // User clicked on a menu option in the app bar overflow menu
//        switch (item.getItemId()) {
//            case R.id.action_save:
//                Log.d(TAG, "onClick: navigating to the final share screen.");
//                Toast.makeText(NextActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
//                String caption = "nothing";
//                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_book), caption, imageCount, imgUrl);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        return;
//    }
//}
