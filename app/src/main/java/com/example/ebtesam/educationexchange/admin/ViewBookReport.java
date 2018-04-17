package com.example.ebtesam.educationexchange.admin;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.CustomDialogBlocktClass;
import com.example.ebtesam.educationexchange.Utils.CustomDialogDeleteClass;
import com.example.ebtesam.educationexchange.Utils.CustomDialogIgnoreClass;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.AddGeneralBook;
import com.example.ebtesam.educationexchange.addBook.AddLectureNotes;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.models.Report;
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

public class ViewBookReport extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static String myBook, Idemail;
    TabLayout tabLayout;
    String bookId, typeMaterial;
    ImageLoader imageLoader;
    private Context mContext = ViewBookReport.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView availability, price, state, number_of_course, name_of_book, faculty, type, facultyname, number_of_coursename;
    private ImageView photo;
    private ProgressBar progressBar;
    private FirebaseMethod firebaseMethod;
    private Button b1;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        setTitle(getString(R.string.book_details));
        firebaseMethod = new FirebaseMethod(ViewBookReport.this);

        bookId = getIntent().getStringExtra("id_book");

        availability = findViewById(R.id.availability);
        price = findViewById(R.id.price);
        state = findViewById(R.id.state);
        faculty = findViewById(R.id.faculty);
        progressBar = findViewById(R.id.ProgressBar);

        type = findViewById(R.id.type_of_book);
        number_of_course = findViewById(R.id.number_of_course);
        name_of_book = findViewById(R.id.name_of_book);
        photo = findViewById(R.id.relative1);
        facultyname = findViewById(R.id.facultyname);
        number_of_coursename = findViewById(R.id.number_of_coursename);


        listView = findViewById(R.id.listReport);


        setupGridView();
        setupAnnouncementView();

    }

    private void setupAnnouncementView() {


        final ArrayList<Report> books = new ArrayList<>();
        final ArrayList<User> users = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final String[] d = new String[1];

        final String[] id = new String[1];
        Query query = reference
                .child(getString(R.string.dbname_report)).child(bookId);
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Report book = singleSnapshot.getValue(Report.class);
                    User user = singleSnapshot.getValue(User.class);
                    Log.d(TAG, "s.  "+singleSnapshot.getKey());

                    books.add(singleSnapshot.getValue(Report.class));

                }
                ListAdapterReport adapter = new ListAdapterReport(ViewBookReport.this, R.layout.reports, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");
        final String[] e = new String[1];

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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getId_book().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Book.class));
                        myBook = singleSnapshot.getKey().toString();
                        Log.d(TAG, "."+ myBook);

                        Log.d(TAG, myBook);
                        availability.setText(book.getAvailability().toString());
                        name_of_book.setText(book.getBook_name().toString());
                        if (book.getCourse_id() != null) {
                            number_of_course.setText(book.getCourse_id().toString());
                            faculty.setText(book.getFaculty().toString());

                        } else {
                            number_of_course.setVisibility(View.GONE);
                            number_of_coursename.setVisibility(View.GONE);
                            faculty.setVisibility(View.GONE);
                            facultyname.setVisibility(View.GONE);
                        }

                        e[0] = book.getUser_id();
                        type.setText(book.getType().toString());
                        typeMaterial = book.getType().toString();
                        price.setText(book.getPrice().toString());
                        state.setText(book.getStatus().toString());
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
                Query query = reference
                        .child(getString(R.string.dbname_users));
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            User user = singleSnapshot.getValue(User.class);
                            if (user.getUser_id().equals(e[0])) {
                                Idemail = user.getEmail();


                            }
                        }


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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.cd ..
        getMenuInflater().inflate(R.menu.delete_book, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
//        final FirebaseUser user = mAuth.getCurrentUser();

        MenuItem block = menu.findItem(R.id.block);

        MenuItem setting = menu.findItem(R.id.action_setting);
        MenuItem ignore = menu.findItem(R.id.ignore);


        if (MainActivity.type1 == true) {

            block.setVisible(false);
            ignore.setVisible(false);
        } else {
            setting.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.delete:

                CustomDialogDeleteClass cdd = new CustomDialogDeleteClass(ViewBookReport.this, Idemail);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                return true;

            case R.id.action_setting:
                if (typeMaterial.equals("TextBooks")) {
                    Intent intent = new Intent(ViewBookReport.this, AddTextBook.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;
                } else if (typeMaterial.equals("Lecture Notes")) {
                    Intent intent = new Intent(ViewBookReport.this, AddLectureNotes.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;

                } else if (typeMaterial.equals("General Books")) {
                    Intent intent = new Intent(ViewBookReport.this, AddGeneralBook.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;
                }

            case R.id.block:
                CustomDialogBlocktClass cd = new CustomDialogBlocktClass(ViewBookReport.this, Idemail, (String) name_of_book.getText());
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
                return true;
            case R.id.ignore:
                CustomDialogIgnoreClass f = new CustomDialogIgnoreClass(ViewBookReport.this, Idemail, bookId, (String) name_of_book.getText());
                f.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                f.show();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}