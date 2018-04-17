package com.example.ebtesam.educationexchange.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Request;

import java.util.List;

/**
 * Created by ebtesam on 26/02/2018 AD.
 */

public class ListAdapterRequset extends ArrayAdapter<Request> {
    String id;
    private Context context;

    public ListAdapterRequset(Context context, int resource) {
        super(context, resource);
    }


    public ListAdapterRequset(Context context, int resource, List<Request> items, String request) {
        super(context, resource, items);
        id = request;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_requset, parent, false);
        }
        final FirebaseMethod firebaseMethod = new FirebaseMethod(getContext());
        // Get the data item for this position
        final Request announcement = getItem(position);

        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.title);


        TextView text = convertView.findViewById(R.id.bookText);
        final TextView status = convertView.findViewById(R.id.status);
        TextView date = convertView.findViewById(R.id.date);
        TextView mobile = convertView.findViewById(R.id.mobile);
        TextView mobileName = convertView.findViewById(R.id.mobile_num);
        TextView emailName = convertView.findViewById(R.id.email_name);

        TextView email = convertView.findViewById(R.id.email);
        Button reject = convertView.findViewById(R.id.reject);
        Button accept = convertView.findViewById(R.id.accept);
        ImageButton send = convertView.findViewById(R.id.sendEmail);
        ImageButton call = convertView.findViewById(R.id.callUser);


        name.setText(announcement.getBook_name());
        text.setText(announcement.getText());
        status.setText(announcement.getStatus());
        date.setText(announcement.getDate());
        mobile.setText(String.valueOf(announcement.getMobile()));
        email.setText(announcement.getEmail());

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseMethod.updateStatus(getContext().getString(R.string.rejected), id);
                status.setText(R.string.rejected);
                Toast.makeText(getContext(), getContext().getString(R.string.reject_request), Toast.LENGTH_SHORT).show();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseMethod.updateStatus(getContext().getString(R.string.accepted), id);
                status.setText(R.string.accepted);
                Toast.makeText(getContext(), getContext().getString(R.string.accept_request), Toast.LENGTH_SHORT).show();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailRequest(announcement.getBook_name(), announcement.getEmail());
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Long.parseLong(announcement.getMobile())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
        if (!status.getText().equals("new")&&status.getText().equals("Accepted")) {
            reject.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);




        }else if(!status.getText().equals("new")&&status.getText().equals("Rejected")){
            reject.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);
            mobile.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            mobileName.setVisibility(View.GONE);
            emailName.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            call.setVisibility(View.GONE);
        }
        // Return the completed view to render on screen
        return convertView;
    }

    @SuppressLint("LongLogTag")
    protected void sendEmailRequest(String bookName, String email) {

        Log.i("Send email", "");
        String[] TO = {email};
//      String[] CC = {"1ebteesaam@gmailcom"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//       emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, bookName);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, );


        try {
            getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending email...", "");

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}

