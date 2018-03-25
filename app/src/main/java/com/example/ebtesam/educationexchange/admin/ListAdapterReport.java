package com.example.ebtesam.educationexchange.admin;

import android.content.Context;
import android.util.Log;
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


    public ListAdapterReport(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterReport(Context context, int resource, List<Report> items, String id) {
        super(context, resource, items);
        idUser=id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_reports, parent, false);
        }

        // Get the data item for this position
        Report user = getItem(position);
        Log.d("adapter", "id"+idUser);

        // Lookup view for data population
                final TextView name = (TextView) convertView.findViewById(R.id.name);

        final TextView email = (TextView) convertView.findViewById(R.id.email);
         TextView report = (TextView) convertView.findViewById(R.id.report);

//        name.setText(user.getUsername());
        report.setText(user.getType());

        final ArrayList<User> users= new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getContext().getString(R.string.dbname_users));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if(user.getUser_id().equals(idUser)) {
                        Log.d("adapter", "id"+idUser+user.getEmail());

                        email.setText(user.getEmail());
                        name.setText(user.getUsername());
                    }

                }

//                ListAdapterUser adapter = new ListAdapterUser(UserList.this, R.layout.users_list, users);
//                // Attach the adapter to a ListView
//                listView.setAdapter(adapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(UserList.this, EditUserProfile.class);
//                        String item =  users.get(i).getUser_id();
//                        intent.putExtra("id_user", item);
//                        startActivity(intent);
//                    }
//                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        ImageLoader imageLoader=ImageLoader.getInstance();
//
//        imageLoader.displayImage( user.getProfile_photo(),image,new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//
//
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//
//            }
//
//        });

        // Return the completed view to render on screen
        return convertView;
    }
    private void setupUserView() {


        final ArrayList<User> users= new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getContext().getString(R.string.dbname_users));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if(user.getUser_id().equals(idUser)){

                        users.add(singleSnapshot.getValue(User.class));}
                    users.add(singleSnapshot.getValue(User.class));

                }

//                ListAdapterUser adapter = new ListAdapterUser(UserList.this, R.layout.users_list, users);
//                // Attach the adapter to a ListView
//                listView.setAdapter(adapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(UserList.this, EditUserProfile.class);
//                        String item =  users.get(i).getUser_id();
//                        intent.putExtra("id_user", item);
//                        startActivity(intent);
//                    }
//                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

