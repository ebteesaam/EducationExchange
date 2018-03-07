package com.example.ebtesam.educationexchange.announcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnouncementGeneralActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter3 ;
    private Spinner spinner1,spinner4,spinner5;
    private EditText title,text;
    private Context mContext = AnnouncementGeneralActivity.this;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;
    private int imageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_general);
        setTitle(getString(R.string.add_announcement));

        text=findViewById(R.id.edit_text);
        title=findViewById(R.id.edit_title);

        spinner5=findViewById(R.id.type);
        mFirebaseMethods = new FirebaseMethod(mContext);






setupFirebaseAuth();

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

                imageCount = mFirebaseMethods.getAnnouncementCount(dataSnapshot);
                Log.d(AnnouncementGeneralActivity.this.toString(), "onDataChange: image count: " + imageCount);


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
                //Toast.makeText(AddTextBook.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                //String caption = "nothing".toString();
                String courseId = "";
                String bookNmae = "";
                String price = "";
                String type;
                String faculty;
                String text1="";
                String state;


                try {
                    bookNmae = title.getText().toString();
                    if (bookNmae.equals("")) {
                        Toast.makeText(AnnouncementGeneralActivity.this, "please enter all information", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                        text1 = text.getText().toString();
                        if (text1.equals("")) {
                            Toast.makeText(AnnouncementGeneralActivity.this, "please enter all information", Toast.LENGTH_SHORT).show();
                            return false;
                        //                        throw new IllegalArgumentException("Book requires Name");
                    }

                } catch (NullPointerException e) {

                }

                type=getString(R.string.general_book).toString();


                    //set the new profile picture
                    FirebaseMethod firebaseMethod = new FirebaseMethod(AnnouncementGeneralActivity.this);
                    firebaseMethod.addAnnouncementToDatabase( bookNmae,type, text1,"active",imageCount);

                    //set the new profile picture
//                    FirebaseMethod firebaseMethods = new FirebaseMethod(AnnouncementActivity.this);
//                    firebaseMethod.uploadNewBook(getString(R.string.new_book), bookNmae, courseId, price,faculty,type, available,state, imageCount, null, (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
//



                Intent intent1 = new Intent(AnnouncementGeneralActivity.this, MainActivity.class);
                startActivity(intent1);
                AnnouncementGeneralActivity.this.finish();


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
