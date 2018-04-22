package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.Material;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
import com.example.ebtesam.educationexchange.login.LoginPage;
import com.example.ebtesam.educationexchange.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private boolean type=false;
    //widgets
    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mbookname, courseIdBook, mDescription;
    private ProgressBar mProgressBar;
    private GridView gridView;

    private ImageView profileMenu;

    private Context mContext;
    private Button comput, medicine,general_course, business, science, english, all;

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
        medicine=view.findViewById(R.id.medicine);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "medicine");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        general_course=view.findViewById(R.id.general_course);
        general_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "general_course");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        business=view.findViewById(R.id.business);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "business");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        science=view.findViewById(R.id.science);
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "science");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        english=view.findViewById(R.id.english);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "english");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        all=view.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "all");
                bundle.putString("Type", "TextBooks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setupFirebaseAuth();
        setupGridView();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type==true) {
                    Intent intent = new Intent(getActivity(), AddTextBook.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), getString(R.string.you_blocked), Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;


    }

    private void setupGridView() {


        final ArrayList<User> users = new ArrayList<>();
        final FirebaseUser user1 = mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference
                .child(getString(R.string.dbname_users));
        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    if(user.getUser_id().equals(user1.getUid())){
                        type = user.getStatus().equals("Active") || user.getStatus().equals("نشط");
                    }
                    users.add(singleSnapshot.getValue(User.class));

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //............................Firebase.................................//
    private void checkCurrentUser(FirebaseUser user) {

        if (user == null) {
            Intent intent = new Intent(getActivity(), LoginPage.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth() {

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out

                }
                // ...
            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //............................end Firebase.................................//



}
