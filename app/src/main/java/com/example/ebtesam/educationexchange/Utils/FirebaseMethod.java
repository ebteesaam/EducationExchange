package com.example.ebtesam.educationexchange.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.models.User;
import com.example.ebtesam.educationexchange.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by ebtesam on 07/02/2018 AD.
 */

public class FirebaseMethod {


    private static final String TAG = "FirebaseMethod";

    private Context mContext ;

    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageReference;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethod(Context context){
        mAuth= FirebaseAuth.getInstance();
        mContext=context;
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        if(mAuth.getCurrentUser()!=null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public void uploadNewBook( String photoType,final String bookNmae,final String courseId ,final String price,final String  type,final String available,final String state,final int count, final String imgUrl, Bitmap bm){
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if(photoType.equals(mContext.getString(R.string.new_book))){
            Log.d(TAG, "uploadNewPhoto: uploading NEW book photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/book" + (count + 1));

            //convert image url to bitmap
            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = com.example.ebtesam.educationexchange.Utils.ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "Book upload success", Toast.LENGTH_SHORT).show();

                    //add the new photo to 'photos' node and 'user_photos' node
                    addBookToDatabase(bookNmae, courseId , price,type,available,state, firebaseUrl.toString(),count);

                    //navigate to the main feed so the user can see their photo
//                    Intent intent = new Intent(mContext, HomeActivity.class);
//                    mContext.startActivity(intent);



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure:  upload failed.");
                    Toast.makeText(mContext, " upload failed ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, " upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });


        }}
        //case new profile photo
        public void uploadNewProfilePhoto( String photoType, final String caption,final int count, final String imgUrl, Bitmap bm){

            Log.d(TAG, "uploadNewPhoto: uploading new PROFILE photo");
            FilePaths filePaths = new FilePaths();

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

            //convert image url to bitmap
            if(bm == null){
                                bm = ImageManager.getBitmap(imgUrl);
                  }
//            bm = com.example.ebtesam.educationexchange.Utils.ImageManager.getBitmap(imgUrl);
            byte[] bytes = com.example.ebtesam.educationexchange.Utils.ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();

                    //insert into 'user_account_settings' node
                    setProfilePhoto(firebaseUrl.toString());





                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure:  upload failed.");
                    Toast.makeText(mContext, " upload failed ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, " upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });
        }



    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
        }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Riyadh"));
        return sdf.format(new Date());
    }

    private void addBookToDatabase(String bookNmae, String courseId ,String price, String  type,String available,String state, String url,int count){
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        //String tags = StringManipulation.getTags(caption);
        //String newPhotoKey = myRef.child(mContext.getString(R.string.dbname_user_books)).push().getKey();
        Book book = new Book();
        book.setBook_name(bookNmae);
        book.setCourse_id(courseId);
        book.setAvailability(available);
        book.setStatus(state);
        book.setPrice(price);
        //book.set

        book.setDate_created(getTimestamp());
        book.setImage_path(url);
        //photo.setTags(tags);
        book.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        book.setPhoto_id(newPhotoKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_material))
                .child(FirebaseAuth.getInstance().getCurrentUser()
                .getUid()).child("book"+ (count+1)).setValue(book);
       // myRef.child(mContext.getString(R.string.dbname_photos)).setValue(book);

    }




    //
//    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot){
//        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");
//
//        User user = new User();
//
//        for (DataSnapshot ds: datasnapshot.child(userID).getChildren()){
//            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);
//
//            user.setUsername(ds.getValue(User.class).getUsername());
//            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());
//
//            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
//                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
//                return true;
//            }
//        }
//        return false;
//    }
    public int getBooksCount(DataSnapshot dataSnapshot){
        int  count = 0;
        //BookCount=count;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_material))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }
    public void registerNewEmail(final String email, String password, final String username) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){

                            //send verificaton email
                            sendVerificationEmail();

                            userID = mAuth.getCurrentUser().getUid();
                        }

                    }
                });
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    public void addNewUser(String email, String username,  String profile_photo, String status, String report){

        User user = new User( StringManipulation.condenseUsername(username),  userID,  email , profile_photo, status, report );

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

//        UserAccountSettings settings = new UserAccountSettings(
//                profile_photo,
//                StringManipulation.condenseUsername(username),
//                email,
//                0,
//
//
//        );

//        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                .child(userID)
//                .setValue(settings);

    }

    /**
     * Retrieves the account settings for teh user currently logged in
     * Database: user_acount_Settings node
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");


        //UserAccountSettings settings  = new UserAccountSettings();
        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
//
//            // user_account_settings node
//            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
//                Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);
//
//                try {
//
//                    settings
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getMy_book()
//                    );
//                    settings.setUsername(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getUsername()
//                    );
//                    settings.setProfile_photo(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getProfile_photo()
//                    );
//                    settings.setMy_lecture_note(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getMy_lecture_note()
//                    );
//                    settings.setEmail(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getEmail()
//                    );
//
//                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
//                } catch (NullPointerException e) {
//                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage());
//                }


                // users node
                if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                    Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);

                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );
                    user.setEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getEmail()
                    );
                    user.setUser_id(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUser_id()
                    );
                    user.setProfile_photo(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getProfile_photo()
                    );
                    user.setReport(
                            ds.child(userID)
                            .getValue(User.class)
                            .getReport()
                    );
                    user.setStatus(
                            ds.child(userID)
                                    .getValue(User.class)
                                  .getStatus()
                    );



                    Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
                }

        }
        return new UserSettings(user);

    }

    /**
     * update username in the 'users' node and 'user_account_settings' node
     * @param username
     */

    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

//        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                .child(userID)
//                .child(mContext.getString(R.string.field_username))
//                .setValue(username);
    }

    /**
     * update the email in the 'user's' node
     * @param email
     */
    public void updateEmail(String email) {

        Log.d(TAG, "updateUsername: upadting username to: " + email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);


    }

    public void updateUserAccountSettings(String displayName, String website, String description, long phoneNumber){

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");

//        if(displayName != null){
//            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                    .child(userID)
//                    .child(mContext.getString(R.string.field_display_name))
//                    .setValue(displayName);
//        }
//
//
//        if(website != null) {
//            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                    .child(userID)
//                    .child(mContext.getString(R.string.field_website))
//                    .setValue(website);
//        }
//
//        if(description != null) {
//            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                    .child(userID)
//                    .child(mContext.getString(R.string.field_description))
//                    .setValue(description);
//        }
//
//        if(phoneNumber != 0) {
//            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
//                    .child(userID)
//                    .child(mContext.getString(R.string.field_phone_number))
//                    .setValue(phoneNumber);
//        }
    }
}
