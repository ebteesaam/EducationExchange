package com.example.ebtesam.educationexchange.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.SignOutActivity;
import com.example.ebtesam.educationexchange.models.UserAccountSettings;
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

public class ProfilePage extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    Button signOut;
    Button editProfile;
    StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod firebaseMethod;
    private FirebaseStorage firebaseStorage;
    private TextView user_name,email;
    private ImageView mProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setTitle(getString(R.string.profile));

        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        mProfilePhoto=findViewById(R.id.profile_photo);
        firebaseMethod=new FirebaseMethod(ProfilePage.this);


        editProfile=findViewById(R.id.edit_user);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,EditProfile.class);
                startActivity(intent);
            }
        });
        signOut=findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,SignOutActivity.class);
                startActivity(intent);
            }
        });
        setupFirebaseAuth();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());



        //User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

//        StorageReference storageRef = firebaseStorage.getReference();
//        StorageReference imagesRef = storageRef.child("profile_photo");
//        mStorageRef=FirebaseStorage.getInstance().getReference();
//        //UnvirsalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, "");
//        Glide.with(this /* context */)
//                .using(new FirebaseImageLoader())
//                .load(mStorageRef)
//                .into(mProfilePhoto);

        user_name.setText(settings.getUsername());
        email.setText(settings.getEmail());
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

}
