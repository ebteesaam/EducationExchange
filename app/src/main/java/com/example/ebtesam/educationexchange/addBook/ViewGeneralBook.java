package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 28/02/2018 AD.
 */

public class ViewGeneralBook extends AppCompatActivity {

    TabLayout tabLayout;
    String bookId;
    ImageLoader imageLoader;
    private Context mContext = ViewGeneralBook.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView availability, price, state, type, name_of_book;
    private ImageView photo;
    private ProgressBar progressBar;
  //  private  Boolean vis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_general_book_page);
        setTitle(getString(R.string.book_details));
        bookId = getIntent().getStringExtra("id_book");

        availability = findViewById(R.id.availability);
        price = findViewById(R.id.price);
        state = findViewById(R.id.state);

        progressBar = findViewById(R.id.ProgressBar);
        type = findViewById(R.id.type_of_book);
        name_of_book = findViewById(R.id.name_of_book);
        photo = findViewById(R.id.relative1);
        setupGridView();
    }

    private void setupGridView() {

        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getId_book().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Book.class));

                        availability.setText(book.getAvailability().toString());
                        name_of_book.setText(book.getBook_name().toString());
                        price.setText(book.getPrice().toString());
                        state.setText(book.getStatus().toString());
                        type.setText(book.getType().toString());
                        imageLoader = ImageLoader.getInstance();
//
                        imageLoader.displayImage(book.getImage_path(), photo, new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                        });

                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }


}