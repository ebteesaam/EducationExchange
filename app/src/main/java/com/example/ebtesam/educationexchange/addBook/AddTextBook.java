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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.Utils.Permissions;
import com.example.ebtesam.educationexchange.Utils.UnvirsalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTextBook extends AppCompatActivity {
    private static final String TAG = "addBookActivity";
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    public int imageCount = 0;
    ArrayAdapter<CharSequence> adapter3 ;
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
    private Context mContext = AddTextBook.this;
    private ImageButton takePhoto;
    private ImageView bookPhoto;
    private ViewPager mViewPager;
    private EditText editName, editPrice, numOfCourse;
    private Spinner spinner1, spinner2,spinner3,spinner4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text_book);
        setTitle(getString(R.string.add_book_activity));

        mFirebaseMethods = new FirebaseMethod(mContext);
        bookPhoto = findViewById(R.id.book_photo);
        editName = findViewById(R.id.edit_name);
        editPrice = findViewById(R.id.edit_price);
        numOfCourse = findViewById(R.id.numberOfCourse);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        spinner3=findViewById(R.id.spinner3);
        spinner4=findViewById(R.id.spinnermajorcourse);
        //mCaption = (EditText) findViewById(R.id.caption) ;
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner1.getSelectedItem().equals("Faculty of Computing and Information Technology")){
                    adapter3 = ArrayAdapter.createFromResource(AddTextBook.this,R.array.major_course_faculty_of_computing_and_information_technology, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    adapter3.notifyDataSetChanged();;

                    spinner4.setAdapter(adapter3);
                }else if(spinner1.getSelectedItem().equals("Faculty Of Medicine In Rabigh")){

                    adapter3 = ArrayAdapter.createFromResource(AddTextBook.this,R.array.major_course_faculty_of_medicine_in_rabigh, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter3.notifyDataSetChanged();;
                    spinner4.setAdapter(adapter3);}

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        setupFirebaseAuth();


        takePhoto = findViewById(R.id.photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionsArray(Permissions.PERMISSIONS)) {
                    Intent intent = new Intent(mContext, TakePhotoActivity.class);
                    startActivity(intent);
                } else {
                    verifyPermissions(Permissions.PERMISSIONS);
                }

            }
        });
        setImage();

    }



    private void setBookData() {
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
                AddTextBook.this,
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

        int permissionRequest = ActivityCompat.checkSelfPermission(AddTextBook.this, permission);

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
            bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "setImage: got new bitmap");
            bookPhoto.setImageBitmap(bitmap);

        }
//        imgUrl = intent.getStringExtra(getString(R.string.selected_image));
//        UnvirsalImageLoader.setImage(imgUrl, bookPhoto, null, mAppend);

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
                //String caption = "nothing".toString();
                String courseId = "";
                String bookNmae = "";
                String price = "";
                String type;
                String available;
                String state;


                try {
                    bookNmae = editName.getText().toString();
                    if (bookNmae.equals("")) {
                        Toast.makeText(mContext, "please enter all information", Toast.LENGTH_SHORT).show();
                        return false;
                        //                        throw new IllegalArgumentException("Book requires Name");
                    }
                    courseId = numOfCourse.getText().toString();
                    if (courseId.equals("")) {
                        Toast.makeText(mContext, "please enter all information", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    price = editPrice.getText().toString();

                } catch (NullPointerException e) {

                }
                type=spinner1.getSelectedItem().toString();
                available=spinner3.getSelectedItem().toString();
                state=spinner2.getSelectedItem().toString();
                courseId=spinner4.getSelectedItem().toString();

                if (intent.hasExtra(getString(R.string.selected_image))) {
                    //set the new profile picture
                    FirebaseMethod firebaseMethod = new FirebaseMethod(AddTextBook.this);
                    firebaseMethod.uploadNewBook(getString(R.string.new_book), bookNmae, courseId, price,type,available,state, imageCount, imgUrl, null);

                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    //set the new profile picture
                    FirebaseMethod firebaseMethod = new FirebaseMethod(AddTextBook.this);
                    firebaseMethod.uploadNewBook(getString(R.string.new_book), bookNmae, courseId, price,type,available,state, imageCount, null, (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
                } else {
                    Toast.makeText(mContext, "please Take photo!", Toast.LENGTH_SHORT).show();
                    return false;
                }


                Intent intent1 = new Intent(AddTextBook.this, MainActivity.class);
                startActivity(intent1);
                AddTextBook.this.finish();


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
