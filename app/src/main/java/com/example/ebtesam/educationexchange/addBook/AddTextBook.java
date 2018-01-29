package com.example.ebtesam.educationexchange.addBook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.ebtesam.educationexchange.R;

public class AddTextBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text_book);
        setTitle(getString(R.string.add_book_activity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.save_book, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
