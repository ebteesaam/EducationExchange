package com.example.ebtesam.educationexchange.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class RegisterPage extends AppCompatActivity {

    private static final String TAG = "RegisterPage";
    EditText input_email, input_password, input_username;
    FirebaseMethod firebaseMethods;
    private String email, username, password;
    private Button btnRegister;
    private Context mContext = RegisterPage.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        firebaseMethods = new FirebaseMethod(mContext);
        initWidgets();
        setupFirebaseAuth();
        init();
    }

    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = input_email.getText().toString();
                username = input_username.getText().toString();
                password = input_password.getText().toString();

                if (checkInputs(email, username, password)) {
                    firebaseMethods.registerNewEmail(email, password, username);
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password) {
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("") || username.equals("") || password.equals("")) {
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * Initialize the activity widgets
     */
    private void initWidgets() {
        Log.d(TAG, "initWidgets: Initializing Widgets.");
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_username = findViewById(R.id.input_username);
        btnRegister = findViewById(R.id.btn_register);

    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    //............................Firebase.................................//

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


                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.exists()) {
                        append = myRef.push().getKey().substring(3, 10);
                        Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                    }
                }


                String mUsername = "";
                mUsername = username + append;

                //add new user to the database
                firebaseMethods.addNewUser(email, mUsername, "", "", "");

                Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            checkIfUsernameExists(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

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
