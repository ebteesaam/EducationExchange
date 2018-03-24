package com.example.ebtesam.educationexchange.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapter;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.models.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    public static String reportid;
  ArrayList<Report> users;
  ArrayList<String> report;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        gridView = findViewById(R.id.list);
        setupFirebaseAuth();
        users = new ArrayList<>();
        report=new ArrayList<>();
        setupUserView();


    }

    private void setupUserView() {



//        final ArrayList<Book> books = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_report));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Report user = singleSnapshot.getValue(Report.class);
                    reportid = singleSnapshot.getKey().toString();
                    Log.d(ReportsActivity.this.toString(), reportid);
                    users.add(singleSnapshot.getValue(Report.class));
                    report.add(reportid);

                }
                setupGridView();
//                ListAdapterReport adapter = new ListAdapterReport(ReportsActivity.this,R.layout.list_material_activity, users);
//                // Attach the adapter to a ListView
//                gridView.setAdapter(adapter);
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersdR = rootRef.child(getString(R.string.dbname_report));
//
//        final DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
//        usersdR.addListenerForSingleValueEvent(new ValueEventListener() { //Your case id is -KrHOUwrsBbXHvUaOTIu
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                final String idbook = dataSnapshot.getValue().toString();
//                usersdRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
//                        Book book = singleSnapshot.getValue(Book.class);
//                        if (book.getId_book().equals(idbook)) {
//                            // Book book1=singleSnapshot.getValue(Book.class);
//                            books.add(singleSnapshot.getValue(Book.class));
//
//
//                        }
//                    }}
//
//
//
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                ListAdapter adapter = new ListAdapter(ReportsActivity.this, R.layout.list_material_activity, books);
//                // Attach the adapter to a ListView
//                gridView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//                ListAdapterUser adapter = new ListAdapterUser(UserList.this, R.layout.users_list, users);
//                // Attach the adapter to a ListView
//                listView.setAdapter(adapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(UserList.this, EditUserProfile.class);
//                        String item =  users.get(i).getUser_id();
//                        intent.putExtra("id_user", item);
//                        startActivity(intent);
//                    }
//                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void setupGridView() {
        final ArrayList<Book> books = new ArrayList<>();


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query1 = reference1
                .child(getString(R.string.dbname_material));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);
                    for(int i=0; i<report.size(); i++) {
                      String id;
                        id = report.get(i);
                        Log.d(ReportsActivity.this.toString(), "id"+id);

                        // Do something with the value
                        if (book.getId_book().equals(id)) {
                            books.add(singleSnapshot.getValue(Book.class));


                        }
                    }

                }

                ListAdapter adapter = new ListAdapter(ReportsActivity.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {//
                        if (!books.get(i).getAvailability().equals("Blocked")) {

                            Intent intent = new Intent(ReportsActivity.this, ViewBookReport.class);
                            String item = books.get(i).getId_book();
                            intent.putExtra("id_book", item);
                            startActivity(intent);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


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
