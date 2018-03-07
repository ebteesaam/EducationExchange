package com.example.ebtesam.educationexchange.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.announcement.AnnouncementActivity;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 09/02/2018 AD.
 */

public class HomeActivity  extends Fragment {

    Button addAnnouncement;

    ListView listView;

    public HomeActivity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_activity, container,false);

        listView=rootView.findViewById(R.id.list_announcement);

        View emptyView = rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        addAnnouncement=rootView.findViewById(R.id.add_announcement);
        addAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AnnouncementActivity.class);
                startActivity(intent);

            }
        });
        setupAnnouncementView();
        return rootView;


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

                   // if (book.getType().equals("Lecture Notes") && book.getFaculty().toString().equals(getString(R.string.college_of_english))) {
                        books.add(singleSnapshot.getValue(Announcement.class));

                }

                ListAdapterAnnouncement adapter = new ListAdapterAnnouncement(getActivity(), R.layout.home_activity, books);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(Material.this, ViewBook.class);
//                        String item = books.get(i).getId_book();
//                        intent.putExtra("id_announcement", item);
////                        books.get(i);
//                        startActivity(intent);
//                    }
//                });

            } @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
