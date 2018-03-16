package com.example.ebtesam.educationexchange.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.ViewGeneralBook;
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

public class SearchBook extends AppCompatActivity {

    private EditText title;
    private GridView gridView;
    private ImageView search;
    private String viewBook, ViewGeneralBook, view;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book);
        setTitle(getString(R.string.action_search));
        title=findViewById(R.id.title);
        search=findViewById(R.id.search);
        viewBook="ViewBook";
        ViewGeneralBook="ViewGeneralBook";
        gridView =  findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
        setupFirebaseAuth();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupGridView();
            }
        });




    }

    private void setupGridView(){


        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material)).orderByChild("book_name").equalTo(title.getText().toString());
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Book book=singleSnapshot.getValue(Book.class);

                        books.add(singleSnapshot.getValue(Book.class));}


                ListAdapter adapter = new com.example.ebtesam.educationexchange.Fragment.ListAdapter(SearchBook.this,R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {//
                        if(books.get(i).getType().equals("TextBooks")||books.get(i).getType()=="Lecture Notes"||!books.get(i).getType().equals("General Books")){
                            view = "ViewBook";

                        }else {

                            view = "ViewGeneralBook";
                        }
                        if(view.equals(viewBook)){
                            Intent intent = new Intent(SearchBook.this, com.example.ebtesam.educationexchange.addBook.ViewBook.class);
                            String item =  books.get(i).getId_book();
                            intent.putExtra("id_book", item);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(SearchBook.this, ViewGeneralBook.class);
                            String item =  books.get(i).getId_book();
                            intent.putExtra("id_book", item);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });}
    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){

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




}
