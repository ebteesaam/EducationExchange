package com.example.ebtesam.educationexchange.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.admin.ViewBookReport;
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

public class CustomDialogBlocktClass extends Dialog implements
        View.OnClickListener {
    public int count = 0;
    public Activity c;
    public Dialog d;
    public Button yes, no;
    String Id, bookNmae;
    FirebaseMethod mFirebaseMethods;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private EditText other;

    public CustomDialogBlocktClass(Activity a,String id, String bn) {
        super(a);
        this.c = a;
        Id=id;
        bookNmae=bn;
    }
    public CustomDialogBlocktClass(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_dialog);
        mFirebaseMethods = new FirebaseMethod(getContext());
TextView report=findViewById(R.id.report);
report.setText(R.string.blocked_dialog);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        setupFirebaseAuth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no:
                dismiss();
                break;
            case R.id.yes:
                try {
//                    if(Id.equals(null)){
//                        mFirebaseMethods.updateAvailability(ViewBookReport.myBook);
//                    }else {
                    mFirebaseMethods.updateAvailability(ViewBookReport.myBook,Id, bookNmae);
                }catch (Exception e){}
                c.finish();
//                Intent i =new Intent(getContext(), ProfilePage.class);
//                c.startActivity(i);
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