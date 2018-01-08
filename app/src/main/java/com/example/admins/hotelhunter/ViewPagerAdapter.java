package com.example.admins.hotelhunter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Admins on 1/8/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DangNhapFragment();
            case 1:
                return new DangKyFragment();
    }
    return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
