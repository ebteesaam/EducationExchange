package com.example.ebtesam.educationexchange.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.models.Announcement;
import com.example.ebtesam.educationexchange.models.Book;
import com.example.ebtesam.educationexchange.models.Report;
import com.example.ebtesam.educationexchange.models.Request;
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

    private Context mContext;

    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageReference;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethod(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public void uploadNewBook(String photoType, final String bookNmae, final String courseId, final String price, final String faculty, final String type, final String available, final String state, final int count, final String imgUrl, Bitmap bm) {
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if (photoType.equals(mContext.getString(R.string.new_book))) {
            Log.d(TAG, "uploadNewPhoto: uploading NEW book photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/book" + (count + 1));

            //convert image url to bitmap
            if (bm == null) {
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
                    addBookToDatabase(bookNmae, courseId, price, faculty, type, available, state, firebaseUrl.toString(), count);

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

                    if (progress - 15 > mPhotoUploadProgress) {
                        Toast.makeText(mContext, " upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });


        }
    }

    public void uploadNewBook(String photoType, final String bookNmae, final String price,
                              final String type, final String available, final String state, final int count, final String imgUrl, Bitmap bm) {
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if (photoType.equals(mContext.getString(R.string.new_book))) {
            Log.d(TAG, "uploadNewPhoto: uploading NEW book photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/book" + (count + 1));

            //convert image url to bitmap
            if (bm == null) {
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
                    addBookToDatabase(bookNmae, price, type, available, state, firebaseUrl.toString(), count);

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

                    if (progress - 15 > mPhotoUploadProgress) {
                        Toast.makeText(mContext, " upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });


        }
    }

    //case new profile photo
    public void uploadNewProfilePhoto(String photoType, final String caption, final int count, final String imgUrl, Bitmap bm) {

        Log.d(TAG, "uploadNewPhoto: uploading new PROFILE photo");
        FilePaths filePaths = new FilePaths();

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = mStorageReference
                .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

        //convert image url to bitmap
        if (bm == null) {
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

                if (progress - 15 > mPhotoUploadProgress) {
                    Toast.makeText(mContext, " upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                    mPhotoUploadProgress = progress;
                }

                Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
            }
        });
    }


    private void setProfilePhoto(String url) {
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T  'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Riyadh"));
        return sdf.format(new Date());
    }


    public void addAnnouncementToDatabase(String bookNmae, String courseId, String faculty, String type, String text1, String state, int count) {

        String newAnnouncementKey = myRef.push().getKey();
        Announcement book = new Announcement();
        book.setTitle(bookNmae);
        book.setCourse_id(courseId);
        book.setStatus(state);
        book.setType(type);
        book.setText(text1);
        book.setDate_created(getTimestamp());
        book.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        book.setId_announcement(newAnnouncementKey);
        book.setFaculty(faculty);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_announcement))
                .child("announcement" + (count + 1)).setValue(book);

    }

    public void addAnnouncementToDatabase(String bookNmae, String type, String text1, String state, int count) {

        String newAnnouncementKey = myRef.push().getKey();
        Announcement book = new Announcement();
        book.setTitle(bookNmae);
        book.setStatus(state);
        book.setType(type);
        book.setText(text1);
        book.setDate_created(getTimestamp());
        book.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        book.setId_announcement(newAnnouncementKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_announcement))
                .child("announcement" + (count + 1)).setValue(book);

    }

    public int getAnnouncementCount(DataSnapshot dataSnapshot) {
        int count = 0;
        //BookCount=count;
        for (DataSnapshot ds : dataSnapshot
                .child(mContext.getString(R.string.dbname_announcement))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()) {
            count++;
        }
        return count;
    }


    private void addBookToDatabase(String bookNmae, String courseId, String price, String faculty, String type, String available, String state, String url, int count) {
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        //String tags = StringManipulation.getTags(caption);
        String newBookKey = myRef.push().getKey();
        Book book = new Book();
        book.setBook_name(bookNmae);
        book.setCourse_id(courseId);
        book.setAvailability(available);
        book.setStatus(state);
        book.setPrice(price);
        book.setType(type);
        book.setFaculty(faculty);

        //book.setDate_created(getTimestamp());
        book.setImage_path(url);
        //photo.setTags(tags);
        book.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        book.setId_book(newBookKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_material))
                .child("book" + (count + 1)).setValue(book);
        // myRef.child(mContext.getString(R.string.dbname_photos)).setValue(book);

    }

    private void addBookToDatabase(String bookNmae, String price, String type, String available, String state, String url, int count) {
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        //String tags = StringManipulation.getTags(caption);
        String newBookKey = myRef.push().getKey();
        Book book = new Book();
        book.setBook_name(bookNmae);
        book.setAvailability(available);
        book.setStatus(state);
        book.setPrice(price);
        book.setType(type);

        //book.setDate_created(getTimestamp());
        book.setImage_path(url);
        //photo.setTags(tags);
        book.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        book.setId_book(newBookKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_material))
                .child("book" + (count + 1)).setValue(book);
        // myRef.child(mContext.getString(R.string.dbname_photos)).setValue(book);

    }


    public int getBooksCount(DataSnapshot dataSnapshot) {
        int count = 0;
        //BookCount=count;
        for (DataSnapshot ds : dataSnapshot
                .child(mContext.getString(R.string.dbname_material))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()) {
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

                        } else if (task.isSuccessful()) {

                            //send verificaton email
                            sendVerificationEmail();

                            userID = mAuth.getCurrentUser().getUid();
                        }

                    }
                });
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void addNewUser(String email, String username, String password, String profile_photo, String status, String report) {
        User user = new User(StringManipulation.condenseUsername(username), userID, password, email, profile_photo, "active", report, "Student");

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

    }

    /**
     * Retrieves the account settings for teh user currently logged in
     * Database: user_acount_Settings node
     *
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");


        //UserAccountSettings settings  = new UserAccountSettings();
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            // users node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
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
     *
     * @param username
     */

    public void updateUsername(String username) {
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

    }

    /**
     * update the email in the 'user's' node
     *
     * @param email
     */
    public void updateEmail(String email) {

        Log.d(TAG, "updateUsername: upadting username to: " + email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);


    }

    public void updateAvailability(String book, String email, String bookName) {

        myRef.child(mContext.getString(R.string.dbname_material))
                .child(book)
                .child("availability").setValue("Blocked");
        sendEmail("Book", email, bookName);
        Toast.makeText(mContext, mContext.getString(R.string.block_matreial), Toast.LENGTH_LONG).show();

    }

    public void deleteBook(String book, String email) {


        myRef.child(mContext.getString(R.string.dbname_material))
                .child(book).removeValue();
        //sendEmailD("Book", email);
        Toast.makeText(mContext, mContext.getString(R.string.deleteBook), Toast.LENGTH_SHORT).show();
    }

    public void deleteReport(String book, String email, String bookId, String BookName) {


        myRef.child(mContext.getString(R.string.dbname_report))
                .child(bookId).removeValue();
        Toast.makeText(mContext, mContext.getString(R.string.ignoreReport), Toast.LENGTH_SHORT).show();
    }

    public void deleteBook(String book) {


        myRef.child(mContext.getString(R.string.dbname_material))
                .child(book).removeValue();
        Toast.makeText(mContext, mContext.getString(R.string.deleteBook), Toast.LENGTH_SHORT).show();
    }


    public void removeAnnouncement(String book, String email, String bookName) {


        myRef.child(mContext.getString(R.string.dbname_announcement))
                .child(book).removeValue();
        sendEmailD("Announcement", email, bookName);
        Toast.makeText(mContext, mContext.getString(R.string.deleteAnnouncement), Toast.LENGTH_SHORT).show();


    }

    public void removeAnnouncement(String book) {


        myRef.child(mContext.getString(R.string.dbname_announcement))
                .child(book).removeValue();
        Toast.makeText(mContext, mContext.getString(R.string.deleteAnnouncement), Toast.LENGTH_SHORT).show();


    }

    protected void sendEmailD(String announcement, String email, String bn) {

        Log.i("Send email", "");
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Deleted your " + announcement + " (" + bn + ")");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "your " + announcement + " was deleted");


        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));


        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBook(String bookNmae, String courseId, String price, String faculty, String available, String state, String myBook) {
        if (bookNmae != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("book_name").setValue(bookNmae);
        }

        if (courseId != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("course_id").setValue(courseId);
        }

        if (price != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("price").setValue(price);
        }
        if (faculty != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("faculty").setValue(faculty);
        }

        if (available != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("availability").setValue(available);
        }

        if (state != null) {
            myRef.child(mContext.getString(R.string.dbname_material))
                    .child(myBook).child("status").setValue(state);
        }
        Toast.makeText(mContext, mContext.getString(R.string.success_update), Toast.LENGTH_SHORT).show();
    }


    public void updateAnnouncement(String bookNmae, String courseId, String faculty, String type, String text1, String active, String myBook) {

        if (bookNmae != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("title").setValue(bookNmae);
        }

        if (courseId != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("course_id").setValue(courseId);
        }

        if (active != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("status").setValue(active);
        }
        if (faculty != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("faculty").setValue(faculty);
        }

        if (type != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("type").setValue(type);
        }

        if (text1 != null) {
            myRef.child(mContext.getString(R.string.dbname_announcement))
                    .child(myBook).child("text").setValue(text1);
        }
        Toast.makeText(mContext, mContext.getString(R.string.success_update), Toast.LENGTH_SHORT).show();
    }

    public int getReportsCount(DataSnapshot dataSnapshot) {
        int count = 0;
        //BookCount=count;
        for (DataSnapshot ds : dataSnapshot
                .child(mContext.getString(R.string.dbname_report))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()) {
            count++;
        }
        return count;
    }

    public void reportMaterial(String s, String w, String email) {
        String report_id = myRef.push().getKey();
        Report report = new Report(s, report_id, "new", w, FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.child(mContext.getString(R.string.dbname_report))
                .child(s)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(report);
        sendEmailReport(w, email);
        Toast.makeText(mContext, mContext.getString(R.string.send_report), Toast.LENGTH_SHORT).show();
    }

    protected void sendEmailReport(String announcement, String email) {

        Log.i("Send email", "");
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report your Material by Student ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "your material was repored in Education Exchange becuase " + announcement);
    }

    public void updateUser(String type, String status, String id) {

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(id)
                .child("type").setValue(type);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(id)
                .child("status").setValue(status);
    }

    public void updateUser(String type, String status, String id, String blocked) {

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(id)
                .child("type").setValue(type);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(id)
                .child("status").setValue(status);
        sendEmailUser("Account", blocked);
    }

    protected void sendEmailUser(String announcement, String email) {

        Log.i("Send email", "");
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blocked your " + announcement);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "your " + announcement + " was block in Education Exchange");


        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));


        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAvailabilityAnnuoncement(String myBook, String email, String bookName) {

        myRef.child(mContext.getString(R.string.dbname_announcement))
                .child(myBook)
                .child("status").setValue("Blocked");
        sendEmail("Announcement", email, bookName);
        Toast.makeText(mContext, mContext.getString(R.string.block_announcement), Toast.LENGTH_LONG).show();


    }

    @SuppressLint("LongLogTag")
    protected void sendEmail(String announcement, String email, String bn) {

        Log.i("Send email", "");
        String[] TO = {email};
//      String[] CC = {"1ebteesaam@gmailcom"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//       emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blocked you " + announcement + " (" + bn + ")");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "your " + announcement + " was blocked ");


        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending email...", "");

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStatus(String status, String id) {
        myRef.child(mContext.getString(R.string.dbname_Request))
                .child(id)
                .child("status").setValue(status);

//        sendEmailRequest(bookName, email );
    }

    public void RequestBook(String bookName, String mobile, String text, String email, String id_user, String id_material, int count) {
        String request_id = myRef.push().getKey();
        long mobileNum = Long.parseLong(mobile);
        Request request = new Request(id_material, request_id, "new", bookName, id_user, mobile, email, text, getTimestamp(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.child(mContext.getString(R.string.dbname_Request))
                .child("request" + (count + 1))
                .setValue(request);
        Toast.makeText(mContext, mContext.getString(R.string.done_request), Toast.LENGTH_SHORT).show();

//        sendEmailRequest(bookName, email );


    }

    public int getRequestCount(DataSnapshot dataSnapshot) {
        int count = 0;
        //BookCount=count;
        for (DataSnapshot ds : dataSnapshot
                .child(mContext.getString(R.string.dbname_Request))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()) {
            count++;
        }
        return count;
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Request you " + bookName + " (Material)");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I want to request your " + bookName + " (Material)");


        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending email...", "");

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

