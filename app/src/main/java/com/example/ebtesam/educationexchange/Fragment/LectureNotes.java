package com.example.ebtesam.educationexchange.Fragment;

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

import com.example.ebtesam.educationexchange.Material;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class LectureNotes extends Fragment {

    private Button comput;
    public LectureNotes(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.text_book_fragment, container,false);
        comput=rootView.findViewById(R.id.comput);
        comput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Material.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "Computer");
                bundle.putString("Type", "LectureNotes");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTextBook.class);
                Bundle bundle=new Bundle();
                bundle.putString("Faculty", "midicen");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;


    }
}
