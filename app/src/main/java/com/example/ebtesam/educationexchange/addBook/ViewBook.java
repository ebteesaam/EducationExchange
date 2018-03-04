package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 28/02/2018 AD.
 */

public class ViewBook extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    TabLayout tabLayout;
    private Context mContext = ViewBook.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView availability, price, state, number_of_course, name_of_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_page);
        String bookId=getIntent().getStringExtra("id_book");

        availability=findViewById(R.id.availability);
        price=findViewById(R.id.price);
        state=findViewById(R.id.state);
        number_of_course=findViewById(R.id.number_of_course);
        name_of_book=findViewById(R.id.name_of_book);
        availability.setText(bookId);
        }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");
        final Book[] book = new Book[1];

        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    book[0] =singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Book.class));
                }

               availability.setText(book[0].getAvailability().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}
    }