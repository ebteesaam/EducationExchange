package com.example.ebtesam.educationexchange;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ebtesam.educationexchange.addBook.AdapterFragment;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;
import com.example.ebtesam.educationexchange.login.LoginPage;
import com.example.ebtesam.educationexchange.login.RegisterPage;
import com.example.ebtesam.educationexchange.profile.ProfilePage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTextBook.class);
                startActivity(intent);
            }
        });
        ViewPager viewPager=findViewById(R.id.view_pager);

        AdapterFragment adapter=new AdapterFragment(this,getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout=findViewById(R.id.tab);

        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.cd ..
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.profile:
            Intent intent=new Intent(MainActivity.this,ProfilePage.class) ;
            startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
