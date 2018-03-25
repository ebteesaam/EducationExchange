package com.example.ebtesam.educationexchange.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ebtesam.educationexchange.Fragment.ListAdapterAnnouncement;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.example.ebtesam.educationexchange.profile.ViewMyAnnouncement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnnouncementList extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);

        listView = findViewById(R.id.users);
setupAnnouncementView();
    }
    private void setupAnnouncementView() {


        final ArrayList<Announcement> books = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_announcement));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Announcement book = singleSnapshot.getValue(Announcement.class);

                    books.add(singleSnapshot.getValue(Announcement.class));

                }

                ListAdapterAnnouncement adapter = new ListAdapterAnnouncement(AnnouncementList.this, R.layout.users_list, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(AnnouncementList.this, ViewMyAnnouncement.class);
                        String item =  books.get(i).getId_announcement();
                        intent.putExtra("id_announcement", item);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
