package com.example.ebtesam.educationexchange.profile;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ebtesam.educationexchange.Fragment.GalleryProfileFragment;
import com.example.ebtesam.educationexchange.Fragment.PhotoProfileFragment;
import com.example.ebtesam.educationexchange.Fragment.SectionsPagerAdapter;
import com.example.ebtesam.educationexchange.R;
import com.example.ebtesam.educationexchange.addBook.AddTextBook;

public class TakeProfilePhotoActivity extends AppCompatActivity {
    private static final String TAG = "TakePhotoActivity";
    AddTextBook addTextBook;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_photo);
        setTitle(getString(R.string.choose_photo));
        setupViewPager();
    }

    /**
     +     * return the current tab number
     +     * 0 = GalleryFragment
     +     * 1 = PhotoFragment
     +     * @return
     +     */
    public int getCurrentTabNumber() {
        return mViewPager.getCurrentItem();
    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter =  new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryProfileFragment());
        adapter.addFragment(new PhotoProfileFragment());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));

    }

    /**
     * check a single permission is it has been verified
     * @param permission
     * @return
     */
    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission");

        int permissionRequest= ActivityCompat.checkSelfPermission(TakeProfilePhotoActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n permissin was not granted for: "+permission);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.take_photo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_next:
                Log.d(TAG, "onClick: navigating to the next share screen.");

//                    Intent intent = new Intent(TakeProfilePhotoActivity.this, EditProfile.class);
//                    intent.putExtra(getString(R.string.selected_image), GalleryProfileFragment);
//                    intent.putExtra(getString(R.string.return_to_fragment), getString(R.string.edit_profile));
//                    startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }}
