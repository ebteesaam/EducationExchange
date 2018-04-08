package com.example.ebtesam.educationexchange.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.ViewBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ebtesam on 16/03/2018 AD.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {
    public int count = 0;
    public Activity c;
    public Dialog d;
    public Button unEthical, cancel, unRelated, send;
    FirebaseMethod mFirebaseMethods;
    String tx;
    String Id;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private EditText other;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }
    public CustomDialogClass(Activity a, String id) {
        super(a);
        this.c = a;
        Id=id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        mFirebaseMethods = new FirebaseMethod(getContext());

        unEthical = findViewById(R.id.unEthical);
        unRelated = findViewById(R.id.unrelated);
        other = findViewById(R.id.other);
        send = findViewById(R.id.send);
        cancel = findViewById(R.id.cancel);

        unEthical.setOnClickListener(this);
        unRelated.setOnClickListener(this);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setupFirebaseAuth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unEthical:

                mFirebaseMethods.reportMaterial(ViewBook.id_material.toString(), getContext().getString(R.string.unEthical).toString(),Id);
                break;

            case R.id.cancel:
                dismiss();
                break;

            case R.id.send:
                tx = other.getText().toString();
                if (tx != null) {
                    mFirebaseMethods.reportMaterial(ViewBook.id_material.toString(), tx,Id);
                    break;
                } else {
                    Toast.makeText(c, getContext().getString(R.string.fill_other).toString(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.unrelated:
                mFirebaseMethods.reportMaterial(ViewBook.id_material.toString(), getContext().getString(R.string.unRelated).toString(), Id);
                break;

            default:
                break;
        }
        dismiss();
    }

     /*
     ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = mFirebaseMethods.getReportsCount(dataSnapshot);

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