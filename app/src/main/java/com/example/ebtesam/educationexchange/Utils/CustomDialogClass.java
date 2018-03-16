package com.example.ebtesam.educationexchange.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.ViewBook;

/**
 * Created by ebtesam on 16/03/2018 AD.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button unEthical, cancel;
    FirebaseMethod mFirebaseMethods;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        mFirebaseMethods = new FirebaseMethod(getContext());

        unEthical= (Button) findViewById(R.id.unEthical);
        cancel = (Button) findViewById(R.id.cancel);
       unEthical.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unEthical:

                mFirebaseMethods.reportMaterial( ViewBook.id_material.toString());
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}