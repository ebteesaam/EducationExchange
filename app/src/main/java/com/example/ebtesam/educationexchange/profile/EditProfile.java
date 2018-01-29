package com.example.ebtesam.educationexchange.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.ebtesam.educationexchange.R;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);
        setTitle(getString(R.string.edit_profile));
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
