package com.example.ebtesam.educationexchange.addBook;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ebtesam.educationexchange.Fragment.All;
import com.example.ebtesam.educationexchange.Fragment.LectureNotes;
import com.example.ebtesam.educationexchange.Fragment.TextBook;
import com.example.ebtesam.educationexchange.R;

/**
 * Created by ebtesam on 29/01/2018 AD.
 */

public class AdapterFragment extends FragmentPagerAdapter {

    private Context mContext;
    public AdapterFragment(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new All();
        } else if (position == 1) {
            return new LectureNotes();
        } else  {
            return new TextBook();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.all);
        } else if (position == 1) {
            return mContext.getString(R.string.lecture_notes);
        } else {
            return mContext.getString(R.string.text_book);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
