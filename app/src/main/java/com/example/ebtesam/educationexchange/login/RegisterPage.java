package com.example.ebtesam.educationexchange.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ebtesam.educationexchange.MainActivity;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        Button btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterPage.this, MainActivity.class);
            }
        });
    }

}
