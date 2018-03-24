package com.example.ebtesam.educationexchange.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.models.UserSettings;
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

public class EditUserProfile extends AppCompatActivity {

    private static final String TAG = "editProfileActivity";
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod firebaseMethod;
    private TextView user_name, email;
    private ImageView mProfilePhoto;
    private ProgressBar progressBar;
    private String userID;
    private FirebaseUser user;
    private UserSettings mUserSettings;
    private String id;
    private Spinner spinner1, spinner2;
    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private TextView changePhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_page);
        setTitle(getString(R.string.edit_profile));
        id = getIntent().getStringExtra("id_user");
        spinner1 = findViewById(R.id.status);
        spinner2 = findViewById(R.id.type);
        user_name = findViewById(R.id.edit_user_name);
        email = findViewById(R.id.edit_email);
        mProfilePhoto = findViewById(R.id.edit_profile_photo);
        progressBar = findViewById(R.id.profileProgressBar);



        firebaseMethod = new FirebaseMethod(EditUserProfile.this);


        setupFirebaseAuth();



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
                saveProfileSettings();
                Intent i =new Intent(EditUserProfile.this, UserList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    private int getIndex(Spinner spinner, String v){
        int index=0;
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(v)){
                index=i;
                break;
            }
        }
        return index;
    }


    private void setupUserView() {


        final ArrayList<User> users = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_users));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if (user.getUser_id().equals(id)) {
                        UnvirsalImageLoader.setImage(user.getProfile_photo(), mProfilePhoto, progressBar, "");

                        user_name.setText(user.getUsername());
                        email.setText(user.getEmail());
                        spinner1.setSelection(getIndex(spinner1,user.getStatus()));
                        spinner2.setSelection(getIndex(spinner2,user.getType()));
                        users.add(singleSnapshot.getValue(User.class));

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    //............................Firebase.................................//

    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings() {
        // final String displayName = mDisplayName.getText().toString();
        String type = spinner2.getSelectedItem().toString();
        String status = spinner1.getSelectedItem().toString();

        FirebaseMethod firebaseMethod = new FirebaseMethod(EditUserProfile.this);
        firebaseMethod.updateUser( type, status, id);


    }




    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

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
                // setProfileWidgets(firebaseMethod.getUserSettings(dataSnapshot));
                setupUserView();

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
