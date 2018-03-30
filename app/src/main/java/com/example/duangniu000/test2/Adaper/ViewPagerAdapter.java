package com.example.duangniu000.test2.Adaper;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.duangniu000.test2.data.ImageType;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;
    private List<ImageType> title;

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public void setTitle(List<ImageType> title) {
        this.title = title;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  title.get(position).getTitle();
    }
}
