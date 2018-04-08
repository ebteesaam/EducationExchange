package com.example.ebtesam.educationexchange.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapterAnnouncement;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.profile.ViewMyAnnouncement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnnouncementList extends AppCompatActivity {

    public static String bookId, Id;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);
        Id = "";
        listView = findViewById(R.id.users);
        setupAnnouncementView();
    }

    private void setupAnnouncementView() {


        final ArrayList<Announcement> books = new ArrayList<>();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_announcement));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Announcement book = singleSnapshot.getValue(Announcement.class);
                    bookId = book.getUser_id();
                    User user = singleSnapshot.getValue(User.class);
                    if (user.getUser_id().equals(bookId)) {
                        Id = user.getEmail();

                        Log.d(AnnouncementList.this.toString(), "id." + Id);
                    }
                    books.add(singleSnapshot.getValue(Announcement.class));

                }

                ListAdapterAnnouncement adapter = new ListAdapterAnnouncement(AnnouncementList.this, R.layout.users_list, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final Intent intent = new Intent(AnnouncementList.this, ViewMyAnnouncement.class);
                        String item = books.get(i).getId_announcement();
                        String id1 = books.get(i).getUser_id();
                        final String[] email = new String[1];
                        Query query = reference
                                .child(getString(R.string.dbname_users));
                        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                    User user = singleSnapshot.getValue(User.class);
                                    if (user.getUser_id().equals(bookId)) {
                                        Id = user.getEmail();
                                        email[0] = Id;
                                        Log.d(AnnouncementList.this.toString(), "id." + Id);
                                    }
                                }
                                intent.putExtra("id_email", Id);
                                Log.d(AnnouncementList.this.toString(), "id.2" + Id);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        // setupAnnouncementView2(id1);

                        String id = books.get(i).getUser_id();
                        intent.putExtra("id_announcement", item);
                        String string = email[0];
                        Log.d(AnnouncementList.this.toString(), "id.email" + string);


                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setupAnnouncementView2(final String id) {


        final ArrayList<User> users = new ArrayList<>();
        Log.d(AnnouncementList.this.toString(), "id." + id);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


        Query query = reference
                .child(getString(R.string.dbname_users));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if (user.getUser_id().equals(id)) {
                        Id = user.getEmail();
                        Log.d(AnnouncementList.this.toString(), "id." + Id);
                    }

                    users.add(singleSnapshot.getValue(User.class));


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
