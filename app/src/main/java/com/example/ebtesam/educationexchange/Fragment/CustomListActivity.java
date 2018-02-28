package com.example.ebtesam.educationexchange.Fragment;

import android.app.Activity;
import android.os.Bundle;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Book;

import java.util.ArrayList;

/**
 * Created by ebtesam on 28/02/2018 AD.
 */

public class CustomListActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_book_fragment);
        populateUsersList();
    }

    private void populateUsersList() {
        // Construct the data source
        ArrayList<Book> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
//        ListAdapter adapter = new ListAdapter(this, arrayOfUsers);
//        // Attach the adapter to a ListView
//        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(adapter);
    }
}
