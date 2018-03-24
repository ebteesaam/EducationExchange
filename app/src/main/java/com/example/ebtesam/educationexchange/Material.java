package com.example.ebtesam.educationexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapter;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.ViewBook;
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

/**
 * Created by ebtesam on 04/03/2018 AD.
 */

public class Material extends AppCompatActivity {

    private static final String TAG = "MyBooks";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;
    //widgets
    private TextView  mbookname, courseIdBook;
    private ProgressBar mProgressBar;
    private GridView gridView;

    private ImageView profileMenu;

    private Context mContext;




    @Nullable
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_material_activity);

        setTitle(getString(R.string.matrial));
        FloatingActionButton fab = findViewById(R.id.fab);

        mbookname = findViewById(R.id.nameBook);
        courseIdBook = (TextView) findViewById(R.id.courseIdBook);

        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        gridView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
        setupFirebaseAuth();

        String intentExtra = getIntent().getStringExtra("Faculty");
        String intentExtra2 = getIntent().getStringExtra("Type");

        if (getIntent().getExtras() != null) {
            if (intentExtra.equals("Computer") && intentExtra2.equals("TextBooks")) {
                setupGridViewComputerBook();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("Computer") && intentExtra2.equals("LectureNotes")) {
                setupGridViewComputerLN();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("medicine") && intentExtra2.equals("TextBooks")) {
                setupGridViewMedicine();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("medicine") && intentExtra2.equals("LectureNotes")) {
                setupGridViewMedicineLN();
                fab.setVisibility(View.INVISIBLE);
            }else if (intentExtra.equals("general_course") ) {
                setupGridViewgeneral_course();
                fab.setVisibility(View.INVISIBLE);
            }else if (intentExtra.equals("business") && intentExtra2.equals("TextBooks")) {
                setupGridViewbusiness();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("business") && intentExtra2.equals("LectureNotes")) {
                setupGridViewbusinessLN();
                fab.setVisibility(View.INVISIBLE);
            }else if (intentExtra.equals("science") && intentExtra2.equals("TextBooks")) {
                setupGridViewscience();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("science") && intentExtra2.equals("LectureNotes")) {
                setupGridViewscienceLN();
                fab.setVisibility(View.INVISIBLE);
            }else if (intentExtra.equals("english") && intentExtra2.equals("TextBooks")) {
                setupGridViewenglish();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("english") && intentExtra2.equals("LectureNotes")) {
                setupGridViewenglishLN();
                fab.setVisibility(View.INVISIBLE);
            } if (intentExtra.equals("all") && intentExtra2.equals("TextBooks")) {
                setupGridViewAll();
                fab.setVisibility(View.INVISIBLE);
            } else if (intentExtra.equals("all") && intentExtra2.equals("LectureNotes")) {
                setupGridViewAllLN();
                fab.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setupGridViewAllLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("Lecture Notes")&& !book.getAvailability().toString().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewAll() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("TextBooks")&&!book.getAvailability().toString().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewenglishLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("Lecture Notes") && (book.getFaculty().toString().equals("Faculty of English")
                            ||book.getFaculty().toString().equals("كلية اللغة الانجليزية"))
                            && !book.getAvailability().toString().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewenglish() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("TextBooks") && (book.getFaculty().toString().equals("Faculty of English")
                            ||book.getFaculty().toString().equals("كلية اللغة الانجليزية"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewscienceLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("Lecture Notes") && (book.getFaculty().toString().equals("College of Sciences")
                            ||book.getFaculty().toString().equals("كلية العلوم والآداب"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewscience() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("TextBooks") && (book.getFaculty().toString().equals("College of Sciences")
                            ||book.getFaculty().toString().equals("كلية العلوم والآداب"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewbusinessLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("Lecture Notes") && (book.getFaculty().toString().equals("College of Business(COB)")
                            ||book.getFaculty().toString().equals("كلية إدارة الأعمال"))&&
                            !book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewbusiness() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getType().equals("TextBooks") && (book.getFaculty().toString().equals("College of Business(COB)")
                            ||book.getFaculty().toString().equals("كلية إدارة الأعمال"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupGridViewgeneral_course() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if ( book.getFaculty().toString().equals("General Course Books")||book.getFaculty().toString().equals("الكتب الدراسية العامة")
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class)
                        );
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }
    private void setupGridViewMedicineLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getType().equals("LectureNotes") && (book.getFaculty().toString().equals("Faculty Of Medicine In Rabigh")
                            ||book.getFaculty().toString().equals("كلية الطب في رابغ"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }
    private void setupGridViewMedicine() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getType().equals("TextBooks") && (book.getFaculty().toString().equals("Faculty Of Medicine In Rabigh")
                            ||book.getFaculty().toString().equals("كلية الطب في رابغ"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }


    private void setupGridViewComputerLN() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getType().equals("Lecture Notes") && (book.getFaculty().toString().equals("Faculty of Computing and Information Technology")
                            ||book.getFaculty().toString().equals("كلية الحاسبات وتقنية المعلومات"))
                            &&!book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
//                        books.get(i);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }
    private void setupGridViewComputerBook() {
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
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Book book = singleSnapshot.getValue(Book.class);

                    if (book.getType().equals("TextBooks") && (book.getFaculty().toString().equals("Faculty of Computing and Information Technology")
                            ||book.getFaculty().toString().equals("كلية الحاسبات وتقنية المعلومات"))&&
                            !book.getAvailability().equals("Blocked")&& !book.getAvailability().equals("Pied")) {
                        books.add(singleSnapshot.getValue(Book.class));
                    }
                }

                ListAdapter adapter = new ListAdapter(Material.this, R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Material.this, ViewBook.class);
                        String item =  books.get(i).getId_book();
                        intent.putExtra("id_book", item);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
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
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }


}

