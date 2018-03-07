package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Announcement;

import java.util.List;

/**
 * Created by ebtesam on 26/02/2018 AD.
 */

public class ListAdapterAnnouncement extends ArrayAdapter<Announcement> {

    public ListAdapterAnnouncement(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterAnnouncement(Context context, int resource, List<Announcement> items) {
        super(context, resource, items);
    }

//    public ListAdapter(ValueEventListener context, List<Book> items) {
//
//        super(context, 0,items);
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_announcement, parent, false);
        }

        // Get the data item for this position
        Announcement announcement = getItem(position);

        // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.title);
         TextView courseID = (TextView) convertView.findViewById(R.id.courseIdBook);
        TextView courseIDname = (TextView) convertView.findViewById(R.id.courseIdBookName);
        TextView facultyname = (TextView) convertView.findViewById(R.id.facultyname);
        TextView faculty = (TextView) convertView.findViewById(R.id.faculty);

        TextView text=(TextView)convertView.findViewById(R.id.bookText);
          TextView type=(TextView) convertView.findViewById(R.id.booktype);
        TextView date=(TextView) convertView.findViewById(R.id.date);

        name.setText(announcement.getTitle());
        text.setText(announcement.getText());
        type.setText(announcement.getType());
        date.setText(announcement.getDate_created());

        if(announcement.getCourse_id()!=null||announcement.getFaculty()!=null){
            courseID.setText(announcement.getCourse_id());
            faculty.setText(announcement.getFaculty());
        }else {
            courseID.setVisibility(View.GONE);
            courseIDname.setVisibility(View.GONE);
            faculty.setVisibility(View.GONE);
            facultyname.setVisibility(View.GONE);
        }



        // Return the completed view to render on screen
        return convertView;
    }
}

