package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.Material;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
import com.example.ebtesam.educationexchange.models.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class TextBook extends Fragment {
    private static final String TAG = "TextBook";
    private static final int NUM_GRID_COLUMNS = 3;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethods;
    //widgets
    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mbookname, courseIdBook, mDescription;
    private ProgressBar mProgressBar;
    private GridView gridView;

    private ImageView profileMenu;

    private Context mContext;
    private Button comput;

    public TextBook(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.text_book_fragment, container,false);
        comput=view.findViewById(R.id.comput);
        comput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "Computer");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTextBook.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("Button", "TBButton");
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
     //   mDisplayName = (TextView) view.findViewById(R.id.display_name);
//        mbookname = (TextView) view.findViewById(R.id.nameBook);
//        courseIdBook = (TextView) view.findViewById(R.id.courseIdBook);
//      //  mDescription = (TextView) view.findViewById(R.id.description);
////        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
////        mPosts = (TextView) view.findViewById(R.id.tvPosts);
////        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
////        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
//        mProgressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);
//        gridView =  view.findViewById(R.id.list);
//        View emptyView = view.findViewById(R.id.empty_view);
//        gridView.setEmptyView(emptyView);
//        setupFirebaseAuth();
//        setupGridView();
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), ViewBook.class);
//                startActivity(intent);
//            }
//        });

        return view;


    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");
        // get data from the table by the ListAdapter



        final ArrayList<Book> books = new ArrayList<>();
        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersdRef = rootRef.child(getString(R.string.dbname_material));
        Query query = reference
                .child(getString(R.string.dbname_material));
             //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Book book=singleSnapshot.getValue(Book.class);
                    books.add(singleSnapshot.getValue(Book.class));
                }

                ListAdapter adapter = new ListAdapter(getActivity(),R.layout.list_material_activity, books);
                // Attach the adapter to a ListView
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }

        });}




}
