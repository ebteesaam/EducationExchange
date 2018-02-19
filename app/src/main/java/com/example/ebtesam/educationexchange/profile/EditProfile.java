package com.example.ebtesam.educationexchange.profile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.Permissions;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.example.ebtesam.educationexchange.addBook.TakePhotoActivity;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.models.UserAccountSettings;
import com.example.ebtesam.educationexchange.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class EditProfile extends AppCompatActivity implements ConfirmPasswordDialog.OnConfirmPasswordListener {

    private static final String TAG = "editProfileActivity";
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod firebaseMethod;
    private TextView user_name, email, changePassword;
    private ImageView mProfilePhoto;
    private ProgressBar progressBar;
    private String userID;
    private FirebaseUser user;
    private UserSettings mUserSettings;
    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private TextView changePhoto;

    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password: " + password);

         user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        ///////////////////// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User re-authenticated.");
                            ///////////////////////check to see if the email is not already present in the database
                            mAuth.fetchProvidersForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                    if(task.isSuccessful()){
                                        try{
                                            if(task.getResult().getProviders().size() == 1){
                                                Log.d(TAG, "onComplete: that email is already in use.");
                                                Toast.makeText(EditProfile.this, "That email is already in use", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Log.d(TAG, "onComplete: That email is available.");

                                                //////////////////////the email is available so update it
                                                mAuth.getCurrentUser().updateEmail(email.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(EditProfile.this, "email updated", Toast.LENGTH_SHORT).show();
                                                                    firebaseMethod.updateEmail(email.getText().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: "  +e.getMessage() );
                                        }
                                    }
                                }

                            });

                        }else {
                            Log.d(TAG,"re-authentication failed");
                        }
                    }
                });
    }

    public void onConfirmchangePassword(final String password) {
        Log.d(TAG, "onConfirmPassword: got the password: " + password);
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .

                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Password updated");
                                            } else {
                                                Log.d(TAG, "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "Error auth failed");
                                }
                            }
                        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);
        setTitle(getString(R.string.edit_profile));

        user_name = findViewById(R.id.edit_user_name);
        email = findViewById(R.id.edit_email);
        mProfilePhoto=findViewById(R.id.edit_profile_photo);
        progressBar=findViewById(R.id.profileProgressBar);
//        changePassword=findViewById(R.id.change_password);
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
//                dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
//
//
//
//            }
//        });
        changePhoto=findViewById(R.id.change_photo);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionsArray(Permissions.PERMISSIONS)) {
                    Intent intent = new Intent(EditProfile.this, TakePhotoActivity.class);
                    startActivity(intent);
                } else {
                    verifyPermissions(Permissions.PERMISSIONS);
                }

            }
        });
        firebaseMethod = new FirebaseMethod(EditProfile.this);
//initImageLoader();
setProfileImage();

        setupFirebaseAuth();


    }

    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                EditProfile.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    /**
     * Check an array of permissions
     *
     * @param permissions
     * @return
     */
    public boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for (int i = 0; i < permissions.length; i++) {
            String check = permissions[i];
            if (!checkPermissions(check)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check a single permission is it has been verified
     *
     * @param permission
     * @return
     */
    public boolean checkPermissions(String permission) {
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(EditProfile.this, permission);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        } else {
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image");
        String imgURL="www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UnvirsalImageLoader.setImage(imgURL,mProfilePhoto, progressBar,"http://");


    }
    private void initImageLoader(){
        UnvirsalImageLoader universalImageLoader = new UnvirsalImageLoader(EditProfile.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    private void setProfileWidgets(UserSettings userSettings) {
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());
        mUserSettings = userSettings;

        //User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UnvirsalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, progressBar, "");

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

    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings() {
        // final String displayName = mDisplayName.getText().toString();
         String username = user_name.getText().toString();
         String memail = email.getText().toString();
        //        final String website = mWebsite.getText().toString();
        //        final String description = mDescription.getText().toString();
        //        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());


        //case1: the user did not change their username
        if (!mUserSettings.getUser().getUsername().equals(username)) {
            checkIfUsernameExists(username);

        }
        //case2: if the user made a change to their email
        if (!mUserSettings.getUser().getEmail().equals(memail)) {

            // step1) Reauthenticate
            //          -Confirm the password and email
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));

            // dialog.setTargetFragment(, 1);
            //dialog.setShowsDialog(true);


            // step2) check if the email already is registered
            //          -'fetchProvidersForEmail(String email)'
            // step3) change the email
            //          -submit the new email to the database and authentication
        }
//        /**
//         * change the rest of the settings that do not require uniqueness
//         */
//        if(!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
//            //update displayname
//            mFirebaseMethods.updateUserAccountSettings(displayName, null, null, 0);
//        }
//        if(!mUserSettings.getSettings().getWebsite().equals(website)){
//            //update website
//            mFirebaseMethods.updateUserAccountSettings(null, website, null, 0);
//        }
//        if(!mUserSettings.getSettings().getDescription().equals(description)){
//            //update description
//            mFirebaseMethods.updateUserAccountSettings(null, null, description, 0);
//        }
//        if(!mUserSettings.getSettings().getProfile_photo().equals(phoneNumber)){
//            //update phoneNumber
//            mFirebaseMethods.updateUserAccountSettings(null, null, null, phoneNumber);
//        }


    }

    /**
     * Check is @param username already exists in teh database
     *
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    //add the username
                    firebaseMethod.updateUsername(username);
                    Toast.makeText(EditProfile.this, "saved username.", Toast.LENGTH_SHORT).show();

                }
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.exists()) {
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(EditProfile.this, "That username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
