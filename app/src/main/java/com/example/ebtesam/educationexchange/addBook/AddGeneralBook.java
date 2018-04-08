package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.Permissions;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.profile.MyBooksActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class AddGeneralBook extends AppCompatActivity {
    private static final String TAG = "addBookActivity";
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    public int imageCount = 0;
    ArrayAdapter<CharSequence> adapter3;
    ImageLoader imageLoader;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    //widgets
    //private EditText mCaption;
    private FirebaseMethod mFirebaseMethods;
    //vars
    private String mAppend = "file:/";
    private String imgUrl;
    private String type, bookId, myBook;
    private Context mContext = AddGeneralBook.this;
    private ImageButton takePhoto;
    private ImageView bookPhoto;
    private ViewPager mViewPager;
    private EditText editName, editPrice, numOfCourse;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private RelativeLayout relativeLayout3, relativeLayout9;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_general_book);
        setTitle(getString(R.string.add_general_activity));


        mFirebaseMethods = new FirebaseMethod(mContext);
        bookPhoto = findViewById(R.id.book_photo);
        editName = findViewById(R.id.edit_name);
        editPrice = findViewById(R.id.edit_price);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);


        setupFirebaseAuth();


        takePhoto = findViewById(R.id.photo);

        if (getIntent().getExtras() != null) {
            bookId = getIntent().getStringExtra("id_book");
            setupGridView();
            takePhoto.setVisibility(View.GONE);
        }
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionsArray(Permissions.PERMISSIONS)) {
                    Intent intent = new Intent(mContext, TakePhotoGeneralActivity.class);
                    startActivity(intent);
                } else {
                    verifyPermissions(Permissions.PERMISSIONS);
                }

            }
        });

        setImage();

    }

    private int getIndex(Spinner spinner, String v) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(v)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void setupGridView() {
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

                    if (book.getId_book().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Book.class));
                        myBook = singleSnapshot.getKey().toString();
                        Log.d(TAG, myBook);
                        editName.setText(book.getBook_name().toString());
                        editPrice.setText(book.getPrice().toString());
                        spinner1.setSelection(getIndex(spinner1, book.getStatus()));
                        spinner2.setSelection(getIndex(spinner2, book.getAvailability()));

                        imageLoader = ImageLoader.getInstance();
//
                        imageLoader.displayImage(book.getImage_path(), bookPhoto, new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {


                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {


                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {


                            }

                        });

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }


    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
//    private void setImage(){
//        Intent intent = getIntent();
//        ImageView image = (ImageView) findViewById(R.id.imageShare);
//        UnvirsalImageLoader.setImage(intent.getStringExtra(getString(R.string.selected_image)), image, null, mAppend);
//    }
    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                AddGeneralBook.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    /**
     * Check an array of permissions
     *
     * @param permissions
     * @return
     */
    public boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for (int i = 0; i < permissions.length; i++) {
            String check = permissions[i];
            if (!checkPermissions(check)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check a single permission is it has been verified
     *
     * @param permission
     * @return
     */
    public boolean checkPermissions(String permission) {
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(AddGeneralBook.this, permission);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        } else {
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }

    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
    private void setImage() {
        Bitmap bitmap;
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.selected_image))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            Log.d(TAG, "setImage: got new image url: " + imgUrl);
            UnvirsalImageLoader.setImage(imgUrl, bookPhoto, null, mAppend);
        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "setImage: got new bitmap");
            bookPhoto.setImageBitmap(bitmap);

        }


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
        Log.d(TAG, "onDataChange: image count: " + imageCount);

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

                imageCount = mFirebaseMethods.getBooksCount(dataSnapshot);
                Log.d(TAG, "onDataChange: image count: " + imageCount);

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
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                Log.d(TAG, "onClick: navigating to the final share screen.");
                //Toast.makeText(AddTextBook.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();

                String bookNmae = "";
                String price = "";
                String type = "General Books";
                String available;
                String state;


                try {
                    bookNmae = editName.getText().toString();
                    if (bookNmae.equals("")) {
                        Toast.makeText(mContext, "please enter all information", Toast.LENGTH_SHORT).show();
                        return false;
                        //                        throw new IllegalArgumentException("Book requires Name");
                    }
//                    courseId = numOfCourse.getText().toString();
//                    if (courseId.equals("")) {
//                        Toast.makeText(mContext, "please enter all information", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
                    price = editPrice.getText().toString();
                    if (price.equals("")) {
                        price = "Free";
                    }

                } catch (NullPointerException e) {

                }
                state = spinner1.getSelectedItem().toString();
                available = spinner2.getSelectedItem().toString();

                if (intent.hasExtra(getString(R.string.selected_image))) {
                    //set the new profile picture
                    FirebaseMethod firebaseMethod = new FirebaseMethod(AddGeneralBook.this);
                    firebaseMethod.uploadNewBook(getString(R.string.new_book), bookNmae, price, type, available, state, imageCount, imgUrl, null);

                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    //set the new profile picture
                    FirebaseMethod firebaseMethod = new FirebaseMethod(AddGeneralBook.this);
                    firebaseMethod.uploadNewBook(getString(R.string.new_book), bookNmae, price, type, available, state, imageCount, null, (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
                } else if (intent.hasExtra("id_book")) {
                    try {
                        Log.d(TAG, "onCancelled: query cancelled." + myBook);

                        mFirebaseMethods.updateBook(bookNmae, null, price, null, available, state, myBook);

                    } catch (Exception e) {
                    }
                    AddGeneralBook.this.finish();
                    Intent intent1 = new Intent(AddGeneralBook.this, MyBooksActivity.class);
                    startActivity(intent1);
                    return true;
                } else {
                    Toast.makeText(mContext, "please Take photo!", Toast.LENGTH_SHORT).show();
                    return false;
                }


                Intent intent1 = new Intent(AddGeneralBook.this, MainActivity.class);
                startActivity(intent1);
                AddGeneralBook.this.finish();


                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
