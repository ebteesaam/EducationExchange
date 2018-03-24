package com.example.ebtesam.educationexchange.chatApp;

/**
 * Created by ebtesam on 21/03/2018 AD.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.models.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class MessagesActivity extends AppCompatActivity {

    private final List<User> listOfUserId = new ArrayList<>();
    private DatabaseReference userData ;
    private RecyclerView chatList;
    private LinearLayoutManager mLinearLayout2;
    private chatAdapter mAdapter2;

    private TextView ubi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_users_list);
//
//        mAdapter2 = new chatAdapter(listOfUserId);
//        chatList=(RecyclerView)findViewById(R.id.userList);
//        mLinearLayout2 = new LinearLayoutManager(this);
//        chatList.setHasFixedSize(true);
//        chatList.setLayoutManager(mLinearLayout2);
//        chatList.setAdapter(mAdapter2);
//
//        CahtActivity c = new CahtActivity();
//
//        String idUB=c.getubi();
//        boolean sent = c.getSend();
//
//        User m = new User();
//        m.setUser_id(idUB);
//        listOfUserId.add(m);
//        mAdapter2.notifyDataSetChanged();


    }




}

