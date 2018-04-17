package com.example.ebtesam.educationexchange.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.CustomDialogBlockAnnoncementClass;
import com.example.ebtesam.educationexchange.Utils.CustomDialogDeleteAnnouncementClass;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.admin.AnnouncementList;
import com.example.ebtesam.educationexchange.announcement.AnnouncementActivity;
import com.example.ebtesam.educationexchange.announcement.AnnouncementGeneralActivity;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ebtesam on 11/03/2018 AD.
 */

public class ViewMyAnnouncement extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static String myBook;
    TabLayout tabLayout;
    String bookId,  typeMaterial, emailId;
    ImageLoader imageLoader;
    private Context mContext = ViewMyAnnouncement.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView availability, price, state, number_of_course, name_of_book, faculty, type, facultyname, number_of_coursename, bookText;
    private ImageView photo;
    private ProgressBar progressBar;
    private FirebaseMethod firebaseMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_announcement);
        setTitle(getString(R.string.view_my_announcement));
        bookId = getIntent().getStringExtra("id_announcement");
        emailId = getIntent().getStringExtra("id_email");

        firebaseMethod = new FirebaseMethod(ViewMyAnnouncement.this);

//        availability = findViewById(R.id.availability);

        faculty = findViewById(R.id.faculty);

        type = findViewById(R.id.booktype);
        number_of_course = findViewById(R.id.courseIdBook);
        name_of_book = findViewById(R.id.title);
        facultyname = findViewById(R.id.facultyname);
        bookText=findViewById(R.id.bookText);
        number_of_coursename = findViewById(R.id.courseIdBookName);

setupGridView();

    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");

        final ArrayList<Announcement> books = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_announcement));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()) {
                    Announcement book = singleSnapshot.getValue(Announcement.class);

                    if (book.getId_announcement().equals(bookId)) {
                        books.add(singleSnapshot.getValue(Announcement.class));
                        myBook=singleSnapshot.getKey().toString();

//                        availability.setText(book.getAvailability().toString());
                        name_of_book.setText(book.getTitle().toString());

                        type.setText(book.getType().toString());
                        typeMaterial=book.getType().toString();

                        bookText.setText(book.getText().toString());
                        Log.d(TAG, "email.id."+ AnnouncementList.Id);

                        // state.setText(book.getStatus().toString());
                        if(book.getCourse_id()!=null||book.getFaculty()!=null){
                            number_of_course.setText(book.getCourse_id().toString());
                            faculty.setText(book.getFaculty().toString());
                        }else {
                            number_of_course.setVisibility(View.GONE);
                            faculty.setVisibility(View.GONE);
                            facultyname.setVisibility(View.GONE);
                            number_of_coursename.setVisibility(View.GONE);
                        }



                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });}

    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.cd ..
        getMenuInflater().inflate(R.menu.delete_book, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
//        final FirebaseUser user = mAuth.getCurrentUser();

        MenuItem block = menu.findItem(R.id.block);
        MenuItem setting = menu.findItem(R.id.action_setting);


        if (MainActivity.type1 == true) {

            block.setVisible(false);
        }else {
            setting.setVisible(false);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.delete:

                if (MainActivity.type1 == true){
                CustomDialogDeleteAnnouncementClass cdd = new CustomDialogDeleteAnnouncementClass(ViewMyAnnouncement.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();}else {
                    CustomDialogDeleteAnnouncementClass cdd = new CustomDialogDeleteAnnouncementClass(ViewMyAnnouncement.this, AnnouncementList.Id,  (String) name_of_book.getText());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }
                return true;

            case R.id.action_setting:

                    if(typeMaterial.equals("General Books")){
                    Intent intent=new Intent(ViewMyAnnouncement.this,AnnouncementGeneralActivity.class);
                    intent.putExtra("id_book", bookId);
                    startActivity(intent);
                    return true;
                }else {
                        Intent intent = new Intent(ViewMyAnnouncement.this, AnnouncementActivity.class);
                        intent.putExtra("id_book", bookId);
                        startActivity(intent);
                        return true;

                    }
            case R.id.block:
                CustomDialogBlockAnnoncementClass cd= new CustomDialogBlockAnnoncementClass(ViewMyAnnouncement.this, AnnouncementList.Id, (String) name_of_book.getText());
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
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