package com.example.ebtesam.educationexchange.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ebtesam.educationexchange.R;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);//

        Button editProfile=findViewById(R.id.edit_user);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,EditProfile.class);
                startActivity(intent);
            }
        });

        setTitle(getString(R.string.profile));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
