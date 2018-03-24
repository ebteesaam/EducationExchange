package com.example.ebtesam.educationexchange.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.CustomDialogDeleteClass;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.AddGeneralBook;
import com.example.ebtesam.educationexchange.addBook.AddLectureNotes;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
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

public class ViewBookProfile extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static String myBook;
    TabLayout tabLayout;
    String bookId, typeMaterial;
    ImageLoader imageLoader;
    private Context mContext = ViewBookProfile.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView availability, price, state, number_of_course, name_of_book, faculty, type, facultyname, number_of_coursename;
    private ImageView photo;
    private ProgressBar progressBar;
    private FirebaseMethod firebaseMethod;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_page);
        setTitle(getString(R.string.book_details));
        firebaseMethod = new FirebaseMethod(ViewBookProfile.this);

        bookId=getIntent().getStringExtra("id_book");

        availability=findViewById(R.id.availability);
        price=findViewById(R.id.price);
        state=findViewById(R.id.state);
        faculty=findViewById(R.id.faculty);
        progressBar=findViewById(R.id.ProgressBar);

        type = findViewById(R.id.type_of_book);
        number_of_course=findViewById(R.id.number_of_course);
        name_of_book=findViewById(R.id.name_of_book);
        photo=findViewById(R.id.relative1);
        facultyname=findViewById(R.id.facultyname);
        number_of_coursename=findViewById(R.id.number_of_coursename);
b1=findViewById(R.id.my_book);
b1.setVisibility(View.GONE);
//        myBook=getIntent().getStringExtra("myBook");



        setupGridView();

        }

    private void setupGridViewMyBook(){
        Log.d(TAG, "setupGridView: Setting up image grid.");

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
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getId_book().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Book.class));

                        availability.setText(book.getAvailability().toString());
                        name_of_book.setText(book.getBook_name().toString());

                        type.setText(book.getType().toString());
                        price.setText(book.getPrice().toString());
                        state.setText(book.getStatus().toString());
                        imageLoader= ImageLoader.getInstance();
//
                        imageLoader.displayImage( book.getImage_path(),photo,new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                if(progressBar !=null){
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                if(progressBar !=null){
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                if(progressBar !=null){
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                if(progressBar !=null){
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                        });

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}


    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");

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
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getId_book().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Book.class));
                        myBook=singleSnapshot.getKey().toString();
                 Log.d(TAG, myBook);
                    availability.setText(book.getAvailability().toString());
                    name_of_book.setText(book.getBook_name().toString());
                    number_of_course.setText(book.getCourse_id().toString());
                    type.setText(book.getType().toString());
                    typeMaterial=book.getType().toString();
                    price.setText(book.getPrice().toString());
                    state.setText(book.getStatus().toString());
                    faculty.setText(book.getFaculty().toString());
                            imageLoader= ImageLoader.getInstance();
//
                        imageLoader.displayImage( book.getImage_path(),photo,new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                if(progressBar !=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(progressBar !=null){
                    progressBar.setVisibility(View.INVISIBLE);
                }

                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(progressBar !=null){
                    progressBar.setVisibility(View.INVISIBLE);
                }

                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                if(progressBar !=null){
                    progressBar.setVisibility(View.INVISIBLE);
                }

                            }

                        });

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.cd ..
        getMenuInflater().inflate(R.menu.delete_book, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.delete:
//                try {
//                    Log.d(TAG, "onCancelled: query cancelled."+myBook);
//
//                    firebaseMethod.deleteBook(myBook);
//
//                }catch (Exception e){}
                CustomDialogDeleteClass cdd = new CustomDialogDeleteClass(ViewBookProfile.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                return true;

            case R.id.action_setting:
                if(typeMaterial.equals("TextBooks")){
                Intent intent=new Intent(ViewBookProfile.this,AddTextBook.class);
                intent.putExtra("id_book", bookId);
                startActivity(intent);
                return true;
                }else if(typeMaterial.equals("Lecture Notes")){
                    Intent intent=new Intent(ViewBookProfile.this,AddLectureNotes.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;

                }else if(typeMaterial.equals("General Books")){
                    Intent intent=new Intent(ViewBookProfile.this,AddGeneralBook.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;
                }

//            case R.id.search:
//                Intent i=new Intent(MainActivity.this, CustomListActivity.class) ;
//                startActivity(i);
//                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
    }