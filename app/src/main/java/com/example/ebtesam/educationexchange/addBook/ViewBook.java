package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
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
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.CustomDialogClass;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.models.User;
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

public class ViewBook extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static String id_material;
    TabLayout tabLayout;
    String bookId, myBook, id_user, email, bookName;

    ImageLoader imageLoader;
    private Context mContext = ViewBook.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethod mFirebaseMethods;
    private TextView availability, price, state, number_of_course, name_of_book, faculty, type, facultyname, number_of_coursename;
    private ImageView photo;
    private ProgressBar progressBar;
    private Button requestBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_page);
        setTitle(getString(R.string.book_details));
        mAuth = FirebaseAuth.getInstance();

        bookId=getIntent().getStringExtra("id_book");
        mFirebaseMethods = new FirebaseMethod(mContext);

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

        requestBook=findViewById(R.id.request_book);
        setupGridView();
        
        requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_user.equals(mAuth.getInstance().getCurrentUser().getUid())){
                    //Log.d(ViewBook.this.toString(), "View: not mine."+mAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(mContext, getString(R.string.request_your_book), Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(ViewBook.this.toString(), "View: not mine."+mAuth.getInstance().getCurrentUser().getUid());

                    mFirebaseMethods.RequestBook(id_user,bookName,id_material,email);
                    Log.d(ViewBook.this.toString(), "View:  mine.");
                }
            }
        });



        }


    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");
        final String e[] = new String[1];
        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
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
                    id_material=book.getId_book();
id_user=book.getUser_id();
                     e[0]=book.getUser_id();
                    bookName=book.getBook_name();
//                        User user = singleSnapshot.getValue(User.class);
//                        if(user.getUser_id().equals(e[0])) {
//                            email = user.getEmail();
//
//
//
//                        }

                    availability.setText(book.getAvailability().toString());
                    name_of_book.setText(book.getBook_name().toString());
                    number_of_course.setText(book.getCourse_id().toString());
                    type.setText(book.getType().toString());
                    price.setText(book.getPrice().toString());
                    state.setText(book.getStatus().toString());
                    faculty.setText(book.getFaculty().toString());
//                        final String[] email = new String[1];
//                        Query query = reference
//                                .child(getString(R.string.dbname_users));
//                        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                                    User user = singleSnapshot.getValue(User.class);
//                                    if(user.getUser_id().equals(bookId)) {
//                                        Id = user.getEmail();
//                                        email[0] =Id;
//                                        Log.d(AnnouncementList.this.toString(), "id." + Id);
//                                    }
//                                }
//                                intent.putExtra("id_email",Id);
//                                Log.d(AnnouncementList.this.toString(), "id.2" + Id);
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
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
                Query query1 = reference
                        .child(getString(R.string.dbname_users));
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            User user = singleSnapshot.getValue(User.class);
                            if (user.getUser_id().equals(e[0])) {
                                email = user.getEmail();


                            }
                        }
                        Log.d(TAG, "View:."+email);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_report:
                if(id_user.equals(mAuth.getCurrentUser().getUid())){
                    Toast.makeText(mContext, getString(R.string.you_report), Toast.LENGTH_SHORT).show();
                }else {
                CustomDialogClass cdd = new CustomDialogClass(ViewBook.this, email);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();}

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.report, menu);
        return true;
    }

}