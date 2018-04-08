package com.example.ebtesam.educationexchange.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Report;
import com.example.ebtesam.educationexchange.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebtesam on 26/02/2018 AD.
 */

public class ListAdapterReport extends ArrayAdapter<Report> {

    String idUser;

    //int count=0;
    public ListAdapterReport(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterReport(Context context, int resource, List<Report> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_reports, parent, false);
        }

        // Get the data item for this position
        final Report user = getItem(position);

        // Lookup view for data population
        final TextView name = convertView.findViewById(R.id.name);

        final TextView email = convertView.findViewById(R.id.email);
        TextView report = convertView.findViewById(R.id.report);

        report.setText(user.getType());
        user.getId_book();
        final ArrayList<User> users = new ArrayList<>();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getContext().getString(R.string.dbname_users));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User userl = singleSnapshot.getValue(User.class);
                    if (userl.getUser_id().equals(user.getId_user())) {
                        email.setText(userl.getEmail());
                        name.setText(userl.getUsername());
                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }

    private void setupUserView() {


        final ArrayList<User> users = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getContext().getString(R.string.dbname_users));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if (user.getUser_id().equals(idUser)) {

                        users.add(singleSnapshot.getValue(User.class));
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

