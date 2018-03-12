package com.example.ebtesam.educationexchange.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.SignOutActivity;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfilePage extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    Button signOut;
    Button announcement;
    Button myBook;
    StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod firebaseMethod;
    private FirebaseStorage firebaseStorage;
    private TextView user_name,email;
    private ImageView mProfilePhoto, setting;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setTitle(getString(R.string.profile));

        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        mProfilePhoto=findViewById(R.id.profile_photo);
        progressBar=findViewById(R.id.profileProgressBar);
        firebaseMethod=new FirebaseMethod(ProfilePage.this);



        signOut=findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,SignOutActivity.class);
                startActivity(intent);
            }
        });
        myBook=findViewById(R.id.my_book);
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,MyBooksActivity.class);
                startActivity(intent);
            }
        });

        announcement=findViewById(R.id.announcement);
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,MyAnnouncementsActivity.class);
                startActivity(intent);
            }
        });


        //initImageLoader();
       // setProfileImage();
        setupFirebaseAuth();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
    private void initImageLoader(){
        UnvirsalImageLoader universalImageLoader = new UnvirsalImageLoader(ProfilePage.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image");
        String imgURL="www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UnvirsalImageLoader.setImage(imgURL,mProfilePhoto,progressBar,"http://");


    }


    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        //Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());



        User user = userSettings.getUser();
       // UserAccountSettings settings = userSettings.getSettings();

//        StorageReference storageRef = firebaseStorage.getReference();
//        StorageReference imagesRef = storageRef.child("profile_photo");
//        mStorageRef=FirebaseStorage.getInstance().getReference();
        UnvirsalImageLoader.setImage(user.getProfile_photo(), mProfilePhoto,progressBar, "");
//        Glide.with(this /* context */)
//                .using(new FirebaseImageLoader())
//                .load(mStorageRef)
//                .into(mProfilePhoto);

        user_name.setText(user.getUsername());
        email.setText(user.getEmail());
//        mWebsite.setText(settings.getWebsite());
//        mDescription.setText(settings.getDescription());
//        mPosts.setText(String.valueOf(settings.getPosts()));
//        mFollowing.setText(String.valueOf(settings.getFollowing()));
//        mFollowers.setText(String.valueOf(settings.getFollowers()));
//        mProgressBar.setVisibility(View.GONE);
    }

    //............................Firebase.................................//

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();

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
                setProfileWidgets(firebaseMethod.getUserSettings(dataSnapshot));

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
    //............................end Firebase.................................//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent=new Intent(ProfilePage.this,EditProfile.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
