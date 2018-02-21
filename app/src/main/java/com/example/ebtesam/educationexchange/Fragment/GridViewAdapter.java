package com.example.ebtesam.educationexchange.Fragment;

import android.content.Context;

import com.example.ebtesam.educationexchange.Utils.FirebaseMethod;
import com.example.ebtesam.educationexchange.models.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ebtesam on 21/02/2018 AD.
 */

public class GridViewAdapter extends ArrayList<Book>{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod firebaseMethod;
    public GridViewAdapter(Context context) {

    }}

//    public GridViewAdapter(@NonNull Context context, ArrayList<Book> books) {
//        super((Collection<? extends Book>) context);
//
//    }
//
//    @NonNull
//    @Override
//    public View getView(@NonNull Context context,int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        View listItemView=convertView;
//        if(listItemView==null){
//            listItemView= LayoutInflater.from(context).inflate(R.layout.list, parent,false);
//        }
//Book book=g
//        Location currentLocation = getItem(position);
//
//
//        TextView name=listItemView.findViewById(R.id.name);
//
//        name.setText(currentLocation.getName());
//
//        TextView location=listItemView.findViewById(R.id.location);
//        location.setText(currentLocation.getLocation());
//
//        ImageView imageView=listItemView.findViewById(R.id.image);
//        if(currentLocation.hasImage()){
//            imageView.setImageResource(currentLocation.getImage());
//            imageView.setVisibility(View.VISIBLE);
//        }else {
//            imageView.setVisibility(View.GONE);
//        }
//
//        View textContainer=listItemView.findViewById(R.id.text_container);
//
//        int color= ContextCompat.getColor(getContext(), mColorResourceId);
//
//        textContainer.setBackgroundColor(color);
//
//        return listItemView;

//    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        TextView nameTextView = (TextView) view.findViewById(R.id.nameBook);
//        TextView summaryTextView = (TextView) view.findViewById(R.id.courseIdBook);
//        UserAccountSettings settings = userSettings.getSettings();
//Book book=
////        // Find the columns of pet attributes that we're interested in
////        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
////        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
//
//        // Read the pet attributes from the Cursor for the current pet
//        String petName = .setText(settings.getUsername());
//        String petBreed = cursor.getString(breedColumnIndex);
//// If the pet breed is empty string or null, then use some default text
//        // that says "Unknown breed", so the TextView isn't blank.
//        if (TextUtils.isEmpty(petBreed)) {
//            petBreed = context.getString(R.string.unknown_breed);
//        }
//        // Update the TextViews with the attributes for the current pet
//        nameTextView.setText(petName);
//        summaryTextView.setText(petBreed);
//
//    }
//    private void setProfileWidgets(UserSettings userSettings){
////        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
////        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());
//
//
//
//        //User user = userSettings.getUser();
//        UserAccountSettings settings = userSettings.getSettings();
//
////        StorageReference storageRef = firebaseStorage.getReference();
////        StorageReference imagesRef = storageRef.child("profile_photo");
////        mStorageRef=FirebaseStorage.getInstance().getReference();
//        UnvirsalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto,progressBar, "");
////        Glide.with(this /* context */)
////                .using(new FirebaseImageLoader())
////                .load(mStorageRef)
////                .into(mProfilePhoto);
//
//        user_name.setText(settings.getUsername());
//        email.setText(settings.getEmail());
////        mWebsite.setText(settings.getWebsite());
////        mDescription.setText(settings.getDescription());
////        mPosts.setText(String.valueOf(settings.getPosts()));
////        mFollowing.setText(String.valueOf(settings.getFollowing()));
////        mFollowers.setText(String.valueOf(settings.getFollowers()));
////        mProgressBar.setVisibility(View.GONE);
//    }
//
//
//
//}
